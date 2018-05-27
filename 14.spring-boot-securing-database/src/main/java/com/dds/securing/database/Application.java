package com.dds.securing.database;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootApplication
public class Application implements CommandLineRunner {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		this.jdbcTemplate
				.execute("create table if not exists DDS_USER(ID serial,USERNAME VARCHAR(50),PASSWORD VARCHAR(50))");
		System.out.println("create table...");
		List<Object[]> data = Arrays.asList("SUBERGUO 123456", "ZHANGSAN 12345").stream().map(s -> s.split(" "))
				.collect(Collectors.toList());
		this.jdbcTemplate.batchUpdate(
				  "INSERT INTO DDS_USER(USERNAME,PASSWORD) SELECT USERNAME,PASSWORD FROM(SELECT ? USERNAME,? PASSWORD FROM DUAL)T "
				+ "WHERE NOT EXISTS(SELECT 1 FROM DDS_USER A WHERE A.USERNAME=T.USERNAME)",
				data);
		System.out.println("inserted user...");
	}

	/*
	 * @Bean public UserService createUserService() { return new UserServiceImpl();
	 * }
	 */
}
