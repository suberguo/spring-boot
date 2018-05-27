package com.dds.mustache;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class WelcomeController {

	@Value("${app.welcome.title}")
	private String title = "";

	@Value("${app.welcome.message}")
	private String message = "";

	@RequestMapping("/")
	public String welcome(Map<String, String> map) {
		map.put("title", title);
		map.put("message", message);
		return "welcome";
	}

	@RequestMapping("/5xx")
	public String ServiceUnavailable() {
		throw new RuntimeException("ABC");
	}

}
