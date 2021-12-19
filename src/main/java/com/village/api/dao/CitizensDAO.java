package com.village.api.dao;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.village.api.model.transport.CitizensDTO;

@Repository
public class CitizensDAO {

	public List<String> listCitzensNames() throws SQLException {

		try (Connection connection = new ConnectionFactoryJDBC().getConnection()) {

			Statement statement = connection.createStatement();
			statement.execute("SELECT name FROM citizen");
			ResultSet resultSet = statement.getResultSet();

			List<String> names = new ArrayList<>();
			while (resultSet.next()) {
				String name = resultSet.getString("name");
				names.add(name);

			}
			return names;
		}
	}

	public List<CitizensDTO> listCitizens() throws SQLException {

		try (Connection connection = new ConnectionFactoryJDBC().getConnection()) {

			Statement statement = connection.createStatement();
			statement.execute("SELECT * FROM citizen");
			ResultSet resultSet = statement.getResultSet();

			List<CitizensDTO> citizens = new ArrayList<>();
			while (resultSet.next()) {
				CitizensDTO citizen = extractedCitizen(resultSet);

				citizens.add(citizen);

			}
			return citizens;
		}

	}

	public Optional<CitizensDTO> findById(Long id) throws SQLException {
		try (Connection connection = new ConnectionFactoryJDBC().getConnection()) {
			PreparedStatement prepareStatement = connection.prepareStatement("SELECT * FROM citizen WHERE id=?");
			prepareStatement.setLong(1, id);
			prepareStatement.execute();
			ResultSet resultSet = prepareStatement.getResultSet();
			CitizensDTO citizen = null;
			while (resultSet.next()) {
				citizen = extractedCitizen(resultSet);
			}
			return Optional.ofNullable(citizen);
		}
	}

	private CitizensDTO extractedCitizen(ResultSet resultSet) throws SQLException {
		int id = resultSet.getInt("id");
		String name = resultSet.getString("name");
		String lastname = resultSet.getString("cpf");
		String cpf = resultSet.getString("name");
		String income = resultSet.getString("income");
		String dataNascimento = resultSet.getString("datanascimento");
		CitizensDTO citizen = new CitizensDTO(name, lastname, cpf, income, dataNascimento);
		citizen.setId(id);
		return citizen;
	}

}
