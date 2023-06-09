package com.derso.vendas.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
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

import com.derso.vendas.domain.Cliente;
import com.derso.vendas.repository.ClientesRepository;

// @RestController = @Controller + @ResponseBody em todos os métodos
// @ResponseBody = serializa automaticamente a resposta em JSON e empacota
// em HttpResponse

@RestController
@RequestMapping("/clientes")
public class ClientesController {
	
	private ClientesRepository repositorio;
	
	@Autowired
	public ClientesController(ClientesRepository repositorio) {
		this.repositorio = repositorio;
	}
	
	@GetMapping
	public List<Cliente> listar(@RequestParam(name="nome", required=false) String nomeBusca) {
		return nomeBusca != null
				//? repositorio.findByNomeLikeIgnoreCase("%" + nomeBusca + "%")
				? repositorio.encontrarPorNome(nomeBusca)
				: repositorio.findAll();
	}
	
	// GET com objeto: já traduz os parâmetros nome, cpf, etc. na query string
	@GetMapping("/example")
	public List<Cliente> listarUsandoExample(Cliente filtro) {
		// Examples: realizando consultas a partir de um objeto
		ExampleMatcher matcher = ExampleMatcher
				.matching()
				.withIgnoreCase()  // todos os atributos String :)
				.withStringMatcher(StringMatcher.CONTAINING);
		Example<Cliente> example = Example.of(filtro, matcher);
		return repositorio.findAll(example);
	}
	
	// ResponseEntity: uma forma de controlar a resposta
	@GetMapping("/{id}")
	public ResponseEntity<Cliente> porId(@PathVariable("id") long id) {
		Optional<Cliente> cliente = repositorio.findById(id);
		return cliente.isPresent()
				? ResponseEntity.ok(cliente.get())
				: ResponseEntity.notFound().build();
		
		// Outra forma é fazer a função devolver somente o tipo Cliente
		// e lançar alguma exceção que faça o Spring controlar a resposta, ex.:
		
		// throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado");
	}
	
	@GetMapping("/cpf/{cpf}")
	public ResponseEntity<Cliente> porCpf(@PathVariable("cpf") String cpf) {
		Cliente cliente = repositorio.findOneByCpf(cpf);
		return cliente != null
				? ResponseEntity.ok(cliente)
				: ResponseEntity.notFound().build();
	}

	@PostMapping
	@Transactional
	@ResponseStatus(HttpStatus.CREATED)  // Em caso de sucesso
	public Cliente novoCliente(@RequestBody Cliente cliente) {
		repositorio.save(cliente);
		return cliente;  // Devolve com ID
	}
	
	@PutMapping("/{id}")
	@Transactional
	public void atualizarCliente(@PathVariable("id") long id, @RequestBody Cliente cliente) {
		// Não queria criar este setter mas facilita bastante alguns usos do ORM
		// Não precisamos ficar copiando atributos, recebemos um Cliente na
		// requisição e setamos o id para fazer o .save()
		cliente.setId(id);
		repositorio.save(cliente);
	}
	
	@DeleteMapping("/cpf/{cpf}")
	@Transactional
	@ResponseStatus(HttpStatus.NO_CONTENT)  // Em caso de sucesso :)
	public void deletarPorCpf(@PathVariable("cpf") String cpf) {
		// Aqui temos o ápice da eficiência e simplicidade: executamos diretamente
		// um DELETE sem precisar consultar antes, devolvemos o código apropriado
		// e o método não precisa devolver tipos estranhos
		
		// Desvantagem: a stack trace da exception vai toda para a resposta, razão
		// porque um ResponseEntity pode ser preferível
		
		int qtdLinhas = repositorio.excluirPorCpf(cpf);
		
		if (qtdLinhas == 0) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "CPF não encontrado");
		}
	}

	@GetMapping("/hello/{id}")
	public String hello(@PathVariable("id") long id) {
		Optional<Cliente> cliente = repositorio.findById(id);
		return String.format(
				"Hellô, %s, seu paidégua", 
				cliente.orElse(new Cliente("Ninguém", "")).getNome());
	}
	
	@GetMapping("/hello-object/{id}")
	public Cliente helloObject(@PathVariable("id") long id) {
		return repositorio.findById(id).orElse(new Cliente("Ninguém", ""));
	}
	
}
