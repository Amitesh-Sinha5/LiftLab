package com.workoutapp.workoutapp.repository;

import com.workoutapp.workoutapp.model.Workout;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkoutRepository extends JpaRepository<Workout, Long> {
}