package com.workoutapp.workoutapp.controller;

import com.workoutapp.workoutapp.command.ExcelExportCommand;
import com.workoutapp.workoutapp.command.ExportCommand;
import com.workoutapp.workoutapp.model.BodyMeasurement;
import com.workoutapp.workoutapp.repository.ProgressRepository;
import com.workoutapp.workoutapp.repository.WorkoutLogRepository;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import jakarta.validation.Valid;

import java.beans.PropertyEditorSupport;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/progress")
public class ProgressController {

    @Autowired
    private ProgressRepository progressRepository;

    @Autowired
    private WorkoutLogRepository workoutLogRepository;

    // Dashboard showing both body measurements and workout history
    @GetMapping
    public String viewProgress(Model model) {
        model.addAttribute("bodyMeasurements", progressRepository.findAllByOrderByDateDesc());
        model.addAttribute("workoutHistory", workoutLogRepository.findAllByOrderByStartTimeDesc());
        return "progress-dashboard";
    }

    // Create new body measurement form
    @GetMapping("/body/new")
    public String newBodyMeasurementForm(Model model) {
        BodyMeasurement measurement = new BodyMeasurement();
        measurement.setDate(LocalDate.now());
        model.addAttribute("measurement", measurement);
        return "progress-body-form";
    }

    // Save body measurement
    @PostMapping("/body/save")
    public String saveBodyMeasurement(@Valid @ModelAttribute("measurement") BodyMeasurement measurement,
            BindingResult result) {
        if (result.hasErrors()) {
            return "progress-body-form";
        }
        progressRepository.save(measurement);
        return "redirect:/progress";
    }

    // View workout history
    @GetMapping("/workouts")
    public String viewWorkoutHistory(Model model) {
        model.addAttribute("workoutLogs", workoutLogRepository.findAllByOrderByStartTimeDesc());
        return "progress-workout-history";
    }

    // Edit existing body measurement
    @GetMapping("/body/edit/{id}")
    public String editBodyMeasurement(@PathVariable Long id, Model model) {
        BodyMeasurement measurement = progressRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid measurement Id: " + id));
        model.addAttribute("measurement", measurement);
        return "progress-body-form";
    }

    // Delete body measurement
    @GetMapping("/body/delete/{id}")
    public String deleteBodyMeasurement(@PathVariable Long id) {
        progressRepository.deleteById(id);
        return "redirect:/progress";
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(LocalDate.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) throws IllegalArgumentException {
                setValue(LocalDate.parse(text));
            }
        });
    }

    /**
     * Command Pattern in action — the controller (Invoker) creates the
     * ExcelExportCommand (Concrete Command) and calls execute(), knowing
     * nothing about POI internals, content-type headers, or column layout.
     */
    @GetMapping("/export")
    public void exportProgressToExcel(HttpServletResponse response) throws IOException {
        List<BodyMeasurement> measurements = progressRepository.findAllByOrderByDateDesc();
        ExportCommand exportCommand = new ExcelExportCommand(measurements);
        exportCommand.execute(response);
    }
}
