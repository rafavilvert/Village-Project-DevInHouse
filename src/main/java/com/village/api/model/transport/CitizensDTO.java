package com.village.api.model.transport;

import java.util.Objects;

public class CitizensDTO {
	
	private Integer id;
	private String name;
	private String lastname;
	private String cpf;
	private String income;
	private String dataNascimento;
	
	public CitizensDTO() {
	}

	public CitizensDTO(String name, String lastname, String cpf, String income, String dataNascimento) {
		this.name = name;
		this.lastname = lastname;
		this.cpf = cpf;
		this.income = income;
		this.dataNascimento = dataNascimento;
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

	public String getIncome() {
		return income;
	}

	public void setIncome(String income) {
		this.income = income;
	}

	public String getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(String dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	@Override
	public int hashCode() {
		return Objects.hash(lastname, name);
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
		return Objects.equals(lastname, other.lastname) && Objects.equals(name, other.name);
	}

	@Override
	public String toString() {
		return "CitizensDTO [id=" + id + ", name=" + name + ", lastname=" + lastname + ", cpf=" + cpf + ", income="
				+ income + ", dataNascimento=" + dataNascimento + "]";
	}
}
