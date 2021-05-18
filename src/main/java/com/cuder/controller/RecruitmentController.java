package com.cuder.controller;

import java.util.ArrayList;
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

import com.cuder.model.Candidate;
import com.cuder.model.RecruitmentPosition;
import com.cuder.model.RecruitmentTerm;
import com.cuder.model.Title;

@Controller
@RequestMapping("/recruitment")
public class RecruitmentController {

	
	private String apiUrl = "http://localhost:8081/";

	RestTemplate template = new RestTemplate();

	// recruitment term
	@GetMapping("")
	public String showRecruitmentTerm(Model model) {
//		System.out.println(apiUrl);
		List<RecruitmentTerm> terms = template.getForObject(apiUrl + "/recruitment-term", List.class);
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
		recruitmentTerm = template.postForObject(apiUrl + "/recruitment-term", recruitmentTerm, RecruitmentTerm.class);
//		System.out.println(recruitmentTerm);
		return "redirect:/recruitment";
	}

	// show insert recruitment position
	@GetMapping("/insert-recruitment-position")
	public String showInsertPosition(Model model) {
		List<Title> titles = template.getForObject(apiUrl + "/title", List.class);
		List<RecruitmentTerm> terms = template.getForObject(apiUrl + "/recruitment-term", List.class);
		model.addAttribute("titles", titles);
		model.addAttribute("terms", terms);
		model.addAttribute("recruitmentPosition", new RecruitmentPosition());
		return "/recruitment/insert_recruitment_position.html";
	}

	// handle post req create recruitment position
	@PostMapping("/insert-recruitment-position")
	public String insertTerm(@ModelAttribute RecruitmentPosition recruitmentPosition) {
		recruitmentPosition = template.postForObject(apiUrl + "/recruitment-position", recruitmentPosition,
				RecruitmentPosition.class);
//		System.out.println(recruitmentPosition);
		return "redirect:/recruitment";
	}

	// show recruitment term detail
	@GetMapping("/term/{id}")
	public String showTermDetail(Model model, @PathVariable Integer id) {
		
		RecruitmentTerm term = template.getForObject(apiUrl + "/recruitment-term/{id}", RecruitmentTerm.class, id);
		
		System.err.println(term.getRecruitmentPositions());
		List<Integer> number_accepted_candidate = new ArrayList<Integer>();
		
		for (RecruitmentPosition position : term.getRecruitmentPositions()) {
			int count = 0;
			for (Candidate candidate : position.getCandidates()) {
				if (candidate.getStatus() != null && candidate.getStatus().equals("accepted")) {
					count += 1;
				}
			}
			number_accepted_candidate.add(count);
		}
		
		model.addAttribute("number_acc", number_accepted_candidate);
		model.addAttribute("term", term);

		return "/recruitment/recruitment_term_detail.html";
	}

	// show recruitment position detail
	@GetMapping("/term/{term_id}/position/{position_id}")
	public String showRecruitmentPositionDetail(Model model, @PathVariable("term_id") Integer term_id,
			@PathVariable("position_id") Integer position_id) {

		System.err.println("term " + term_id + "/ positon " + position_id);
		RecruitmentPosition position = template.getForObject(apiUrl + "/recruitment-position/{position_id}",
				RecruitmentPosition.class, position_id);

		
		List<Candidate> accepted_candidates = new ArrayList<Candidate>();
		List<Candidate> processing_candidates = new ArrayList<Candidate>();
		
		for (Candidate c : position.getCandidates()) {
			if (c.getStatus().equals("accepted")) {
				accepted_candidates.add(c);
			} else if (c.getStatus().equals("processing")) {
				processing_candidates.add(c);
			}
		}
		
		Integer number_acc = accepted_candidates.size();
		
		model.addAttribute("number_acc", number_acc);
		System.err.println("acc" + accepted_candidates);
		System.err.println("pro" + processing_candidates);
		model.addAttribute("accepted_candidates", accepted_candidates);
		model.addAttribute("processing_candidates", processing_candidates);

		model.addAttribute("position", position);

		return "/recruitment/position_detail.html";
	}

	// show view insert candidate in term
	@GetMapping("/insert-candidate")
	public String showInsertCandidateInTerm(Model model) {

		RecruitmentTerm[] terms = template.getForObject(apiUrl + "/recruitment-term", RecruitmentTerm[].class);

		RecruitmentPosition[] positions = template.getForObject(apiUrl + "/recruitment-position/term/{term_id}",
				RecruitmentPosition[].class, terms[0].getId());

		model.addAttribute("positions", positions);
		model.addAttribute("terms", terms);
		model.addAttribute("candidate", new Candidate());
		return "/recruitment/insertCandidate.html";
	}
	
//	// show candidate
//	@GetMapping("/candidate")
//	public String showCandidate() {
//		
//		return "/recruitment/
//	}
	// handle insert candidate
	@PostMapping("/insert-candidate")
	public String insertCandidate(@ModelAttribute("candidate") Candidate candidate) {

		candidate.setStatus("processing");
		candidate = template.postForObject(apiUrl+"/recruitment/candidate", candidate, Candidate.class);
		System.out.println(candidate);
		
		return "redirect:/recruitment/";
	}
	
	@GetMapping("/accept_candidate/{candidate_id}/{term_id}/{position_id}")
	public String acceptCandidate(@PathVariable("candidate_id") Integer candidate_id, @PathVariable("term_id") Integer term_id,
			@PathVariable("position_id") Integer position_id) {
		
		Candidate c = new Candidate();
		c.setId(candidate_id);
		
		c = template.postForObject(apiUrl+"/recruitment/candidate/accept/", c, Candidate.class);
		
		System.err.println(c);
		return "redirect:/recruitment/term/"+term_id+"/position/"+position_id;
	}
}