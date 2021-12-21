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
	
	public List<CitizensDTO> getAvengersByFilter(String name) throws SQLException {
		try (Connection connection = new ConnectionFactoryJDBC().getConnection()) {
			PreparedStatement pStmt = 
					connection.prepareStatement("SELECT * FROM citizen WHERE name LIKE ?");
			pStmt.setString(1, name + "%");
			pStmt.execute();
			ResultSet resultSet = pStmt.getResultSet();
			List<CitizensDTO> avengers = new ArrayList<>();
			while (resultSet.next()) {
				avengers.add(extractedCitizen(resultSet));
			}
			return avengers;
		}
	}
	
	public CitizensDTO create(CitizensDTO citizen) throws SQLException {
		try (Connection connection = new ConnectionFactoryJDBC().getConnection()) {
			PreparedStatement pStmt = connection.prepareStatement(
					"INSERT INTO citizen (name, lastname, cpf, income, datanascimento) VALUES(?, ?, ?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);

			pStmt.setString(1, citizen.getName());
			pStmt.setString(2, citizen.getLastname());
			pStmt.setString(3, citizen.getCpf());
			pStmt.setString(4, citizen.getIncome());
			pStmt.setString(5, citizen.getDataNascimento());

			pStmt.execute();

			ResultSet resultSet = pStmt.getGeneratedKeys();
			while (resultSet.next()) {
				Integer id = resultSet.getInt(1);
				citizen.setId(id);
			}
			return citizen;
		}

	}

	private CitizensDTO extractedCitizen(ResultSet resultSet) throws SQLException {
		int id = resultSet.getInt("id");
		String name = resultSet.getString("name");
		String lastname = resultSet.getString("lastname");
		String cpf = resultSet.getString("cpf");
		String income = resultSet.getString("income");
		String dataNascimento = resultSet.getString("datanascimento");
		CitizensDTO citizen = new CitizensDTO(name, lastname, cpf, income, dataNascimento);
		citizen.setId(id);
		return citizen;
	}

}
