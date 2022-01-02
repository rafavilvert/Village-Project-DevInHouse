package com.village.api.model.transport;

import java.util.Date;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonFormat;

public class CreateCitizenDTO {

	private String name;
	private String lastname;
	private String cpf;
	private Double income;
	private Double expense;
	private Date dataNascimento;
	private String email;
	private String password;
	private List<String> roles;

	public CreateCitizenDTO(String name, String lastname, String cpf, Double income, Double expense,
			Date dataNascimento, String email, String password, List<String> roles) {
		super();
		this.name = name;
		this.lastname = lastname;
		this.cpf = cpf;
		this.income = income;
		this.expense = expense;
		this.dataNascimento = dataNascimento;
		this.email = email;
		this.password = password;
		this.roles = roles;
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

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public Double getIncome() {
		return income;
	}

	public void setIncome(Double income) {
		this.income = income;
	}

	public Double getExpense() {
		return expense;
	}

	public void setExpense(Double expense) {
		this.expense = expense;
	}

	public Date getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}

	@Override
	public int hashCode() {
		return Objects.hash(cpf);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CreateCitizenDTO other = (CreateCitizenDTO) obj;
		return Objects.equals(cpf, other.cpf);
	}

	@Override
	public String toString() {
		return "CreateCitizenDTO [name=" + name + ", lastname=" + lastname + ", cpf=" + cpf + ", income=" + income
				+ ", expense=" + expense + ", dataNascimento=" + dataNascimento + ", email=" + email + ", password="
				+ password + ", roles=" + roles + "]";
	}

}
