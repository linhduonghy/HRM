package com.cuder.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import com.cuder.model.RecruitmentPosition;
import com.cuder.model.RecruitmentTerm;
import com.cuder.model.Title;

@Controller
@RequestMapping("/recruitment")
public class RecruitmentController {
	
	@Value("${apiUrl}")
	private String apiUrl;
	
	RestTemplate template = new RestTemplate();
	
	// recruitment term
	@GetMapping("")
	public String showRecruitmentTerm(Model model) {
//		System.out.println(apiUrl);
		List<RecruitmentTerm> terms = template.getForObject(apiUrl+"/recruitment-term", List.class);
		model.addAttribute("terms", terms);
		return "/recruitment/recruitment.html";
	}
	// show insert term view
	@GetMapping("/insert-term")
	public String showInsertTerm(Model model) {
		model.addAttribute("recruitmentTerm", new RecruitmentTerm());
		return "/recruitment/insertTerm.html";
	}
	// handle post req creat term
	@PostMapping("/insert-term")
	public String insertTerm(@ModelAttribute RecruitmentTerm recruitmentTerm) {
		recruitmentTerm = template.postForObject(apiUrl+"/recruitment-term", recruitmentTerm, RecruitmentTerm.class);
//		System.out.println(recruitmentTerm);
		return "redirect:/recruitment";
	}
	
	// show insert recruitment position
	@GetMapping("/insert-recruitment-position")
	public String showInsertPosition(Model model) {
		List<Title> titles = template.getForObject(apiUrl+"/title", List.class);
		List<RecruitmentTerm> terms = template.getForObject(apiUrl+"/recruitment-term", List.class);
		model.addAttribute("titles", titles);
		model.addAttribute("terms", terms);
		model.addAttribute("recruitmentPosition", new RecruitmentPosition());
		return "/recruitment/insert_recruitment_position.html";
	}
	
	// handle post req create recruitment position
	@PostMapping("/insert-recruitment-position")
	public String insertTerm(@ModelAttribute RecruitmentPosition recruitmentPosition) {
		recruitmentPosition = template.postForObject(apiUrl+"/recruitment-position", recruitmentPosition, RecruitmentPosition.class);
//		System.out.println(recruitmentPosition);
		return "redirect:/recruitment";
	}
	
	// show recruitment term detail
	@GetMapping("/term/{id}")
	public String showTermDetail(Model model, @PathVariable Integer id) {

		RecruitmentTerm term = template.getForObject(apiUrl+"/recruitment-term/{id}", RecruitmentTerm.class, id);

		model.addAttribute("term", term);
		return "/recruitment/recruitment_term_detail.html";
	}
	
}
