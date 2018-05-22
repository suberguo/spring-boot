package com.dds.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
	private final CustomProperty customProperty;
	private final GlobalProperty globalProperty;
	private final DDSProperty ddsProperty;

	@Autowired
	public HomeController(CustomProperty prop, GlobalProperty globalProperty,DDSProperty ddsProperty) {
		this.customProperty = prop;
		this.globalProperty = globalProperty;
		this.ddsProperty = ddsProperty;
	}

	@GetMapping("/c")
	public String custom() {
		return customProperty.toString();
	}

	@GetMapping("/g")
	public String global() {
		return globalProperty.toString();
	}
	
	@GetMapping("/d")
	public String dds() {
		return ddsProperty.toString();
	}

}
