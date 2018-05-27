package com.dds.securing.database;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class WelcomeController {

	@RequestMapping("/")
	public String welcome() {
		return "hello";
	}
	
	@RequestMapping("/login")
	public String login() {
		return "/login";
	}
	
}
