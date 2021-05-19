package com.cuder.controller;

import java.sql.Date;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import com.cuder.model.Member;
import com.cuder.model.MemberTrainingCource;
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
		
		String[] memberStatuses = new String[cource.getMemberTraningCources().size()];
		
		for (int i = 0; i < memberStatuses.length; ++i) {
			Date date_now = new Date(System.currentTimeMillis());
			Date start_date = cource.getMemberTraningCources().get(i).getTraining_start_date();
			int diffInDays = (int)( (date_now.getTime() - start_date.getTime()) 
	                 / (1000 * 60 * 60 * 24) );
			if (diffInDays < cource.getDuration()) {
				memberStatuses[i] = "Chưa hoàn thành";
			} else {
				memberStatuses[i] = "Đã hoàn thành";
			}
		}
		model.addAttribute("statuses", memberStatuses);
		
		return "/training/training_cource_detail.html";
		
	}
	
	@GetMapping("/remove_training_cource/{cource_id}")
	public String removeTrainingCource(@PathVariable("cource_id") Integer cource_id) {
		
		rest.delete(apiUrl+"/training_cource/{cource_id}", cource_id);
		
		return "redirect:/training_cource";
	}
	@GetMapping("/insert_member")
	public String showInsertMember(Model model) {
		Member[] members = rest.getForObject(apiUrl+"/member", Member[].class);
		model.addAttribute("members", members);
		TrainingCource[] cources = rest.getForObject(apiUrl+"/training_cource", TrainingCource[].class);
		model.addAttribute("trainingCources", cources);
		model.addAttribute("memberTC", new MemberTrainingCource());
		return "training/insert_member_training_cource.html";
	}
	@PostMapping("/insert_member")
	public String insertMemberTrainingCource(@ModelAttribute("memberTC") MemberTrainingCource memberTC) {
		memberTC = rest.postForObject(apiUrl+"/memberTrainingCource", memberTC, MemberTrainingCource.class);
		System.err.println(memberTC);
		return "redirect:/training_cource";
	}
}
