package com.village.api.model;

import java.util.Date;

public class Citizen {

	private Integer id;
	private String name;
	private String lastname;
	private String CPF;
	private String income;
	private Double costs;
	private Date dataNascimento;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastname() {
		return lastname;
	}
	
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public void setSurname(String lastname) {
		this.lastname = lastname;
	}

	public String getCPF() {
		return CPF;
	}

	public void setCPF(String cpf) {
		this.CPF = cpf;
	}

	public String getIncome() {
		return income;
	}

	public void setIncome(String income) {
		this.income = income;
	}

	public Double getCosts() {
		return costs;
	}

	public void setCosts(Double costs) {
		this.costs = costs;
	}

	public Date getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

}
