package com.village.api.dao;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import com.village.api.model.User;

@Repository
public class UserDAO {

	private static Map<String, User> db = new HashMap<>();

	public UserDAO() {
		BCryptPasswordEncoder pe = new BCryptPasswordEncoder();
		User rafael = new User("rafavilvert@gmail.com", pe.encode("123456"), Set.of("USER", "ADMIN"));
		User vilvert = new User("vilvert@gmail.com", pe.encode("1234"), Set.of("USER"));
		db.put(rafael.getEmail(), rafael);
		db.put(vilvert.getEmail(), vilvert);
	}
	
	public void updateUser(User user) {
		db.replace(user.getEmail(), user);
	}

	public User getUser(String email) {
		return db.get(email);
	}

}
