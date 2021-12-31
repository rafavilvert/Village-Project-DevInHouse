package com.village.api.controller.rest;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.village.api.controller.service.UserService;
import com.village.api.controller.util.JWTUtil;
import com.village.api.dao.UserSpringSecurity;

@RestController
@RequestMapping("/auth")
public class AuthController {
	
	private JWTUtil jwtUtil;
	
	public AuthController(JWTUtil jwtUtil) {
		this.jwtUtil = jwtUtil;
	}

	@PostMapping("/refresh-token")
	public ResponseEntity<Void> refreshToken(HttpServletResponse response) {
		UserSpringSecurity userSpringSecurity = UserService.authenticated();
		String newToken = jwtUtil.generateToken(userSpringSecurity.getUsername());
		response.addHeader("Authorization", "Bearer " + newToken);
		return ResponseEntity.noContent().build();
	}

}
