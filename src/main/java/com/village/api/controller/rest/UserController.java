package com.village.api.controller.rest;

import java.util.Set;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.village.api.controller.service.UserService;
import com.village.api.model.User;

@RestController
@RequestMapping("user")
public class UserController {

	private UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping
	public Set<User> listUsers() {
		return null;
	}

	@GetMapping("/{name}")
	public User getUser(@PathVariable("name") String name) {
		return userService.getUser(name);
	}

}
