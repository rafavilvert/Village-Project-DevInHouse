package com.village.api.controller.service;

import java.sql.SQLException;
import java.util.ArrayList;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

	public void updateUser(User user) {
//		userDAO.updateUser(user);
	}

	public User getUser(String email) throws SQLException {
		return userDAO.getUser(email);
	}

	public static UserSpringSecurity authenticated() {
		try {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			return new UserSpringSecurity((String) authentication.getPrincipal(), null, new ArrayList<>());
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		try {
			User user = getUser(username);
			return new UserSpringSecurity(user.getEmail(), user.getPassword(), user.getRoles());
		} catch (SQLException e) {

			throw new UsernameNotFoundException(username);
		}

	}

}
