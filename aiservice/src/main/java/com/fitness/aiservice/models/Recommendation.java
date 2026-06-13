package com.fitness.aiservice.models;

import java.time.LocalDateTime;
import jakarta.persistence.Column;
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

	@Column(columnDefinition = "TEXT")
	private String recommendation;

	@Column(columnDefinition = "TEXT")
	private String improvements;

	@Column(columnDefinition = "TEXT")
	private String suggestions;

	@Column(columnDefinition = "TEXT")
	private String safety;

	@CreationTimestamp
	private LocalDateTime createdAt;
}