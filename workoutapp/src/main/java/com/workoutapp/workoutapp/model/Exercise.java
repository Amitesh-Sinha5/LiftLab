package com.workoutapp.workoutapp.model;

import jakarta.persistence.*;

@Entity
public class Exercise {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private String type;

    // ── Builder Pattern (Creational) ──────────────────────────────────────────
    // Constructs Exercise objects step-by-step, keeping the creation logic
    // separate from the domain class and making optional fields explicit.
    public static class Builder {
        private String name;
        private String description;
        private String type;

        public Builder name(String name)               { this.name = name;               return this; }
        public Builder description(String description) { this.description = description; return this; }
        public Builder type(String type)               { this.type = type;               return this; }

        public Exercise build() {
            Exercise exercise = new Exercise();
            exercise.setName(this.name);
            exercise.setDescription(this.description);
            exercise.setType(this.type);
            return exercise;
        }
    }
    // ─────────────────────────────────────────────────────────────────────────

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {  // Correct getter for 'name'
        return name;
    }

    public void setName(String name) {  // Correct setter for 'name'
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
