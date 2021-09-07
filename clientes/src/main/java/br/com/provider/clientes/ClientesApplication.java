package br.com.provider.clientes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import br.com.provider.clientes.model.entity.Cliente;
import br.com.provider.clientes.model.repository.ClienteRepository;

@SpringBootApplication
public class ClientesApplication {

	@Bean
	public CommandLineRunner run(@Autowired ClienteRepository repository) {
		return args -> {
			Cliente cliente = new Cliente();
			cliente.setCpf("29858141890");
			cliente.setNome("Ricardo Oliveira");
			repository.save(cliente);
		};
	}
	
	public static void main(String[] args) {
		SpringApplication.run(ClientesApplication.class, args);		
	}

}
