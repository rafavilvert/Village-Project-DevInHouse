package com.village.api.controller.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.village.api.dao.CitizensDAO;
import com.village.api.dao.ConnectionFactoryJDBC;
import com.village.api.model.transport.CitizensDTO;

@Service
public class CitienService {
	
	private CitizensDAO citizenDAO;
	
	public CitienService(CitizensDAO citizenDAO) {
		this.citizenDAO = citizenDAO;
	}
	
//	public List<Citizen> listAllCitizens() throws SQLException, CitizensNotFoundException {
//		List<Citizen> listAllCitizens = this.citizenDAO.listAll();
//		if (listAllCitizens.isEmpty()) {
//			System.out.println("Não foram encontrados cidadões no Banco");
//			throw new CitizensNotFoundException();
//		}
//		return listAllCitizens;
//		
//	}
	public List<String> listCitizens() throws SQLException {
		return this.citizenDAO.listCitzensNames();
	}
	
	public CitizensDTO getById(Long id) throws SQLException {
		if (id == null) {
			throw new IllegalArgumentException("O Id não pode ser nulo");
		}
		
		Optional<CitizensDTO> citizen = citizenDAO.findById(id);
		
		if (citizen.isPresent()) {
			return citizen.get();
		}
		return null;
	}
	
	public List<CitizensDTO> getCitizensByFilter(String name) throws SQLException {
		if (name == null || name.isEmpty()) {
			throw new IllegalArgumentException("O nome não pode ser Vazio!");
		}

		List<CitizensDTO> citizensFiltred = new ArrayList<>();
		citizensFiltred = this.citizenDAO.getAvengersByFilter(name);
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

}
