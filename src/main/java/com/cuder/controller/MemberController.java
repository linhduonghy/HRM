package com.cuder.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.websocket.Session;

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
import com.cuder.model.Appointment;
import com.cuder.model.Contract;
import com.cuder.model.Department;
import com.cuder.model.Manager;
import com.cuder.model.Member;
import com.cuder.model.Staff;
import com.cuder.model.Title;

@Controller
@RequestMapping(value = "")
public class MemberController {
	RestTemplate rest = new RestTemplate();

	private String apiUrl = "http://localhost:8081/";
	// get list staff
	@GetMapping("/login")
	public String showloginForm(Model model, HttpSession session) {
		// add model staff

		model.addAttribute("member", new Member());
		if (session.getAttribute("logMsg") != null) {
			String s = (String) session.getAttribute("logMsg");
			model.addAttribute("msg", s);
			session.removeAttribute("logMsg");
		}

		return "user/login.html";
	}

	@PostMapping("/login")
	public String showFrofile(Model model, @ModelAttribute("member") Member member, HttpSession session) {
		//

		Member logMember = rest.getForObject("http://localhost:8081/member/usrn/{usrn}", Member.class,
				member.getUsername());

		if (logMember == null) {
			session.setAttribute("logMsg", "user");

			return "redirect:/login";
		}

		if (!logMember.getPassword().equals(member.getPassword())) {
			session.setAttribute("logMsg", "password");

			return "redirect:/login";

		}
		session.setAttribute("user", logMember);
		if (logMember.getStaff() != null) {
			return "redirect:/profile/";
		}
		return "redirect:/";
	}
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		// add model staff
		session.removeAttribute("user");
		return "redirect:/login";
	}

	@GetMapping("/profile")
	public String showProfile() {
		// add model staff


		return "user/profile.html";
	}
	
	@GetMapping("/create_member") 
	public String showCreateMember(Model model) {
		
		Member[] members = rest.getForObject(apiUrl+"/member/not_user", Member[].class);
		model.addAttribute("members", members);
		
		Department[] departments = rest.getForObject(apiUrl+"/department", Department[].class);
		model.addAttribute("departments", departments);
		
		model.addAttribute("member", new Member());
		
		return "/user/create_member.html";
	}
	
	@PostMapping("/create_member") 
	public String createMember(@ModelAttribute("member") Member member, @RequestParam("member_permission") Integer permission) {
		
		System.err.println(member);
		System.err.println(permission);
		Member m = rest.getForObject(apiUrl+"/member/{id}", Member.class, member.getId());
		m.setUsername(member.getUsername());
		m.setPassword(member.getPassword());
		m.setDepartment(member.getDepartment());
		
		m = rest.postForObject(apiUrl+"/member/", m, Member.class);
		
		System.err.println(m);
		
		if (permission == 0) { // staff
			Staff[] ss = rest.getForObject(apiUrl+"/staff", Staff[].class);
			
			Staff s = new Staff();
			s.setMember(m);
			s.setId("NV"+String.valueOf(ss.length+1));
			s = rest.postForObject(apiUrl+"/staff", s, Staff.class);
			System.err.println(s);
		} else {
			Manager[] mm = rest.getForObject(apiUrl+"/manager", Manager[].class);
			Manager manager = new Manager();
			manager.setMember(m);
			manager.setId("QL"+mm.length);
			manager = rest.postForObject(apiUrl+"/manager", manager, Manager.class);
			System.err.println(manager);
		}
		return "redirect:/create_member";
	}
}