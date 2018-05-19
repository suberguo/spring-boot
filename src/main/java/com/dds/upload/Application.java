package com.dds.upload;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	/*@Bean
	CommandLineRunner init(StorageService storageService) {
		try {
			return (args) -> {
				storageService.deleteAll();
				storageService.init();

			};
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}*/
}
