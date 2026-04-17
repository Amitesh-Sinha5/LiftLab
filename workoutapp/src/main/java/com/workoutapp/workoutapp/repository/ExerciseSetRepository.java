package com.workoutapp.workoutapp.repository;

import com.workoutapp.workoutapp.model.ExerciseSet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExerciseSetRepository extends JpaRepository<ExerciseSet, Long> {
}