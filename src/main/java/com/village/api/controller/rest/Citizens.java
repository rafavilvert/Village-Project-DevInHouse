package com.village.api.controller.rest;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.village.api.controller.service.CitienService;
import com.village.api.exceptions.CitizensNotFoundException;
import com.village.api.model.transport.CitizensDTO;

@RestController
@RequestMapping("/citizens")
public class Citizens {
	
	@Autowired
	private CitienService citizenService;
	
	@GetMapping("/list-names")
	public List<String> listAll() throws SQLException, CitizensNotFoundException {
		return citizenService.listCitizens();
	}
	
	@GetMapping("/{id}")
	public CitizensDTO getById(@PathVariable("id") Long id) throws SQLException {
		return citizenService.getById(id);
	}

}
