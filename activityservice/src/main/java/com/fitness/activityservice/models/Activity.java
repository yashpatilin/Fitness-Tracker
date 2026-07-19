package com.fitness.activityservice.models;

import java.time.LocalDateTime;
import java.util.Map;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "activity")
public class Activity {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private String id;
	private String userId;
	@jakarta.persistence.Enumerated(jakarta.persistence.EnumType.STRING)
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
