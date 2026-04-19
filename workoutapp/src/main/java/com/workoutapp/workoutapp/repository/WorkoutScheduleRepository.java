package com.workoutapp.workoutapp.repository;

import com.workoutapp.workoutapp.model.WorkoutSchedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkoutScheduleRepository extends JpaRepository<WorkoutSchedule, Long> {
}
