package com.fitness.aiservice.models;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "recommendations")
public class Recommendation {
	
	@Id
	private String Id;
	private String activityId;
	private String userId;
	private String recommendation;
	private String improvements;
	private String suggestions;
	private String safety;
	
	@CreationTimestamp
	private LocalDateTime createdAt;
}