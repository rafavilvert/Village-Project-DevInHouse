package com.village.api.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.village.api.model.transport.CitizenDetailDTO;
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

	public List<CitizenDetailDTO> listCitizens(Integer id) throws SQLException {

		try (Connection connection = new ConnectionFactoryJDBC().getConnection()) {
			PreparedStatement prepareStatement = connection.prepareStatement("SELECT * FROM citizen WHERE id=?");
			prepareStatement.setInt(1, id);
			prepareStatement.execute();
			ResultSet resultSet = prepareStatement.getResultSet();

			List<CitizenDetailDTO> citizens = new ArrayList<>();
			while (resultSet.next()) {
				String name = resultSet.getString("name");
				String lastname = resultSet.getString("lastname");
				String cpf = resultSet.getString("cpf");
				Double income = resultSet.getDouble("income");
				Double expense = resultSet.getDouble("expense");
				Date dataNascimento = resultSet.getDate("datanascimento");

				CitizenDetailDTO citizen = new CitizenDetailDTO(name, lastname, cpf, income, expense, dataNascimento);

				citizens.add(citizen);

			}
			return citizens;
		}

	}

	public List<CitizensDTO> listAllCitizens() throws SQLException {

		try (Connection connection = new ConnectionFactoryJDBC().getConnection()) {
			PreparedStatement prepareStatement = connection.prepareStatement("SELECT * FROM citizen");
			prepareStatement.execute();
			ResultSet resultSet = prepareStatement.getResultSet();

			List<CitizensDTO> citizens = new ArrayList<>();
			while (resultSet.next()) {

				CitizensDTO citizen = extractedCitizen(resultSet);

				citizens.add(citizen);

			}
			return citizens;
		}

	}

	public Optional<CitizensDTO> findById(Integer id) throws SQLException {
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

	public List<CitizensDTO> getCitizensByName(String name) throws SQLException {
		try (Connection connection = new ConnectionFactoryJDBC().getConnection()) {
			PreparedStatement pStmt = connection.prepareStatement("SELECT * FROM citizen WHERE name LIKE ?");
			pStmt.setString(1, name + "%");
			pStmt.execute();
			ResultSet resultSet = pStmt.getResultSet();
			List<CitizensDTO> citizens = new ArrayList<>();
			while (resultSet.next()) {
				citizens.add(extractedCitizen(resultSet));
			}
			return citizens;
		}
	}

	public List<CitizensDTO> getCitizensByMonth(Integer month) throws SQLException {
		try (Connection connection = new ConnectionFactoryJDBC().getConnection()) {
			PreparedStatement pStmt = connection
					.prepareStatement("SELECT * FROM citizen WHERE date_part('month', (dataNascimento))=?");
			pStmt.setInt(1, month);
			pStmt.execute();
			ResultSet resultSet = pStmt.getResultSet();
			List<CitizensDTO> citizens = new ArrayList<>();
			while (resultSet.next()) {
				citizens.add(extractedCitizen(resultSet));
			}
			return citizens;
		}
	}

	public List<CitizensDTO> getCitizensByAge(Integer age) throws SQLException {
		try (Connection connection = new ConnectionFactoryJDBC().getConnection()) {
			PreparedStatement pStmt = connection
					.prepareStatement("SELECT * FROM citizen WHERE date_part('year', age(dataNascimento)) >= ?");
			pStmt.setInt(1, age);
			pStmt.execute();
			ResultSet resultSet = pStmt.getResultSet();
			List<CitizensDTO> citizens = new ArrayList<>();
			while (resultSet.next()) {
				citizens.add(extractedCitizen(resultSet));
			}
			return citizens;
		}
	}

	public CitizensDTO create(CitizensDTO citizen) throws SQLException {
		try (Connection connection = new ConnectionFactoryJDBC().getConnection()) {
			PreparedStatement pStmt = connection.prepareStatement(
					"INSERT INTO citizen (name, lastname, cpf, income, expense, datanascimento) VALUES(?, ?, ?, ?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);

			pStmt.setString(1, citizen.getName());
			pStmt.setString(2, citizen.getLastname());
			pStmt.setString(3, citizen.getCpf());
			pStmt.setDouble(4, citizen.getIncome());
			pStmt.setDouble(5, citizen.getExpense());
			pStmt.setDate(6, new java.sql.Date(citizen.getDataNascimento().getTime()));

			pStmt.execute();

			ResultSet resultSet = pStmt.getGeneratedKeys();
			while (resultSet.next()) {
				Integer id = resultSet.getInt(1);
				citizen.setId(id);
			}
			return citizen;
		}

	}
	
	public String delete(Integer id) throws SQLException {
		try (Connection connection = new ConnectionFactoryJDBC().getConnection()) {
			PreparedStatement prepareStatement = connection.prepareStatement("DELETE FROM citizen WHERE id = ?");
			prepareStatement.setInt(1, id);
			prepareStatement.execute();
			String successesDeleted = "Usuário deletado com sucesso";
			return successesDeleted;
		}
	}

	private CitizensDTO extractedCitizen(ResultSet resultSet) throws SQLException {
		int id = resultSet.getInt("id");
		String name = resultSet.getString("name");
		String lastname = resultSet.getString("lastname");
		String cpf = resultSet.getString("cpf");
		Double income = resultSet.getDouble("income");
		Double expense = resultSet.getDouble("expense");
		Date dataNascimento = resultSet.getDate("datanascimento");
		CitizensDTO citizen = new CitizensDTO(name, lastname, cpf, income, expense, dataNascimento);
		citizen.setId(id);
		return citizen;
	}

}
