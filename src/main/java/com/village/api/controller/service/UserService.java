package com.village.api.controller.service;

import java.util.ArrayList;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.village.api.dao.UserDAO;
import com.village.api.dao.UserSpringSecurity;
import com.village.api.model.User;

@Service
public class UserService implements UserDetailsService {

	private UserDAO userDAO;

	public UserService(UserDAO userDAO) {
		this.userDAO = userDAO;
	}

	public User getUser(String email) {
		return userDAO.getUser(email);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = getUser(username);
		if (user == null) {
			throw new UsernameNotFoundException(username);
		}
		
		return new UserSpringSecurity(user.getEmail(), user.getPassword(), new ArrayList<>());
	}

}
