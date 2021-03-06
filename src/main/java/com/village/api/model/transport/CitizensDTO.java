package com.village.api.model.transport;

import java.util.Date;
import java.util.Objects;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CitizensDTO {

	private Integer id;
	private String name;
	private String lastname;
	private String cpf;
	private Double income;
	private Double expense;
	private Date birthDate;
	private String email;
	private Set<String> roles;

	public CitizensDTO() {
	}

	public CitizensDTO(String name, String lastname, String cpf, Double income, Double expense, Date birthDate) {
		this.name = name;
		this.lastname = lastname;
		this.cpf = cpf;
		this.income = income;
		this.birthDate = birthDate;
		this.expense = expense;
	}

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
		return birthDate;
	}

	public void setDataNascimento(Date birthDate) {
		this.birthDate = birthDate;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Set<String> getRoles() {
		return roles;
	}

	public void setRoles(Set<String> roles) {
		this.roles = roles;
	}

	@Override
	public int hashCode() {
		return Objects.hash(cpf, lastname, name);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CitizensDTO other = (CitizensDTO) obj;
		return Objects.equals(cpf, other.cpf) && Objects.equals(lastname, other.lastname)
				&& Objects.equals(name, other.name);
	}

	@Override
	public String toString() {
		return "CitizensDTO [id=" + id + ", name=" + name + ", lastname=" + lastname + ", cpf=" + cpf + ", income="
				+ income + ", expense=" + expense + ", dataNascimento=" + birthDate + "]";
	}

}
