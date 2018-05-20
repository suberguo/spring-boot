package com.dds.transactions;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class BookingsService {

	private final static Logger logger = LoggerFactory.getLogger(BookingsService.class);

	private final JdbcTemplate jdbcTemplate;

	@Autowired
	public BookingsService(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Transactional
	public void book(String... books) {
		for (String book : books) {
			logger.info("saved " + book);
			this.jdbcTemplate.update("INSERT INTO BOOKINGS(FIRST_NAME) VALUES(?)", book);
		}
	}

	public List<String> listAllBooks() {
		return this.jdbcTemplate.query("SELECT FIRST_NAME FROM BOOKINGS", (r, s) -> {
			return r.getString("FIRST_NAME");
		});
	}

}
