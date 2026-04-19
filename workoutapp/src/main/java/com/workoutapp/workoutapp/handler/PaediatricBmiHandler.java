package com.workoutapp.workoutapp.handler;

/**
 * Chain of Responsibility — Concrete Handler 1.
 *
 * Handles subjects under 18: standard adult thresholds do not apply.
 * All others are passed down the chain.
 */
public class PaediatricBmiHandler extends BmiClassificationHandler {

    @Override
    public String classify(double bmi, int age, String gender) {
        if (age < 18) {
            return "See Paediatrician (growth-chart percentiles apply under 18)";
        }
        return next != null ? next.classify(bmi, age, gender) : "Unknown";
    }
}
