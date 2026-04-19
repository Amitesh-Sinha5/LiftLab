package com.workoutapp.workoutapp.handler;

/**
 * Chain of Responsibility Pattern (Behavioural) — Abstract Handler.
 *
 * Each concrete handler either handles the BMI classification request
 * (if its condition matches) or forwards it to the next handler in the
 * chain. BmiController builds the chain and passes the request to the
 * first handler — no if-else ladder needed in the controller.
 */
public abstract class BmiClassificationHandler {

    protected BmiClassificationHandler next;

    public BmiClassificationHandler setNext(BmiClassificationHandler next) {
        this.next = next;
        return next;
    }

    public abstract String classify(double bmi, int age, String gender);
}
