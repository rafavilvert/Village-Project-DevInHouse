package com.village.api.controller.util;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JWTUtil {

	private String secret;
	private Long expiration;

	public JWTUtil(@Value("${jwt.secret}") String secret, 
					@Value("${jwt.expiration}") Long expiration) {
		this.secret = secret;
		this.expiration = expiration;
	}
	
	public String generateToken(String email) {
		return Jwts
				.builder()
					.setSubject(email)
						.setExpiration(new Date(System.currentTimeMillis() + expiration))
							.signWith(SignatureAlgorithm.HS512, secret).compact();
	}

}
