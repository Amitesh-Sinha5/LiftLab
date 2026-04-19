package com.workoutapp.workoutapp.repository;

import com.workoutapp.workoutapp.model.BodyMeasurement;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface BodyMeasurementRepository extends JpaRepository<BodyMeasurement, Long> {
    List<BodyMeasurement> findByUserIdOrderByDateAsc(Long userId);
}