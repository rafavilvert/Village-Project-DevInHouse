package com.village.api.controller.rest;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.village.api.controller.service.AuthService;
import com.village.api.controller.service.UserService;
import com.village.api.controller.util.JWTUtil;
import com.village.api.dao.UserSpringSecurity;
import com.village.api.model.transport.MailDTO;

@RestController
@RequestMapping("/auth")
public class AuthController {
	
	private JWTUtil jwtUtil;
	
	private AuthService authService;
	
	public AuthController(JWTUtil jwtUtil, AuthService authService) {
		this.jwtUtil = jwtUtil;
		this.authService = authService;
	}

	@PostMapping("/refresh-token")
	public ResponseEntity<Void> refreshToken(HttpServletResponse response) {
		UserSpringSecurity userSpringSecurity = UserService.authenticated();
		String newToken = jwtUtil.generateToken(userSpringSecurity.getUsername());
		response.addHeader("Authorization", "Bearer " + newToken);
		return ResponseEntity.noContent().build();
	}
	
	@PostMapping("/forgot")
	public ResponseEntity<Void> forgot(@RequestBody MailDTO mail) {
		authService.sendNewPass(mail.getEmail());
		return ResponseEntity.noContent().build();
	}

}
