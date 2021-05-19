package com.cuder.controller;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
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
@RequestMapping(value = "/statis")
public class StatisController {

	RestTemplate template = new RestTemplate();

	@GetMapping("employee")
	public String showBarChart(Model model, HttpSession session) {

		// get all Basic Salary
//		List<BasicSalary> basicSalaries = template.getForObject("http://localhost:8081/basicSalary", List.class);
//		model.addAttribute("basicSalaries", basicSalaries);
//		model.addAttribute("bs", new BasicSalary());
//		if(session.getAttribute("msg")!=null) {
//			model.addAttribute("msg",session.getAttribute("msg"));
//			session.removeAttribute("msg");
//			}
//		
		int a[][] = { { 2019, 0 }, { 2020, 0 }, { 2021, 0 },{ 2022, 0 } };
		Contract[] contracts = template.getForObject("http://localhost:8081/contract", Contract[].class);
		for (int i = 0; i < contracts.length; i++) {
			for (int j = 0; j < a.length; j++) {
				
				Calendar cal = Calendar.getInstance();
				cal.setTime(contracts[i].getContract_signing_date());
				if ((contracts[i].getContractType().getId() == 1)
						&& (cal.get(Calendar.YEAR) == a[j][0])) {
					a[j][1]++;
				}
			}

		}

		model.addAttribute("a", a);
		return "/statistical/a.html";
	}

}
