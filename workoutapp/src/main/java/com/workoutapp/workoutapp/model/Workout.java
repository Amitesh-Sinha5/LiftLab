package com.workoutapp.workoutapp.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;

@Entity
public class Workout {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;

    @OneToMany(mappedBy = "workout", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ExerciseSet> exercises = new ArrayList<>();

    @OneToMany(mappedBy = "workout", cascade = CascadeType.REMOVE)
    private List<WorkoutLog> workoutLogs = new ArrayList<>();

    private LocalDateTime scheduledTime;

    public LocalDateTime getScheduledTime() {
        return scheduledTime;
    }

    public void setScheduledTime(LocalDateTime scheduledTime) {
        this.scheduledTime = scheduledTime;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<ExerciseSet> getExercises() {
        return exercises;
    }

    public void setExercises(List<ExerciseSet> exercises) {
        this.exercises = exercises;
    }

    public void addExercise(Exercise exercise, int sets, int reps) {
        ExerciseSet exerciseSet = new ExerciseSet();
        exerciseSet.setExercise(exercise);
        exerciseSet.setWorkout(this);
        exerciseSet.setSets(sets);
        exerciseSet.setReps(reps);
        exercises.add(exerciseSet);
    }
}
