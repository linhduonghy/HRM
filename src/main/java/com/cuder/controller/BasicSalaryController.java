package com.cuder.controller;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import com.cuder.model.BasicSalary;
import com.cuder.model.Contract;
import com.cuder.model.ContractType;
import com.cuder.model.Manager;
import com.cuder.model.Staff;

@Controller
@RequestMapping(value = "/basicSalary")
public class BasicSalaryController {
	
	RestTemplate template = new RestTemplate();
	
	
	@GetMapping("")
	public String showBasicSalary(Model model) {
		
		
		// get all Basic Salary
		List<BasicSalary> basicSalaries = template.getForObject("http://localhost:8081/basicSalary", List.class);
		model.addAttribute("basicSalaries", basicSalaries);
		

		
		return "/basic_Salary/basic_Salary.html";
	}
	
	@GetMapping("insert")
	public String showInsertForm(Model model) {
		
	
		
		model.addAttribute("bs",  new BasicSalary());
		

		
		return "/basic_Salary/insert.html";
	}
	
	@PostMapping("/insert")
	public String addContract(@ModelAttribute Contract contract, @RequestParam Map<String, String> requestParams) {
		
		System.out.println(contract);

//		set manager 
		Manager m = new Manager();
		m.setId(1);
		contract.setManager(m);
		
		contract = template.postForObject("http://localhost:8081/contract", contract, Contract.class);
		
		System.out.println(contract);
		return "redirect:/contract/insert";
		
	}
	
}