package com.fitness.aiservice.models;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor	
@Builder
@AllArgsConstructor
@Data
@Table(name = "activity")
public class Activity {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private String id;
	private String userId;
	private ActivityType type; 
	private double duration; 
	private Integer caloriesBurned;
	private LocalDateTime startTime;
	
	
	private String additionalData;
	
	@CreationTimestamp
	private LocalDateTime createdAt;
	
	@UpdateTimestamp
	private LocalDateTime updatedAt;
}
