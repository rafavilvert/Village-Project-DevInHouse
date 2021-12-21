package com.village.api.controller.rest;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
	
	@GetMapping("/filter")
	public List<CitizensDTO> getCitizensByFilter(@RequestParam("name") String name)
			throws SQLException {
		return citizenService.getCitizensByFilter(name);
	}
	
	@PostMapping("/create")
	public ResponseEntity<CitizensDTO> createNewCitizen(@RequestBody CitizensDTO citizen) throws SQLException, IllegalAccessException {
		CitizensDTO citizenCreated = this.citizenService.create(citizen);
		if (citizenCreated == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
		return ResponseEntity.status(HttpStatus.CREATED).body(citizenCreated);
	}

}
