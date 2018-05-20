package com.dds.securing;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/securing/home").setViewName("home");
		registry.addViewController("/securing/").setViewName("home");
		registry.addViewController("/securing/hello").setViewName("hello");
		registry.addViewController("/securing/login").setViewName("login");
	}

}
