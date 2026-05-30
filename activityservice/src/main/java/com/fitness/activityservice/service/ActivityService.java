package com.fitness.activityservice.service;
import org.springframework.stereotype.Service;

import com.fitness.activityservice.ActivityRepository;
import com.fitness.activityservice.dto.ActivityRequest;
import com.fitness.activityservice.dto.ActivityResponse;
import com.fitness.activityservice.models.Activity;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ActivityService {
	
	private final ActivityRepository activityRepository;
	private final UserValidationService userValidationService;

	public ActivityResponse trackActivity(ActivityRequest request) {
		
		boolean isValidUser = userValidationService.validateUser(request.getUserId());
		
		if (!isValidUser) {
			throw new RuntimeException("Invalid user ID: " + request.getUserId());
		}
		
		Activity activity = Activity.builder()
			.userId(request.getUserId())			
			.type(request.getType())
			.duration(request.getDuration())
			.caloriesBurned(request.getCaloriesBurned())
			.startTime(request.getStartTime())
			.additionalData(request.getAdditionalData())
			.build();
		
		Activity savedActivity = activityRepository.save(activity);
		return mapToResponse(savedActivity);
			
	
	}

	private ActivityResponse mapToResponse(Activity savedActivity) {
		ActivityResponse response = new ActivityResponse();
		response.setId(savedActivity.getId());
		response.setUserId(savedActivity.getUserId());
		response.setType(savedActivity.getType());
		response.setDuration(savedActivity.getDuration());
		response.setCaloriesBurned(savedActivity.getCaloriesBurned());
		response.setStartTime(savedActivity.getStartTime());
		response.setAdditionalData(savedActivity.getAdditionalData());
		response.setCreatedAt(savedActivity.getCreatedAt());
		response.setUpdatedAt(savedActivity.getUpdatedAt());
		return response;
	}

}
