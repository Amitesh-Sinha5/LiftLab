package com.workoutapp.workoutapp.handler;

/**
 * Chain of Responsibility — Concrete Handler 3 (terminal handler).
 *
 * Handles all remaining adults (ages 18–64) using standard WHO
 * thresholds. As the last link in the chain it never forwards.
 */
public class StandardBmiHandler extends BmiClassificationHandler {

    @Override
    public String classify(double bmi, int age, String gender) {
        if (bmi < 18.5)  return "Underweight";
        if (bmi <= 24.9) return "Healthy";
        if (bmi < 30.0)  return "Overweight";
        return "Obese";
    }
}
