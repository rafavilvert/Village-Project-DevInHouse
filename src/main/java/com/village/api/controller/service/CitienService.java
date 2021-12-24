package com.village.api.controller.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.village.api.dao.CitizensDAO;
import com.village.api.model.transport.CitizenDetailDTO;
import com.village.api.model.transport.CitizensDTO;
import com.village.api.model.transport.VillageReportDTO;

@Service
public class CitienService {

	@Value("${VILLAGE_BUDGET}")
	private Double TotalRevenueVillage;

	private CitizensDAO citizenDAO;

	public CitienService(CitizensDAO citizenDAO) {
		this.citizenDAO = citizenDAO;
	}

	public List<CitizenDetailDTO> listCitizens(Integer id) throws SQLException {
		return this.citizenDAO.listCitizens(id);
	}

	public List<CitizensDTO> listAllCitizens() throws SQLException {
		return this.citizenDAO.listAllCitizens();
	}

	public List<String> listCitizensNames() throws SQLException {
		return this.citizenDAO.listCitzensNames();
	}

	public CitizensDTO getById(Integer id) throws SQLException {
		if (id == null) {
			throw new IllegalArgumentException("O Id não pode ser nulo");
		}

		Optional<CitizensDTO> citizen = citizenDAO.findById(id);

		if (citizen.isPresent()) {
			return citizen.get();
		}
		return null;
	}

	public List<CitizensDTO> getCitizensByName(String name) throws SQLException {
		if (name == null || name.isEmpty()) {
			throw new IllegalArgumentException("O nome não pode ser Vazio!");
		}

		List<CitizensDTO> citizensFiltred = new ArrayList<>();
		citizensFiltred = this.citizenDAO.getCitizensByName(name);
		if (!citizensFiltred.isEmpty()) {
			return citizensFiltred;
		}

		return citizensFiltred;
	}

	public List<CitizensDTO> getCitizensByMonth(Integer month) throws SQLException {
		if (month == null) {
			throw new IllegalArgumentException("O nome não pode ser Vazio!");
		}

		List<CitizensDTO> citizensFiltred = new ArrayList<>();
		citizensFiltred = this.citizenDAO.getCitizensByMonth(month);
		if (!citizensFiltred.isEmpty()) {
			return citizensFiltred;
		}

		return citizensFiltred;
	}

	public List<CitizensDTO> getCitizensByAge(Integer age) throws SQLException {
		if (age == null) {
			throw new IllegalArgumentException("O nome não pode ser Vazio!");
		}

		List<CitizensDTO> citizensFiltred = new ArrayList<>();
		citizensFiltred = this.citizenDAO.getCitizensByAge(age);
		if (!citizensFiltred.isEmpty()) {
			return citizensFiltred;
		}

		return citizensFiltred;
	}

	public CitizensDTO create(CitizensDTO citizen) throws SQLException, IllegalAccessException {
		if (citizen == null) {
			throw new IllegalAccessException("O cidadão está nulo!");
		}
		return this.citizenDAO.create(citizen);
	}

	public String delete(Integer id) throws SQLException, IllegalAccessException {
		Optional<CitizensDTO> findCitizenToDelete = citizenDAO.findById(id);
		if (findCitizenToDelete == null || findCitizenToDelete.isEmpty()) {
			return "Usuário não encontrado";
		}

		return this.citizenDAO.delete(id);
	}

	public VillageReportDTO getReport() throws SQLException, IllegalAccessException {
		Double revenue = 0.0;
		Double expense = 0.0;
		Double mostExpenseCitizen = 0.0;

		List<CitizensDTO> citizens = this.listAllCitizens();
		VillageReportDTO villageReport = new VillageReportDTO();
		if (citizens == null || citizens.isEmpty()) {
			throw new IllegalAccessException("O relatório está nulo!");
		}

		for (CitizensDTO citizen : citizens) {

			System.out.println(citizen.getIncome() + revenue);
			if (citizen.getExpense() > mostExpenseCitizen) {
				villageReport.setMostExpenseCitizen(citizen.getExpense()); // Cidadao que gasta mais
			}

			revenue += citizen.getIncome();
			expense += citizen.getExpense();
			villageReport.setTotalRevenue(revenue); // Total de ganhos dos cidadãos
			villageReport.setDifferenceRevenueAndExpense(TotalRevenueVillage - expense); // Total de gastos dos Cidadãos

			villageReport.setBudget(TotalRevenueVillage); // Orçamento

		}

		villageReport.setBudget(TotalRevenueVillage);

		System.out.println(villageReport.getMostExpenseCitizen());
		return villageReport;

	}

}
