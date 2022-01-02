package com.village.api.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.springframework.context.annotation.Bean;

public class ConnectionFactoryJDBC {
	
	@Bean
	public Connection getConnection() throws SQLException {
		return DriverManager.getConnection(
				"jdbc:postgresql://localhost:5432/village", 
				"postgres", 
				"postgres");
	}

}
