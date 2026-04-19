package com.workoutapp.workoutapp.controller;

import com.workoutapp.workoutapp.model.*;
import com.workoutapp.workoutapp.repository.*;
import com.workoutapp.workoutapp.service.WorkoutFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/workouts")
public class WorkoutController {

    @Autowired
    private WorkoutRepository workoutRepository;

    @Autowired
    private ExerciseRepository exerciseRepository;

    @Autowired
    private WorkoutLogRepository workoutLogRepository;

    @Autowired
    private WorkoutScheduleRepository workoutScheduleRepository;

    @Autowired
    private WorkoutFacade workoutFacade;

    private static final Logger logger = LoggerFactory.getLogger(WorkoutController.class);
    
    // Existing workout management methods
    @GetMapping
    public String listWorkouts(Model model) {
        List<Workout> workouts = workoutRepository.findAll();
        model.addAttribute("workouts", workouts);

        Map<Long, LocalDateTime> nextSchedules = workouts.stream()
                .filter(w -> w.getScheduledTime() != null && w.getScheduledTime().isAfter(LocalDateTime.now()))
                .collect(Collectors.toMap(
                        Workout::getId,
                        Workout::getScheduledTime
                ));

        model.addAttribute("nextSchedules", nextSchedules);
        model.addAttribute("schedule", new WorkoutSchedule());  // For the integrated form

        return "workout-list";
    }

    @PostMapping("/{workoutId}/schedule")
    public String scheduleWorkout(@PathVariable Long workoutId,
                                @ModelAttribute WorkoutSchedule schedule) {
        Workout workout = workoutRepository.findById(workoutId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid workout Id:" + workoutId));

        schedule.setWorkout(workout);
        workoutScheduleRepository.save(schedule);
        // Sync the denormalised scheduledTime on Workout so the list view can show it
        workout.setScheduledTime(schedule.getScheduledTime());
        workoutRepository.save(workout);
        return "redirect:/workouts";  // Redirect to workout-list after scheduling
    }

    @GetMapping("/scheduled")
    public String viewScheduledWorkouts(Model model) {
        model.addAttribute("schedules", workoutScheduleRepository.findAll());
        return "workout-schedule-list";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("workout", new Workout());
        return "workout-create";
    }

    @PostMapping("/create")
    public String createWorkout(@ModelAttribute Workout workout) {
        workoutRepository.save(workout);
        return "redirect:/workouts";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Workout workout = workoutRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid workout Id:" + id));
        model.addAttribute("workout", workout);
        model.addAttribute("exercises", exerciseRepository.findAll());
        return "workout-edit";
    }

    @PostMapping("/edit/{id}")
    public String updateWorkout(@PathVariable Long id, @ModelAttribute Workout workout) {
        Workout existing = workoutRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid workout Id:" + id));

        existing.setName(workout.getName());
        existing.setDescription(workout.getDescription());
        // Do not override exercises here — they're managed separately
        workoutRepository.save(existing);
        return "redirect:/workouts";
    }

    @PostMapping("/delete/{id}")
    public String deleteWorkout(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            workoutFacade.deleteWorkoutWithDependencies(id);
            redirectAttributes.addFlashAttribute("successMessage", "Workout deleted successfully");
        } catch (Exception e) {
            logger.error("Error deleting workout: {}", e.getMessage());
            redirectAttributes.addFlashAttribute("errorMessage", "Error deleting workout: " + e.getMessage());
        }
        return "redirect:/workouts";
    }


    @GetMapping("/{workoutId}/add-exercise")
    public String showAddExerciseForm(@PathVariable Long workoutId, Model model) {
        Workout workout = workoutRepository.findById(workoutId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid workout Id:" + workoutId));
        model.addAttribute("workout", workout);
        model.addAttribute("exercises", exerciseRepository.findAll());
        return "workout-add-exercise";
    }

    @PostMapping("/{workoutId}/add-exercise")
    public String addExerciseToWorkout(
            @PathVariable Long workoutId,
            @RequestParam Long exerciseId,
            @RequestParam int sets,
            @RequestParam int reps) {

        Workout workout = workoutRepository.findById(workoutId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid workout Id:" + workoutId));
        Exercise exercise = exerciseRepository.findById(exerciseId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid exercise Id:" + exerciseId));

        workout.addExercise(exercise, sets, reps);
        workoutRepository.save(workout);
        return "redirect:/workouts/edit/" + workoutId;
    }

    @GetMapping("/{workoutId}/remove-exercise/{exerciseSetId}")
    public String removeExerciseFromWorkout(
            @PathVariable Long workoutId,
            @PathVariable Long exerciseSetId) {

        Workout workout = workoutRepository.findById(workoutId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid workout Id:" + workoutId));

        workout.getExercises().removeIf(es -> es.getId().equals(exerciseSetId));
        workoutRepository.save(workout);
        return "redirect:/workouts/edit/" + workoutId;
    }

    // New workout tracking methods
    @GetMapping("/start/{workoutId}")
    public String startWorkout(@PathVariable Long workoutId, Model model) {
        Workout workout = workoutRepository.findById(workoutId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid workout Id:" + workoutId));

        WorkoutLog workoutLog = new WorkoutLog();
        workoutLog.setWorkout(workout);
        workoutLog.setStartTime(LocalDateTime.now());

        // Initialize all sets as not completed
        List<CompletedSet> completedSets = new ArrayList<>();
        for (ExerciseSet exerciseSet : workout.getExercises()) {
            for (int i = 1; i <= exerciseSet.getSets(); i++) {
                CompletedSet completedSet = new CompletedSet();
                completedSet.setExercise(exerciseSet.getExercise());
                completedSet.setSetNumber(i);
                completedSet.setCompleted(false);
                completedSets.add(completedSet);
            }
        }
        workoutLog.setCompletedSets(completedSets);

        workoutLogRepository.save(workoutLog);
        return "redirect:/workouts/track/" + workoutLog.getId();
    }

    @GetMapping("/track/{logId}")
    public String trackWorkout(@PathVariable Long logId, Model model) {
        WorkoutLog workoutLog = workoutLogRepository.findById(logId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid log Id:" + logId));

        model.addAttribute("workoutLog", workoutLog);
        return "workout-track";
    }

    @PostMapping("/complete-set/{logId}/{setId}")
    public String completeSet(@PathVariable Long logId, @PathVariable Long setId) {
        WorkoutLog workoutLog = workoutLogRepository.findById(logId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid log Id:" + logId));

        workoutLog.getCompletedSets().stream()
                .filter(set -> set.getId().equals(setId))
                .findFirst()
                .ifPresent(set -> set.setCompleted(true));

        workoutLogRepository.save(workoutLog);
        return "redirect:/workouts/track/" + logId;
    }

    @GetMapping("/complete-workout/{logId}")
    public String completeWorkout(@PathVariable Long logId) {
        WorkoutLog workoutLog = workoutLogRepository.findById(logId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid log Id:" + logId));

        workoutLog.setEndTime(LocalDateTime.now());
        workoutLog.setTotalDuration(Duration.between(workoutLog.getStartTime(), workoutLog.getEndTime()));
        workoutLogRepository.save(workoutLog);
        return "redirect:/workouts/logs";
    }

    @GetMapping("/logs")
    public String viewWorkoutLogs(Model model) {
        model.addAttribute("workoutLogs", workoutLogRepository.findAll());
        return "workout-logs";
    }

}
