package com.fitness.userservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fitness.userservice.dto.RegisterRequest;
import com.fitness.userservice.dto.UserResponse;
import com.fitness.userservice.service.UserService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/users")

public class UserController {
	private final UserService userService;
	
	
	@GetMapping("/{id}")
	public ResponseEntity<UserResponse> getUserProfile(@PathVariable String id) {
		// Implementation to get user by ID
		return ResponseEntity.ok(userService.getUserProfile(id));
	}
	
	@PostMapping("/register")
	public ResponseEntity<UserResponse> register(@RequestBody RegisterRequest request) {
		UserResponse response = userService.register(request);
		return ResponseEntity.ok(response);
	}
	
	@GetMapping("/{id}/validate")
	public ResponseEntity<Boolean> validateUser(@PathVariable String id) {
		// Implementation to get user by ID
		return ResponseEntity.ok(userService.existByUserId(id));
	}
}
