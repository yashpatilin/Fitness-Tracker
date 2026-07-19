package com.fitness.aiservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fitness.aiservice.models.Recommendation;
import com.fitness.aiservice.service.RecommendationService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/recommendations")
@RequiredArgsConstructor
public class RecommendationController {
	private final RecommendationService recommendationService;
	
	@GetMapping("/user/{userId}")
	public ResponseEntity<Recommendation> getUserRecommendations(@PathVariable String userId) {
		return ResponseEntity.ok(recommendationService.getUserRecommendations(userId));
	
	}
	
	@GetMapping("/activity/{activityId}")
	public ResponseEntity<Recommendation> getActivityRecommendations(@PathVariable String activityId) {
		return ResponseEntity.ok(recommendationService.getActivityRecommendations(activityId));
	
	}
		
}
