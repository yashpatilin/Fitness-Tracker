package com.fitness.aiservice.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fitness.aiservice.models.Recommendation;
import com.fitness.aiservice.service.RecommendationService;

import lombok.RequiredArgsConstructor;

@Repository

public interface RecommendationRepository extends JpaRepository<Recommendation, String> {
	


    Optional<Recommendation> findFirstByUserIdOrderByCreatedAtDesc(String userId);

    Optional<Recommendation> findByActivityId(String activityId);
	
	
	
	
}
