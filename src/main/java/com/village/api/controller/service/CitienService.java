package com.village.api.controller.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.village.api.dao.CitizensDAO;
import com.village.api.model.transport.CitizensDTO;

@Service
public class CitienService {
	
	@Autowired
	private CitizensDAO citizenDAO;
	
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

}
