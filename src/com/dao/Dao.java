package com.dao;

import java.util.List;
import java.util.Map;

import com.domain.Person;

public interface Dao {
	public Person findPerson(String id) throws Exception;
	public List<Object> findPersonId() throws Exception;
	public void alterCount(String id, int count) throws Exception;
	public List<Person> getBeanList() throws Exception;
	public List<Map<String, Object>> getRanObj() throws Exception;
}
