package com.workoutapp.workoutapp.model;

public class BmiForm {

    private double height;   // Height in meters
    private double weight;   // Weight in kilograms
    private String gender;   // Gender (male/female)
    private int age;         // Age

    // Default constructor
    public BmiForm() {}

    // Getters and setters
    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}


