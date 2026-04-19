package com.workoutapp.workoutapp.controller;

import com.workoutapp.workoutapp.model.OneRepMax;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class OneRepMaxController {

    // Displays the form to input the weight and reps for calculating 1RM
    @GetMapping("/onerepmax")
    public String getOneRepMaxForm(Model model) {
        model.addAttribute("oneRepMax", new OneRepMax());  // Empty OneRepMax object for the form
        return "onerepmax";  // Return the HTML page for the One Rep Max form
    }

    // Handles the form submission and calculates the 1RM
    @PostMapping("/onerepmax")
    public String calculateOneRepMax(@ModelAttribute OneRepMax oneRepMax, Model model) {
        // Calculate the 1RM
        oneRepMax.calculateOneRepMax();  // Call the method to calculate 1RM
        model.addAttribute("oneRepMax", oneRepMax);  // Add the result to the model
        return "onerepmax";  // Return the same page to display the result
    }
}
