package com.derso.vendas.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.derso.vendas.domain.Cliente;
import com.derso.vendas.repository.ClientesRepository;

@RestController
@RequestMapping("/clientes")
public class ClientesController {

	@Autowired
	private ClientesRepository repositorio;
	
	@GetMapping
	public List<Cliente> todos() {
		return repositorio.obterTodos();
	}

	@PostMapping
	public void novoCliente(Cliente cliente) {
		repositorio.salvar(cliente);
	}

}
