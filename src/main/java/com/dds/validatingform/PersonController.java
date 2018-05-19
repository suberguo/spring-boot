package com.dds.validatingform;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Controller
public class PersonController implements WebMvcConfigurer {

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/results").setViewName("results");
	}

	@GetMapping("/")
	public String showForm(PersonVO person) {
		return "personForm";
	}

	@PostMapping("/")
	public String checkPerson(PersonVO person, BindingResult result) {
		if (result.hasErrors()) {
			return "personForm";
		}
		return "redirect:/results";
	}

}
