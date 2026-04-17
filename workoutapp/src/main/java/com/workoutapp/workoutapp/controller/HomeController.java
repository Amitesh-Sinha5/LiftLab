package com.workoutapp.workoutapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class    HomeController {

    @GetMapping("/")
    public String home() {
        return "index"; // Redirects to index.html or a template called "index"
    }
}
