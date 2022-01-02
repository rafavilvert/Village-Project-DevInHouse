package com.village.api.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.village.api.model.transport.CitizenDetailDTO;
import com.village.api.model.transport.CitizensDTO;
import com.village.api.model.transport.CreateCitizenDTO;

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
			PreparedStatement pStmt = connection.prepareStatement("SELECT * FROM citizen WHERE name ILIKE ?");
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

	public CitizensDTO create(CreateCitizenDTO createCitizen) throws SQLException {
		try (Connection connection = new ConnectionFactoryJDBC().getConnection()) {
			PreparedStatement pStmt = connection.prepareStatement(
					"INSERT INTO citizen (name, lastname, cpf, income, expense, datanascimento) VALUES(?, ?, ?, ?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);

			pStmt.setString(1, createCitizen.getName());
			pStmt.setString(2, createCitizen.getLastname());
			pStmt.setString(3, createCitizen.getCpf());
			pStmt.setDouble(4, createCitizen.getIncome());
			pStmt.setDouble(5, createCitizen.getExpense());
			pStmt.setDate(6, Date.valueOf(createCitizen.getDataNascimento()));

			pStmt.execute();
			
			CitizensDTO citizen = null;

			ResultSet resultSet = pStmt.getGeneratedKeys();
			while (resultSet.next()) {
				String name = resultSet.getString("name");
				String lastname = resultSet.getString("lastname");
				String cpf = resultSet.getString("cpf");
				double income = resultSet.getDouble("income");
				double expense = resultSet.getDouble("expense");
				Date datanascimento = resultSet.getDate("datanascimento");
				citizen = new CitizensDTO(name, lastname, cpf, income, expense, datanascimento);
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
