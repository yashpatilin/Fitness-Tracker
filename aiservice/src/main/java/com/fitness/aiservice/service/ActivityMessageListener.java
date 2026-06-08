package com.fitness.aiservice.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.fitness.aiservice.models.Activity;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class ActivityMessageListener {
	
	private final ActivityAIService activityAIService;
	
	@KafkaListener(topics = "${kafka.topic.name}",groupId = "activity-consumer-group")
	public void processActivityMessage(Activity activity) {
		log.info("Received activity for processing: {}", activity.getUserId());
		activityAIService.generateRecommendation(activity);
	}
}
