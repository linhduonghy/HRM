package com.cuder.controller;

import org.springframework.stereotype.Controller;

@Controller
public class HomeController {

	public String showHome() {
		return "index";
	}
}
