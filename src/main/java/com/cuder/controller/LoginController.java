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
import com.cuder.model.Member;
import com.cuder.model.Staff;
import com.cuder.model.Title;

@Controller
@RequestMapping(value = "/login")
public class LoginController {
	RestTemplate rest = new RestTemplate();

	// get list staff
	@GetMapping("")
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

	@PostMapping("/check")
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
		return "redirect:/";
	}

}
