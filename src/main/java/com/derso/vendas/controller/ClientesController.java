package com.derso.vendas.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.bind.annotation.RestController;

import com.derso.vendas.domain.Cliente;
import com.derso.vendas.repository.ClientesRepository;

@RestController
@RequestMapping("/clientes")
public class ClientesController {
	
	record ClienteDTO(String nome, String cpf) {
		public Cliente criar() {
			return new Cliente(nome, cpf);
		}
	}

	@Autowired
	private ClientesRepository repositorio;
	
	@GetMapping
	public List<Cliente> listar(@RequestParam(name="nome", required=false) String nomeBusca) {
		return nomeBusca != null
				//? repositorio.findByNomeLikeIgnoreCase("%" + nomeBusca + "%")
				? repositorio.encontrarPorNome(nomeBusca)
				: repositorio.findAll();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Cliente> porId(@PathVariable("id") long id) {
		Optional<Cliente> cliente = repositorio.findById(id);
		return cliente.isPresent()
				? ResponseEntity.ok(cliente.get())
				: ResponseEntity.notFound().build();
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
	public ResponseEntity<Cliente> novoCliente(@RequestBody ClienteDTO clienteDto) {
		Cliente cliente = clienteDto.criar();
		repositorio.save(cliente);
		return ResponseEntity.ok(cliente);
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
	public void deletarPorCpf(@PathVariable("cpf") String cpf) {
		// Idealmente precisaríamos consultar para decidir se devolvemos 404 :P
		repositorio.excluirPorCpf(cpf);
	}

	@GetMapping("/hello/{id}")
	public String hello(@PathVariable("id") long id) {
		Optional<Cliente> cliente = repositorio.findById(id);
		return String.format(
				"Hellô, %s, seu paidégua", 
				cliente.orElse(new Cliente("Ninguém", "")).getNome());
	}
	
	@GetMapping("/hello-object/{id}")
	public ClienteDTO helloObject(@PathVariable("id") long id) {
		Cliente cliente = repositorio.findById(id).orElse(new Cliente("Ninguém", ""));
		
		// Está feio mas é para fins didáticos
		return new ClienteDTO(cliente.getNome(), cliente.getCpf());
	}
	
}
