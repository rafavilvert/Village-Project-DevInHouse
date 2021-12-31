package com.village.api.controller.service;

import org.springframework.mail.SimpleMailMessage;

import com.village.api.model.User;



public interface EmailService {
	
	void sendEmail(SimpleMailMessage message);
	
	void sendNewPassword(User user, String newPassword);

}
