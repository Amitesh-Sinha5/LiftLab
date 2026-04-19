package com.workoutapp.workoutapp.service;

import com.workoutapp.workoutapp.repository.CompletedSetRepository;
import com.workoutapp.workoutapp.repository.WorkoutLogRepository;
import com.workoutapp.workoutapp.repository.WorkoutRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Facade Pattern (Structural).
 *
 * Provides a single simplified entry point for complex workout operations
 * that would otherwise require the caller to coordinate multiple
 * repositories (WorkoutRepository, WorkoutLogRepository,
 * CompletedSetRepository). Controllers only need to know about this
 * facade instead of the underlying persistence graph.
 */
@Service
public class WorkoutFacade {

    @Autowired
    private WorkoutRepository workoutRepository;

    @Autowired
    private WorkoutLogRepository workoutLogRepository;

    @Autowired
    private CompletedSetRepository completedSetRepository;

    @Transactional
    public void deleteWorkoutWithDependencies(Long workoutId) {
        workoutLogRepository.findAllByWorkoutId(workoutId).forEach(log ->
                completedSetRepository.deleteAll(log.getCompletedSets())
        );
        workoutLogRepository.deleteByWorkoutId(workoutId);
        workoutRepository.deleteById(workoutId);
    }
}
