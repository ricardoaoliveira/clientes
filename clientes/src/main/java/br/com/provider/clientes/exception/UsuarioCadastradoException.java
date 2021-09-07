package br.com.provider.clientes.exception;

public class UsuarioCadastradoException extends RuntimeException{

	public UsuarioCadastradoException(String login) {
		super("Usuario ja cadastrado: " + login);
	}
	
}
