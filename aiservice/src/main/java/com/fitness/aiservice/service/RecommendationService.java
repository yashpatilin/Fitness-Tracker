package com.fitness.aiservice.service;

import org.springframework.stereotype.Service;

import com.fitness.aiservice.models.Recommendation;
import com.fitness.aiservice.repository.RecommendationRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RecommendationService {

	private final RecommendationRepository recommendationRepository;
	public Recommendation getUserRecommendations(String userId) {
		
        return recommendationRepository.findFirstByUserIdOrderByCreatedAtDesc(userId)
                .orElseThrow(() ->
                        new RuntimeException("No recommendations found for userId: " + userId));
	}
	public Recommendation getActivityRecommendations(String activityId) {
		// The AI generation via Kafka is asynchronous and might take up to 15 seconds.
		// Since the frontend does not poll, we will wait here for the recommendation to appear.
		for (int i = 0; i < 20; i++) {
			java.util.Optional<Recommendation> rec = recommendationRepository.findByActivityId(activityId);
			if (rec.isPresent()) {
				return rec.get();
			}
			try {
				Thread.sleep(1000); // Wait 1 second before checking again
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
				break;
			}
		}
		
		return recommendationRepository.findByActivityId(activityId)
		            .orElseThrow(() ->
		                    new RuntimeException("No recommendations found for activityId: " + activityId));
	}

}
