package com.dds.batchservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

public class PersonItemProcessor implements ItemProcessor<Person, Person> {
	protected final static Logger logger = LoggerFactory.getLogger(PersonItemProcessor.class);

	@Override
	public Person process(final Person person) throws Exception {
		String firstName = person.getFirstName();
		String lastName = person.getLastName();
		final Person transformPerson = new Person(firstName, lastName);
		logger.info("Transformed " + transformPerson.toString());
		return transformPerson;
	}

}
