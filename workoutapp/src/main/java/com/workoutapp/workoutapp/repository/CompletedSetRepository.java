package com.workoutapp.workoutapp.repository;

import com.workoutapp.workoutapp.model.CompletedSet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompletedSetRepository extends JpaRepository<CompletedSet, Long> {
}