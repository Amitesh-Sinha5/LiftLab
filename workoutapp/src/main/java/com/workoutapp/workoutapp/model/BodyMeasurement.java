package com.workoutapp.workoutapp.model;

import jakarta.persistence.*;
import java.time.LocalDate;

import jakarta.validation.constraints.NotNull;

@Entity
public class BodyMeasurement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId; // Optional for multi-user support
    private LocalDate date;
    @NotNull
    private double weight; // in kg
    
    @NotNull
    private double height; // in meters
    
    private double chest;
    private double waist;
    private double biceps;

    @Transient
    private Double bmi;

    // --- BMI Calculation ---
    public Double getBmi() {
        if (height > 0) {
            return weight / (height * height);
        }
        return null;
    }

    // --- Getters & Setters ---

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    private String notes; // Add this field

    // Add getter and setter
    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public double getChest() {
        return chest;
    }

    public void setChest(double chest) {
        this.chest = chest;
    }

    public double getWaist() {
        return waist;
    }

    public void setWaist(double waist) {
        this.waist = waist;
    }

    public double getBiceps() {
        return biceps;
    }

    public void setBiceps(double biceps) {
        this.biceps = biceps;
    }
}