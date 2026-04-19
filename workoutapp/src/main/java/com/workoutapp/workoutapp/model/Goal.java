package com.workoutapp.workoutapp.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class Goal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String goalType;
    private String description;
    private boolean completed;
    private double progress;
    private LocalDate targetDate;
    private LocalDate reminderDate;

    // Constructors
    public Goal() {
    }

    public Goal(Long id, String goalType, String description, boolean completed, double progress, LocalDate targetDate, LocalDate reminderDate) {
        this.id = id;
        this.goalType = goalType;
        this.description = description;
        this.completed = completed;
        this.progress = progress;
        this.targetDate = targetDate;
        this.reminderDate = reminderDate;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGoalType() {
        return goalType;
    }

    public void setGoalType(String goalType) {
        this.goalType = goalType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public double getProgress() {
        return progress;
    }

    public void setProgress(double progress) {
        this.progress = progress;
    }

    public LocalDate getTargetDate() {
        return targetDate;
    }

    public void setTargetDate(LocalDate targetDate) {
        this.targetDate = targetDate;
    }

    public LocalDate getReminderDate() {
        return reminderDate;
    }

    public void setReminderDate(LocalDate reminderDate) {
        this.reminderDate = reminderDate;
    }

    public String getStatus() {
        if (completed) {
            return "completed";
        } else if (targetDate != null && targetDate.isBefore(LocalDate.now())) {
            return "overdue";
        } else {
            return "active";
        }
    }
}
