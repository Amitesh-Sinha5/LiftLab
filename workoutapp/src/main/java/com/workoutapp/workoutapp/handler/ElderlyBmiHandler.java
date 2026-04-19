package com.workoutapp.workoutapp.handler;

/**
 * Chain of Responsibility — Concrete Handler 2.
 *
 * Handles adults aged 65 and over using WHO's raised healthy upper limit
 * of 27.0 (instead of 24.9) for older adults. All younger adults are
 * passed to the next handler.
 */
public class ElderlyBmiHandler extends BmiClassificationHandler {

    @Override
    public String classify(double bmi, int age, String gender) {
        if (age >= 65) {
            if (bmi < 18.5)  return "Underweight";
            if (bmi <= 27.0) return "Healthy";
            if (bmi < 30.0)  return "Overweight";
            return "Obese";
        }
        return next != null ? next.classify(bmi, age, gender) : "Unknown";
    }
}
