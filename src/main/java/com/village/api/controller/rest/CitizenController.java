package com.village.api.controller.rest;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.village.api.controller.service.CitizenService;
import com.village.api.exceptions.CitizensNotFoundException;
import com.village.api.model.transport.CitizenDetailDTO;
import com.village.api.model.transport.CitizensDTO;
import com.village.api.model.transport.CreateCitizenDTO;
import com.village.api.model.transport.VillageReportDTO;

@RestController
@RequestMapping("/citizens")
public class CitizenController {
	
	@Autowired
	private CitizenService citizenService;
	
	@GetMapping("/list-names")
	public List<String> listNames() throws SQLException, CitizensNotFoundException {
		return citizenService.listCitizensNames();
	}
	
	@GetMapping("/list-citizens/{id}")
	public List<CitizenDetailDTO> listCitizensDetails(@PathVariable("id") Integer id) throws SQLException, CitizensNotFoundException {
		return citizenService.listCitizens(id);
	}
	
	@GetMapping("/list-all")
	public List<CitizensDTO> listAll() throws SQLException, CitizensNotFoundException {
		return citizenService.listAllCitizens();
	}
	
	@GetMapping("/{id}")
	public CitizensDTO getById(@PathVariable("id") Integer id) throws SQLException {
		return citizenService.getById(id);
	}
	
	@GetMapping("/filter")
	public List<CitizensDTO> getCitizensByFilter(@RequestParam("name") String name)
			throws SQLException {
		return citizenService.getCitizensByName(name);
	}
	
	@GetMapping("/filter-month")
	public List<CitizensDTO> getCitizensByMonth(@RequestParam("month") Integer month)
			throws SQLException {
		return citizenService.getCitizensByMonth(month);
	}
	
	@GetMapping("/filter-age")
	public List<CitizensDTO> getCitizensByAge(@RequestParam("age") Integer age)
			throws SQLException {
		return citizenService.getCitizensByAge(age);
	}
	
	@PreAuthorize("hasAnyRole('ADMIN')")
	@PostMapping("/create")
	public ResponseEntity<CitizensDTO> createNewCitizen(@RequestBody CreateCitizenDTO citizen) throws SQLException, IllegalAccessException {
		CitizensDTO citizenCreated = this.citizenService.create(citizen);
		if (citizenCreated == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
		return ResponseEntity.status(HttpStatus.CREATED).body(citizenCreated);
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> delete(@PathVariable("id") Integer id) throws SQLException, IllegalAccessException {
		String citizenCreated = this.citizenService.delete(id);
		System.out.println(citizenCreated);
		if (citizenCreated == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
		return ResponseEntity.status(HttpStatus.OK).body(citizenCreated);
	}
	
	@PreAuthorize("hasAnyRole('ADMIN')")
	@GetMapping("/village-report")
	public VillageReportDTO getReport() throws SQLException, CitizensNotFoundException, IllegalAccessException {
		return citizenService.getReport();
	}

}
