package com.workoutapp.workoutapp.model;

import jakarta.persistence.*;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "workout_log")
public class WorkoutLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Workout workout;

    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Duration totalDuration;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CompletedSet> completedSets = new ArrayList<>();

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Workout getWorkout() { return workout; }
    public void setWorkout(Workout workout) { this.workout = workout; }
    public LocalDateTime getStartTime() { return startTime; }
    public void setStartTime(LocalDateTime startTime) { this.startTime = startTime; }
    public LocalDateTime getEndTime() { return endTime; }
    public void setEndTime(LocalDateTime endTime) { this.endTime = endTime; }
    public Duration getTotalDuration() { return totalDuration; }
    public void setTotalDuration(Duration totalDuration) { this.totalDuration = totalDuration; }
    public List<CompletedSet> getCompletedSets() { return completedSets; }
    public void setCompletedSets(List<CompletedSet> completedSets) { this.completedSets = completedSets; }
}