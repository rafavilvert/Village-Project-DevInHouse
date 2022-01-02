package com.village.api.controller.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.village.api.controller.util.ValidationUtil;
import com.village.api.dao.UserDAO;
import com.village.api.dao.UserSpringSecurity;
import com.village.api.model.User;

@Service
public class UserService implements UserDetailsService {

	private UserDAO userDAO;
	
	private PasswordEncoder passwordEncoder;

	public UserService(UserDAO userDAO) {
		this.userDAO = userDAO;
		this.passwordEncoder = new BCryptPasswordEncoder();
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

	public void create(String email, String password, List<String> roles, Integer citizenId) throws SQLException, IllegalAccessException {
		String passwordEnconde = passwordEncoder.encode(password);
		if (!ValidationUtil.isValidUsername(email)) {
			throw new IllegalAccessException("Email inválido");
		}
		if (!ValidationUtil.isValidPassword(password)) {
			throw new IllegalAccessException("Senha incorreta");
		} 
		userDAO.createUser(email, passwordEnconde, roles, citizenId);
		
	}

	public Optional<User> getByUserId(Integer id) throws SQLException {
		return this.userDAO.getByUserId(id);
		
	}

	public void deleteByCitizenId(Integer citizenId) throws SQLException {
		userDAO.deleteUser(citizenId);
	}

}
