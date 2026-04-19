package com.workoutapp.workoutapp.controller;

import com.workoutapp.workoutapp.model.Goal;
import com.workoutapp.workoutapp.repository.GoalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/goals")
public class GoalController {

    @Autowired
    private GoalRepository goalRepository;

    // --------------------------------------------------------------------------------------
    // API Endpoints (return JSON)
    // --------------------------------------------------------------------------------------

    @GetMapping("/api")
    @ResponseBody
    public List<Goal> getAllGoals() {
        return goalRepository.findAll();
    }

    @GetMapping("/api/{id}")
    @ResponseBody
    public ResponseEntity<Goal> getGoalById(@PathVariable Long id) {
        Optional<Goal> goalOpt = goalRepository.findById(id);
        return goalOpt.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/api")
    @ResponseBody
    public Goal createGoalApi(@RequestBody Goal goal) {
        return goalRepository.save(goal);
    }

    @PutMapping("/api/{id}")
    @ResponseBody
    public ResponseEntity<Goal> updateGoalApi(@PathVariable Long id, @RequestBody Goal updatedGoal) {
        Optional<Goal> goalOpt = goalRepository.findById(id);
        if (goalOpt.isPresent()) {
            Goal goal = goalOpt.get();
            goal.setGoalType(updatedGoal.getGoalType());
            goal.setDescription(updatedGoal.getDescription());
            goal.setTargetDate(updatedGoal.getTargetDate());
            goal.setReminderDate(updatedGoal.getReminderDate());
            goal.setProgress(updatedGoal.getProgress());
            goal.setCompleted(updatedGoal.isCompleted());
            return ResponseEntity.ok(goalRepository.save(goal));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/api/{id}/complete")
    @ResponseBody
    public ResponseEntity<Goal> completeGoalApi(@PathVariable Long id) {  // Renamed from completeGoal
        Optional<Goal> goalOpt = goalRepository.findById(id);
        if (goalOpt.isPresent()) {
            Goal goal = goalOpt.get();
            goal.setCompleted(true);
            goal.setProgress(100.0);
            return ResponseEntity.ok(goalRepository.save(goal));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/api/{id}/progress")
    @ResponseBody
    public ResponseEntity<Goal> updateProgress(@PathVariable Long id, @RequestParam double progress) {
        Optional<Goal> goalOpt = goalRepository.findById(id);
        if (goalOpt.isPresent()) {
            Goal goal = goalOpt.get();
            goal.setProgress(progress);
            if (progress >= 100.0) {
                goal.setCompleted(true);
            }
            return ResponseEntity.ok(goalRepository.save(goal));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/api/{id}")
    @ResponseBody
    public ResponseEntity<Void> deleteGoalApi(@PathVariable Long id) {
        if (goalRepository.existsById(id)) {
            goalRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // --------------------------------------------------------------------------------------
    // UI Views (Thymeleaf)
    // --------------------------------------------------------------------------------------

    @GetMapping
    public String getAllGoalsForView(Model model) {
        model.addAttribute("goals", goalRepository.findAll());
        return "goals";
    }

    @GetMapping("/create")
    public String createGoalForm(Model model) {
        model.addAttribute("goal", new Goal());
        return "create-goal";
    }

    @PostMapping("/create")
    public String createGoal(@ModelAttribute Goal goal) {
        goalRepository.save(goal);
        return "redirect:/goals";
    }

    @GetMapping("/edit/{id}")
    public String editGoalForm(@PathVariable Long id, Model model) {
        Optional<Goal> goalOpt = goalRepository.findById(id);
        if (goalOpt.isPresent()) {
            model.addAttribute("goal", goalOpt.get());
            return "edit-goal";
        }
        return "redirect:/goals";
    }

    @PostMapping("/{id}/edit")
    public String updateGoal(@PathVariable Long id, @ModelAttribute Goal updatedGoal) {
        Optional<Goal> goalOpt = goalRepository.findById(id);
        if (goalOpt.isPresent()) {
            Goal goal = goalOpt.get();
            goal.setGoalType(updatedGoal.getGoalType());
            goal.setDescription(updatedGoal.getDescription());
            goal.setTargetDate(updatedGoal.getTargetDate());
            goal.setReminderDate(updatedGoal.getReminderDate());
            goal.setProgress(updatedGoal.getProgress());
            // Auto-complete when progress reaches 100; do not let the edit form reset completed to false
            if (updatedGoal.getProgress() >= 100.0) {
                goal.setCompleted(true);
            }
            goalRepository.save(goal);
        }
        return "redirect:/goals";
    }

    // UI version - returns String for Thymeleaf
    @GetMapping("/complete/{id}")
    public String completeGoalView(@PathVariable Long id) {
        Optional<Goal> goalOpt = goalRepository.findById(id);
        if (goalOpt.isPresent()) {
            Goal goal = goalOpt.get();
            goal.setCompleted(true);
            goal.setProgress(100.0);
            goalRepository.save(goal);
        }
        return "redirect:/goals";
    }

    @GetMapping("/delete/{id}")
    public String deleteGoal(@PathVariable Long id) {
        goalRepository.deleteById(id);
        return "redirect:/goals";
    }
}
