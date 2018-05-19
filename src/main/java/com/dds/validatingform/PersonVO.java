package com.dds.validatingform;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class PersonVO {

	@NotNull
	@Size(min = 2, max = 30)
	private String name;

	@NotNull
	@Min(18)
	private int age;

	@Override
	public String toString() {
		return "PersonVO [name=" + name + ", age=" + age + "]";
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}
}
