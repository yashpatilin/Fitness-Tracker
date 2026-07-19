package com.fitness.activityservice.service;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.fitness.activityservice.ActivityRepository;
import com.fitness.activityservice.dto.ActivityRequest;
import com.fitness.activityservice.dto.ActivityResponse;
import com.fitness.activityservice.models.Activity;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;

@Service
@RequiredArgsConstructor
public class ActivityService {
	
	private final ActivityRepository activityRepository;
	private final KafkaTemplate<String, Activity> kafkaTemplate;

	@Value("${kafka.topic.name}")
	private String topicName;
	
	public ActivityResponse trackActivity(ActivityRequest request) {
		
		Activity activity = Activity.builder()
			.userId(request.getUserId())			
			.type(request.getType())
			.duration(request.getDuration())
			.caloriesBurned(request.getCaloriesBurned())
			.startTime(request.getStartTime())
			.additionalData(request.getAdditionalData())
			.build();
		
		Activity savedActivity = activityRepository.save(activity);
		
		try {
		kafkaTemplate.send(topicName,savedActivity.getUserId(),savedActivity);
		}catch (Exception e) {
			e.printStackTrace();
		}
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

	public List<ActivityResponse> getUserActivities(String userId) {
		List<Activity> activities = activityRepository.findByUserId(userId);
		return activities.stream().map(this::mapToResponse).collect(Collectors.toList());
	}

	public ActivityResponse getActivityById(String id) {
		Activity activity = activityRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Activity not found with id: " + id));
		return mapToResponse(activity);
	}

}
