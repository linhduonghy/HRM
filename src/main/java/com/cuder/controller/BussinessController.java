package com.cuder.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import com.cuder.model.Appointment;
import com.cuder.model.Department;
import com.cuder.model.Manager;
import com.cuder.model.Staff;
import com.cuder.model.Title;

@Controller
@RequestMapping(value = "/bussiness")
public class BussinessController {

	private String apiUrl = "http://localhost:8081";
	RestTemplate rest = new RestTemplate();

	@GetMapping("/appoint")
	public String showAppointForm(Model model) {
		Appointment[] appointments = rest.getForObject(apiUrl + "/appointment", Appointment[].class);
		model.addAttribute("appointments", appointments);
//		System.err.println(appointments);
		return "bussiness/appoint.html";
	}

	// add new appointment
	@GetMapping("/appoint-staff")
	public String showAppointStaffForm(Model model) {

		Staff[] staffs = rest.getForObject(apiUrl + "/staff", Staff[].class);
		model.addAttribute("staffs", staffs);

		Title[] titles = rest.getForObject(apiUrl + "/title", Title[].class);
		model.addAttribute("titles", titles);

		Department[] departments = rest.getForObject(apiUrl + "/department", Department[].class);
		model.addAttribute("departments", departments);

		model.addAttribute("appointment", new Appointment());

		return "bussiness/appoint-staff.html";
	}

	@PostMapping("/appoint-staff")
	public String appointStaff(@ModelAttribute("appointment") Appointment appointment,
			@RequestParam("department_id") Integer department_id) {
		
		Manager m = new Manager();
		m.setId("QL1");
		appointment.setManager(m);
		
		Department d = rest.getForObject(apiUrl+"/department/{id}", Department.class, department_id);
		Staff s = rest.getForObject(apiUrl+"/staff/{id}", Staff.class, appointment.getStaff().getId());
		s.getMember().setDepartment(d);
		
		appointment = rest.postForObject(apiUrl+"/appointment", appointment, Appointment.class);
		System.out.println(appointment);
		
		rest.put(apiUrl+"/staff/{id}", s, s.getId());
		
		return "redirect:/bussiness/appoint";
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
