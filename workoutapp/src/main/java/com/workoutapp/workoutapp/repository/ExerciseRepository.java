package com.workoutapp.workoutapp.repository;

import com.workoutapp.workoutapp.model.Exercise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface ExerciseRepository extends JpaRepository<Exercise, Long> {
}

