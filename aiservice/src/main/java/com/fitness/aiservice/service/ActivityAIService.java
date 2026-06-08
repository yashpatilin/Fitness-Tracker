package com.fitness.aiservice.service;

import org.springframework.stereotype.Service;

import com.fitness.aiservice.models.Activity;
import com.fitness.aiservice.models.Recommendation;
import com.fitness.aiservice.repository.RecommendationRepository;
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
	
	public void generateRecommendation(Activity activity) {
		String prompt = createPromptForActivity(activity);
		log.info("Response from AI {}" + geminiService.getRecommendation(prompt));
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
