package com.buyg.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WelcomeController {

	@ResponseBody
	@GetMapping(value = "/welcome")
	public String welcome() {
		return "Welcome to Spring Boot ";
	}

	@ResponseBody
	@GetMapping(value = "/")
	public String defaultPage() {
		return "Welcome to Spring Boot default page";
	}
}