package com.cuder.controller;

import java.util.Date;
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

import com.cuder.model.Allowance;
import com.cuder.model.BasicSalary;
import com.cuder.model.Contract;
import com.cuder.model.ContractType;
import com.cuder.model.Manager;
import com.cuder.model.Staff;

@Controller
@RequestMapping(value = "/salary")
public class SalaryController {

	RestTemplate template = new RestTemplate();

	@GetMapping("basic")
	public String showBasicSalary(Model model) {

		// get all Basic Salary
		List<BasicSalary> basicSalaries = template.getForObject("http://localhost:8081/basicSalary", List.class);
		model.addAttribute("basicSalaries", basicSalaries);

		return "/basic_Salary/basic_Salary.html";
	}

	@GetMapping("/basic/insert")
	public String showInsertForm(Model model) {

		model.addAttribute("bs", new BasicSalary());

		return "/basic_Salary/insert.html";
	}

	@PostMapping("/basic/insert")
	public String addBasic(@ModelAttribute BasicSalary bs, @RequestParam Map<String, String> requestParams) {

		System.out.println(bs);

//		Date date =new Date();
//		bs.setCreateDate(new java.sql.Date(date.getTime()));

		bs = template.postForObject("http://localhost:8081/basicSalary", bs, BasicSalary.class);

		System.out.println(bs);
		return "redirect:../basic/";

	}

	// ALLOWANCE
	@GetMapping("allowance")
	public String showAllowance(Model model) {

		// get all Basic Salary
		List<Allowance> allowances = template.getForObject("http://localhost:8081/allowance", List.class);
		model.addAttribute("allowances", allowances);
		model.addAttribute("allo", new Allowance());
		return "/allowance/allowance.html";
	}
	// Thêm


	@PostMapping("/allowance/insert")
	public String addAllownace(@ModelAttribute Allowance allo, @RequestParam Map<String, String> requestParams) {

		System.out.println(allo);

//		Date date =new Date();
//		bs.setCreateDate(new java.sql.Date(date.getTime()));

		allo = template.postForObject("http://localhost:8081/allowance", allo, Allowance.class);

		System.out.println(allo);
		return "redirect:../allowance/";
	}

}
