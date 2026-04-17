package com.workoutapp.workoutapp.controller;

import com.workoutapp.workoutapp.model.Exercise;
import com.workoutapp.workoutapp.repository.ExerciseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;

@Controller
@RequestMapping("/exercises")
public class ExerciseController {

    @Autowired
    private ExerciseRepository exerciseRepository;

    @GetMapping
    public String listExercises(Model model) {
        List<Exercise> exercises = exerciseRepository.findAll();
        exercises.sort(Comparator.comparing(Exercise::getType)); // Sort alphabetically by type
        model.addAttribute("exercises", exercises);
        return "exercise-list";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("exercise", new Exercise());
        return "exercise-create";
    }

    @PostMapping("/create")
    public String createExercise(@ModelAttribute Exercise formExercise) {
        // Builder Pattern: construct the Exercise step-by-step from form input
        Exercise exercise = new Exercise.Builder()
                .name(formExercise.getName())
                .description(formExercise.getDescription())
                .type(formExercise.getType())
                .build();
        exerciseRepository.save(exercise);
        return "redirect:/exercises";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Exercise exercise = exerciseRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid exercise Id:" + id));
        model.addAttribute("exercise", exercise);
        return "exercise-edit";
    }

    @PostMapping("/edit/{id}")
    public String updateExercise(@PathVariable Long id, @ModelAttribute Exercise exercise) {
        exercise.setId(id);
        exerciseRepository.save(exercise);
        return "redirect:/exercises";
    }

    @PostMapping("/delete/{id}")
    public String deleteExercise(@PathVariable Long id) {
        exerciseRepository.deleteById(id);
        return "redirect:/exercises";
    }



    // Save New Exercise (Form Submission)
    @PostMapping("/new")
    public String saveNewExercise(@ModelAttribute Exercise exercise) {
        exerciseRepository.save(exercise); // Save the exercise to the database
        return "redirect:/exercises"; // Redirect to the exercise list after saving
    }

}