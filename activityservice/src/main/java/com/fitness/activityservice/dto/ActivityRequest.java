package com.fitness.activityservice.dto;

import java.time.LocalDateTime;

import com.fitness.activityservice.models.ActivityType;

import lombok.Data;

@Data
public class ActivityRequest {
	
	private String userId;
	private ActivityType type; 
	private double duration; 
	private Integer caloriesBurned;
	private LocalDateTime startTime;
	
	
	private String additionalData;
	
	
}
