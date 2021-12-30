package com.village.api.dao;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.village.api.model.User;

@Repository
public class UserDAO {

	private static Map<String, User> db = new HashMap<>();

	public UserDAO() {
		User rafael = new User("rafavilvert@gmail.com", "123456");
		db.put(rafael.getEmail(), rafael);
	}

	public User getUser(String email) {
		return db.get(email);
	}

}
