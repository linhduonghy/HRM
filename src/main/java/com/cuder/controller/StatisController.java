package com.cuder.controller;

import java.util.ArrayList;
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
import com.cuder.model.Department;
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
		List<Staff> staffs = new ArrayList<Staff>();

		Date utilDate = new Date();
		Date when = new java.sql.Date(utilDate.getTime());
		System.err.println(when);

		int a[][] = { { 2019, 0 }, { 2020, 0 }, { 2021, 0 } };
		Contract[] contracts = template.getForObject("http://localhost:8081/contract", Contract[].class);
		for (int i = 0; i < contracts.length; i++) {

			if (contracts[i].getContract_end_date().after(when) && contracts[i].getContract_signing_date().before(when)
					&& contracts[i].getContractType().getId() == 1) {
				staffs.add(contracts[i].getStaff());

			}
			Calendar cal = Calendar.getInstance();
			cal.setTime(contracts[i].getContract_signing_date());
			int start = cal.get(Calendar.YEAR);
			cal.setTime(contracts[i].getContract_end_date());
			int end = cal.get(Calendar.YEAR);
			for (int j = 0; j < a.length; j++) {

				if ((contracts[i].getContractType().getId() == 1) && a[j][0] >= start && a[j][0] <= end) {

					a[j][1]++;
				}

			}
		}

		model.addAttribute("a", a);
		model.addAttribute("staffs", staffs);
		return "/statistical/employee/a.html";
	}

	@GetMapping("employee/{year}")
	public String showBarChart(Model model, HttpSession session, @PathVariable("year") String year) {

		// get all Basic Salary
//		List<BasicSalary> basicSalaries = template.getForObject("http://localhost:8081/basicSalary", List.class);
//		model.addAttribute("basicSalaries", basicSalaries);
//		model.addAttribute("bs", new BasicSalary());
//		if(session.getAttribute("msg")!=null) {
//			model.addAttribute("msg",session.getAttribute("msg"));
//			session.removeAttribute("msg");
//			}
//		
		List<Staff> staffs = new ArrayList<Staff>();

		String s1 = year + "-12-31";
		String s2 = year + "-01-01";
		Date end2 = java.sql.Date.valueOf(s1);
		Date start2 = java.sql.Date.valueOf(s2);
		

		int a[][] = { { 2019, 0 }, { 2020, 0 }, { 2021, 0 } };
		Contract[] contracts = template.getForObject("http://localhost:8081/contract", Contract[].class);
		for (int i = 0; i < contracts.length; i++) {

			if ((contracts[i].getContract_signing_date().before(start2)|| contracts[i].getContract_end_date().after(end2))
					&& contracts[i].getContractType().getId() == 1) {
				staffs.add(contracts[i].getStaff());

			}
			Calendar cal = Calendar.getInstance();
			cal.setTime(contracts[i].getContract_signing_date());
			int start = cal.get(Calendar.YEAR);
			cal.setTime(contracts[i].getContract_end_date());
			int end = cal.get(Calendar.YEAR);
			for (int j = 0; j < a.length; j++) {

				if ((contracts[i].getContractType().getId() == 1) && a[j][0] >= start && a[j][0] <= end) {

					a[j][1]++;
				}

			}
		}
		model.addAttribute("a", a);
		model.addAttribute("staffs", staffs);
		model.addAttribute("year", year);

		return "/statistical/employee/year.html";
	}

	@GetMapping("department")
	public String showDepartment(Model model, HttpSession session) {

		Department[] departments = template.getForObject("http://localhost:8081/department", Department[].class);

		int n = departments.length;
		String[] dp = new String[n];
		int[] number = new int[n];

		for (int i = 0; i < n; i++) {
			dp[i] = departments[i].getDepartment_name();
		}

		// DS Nhân viên hiện tại
		List<Staff> staffs = new ArrayList<Staff>();

//		Date utilDate = new Date();
//		Date when = new java.sql.Date(utilDate.getTime());
		String s = "2021-12-31";
		Date when = java.sql.Date.valueOf(s);
		System.err.println(when);
		Contract[] contracts = template.getForObject("http://localhost:8081/contract", Contract[].class);

		for (int i = 0; i < contracts.length; i++) {

			if (contracts[i].getContract_signing_date().before(when) && contracts[i].getContractType().getId() == 1) {
				staffs.add(contracts[i].getStaff());

			}
		}

		for (int i = 0; i < staffs.size(); i++) {
			for (int j = 0; j < n; j++) {

				if (staffs.get(i).getMember().getDepartment().getDepartment_name().equals(dp[j])) {

					number[j]++;
				}
			}
		}

		model.addAttribute("dp", dp);
		model.addAttribute("number", number);

////		if(session.getAttribute("msg")!=null) {
////			model.addAttribute("msg",session.getAttribute("msg"));
////			session.removeAttribute("msg");
////			}
////		
//		List<Staff> staffs = new ArrayList<Staff>();
//		
//		Date utilDate = new Date();
//		Date when =  new java.sql.Date(utilDate.getTime());
//		System.err.println(when);
//		
//		
//		int a[][] = { { 2019, 0 }, { 2020, 0 }, { 2021, 0 } };
//		Contract[] contracts = template.getForObject("http://localhost:8081/contract", Contract[].class);
//		for (int i = 0; i < contracts.length; i++) {
//
//			if (contracts[i].getContract_end_date().after(when) && contracts[i].getContract_signing_date().before(when) && contracts[i].getContractType().getId()==1) {
//				staffs.add(contracts[i].getStaff());
//				
//			}
//			for (int j = 0; j < a.length; j++) {
//				
//				Calendar cal = Calendar.getInstance();
//				cal.setTime(contracts[i].getContract_signing_date());
//				if ((contracts[i].getContractType().getId() == 1)
//						&& (cal.get(Calendar.YEAR) == a[j][0])) {
//					a[j][1]++;
//				}
//			}
//
//		}
//		
//		model.addAttribute("a", a);
//		model.addAttribute("staffs", staffs);
		return "/statistical/department/a.html";
	}

}
