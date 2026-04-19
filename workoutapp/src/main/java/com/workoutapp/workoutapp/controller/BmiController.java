package com.workoutapp.workoutapp.controller;

import com.workoutapp.workoutapp.handler.BmiClassificationHandler;
import com.workoutapp.workoutapp.handler.ElderlyBmiHandler;
import com.workoutapp.workoutapp.handler.PaediatricBmiHandler;
import com.workoutapp.workoutapp.handler.StandardBmiHandler;
import com.workoutapp.workoutapp.model.BmiForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class BmiController {

    /**
     * Chain of Responsibility Pattern (Behavioural).
     *
     * The chain is: PaediatricBmiHandler → ElderlyBmiHandler → StandardBmiHandler.
     * Each handler either handles the request (if its age condition matches)
     * or passes it to the next link. The controller has no if-else ladder —
     * it simply calls classify() on the head of the chain.
     */
    private final BmiClassificationHandler classificationChain;

    public BmiController() {
        PaediatricBmiHandler paediatric = new PaediatricBmiHandler();
        ElderlyBmiHandler elderly = new ElderlyBmiHandler();
        StandardBmiHandler standard = new StandardBmiHandler();

        paediatric.setNext(elderly);
        elderly.setNext(standard);

        classificationChain = paediatric;
    }

    @GetMapping("/bmi")
    public String showBMICalculatorForm(Model model) {
        model.addAttribute("bmiForm", new BmiForm());
        return "bmi";
    }

    @PostMapping("/bmi")
    public String calculateBMI(@ModelAttribute BmiForm bmiForm, Model model) {
        double raw = bmiForm.getWeight() / (bmiForm.getHeight() * bmiForm.getHeight());
        double bmi = Math.round(raw * 10.0) / 10.0;

        String bmiCategory = classificationChain.classify(bmi, bmiForm.getAge(), bmiForm.getGender());

        model.addAttribute("bmi", bmi);
        model.addAttribute("bmiCategory", bmiCategory);
        return "bmi";
    }
}
