package com.cuder.controller;

import java.util.ArrayList;
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
import com.cuder.model.Staff;
import com.cuder.model.Title;

@Controller
@RequestMapping(value = "/employee")
public class EmployeeController {
	RestTemplate rest = new RestTemplate();

	// get list staff
	@GetMapping("")
	public String showAppointForm(Model model) {
		// add model staff
		Staff[] staffs = rest.getForObject("http://localhost:8081/staff", Staff[].class);
		model.addAttribute("staffs", staffs);
		
		List<Title> titles = new ArrayList<Title>();
		
		for(int i =0;i < staffs.length;i++) {
			
			List<Appointment> appoint = staffs[i].getAppointments();
			titles.add(appoint.get(appoint.size()-1).getTitle());
		}
		model.addAttribute("titles", titles);
		
		return "employee/employee.html";
	}
	
	@GetMapping("/profile/{id}")
	public String showFrofile(Model model,@PathVariable("id") int id	) {
		// add model staff
		Staff staff = rest.getForObject("http://localhost:8081/staff/{id}", Staff.class,id);
		List<Appointment> appoint = staff.getAppointments();
		String title = appoint.get(appoint.size()-1).getTitle().getTitle_name();
		model.addAttribute("staff",staff);
		model.addAttribute("title",title);
		
		return "employee/edit.html";
	}

}
