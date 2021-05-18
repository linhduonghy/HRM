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

import com.cuder.model.Appointment;
import com.cuder.model.Department;
import com.cuder.model.Staff;

@Controller
@RequestMapping(value = "/bussiness")
public class BussinessController {
	RestTemplate rest = new RestTemplate();
	
	// get list staff
	@GetMapping("/appoint")
	public String showAppointForm(Model model) {
		// add model staff
		List<Staff> staffs = rest.getForObject("http://localhost:8081/staff", List.class);
		model.addAttribute("staffs", staffs);		
		return "bussiness/appoint.html";
	}
	
	// add new appointment
	@GetMapping("/appoint-staff/{id}")
	public String showAppointStaffForm(@PathVariable int id, Model model) {
		Staff staff = rest.getForObject("http://localhost:8081/staff/{id}", Staff.class, id);
		model.addAttribute("staff", staff);
		return "bussiness/appoint-staff.html";
	}
	
	@PostMapping("/appoint-staff")
	public String appointStaff(@ModelAttribute("staff") Staff staff, Model model) {
		rest.put("http://localhost:8081/appointment/{id}", staff, staff.getId());
		return "bussiness/appoint.html";
	}
	
	
	
	
	// Lay-off
	@GetMapping("/lay-off")
	public String showLayOffForm(Model model) {
		// add model staff
		List<Staff> staffs = rest.getForObject("http://localhost:8081/staff", List.class);
		model.addAttribute("staffs", staffs);
		
		return "redirect:/lay-off";
	}
	
	@PostMapping("/lay-off")
	public String layOffStaff(@ModelAttribute Staff staff, @RequestParam String reason) {

		
		return "redirect:/lay-off";
	}
}
