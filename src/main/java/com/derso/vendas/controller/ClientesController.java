package com.derso.vendas.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
	
	@GetMapping("/cpf/{cpf}")
	public Cliente porCpf(@PathVariable("cpf") String cpf) {
		return repositorio.findOneByCpf(cpf);
	}

	@PostMapping
	@Transactional
	public void novoCliente(@RequestBody ClienteDTO clienteDto) {
		repositorio.save(clienteDto.criar());
	}
	
	@DeleteMapping("/cpf/{cpf}")
	@Transactional
	public void deletarPorCpf(@PathVariable("cpf") String cpf) {
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
