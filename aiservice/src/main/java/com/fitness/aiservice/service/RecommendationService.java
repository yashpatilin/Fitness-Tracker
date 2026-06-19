package com.fitness.aiservice.service;

import org.springframework.stereotype.Service;

import com.fitness.aiservice.models.Recommendation;
import com.fitness.aiservice.repository.RecommendationRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RecommendationService {

	private final RecommendationRepository recommendationRepository;
	public String getUserRecommendations(String userId) {
		
        Recommendation recommendation =
                recommendationRepository.findFirstByUserIdOrderByCreatedAtDesc(userId)
                .orElseThrow(() ->
                        new RuntimeException("No recommendations found for userId: " + userId));

        return recommendation.getRecommendation();
	}
	public String getActivityRecommendations(String activityId) {
		
		 Recommendation recommendation =
		            recommendationRepository.findByActivityId(activityId)
		            .orElseThrow(() ->
		                    new RuntimeException("No recommendations found for activityId: " + activityId));

		    return recommendation.getRecommendation();
	}
	
	

}
