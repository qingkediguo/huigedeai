package com.service;

import java.util.List;

import com.domain.Person;

public interface Service {
	public Person search(int quan) throws Exception;
	public List<Object> getAllId() throws Exception;
	public List<Person> getAllBean() throws Exception;
}
