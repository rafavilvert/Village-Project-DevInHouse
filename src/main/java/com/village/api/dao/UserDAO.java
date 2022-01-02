package com.village.api.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.village.api.model.User;

@Repository
public class UserDAO {

	private Connection connection;
	
	
	
	public UserDAO() throws SQLException {
		this.connection = new ConnectionFactoryJDBC().getConnection();
	}
	
	public User getUser(String email) throws SQLException {

		try (PreparedStatement prestatement = connection.prepareStatement("SELECT * FROM \"user\" WHERE email=?")) {
			
			prestatement.setString(1, email);
			prestatement.execute();
			ResultSet resultSet = prestatement.getResultSet();

			User user = null;
			
			while (resultSet.next()) {
				final String[] stringsArr = (String[]) resultSet.getArray("roles").getArray();
				Set<String> roles = Arrays.stream(stringsArr).collect(Collectors.toSet());
				user = new User(resultSet.getString("email"), resultSet.getString("password"), roles);

			}
			return user;
		}
	}

//	
//	public void updateUser(User user) {
//		db.replace(user.getEmail(), user);
//	}

}
