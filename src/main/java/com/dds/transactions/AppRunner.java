package com.dds.transactions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
public class AppRunner implements CommandLineRunner {

	private final static Logger logger = LoggerFactory.getLogger(AppRunner.class);
	private final BookingsService bookService;

	public AppRunner(BookingsService bookService) {
		this.bookService = bookService;
	}

	@Override
	public void run(String... args) throws Exception {
		bookService.book("Alice", "Bob", "Carol");
		Assert.isTrue(bookService.listAllBooks().size() == 3, "First booking should work with no problem");
		logger.info("Alice, Bob and Carol have been booked");
		try {
			bookService.book("Chris", "Samuel");
		} catch (RuntimeException e) {
			logger.info("v--- The following exception is expect because 'Samuel' is too " + "big for the DB ---v");
			logger.error(e.getMessage());
		}

		for (String person : bookService.listAllBooks()) {
			logger.info("So far, " + person + " is booked.");
		}
		logger.info("You shouldn't see Chris or Samuel. Samuel violated DB constraints, "
				+ "and Chris was rolled back in the same TX");
		Assert.isTrue(bookService.listAllBooks().size() == 3, "'Samuel' should have triggered a rollback");

		try {
			bookService.book("Buddy", null);
		} catch (RuntimeException e) {
			logger.info("v--- The following exception is expect because null is not " + "valid for the DB ---v");
			logger.error(e.getMessage());
		}

		for (String person : bookService.listAllBooks()) {
			logger.info("So far, " + person + " is booked.");
		}
		logger.info("You shouldn't see Buddy or null. null violated DB constraints, and "
				+ "Buddy was rolled back in the same TX");
		Assert.isTrue(bookService.listAllBooks().size() == 3, "'null' should have triggered a rollback");

	}

}
