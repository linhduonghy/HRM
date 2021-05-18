package com.cuder.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import com.cuder.model.Staff;

@Controller
@RequestMapping(value = "/employee")
public class EmployeeController {
	RestTemplate rest = new RestTemplate();

	// get list staff
	@GetMapping("")
	public String showAppointForm(Model model) {
		// add model staff
		List<Staff> staffs = rest.getForObject("http://localhost:8081/staff", List.class);
		model.addAttribute("staffs", staffs);
		return "employee/employee.html";
	}

}
