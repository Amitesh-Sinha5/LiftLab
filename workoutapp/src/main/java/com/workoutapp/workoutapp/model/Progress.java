package com.workoutapp.workoutapp.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class Progress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date;
    private double weight;
    private double height;
    private double bmi;
    private double bodyFatPercentage;
    private double chestMeasurement;
    private double waistMeasurement;
    private double hipMeasurement;
    private double armMeasurement;
    private double thighMeasurement;
    private String notes;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }
    public double getWeight() { return weight; }
    public void setWeight(double weight) { this.weight = weight; }
    public double getBmi() { return bmi; }
    public double getHeight() { return height; }
    public void setHeight(double height) { this.height = height; }
    public double getBodyFatPercentage() { return bodyFatPercentage; }
    public void setBodyFatPercentage(double bodyFatPercentage) { this.bodyFatPercentage = bodyFatPercentage; }
    public double getChestMeasurement() { return chestMeasurement; }
    public void setChestMeasurement(double chestMeasurement) { this.chestMeasurement = chestMeasurement; }
    public double getWaistMeasurement() { return waistMeasurement; }
    public void setWaistMeasurement(double waistMeasurement) { this.waistMeasurement = waistMeasurement; }
    public double getHipMeasurement() { return hipMeasurement; }
    public void setHipMeasurement(double hipMeasurement) { this.hipMeasurement = hipMeasurement; }
    public double getArmMeasurement() { return armMeasurement; }
    public void setArmMeasurement(double armMeasurement) { this.armMeasurement = armMeasurement; }
    public double getThighMeasurement() { return thighMeasurement; }
    public void setThighMeasurement(double thighMeasurement) { this.thighMeasurement = thighMeasurement; }
    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }

    @PrePersist
    @PreUpdate
    private void calculateBMI() {
        if (height > 0 && weight > 0) {
            double heightInMeters = height / 100;
            this.bmi = weight / (heightInMeters * heightInMeters);
        }
    }
}