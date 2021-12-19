package com.village.api.exceptions;

public class CitizensNotFoundException extends Exception {
	private static final long serialVersionUID = 1L;

	public CitizensNotFoundException() {
		super("Não foram encontrados cidadãos no banco");
	}

}
