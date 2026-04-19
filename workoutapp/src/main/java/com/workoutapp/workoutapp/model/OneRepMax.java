package com.workoutapp.workoutapp.model;

public class OneRepMax {
    private double weight;
    private int reps;
    private double oneRepMax;

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public int getReps() {
        return reps;
    }

    public void setReps(int reps) {
        this.reps = reps;
    }

    public double getOneRepMax() {
        return oneRepMax;
    }

    public void setOneRepMax(double oneRepMax) {
        this.oneRepMax = oneRepMax;
    }

    public void calculateOneRepMax() {
        if (reps > 0) {
            this.oneRepMax = weight * (1 + (reps / 30.0));
        }
    }
}
