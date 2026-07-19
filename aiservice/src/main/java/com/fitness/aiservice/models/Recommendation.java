package com.fitness.aiservice.models;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
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

	@jakarta.persistence.Enumerated(jakarta.persistence.EnumType.STRING)
	private ActivityType type;
	private Double duration;
	private Integer caloriesBurned;
	private LocalDateTime startTime;

	@Column(columnDefinition = "TEXT")
	private String recommendation;

	@jakarta.persistence.ElementCollection
	private List<String> improvements;

	@jakarta.persistence.ElementCollection
	private List<String> suggestions;

	@jakarta.persistence.ElementCollection
	private List<String> safety;

	@CreationTimestamp
	private LocalDateTime createdAt;
}