package com.fitness.aiservice.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fitness.aiservice.models.Activity;
import com.fitness.aiservice.models.Recommendation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ActivityAIService {

    private final GeminiService geminiService;
    public Recommendation generateRecommendation(Activity activity) {
        try {
            String prompt = createPromptForActivity(activity);

            String aiResponse = geminiService.getRecommendation(prompt);

            log.info("Response from AI {}", aiResponse);

            return processAIResponse(aiResponse, activity);

        } catch (Exception e) {
            log.error("Gemini API failed", e);

            return createDefaultRecommendation(activity);
        }
    }

    private Recommendation processAIResponse(String aiResponse, Activity activity) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            // Gemini returns a top-level JSON with candidates -> content -> parts -> text
            JsonNode rootNode = objectMapper.readTree(aiResponse);
            JsonNode textNode = rootNode.path("candidates").get(0)
                    .path("content").path("parts").get(0).path("text");

            String jsonText = textNode.asText().replace("```json", "").replace("```", "").trim();
            JsonNode parsedAI = objectMapper.readTree(jsonText);

            return Recommendation.builder()
                    .Id(UUID.randomUUID().toString())
                    .activityId(activity.getId())
                    .userId(activity.getUserId())
                    .type(activity.getType())
                    .duration(activity.getDuration())
                    .caloriesBurned(activity.getCaloriesBurned())
                    .startTime(activity.getStartTime())
                    .recommendation(parsedAI.path("analysis").asText())
                    .improvements(toList(parsedAI.path("improvements")))
                    .suggestions(toList(parsedAI.path("suggestions")))
                    .safety(toList(parsedAI.path("safety")))
                    .createdAt(LocalDateTime.now())
                    .build();
        } catch (Exception e) {
            log.error("Failed to parse AI response: {}", aiResponse, e);
            return createDefaultRecommendation(activity);
        }
    }

    private List<String> toList(JsonNode node) {
        if (node.isMissingNode() || !node.isArray()) return Collections.emptyList();
        List<String> list = new ArrayList<>();
        node.forEach(element -> list.add(element.asText()));
        return list;
    }

    private Recommendation createDefaultRecommendation(Activity activity) {
        return Recommendation.builder()
                .Id(UUID.randomUUID().toString())
                .activityId(activity.getId())
                .userId(activity.getUserId())
                .type(activity.getType())
                .duration(activity.getDuration())
                .caloriesBurned(activity.getCaloriesBurned())
                .startTime(activity.getStartTime())
                .recommendation("Great job on your activity! Keep up the good work and stay consistent.")
                .improvements(Collections.singletonList("Try to gradually increase your duration by 10% each week."))
                .suggestions(Collections.singletonList("Make sure to stay hydrated."))
                .safety(Collections.singletonList("Always listen to your body and rest if you feel pain."))
                .createdAt(LocalDateTime.now())
                .build(); 
    }

    private String createPromptForActivity(Activity activity) {
        return String.format("""
                Analyze this fitness activity and provide a helpful, encouraging, and detailed recommendation for the user.
                
                Activity Type: %s
                Duration: %f minutes
                Calories Burned: %d
                Additional Metrics: %s

                Please respond strictly in JSON format matching this exact structure (do not include any other text):
                {
                    "analysis": "A detailed paragraph analyzing the activity and praising the user.",
                    "improvements": ["tip 1 to improve", "tip 2"],
                    "suggestions": ["next workout idea 1", "next workout idea 2"],
                    "safety": ["safety tip 1", "safety tip 2"]
                }
                """,
                activity.getType(),
                activity.getDuration(),
                activity.getCaloriesBurned(),
                activity.getAdditionalData()
        );
    }
}