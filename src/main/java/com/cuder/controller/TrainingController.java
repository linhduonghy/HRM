package com.cuder.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import com.cuder.model.TrainingCource;

@Controller
@RequestMapping("/training_cource")
public class TrainingController {

	private String apiUrl = "http://localhost:8081/";

	RestTemplate rest = new RestTemplate();

	@GetMapping
	public String showTraining(Model model) {
		TrainingCource[] cources = rest.getForObject(apiUrl + "/training_cource", TrainingCource[].class);
		model.addAttribute("cources", cources);
		return "/training/training_cource.html";
	}

	@GetMapping("/insert_cource")
	public String showInsertCource(Model model) {

		model.addAttribute("cource", new TrainingCource());
		return "/training/insert_training_cource.html";
	}

	@PostMapping("/insert_cource")
	public String insertCource(@ModelAttribute("cource") TrainingCource cource) {
		cource = rest.postForObject(apiUrl + "/training_cource", cource, TrainingCource.class);
		System.err.println(cource);
		return "redirect:/training_cource";
	}

	@GetMapping("/training_cource_detail/{cource_id}")
	public String showTrainingCourceDetail(@PathVariable("cource_id") Integer cource_id, Model model) {

		TrainingCource cource = rest.getForObject(apiUrl + "/training_cource/{cource_id}", TrainingCource.class,
				cource_id);
		model.addAttribute("cource", cource);
		return "/training/training_cource_detail.html";
	}
}
