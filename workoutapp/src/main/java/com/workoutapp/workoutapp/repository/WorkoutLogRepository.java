package com.workoutapp.workoutapp.repository;

import com.workoutapp.workoutapp.model.WorkoutLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface WorkoutLogRepository extends JpaRepository<WorkoutLog, Long> {
    List<WorkoutLog> findAllByOrderByStartTimeDesc();
    
    List<WorkoutLog> findAllByWorkoutId(Long workoutId);
    
    @Modifying
    @Query("DELETE FROM WorkoutLog wl WHERE wl.workout.id = :workoutId")
    void deleteByWorkoutId(@Param("workoutId") Long workoutId);
}