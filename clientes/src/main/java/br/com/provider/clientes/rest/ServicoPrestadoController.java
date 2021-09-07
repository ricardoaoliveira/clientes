package br.com.provider.clientes.rest;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.com.provider.clientes.model.entity.Cliente;
import br.com.provider.clientes.model.entity.ServicoPrestado;
import br.com.provider.clientes.model.repository.ClienteRepository;
import br.com.provider.clientes.model.repository.ServicoPrestadoRepository;
import br.com.provider.clientes.rest.dto.ServicoPrestadoDTO;
import br.com.provider.clientes.util.BigDecimalConverter;

@RestController
@RequestMapping("/api/servicos-prestados")
public class ServicoPrestadoController {
	
	private final ServicoPrestadoRepository repository;
	private final ClienteRepository clienteRepository;
	
	@Autowired
	private BigDecimalConverter bigDecimalConverter;

	@Autowired
	public ServicoPrestadoController(ServicoPrestadoRepository repository, ClienteRepository clienteRepository) {
		this.repository = repository;
		this.clienteRepository = clienteRepository;
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ServicoPrestado salvar(@RequestBody ServicoPrestadoDTO dto) {
		LocalDate data = LocalDate.parse(dto.getData(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		
		Integer idCliente = dto.getIdCliente();
		
		Cliente cliente = 
				clienteRepository.findById(idCliente)
				.orElseThrow(() -> 
					new ResponseStatusException(
							HttpStatus.BAD_REQUEST, "Cliente inexistente"));
		
		ServicoPrestado servicoPrestado = new ServicoPrestado();
		servicoPrestado.setCliente(cliente);
		servicoPrestado.setDescricao(dto.getDescricao());
		servicoPrestado.setValor(bigDecimalConverter.converter(dto.getPreco()));
		servicoPrestado.setData(data);
		
		return repository.save(servicoPrestado);
	}
	
	@GetMapping
	public List<ServicoPrestado> pesquisar(
			@RequestParam(value = "nome", required = false) String nome,
			@RequestParam(value = "mes", required = false) Integer mes) {
		
		return repository.findByNomeClienteAndMes("%" + nome + "%", mes);
	}
	
	@GetMapping("{id}")
	public ServicoPrestado acharPorId(@PathVariable Integer id) {
		return repository.findById(id).orElseThrow( 
				() -> new ResponseStatusException(HttpStatus.NOT_FOUND) );
	}

	@DeleteMapping("{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deletar(@PathVariable Integer id) {
		repository
			.findById(id)
			.map( Servico -> {
				repository.delete(Servico);
				return Void.TYPE;
			})
			.orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND) );
	}
	
	@PutMapping("{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void atualizar(@PathVariable Integer id, @RequestBody @Valid ServicoPrestado ServicoAtualizado) {
		repository
			.findById(id)
			.map( Servico -> {
				ServicoAtualizado.setId(Servico.getId());
				return repository.save(ServicoAtualizado);
			})
			.orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND) );
	}
	
}
