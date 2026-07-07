package com.fitness.userservice.service;

import org.springframework.stereotype.Service;

import com.fitness.userservice.UserRepository;
import com.fitness.userservice.dto.RegisterRequest;
import com.fitness.userservice.models.User;

import lombok.AllArgsConstructor;
import user.UserResponse;

@Service
@AllArgsConstructor
public class UserService {

	private final UserRepository repository;
	public UserResponse register(RegisterRequest request) {
		
		if(repository.existsByEmail(request.getEmail())) {
			User existingUser = repository.findByEmail(request.getEmail());
		
			UserResponse userResponse = new UserResponse();
			userResponse.setId(existingUser.getId());
			userResponse.setEmail(existingUser.getEmail());
			userResponse.setFName(existingUser.getFName());
			userResponse.setLName(existingUser.getLName());
			userResponse.setPassword(existingUser.getPassword());
			userResponse.setCreatedAt(existingUser.getCreatedAt());
			userResponse.setUpdatedAt(existingUser.getUpdatedAt());
			
			return userResponse;
		}
		
		User user = new User();
		user.setEmail(request.getEmail());
		user.setFName(request.getFName());
		user.setLName(request.getLName());
		user.setKeycloakId(request.getKeycloakId());
		user.setPassword(request.getPassword());
		
		User savedUser = repository.save(user);
		UserResponse userResponse = new UserResponse();
		userResponse.setId(savedUser.getId());
		userResponse.setEmail(savedUser.getEmail());
		userResponse.setFName(savedUser.getFName());
		userResponse.setLName(savedUser.getLName());
		userResponse.setKeycloakId(savedUser.getKeycloakId());
		userResponse.setPassword(savedUser.getPassword());
		userResponse.setCreatedAt(savedUser.getCreatedAt());
		userResponse.setUpdatedAt(savedUser.getUpdatedAt());
		
		return userResponse;
	}
	public UserResponse getUserProfile(String id) {
		User user = repository.findById(id).orElseThrow(() -> new RuntimeException("User not found with id: " + id));
		UserResponse userResponse = new UserResponse();
		userResponse.setId(user.getId());
		userResponse.setEmail(user.getEmail());
		userResponse.setFName(user.getFName());
		userResponse.setLName(user.getLName());
		userResponse.setPassword(user.getPassword());
		userResponse.setCreatedAt(user.getCreatedAt());
		userResponse.setUpdatedAt(user.getUpdatedAt());
		
		return userResponse;
	}
	public Boolean existByUserId(String userId) {
		return repository.existsByKeycloakId(userId);
	}

}
