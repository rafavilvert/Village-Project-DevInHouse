package com.village.api.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.village.api.model.User;
import com.village.api.model.transport.CitizensDTO;
import com.village.api.model.transport.CreateCitizenDTO;

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

	public void createUser(String email, String passwordEnconde, List<String> roles, Integer citizenId) throws SQLException {
		try (PreparedStatement prestatement = connection.prepareStatement("INSERT INTO \"user\" (email, password, roles, citizen_id) VALUES(?, ?, ?, ?)",
				Statement.RETURN_GENERATED_KEYS)) {
			
			prestatement.setString(1, email);
			prestatement.setString(2, passwordEnconde);
			prestatement.setArray(3 , connection.createArrayOf("VARCHAR", roles.toArray()));
			prestatement.setInt(4, citizenId);
			prestatement.execute();
		}		
		
	}

//	
//	public void updateUser(User user) {
//		db.replace(user.getEmail(), user);
//	}

}
