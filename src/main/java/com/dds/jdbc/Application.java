package com.dds.jdbc;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;

//@SpringBootApplication
public class Application implements CommandLineRunner {

	private final static Logger logger = LoggerFactory.getLogger(Application.class);

	public static final void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Autowired
	JdbcTemplate jdbcTemplate;

	@Override
	public void run(String... args) throws Exception {
		logger.info("create table");

		jdbcTemplate.execute("drop table customer if exists");
		jdbcTemplate.execute("create table customer(id serial,firstname varchar(255),lastname varchar(255))");

		List<Object[]> splitUpNames = Arrays.asList("John Woo", "Jeff Dean", "Josh Bloch", "Josh Long").stream()
				.map(name -> name.split(" ")).collect(Collectors.toList());

		jdbcTemplate.batchUpdate("insert into customer(firstname,lastname) values(?,?)", splitUpNames);

		splitUpNames.forEach(name -> {
			logger.info(" " + name[0] + "," + name[1]);
		});

		jdbcTemplate.query("select id,firstname,lastname from customer where firstname=?", new Object[] { "Josh" },
				(rst, c) -> new Customer(rst.getInt("id"), rst.getString("firstname"), rst.getString("lastname")))
				.forEach(c -> {
					logger.info(c.toString());
				});

	}

}
