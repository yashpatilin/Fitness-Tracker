package com.fitness.activityservice;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fitness.activityservice.models.Activity;

@Repository
public interface ActivityRepository extends JpaRepository<Activity, String> {
	
}
