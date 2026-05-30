package com.fitness.activityservice.service;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserValidationService {
	private final WebClient userServiceWebClient;
	
	
	public boolean validateUser(String userId) {
		try {
			return userServiceWebClient.get()
					.uri("/api/users/{userId}/validate", userId)
					.retrieve()
					.bodyToMono(Boolean.class)
					.block();
		}catch (WebClientResponseException.NotFound e) {
			e.printStackTrace(); // User not found
		}
		return false; 
	}
}
