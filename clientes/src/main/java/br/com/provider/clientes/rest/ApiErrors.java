package br.com.provider.clientes.rest;

import java.util.Arrays;
import java.util.List;

public class ApiErrors {

	private List<String> errors;
	
	public ApiErrors(List<String> errors) {
		this.errors = errors;
	}
	
	public ApiErrors(String error) {
		this.errors = Arrays.asList(error);
	}

	public List<String> getErros() {
		return errors;
	}

}
