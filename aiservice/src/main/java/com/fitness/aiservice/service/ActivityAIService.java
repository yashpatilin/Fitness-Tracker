package com.fitness.aiservice.service;

import org.springframework.stereotype.Service;

import com.fitness.aiservice.models.Activity;
import com.fitness.aiservice.models.Recommendation;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.UUID;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ActivityAIService {

    private final GeminiService geminiService;

    public Recommendation generateRecommendation(Activity activity) {
        String prompt = createPromptForActivity(activity);
        String aiResponse = geminiService.getRecommendation(prompt);
        log.info("Response from AI {}", aiResponse);
        return processAIResponse(aiResponse, activity);
    }

    private Recommendation processAIResponse(String aiResponse, Activity activity) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(aiResponse);
            JsonNode textNode = root.path("candidates").get(0).path("content").path("parts").get(0).path("text");

            String jsonContent = textNode.asText()
                    .replace("```json", "")
                    .replace("```", "")
                    .replace("\\n", "\n")
                    .trim();

            JsonNode analysisJson = mapper.readTree(jsonContent);
            JsonNode analysisNode = analysisJson.path("analysis");

            // Build full analysis string
            StringBuilder fullAnalysis = new StringBuilder();
            addAnalysisSection(fullAnalysis, analysisNode, "overall", "Overall:");
            addAnalysisSection(fullAnalysis, analysisNode, "pace", "Pace:");
            addAnalysisSection(fullAnalysis, analysisNode, "heartRate", "Heart Rate:");
            addAnalysisSection(fullAnalysis, analysisNode, "caloriesBurned", "Calories Burned:");

            // Extract each section as a plain String
            String improvements = extractImprovements(analysisJson.path("improvements"));
            String suggestions  = extractSuggestions(analysisJson.path("suggestions"));
            String safety       = extractSafety(analysisJson.path("safety"));

            // Build and return the Recommendation entity
            return Recommendation.builder()
                    .Id(UUID.randomUUID().toString())
                    .activityId(activity.getId())
                    .userId(activity.getUserId())
                    .recommendation(fullAnalysis.toString().trim())
                    .improvements(improvements)
                    .suggestions(suggestions)
                    .safety(safety)
                    .build();

        } catch (Exception e) {
            log.error("Error processing AI response: {}", e.getMessage());
        }
        return null;
    }

    // ── Improvements ──────────────────────────────────────────────────────────
    // Gemini shape: [{ "area": "...", "recommendation": "..." }, ...]
    private String extractImprovements(JsonNode improvementsNode) {
        StringBuilder improvements = new StringBuilder();

        if (improvementsNode.isArray()) {
            improvementsNode.forEach(item -> {
                String area           = item.path("area").asText();
                String recommendation = item.path("recommendation").asText();
                improvements.append(String.format("%s: %s\n", area, recommendation));
            });
        }

        return improvements.isEmpty()
                ? "No specific improvements identified. Focus on maintaining consistency and gradually increasing intensity."
                : improvements.toString().trim();
    }

    // ── Suggestions ───────────────────────────────────────────────────────────
 
    private String extractSuggestions(JsonNode suggestionsNode) {
        StringBuilder suggestions = new StringBuilder();

        if (suggestionsNode.isArray()) {
            suggestionsNode.forEach(item -> {
                String workout     = item.path("workout").asText();
                String description = item.path("description").asText(); // fixed field name
                suggestions.append(String.format("%s: %s\n", workout, description));
            });
        }

        return suggestions.isEmpty()
                ? "No specific next workouts identified. Focus on maintaining consistency and gradually increasing intensity."
                : suggestions.toString().trim();
    }

    // ── Safety ───────────────────────────────────────────────────────────────
    // Gemini shape: [ "Safety point 1", "Safety point 2", ... ]  ← plain strings
    private String extractSafety(JsonNode safetyNode) {
        StringBuilder safety = new StringBuilder();
        
        if (safetyNode.isArray()) {
            safetyNode.forEach(item -> safety.append(item.asText()).append("\n"));
        }

        return safety.isEmpty()
                ? "No specific safety guidelines provided. Always warm up and cool down properly."
                : safety.toString().trim();
    }

    // ── Helpers ───────────────────────────────────────────────────────────────
    private void addAnalysisSection(StringBuilder fullAnalysis, JsonNode analysisNode,
                                    String key, String prefix) {
        if (!analysisNode.path(key).isMissingNode()) {
            fullAnalysis.append(prefix).append("\n")
                        .append(analysisNode.path(key).asText()).append("\n\n");
        }
    }

    private String createPromptForActivity(Activity activity) {
        return String.format("""
                Analyze this fitness activity and provide detailed recommendations in the following EXACT JSON format:

                {
                  "analysis": {
                    "overall": "Overall analysis here",
                    "pace": "Pace analysis here",
                    "heartRate": "Heart rate analysis here",
                    "caloriesBurned": "Calories analysis here"
                  },
                  "improvements": [
                    {
                      "area": "Area name",
                      "recommendation": "Detailed recommendation"
                    }
                  ],
                  "suggestions": [
                    {
                      "workout": "Workout name",
                      "description": "Detailed workout description"
                    }
                  ],
                  "safety": [
                    "Safety point 1",
                    "Safety point 2"
                  ]
                }

                Analyze this activity:

                Activity Type: %s
                Duration: %f minutes
                Calories Burned: %d
                Additional Metrics: %s

                Provide detailed analysis focusing on performance, improvements, next workouts, and safety recommendations.
                Ensure the response follows the EXACT JSON format shown above.
                """,
                activity.getType(),
                activity.getDuration(),
                activity.getCaloriesBurned(),
                activity.getAdditionalData()
        );
    }
}