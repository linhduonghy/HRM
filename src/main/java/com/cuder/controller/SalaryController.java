package com.cuder.controller;

import java.sql.Date;
import java.util.Arrays;
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
import com.cuder.model.Department;
import com.cuder.model.Salary;

@Controller
@RequestMapping(value = "/salary")
public class SalaryController {

	RestTemplate template = new RestTemplate();

	private String apiUrl = "http://42.118.109.231:8081/";

	@GetMapping
	public String showSalaryForm(Model model) {
		List<Salary> salaries = Arrays.asList(template.getForObject("http://42.118.109.231:8081/salary", Salary[].class));
		model.addAttribute("salaries", salaries);

		List<Department> departments = Arrays
				.asList(template.getForObject("http://42.118.109.231:8081/department", Department[].class));
		model.addAttribute("departments", departments);

		BasicSalary[] basicSalaries = template.getForObject(apiUrl + "/basicSalary", BasicSalary[].class);
		model.addAttribute("basicSaralies", basicSalaries);

		Allowance[] allowances = template.getForObject(apiUrl + "/allowance", Allowance[].class);
		model.addAttribute("allowances", allowances);

		model.addAttribute("salary", new Salary());

		return "/salary/detail_salary.html";
	}

	@PostMapping("/calculate_salary")
	public String calculateSalary(@ModelAttribute("salary") Salary salary) {
		System.err.println(salary);

		Allowance allowance = template.getForObject(apiUrl + "/allowance/{id}", Allowance.class,
				salary.getAllowance().getAllowance_code());
		BasicSalary basicSalary = template.getForObject(apiUrl + "/basicSalary/{id}", BasicSalary.class,
				salary.getBasicSalary().getBasic_salary_name());

		float s = salary.getCoefficientBasicSalary() * basicSalary.getBasic_salary_value()
				+ allowance.getAllowance_value();
		
		salary.setSalary(s);
		
		salary.setCreatedDate(new Date(System.currentTimeMillis()));
		
		salary = template.postForObject(apiUrl+"/salary", salary, Salary.class);
		
		System.err.println(salary);
		
		return "redirect:/salary";
	}

	@PostMapping("/detail")
	public String detailSalary(@ModelAttribute Salary salary) {
		template.put("http://42.118.109.231:8081/salary/{id}", salary, salary.getMember().getId());
		return "salary/staff-salary.html";
	}

	@GetMapping("basic")
	public String showBasicSalary(Model model, HttpSession session) {

		// get all Basic Salary
		List<BasicSalary> basicSalaries = template.getForObject("http://42.118.109.231:8081/basicSalary", List.class);
		model.addAttribute("basicSalaries", basicSalaries);
		model.addAttribute("bs", new BasicSalary());
		if (session.getAttribute("msg") != null) {
			model.addAttribute("msg", session.getAttribute("msg"));
			session.removeAttribute("msg");
		}

		return "/basic_Salary/basic_Salary.html";
	}

	@PostMapping("/basic/insert")
	public String addBasic(@ModelAttribute BasicSalary bs, @RequestParam Map<String, String> requestParams,
			HttpSession session) {

		System.out.println(bs);

//		Date date =new Date();
//		bs.setCreateDate(new java.sql.Date(date.getTime()));

		bs = template.postForObject("http://42.118.109.231:8081/basicSalary", bs, BasicSalary.class);
		session.setAttribute("msg", "1");
		System.out.println(bs);
		return "redirect:../basic/";

	}

	// ALLOWANCE
	@GetMapping("allowance")
	public String showAllowance(Model model, HttpSession session) {

		// get all Basic Salary
		List<Allowance> allowances = template.getForObject("http://42.118.109.231:8081/allowance", List.class);
		model.addAttribute("allowances", allowances);
		if (session.getAttribute("msg") != null) {
			model.addAttribute("msg", session.getAttribute("msg"));
			session.removeAttribute("msg");
		}
		model.addAttribute("allo", new Allowance());
		return "/allowance/allowance.html";
	}

	@PostMapping("/allowance/insert")
	public String addAllownace(@ModelAttribute Allowance allo, @RequestParam Map<String, String> requestParams) {

		System.out.println(allo);

//		Date date =new Date();
//		bs.setCreateDate(new java.sql.Date(date.getTime()));

		allo = template.postForObject("http://42.118.109.231:8081/allowance", allo, Allowance.class);

		System.out.println(allo);
		return "redirect:../allowance/";
	}

	@GetMapping("/allowance/delete/{id}")
	public String delete(@PathVariable("id") String id, HttpSession session) {
		template.delete("http://42.118.109.231:8081/allowance/{id}", id);
		session.setAttribute("msg", "1");
		return "redirect:../";
	}

	@GetMapping("/allowance/edit/{id}")
	public String editAllowance(@PathVariable("id") String id, Model model) {
		List<Allowance> allowances = template.getForObject("http://42.118.109.231:8081/allowance", List.class);
		model.addAttribute("allowances", allowances);

		model.addAttribute("allo", template.getForObject("http://42.118.109.231:8081/allowance/{id}", Allowance.class, id));
		return "/allowance/edit.html";
	}

	@PostMapping("/allowance/edit")
	public String editAllownace(@ModelAttribute Allowance allo, @RequestParam Map<String, String> requestParams,
			HttpSession session) {

		System.out.println(allo);

		template.put("http://42.118.109.231:8081/allowance/{id}", Allowance.class, allo.getAllowance_code());

		System.out.println(allo);
		session.setAttribute("msg", "1");
		return "redirect:../";
	}

}