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

import com.cuder.model.Contract;
import com.cuder.model.ContractType;
import com.cuder.model.Manager;
import com.cuder.model.Staff;

@Controller
@RequestMapping(value = "/contract")
public class ContractController {
	
	RestTemplate template = new RestTemplate();
	
	
	@GetMapping("/insert")
	public String showContractForm(Model model) {
		
		// add model staffs
		List<Staff> staffs = template.getForObject("http://localhost:8081/staff", List.class);
		model.addAttribute("staffs", staffs);
		
		// add model contractTypes
		List<ContractType> contractTypes = template.getForObject("http://localhost:8081/contractType", List.class);
		model.addAttribute("contractTypes", contractTypes);
		
		// add new contract to form
		model.addAttribute("contract", new Contract());
		
		return "/contract/insert.html";
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
