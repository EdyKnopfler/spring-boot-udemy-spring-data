package com.derso.vendas.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotEmpty;

@Entity
public class Cliente {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(length = 100, unique = true)
	@NotEmpty(message = "O campo nome é obrigatório")
	private String nome;

	@Column(length = 11, unique = true)
	private String cpf;
	
	@JsonIgnore  // não enviar os pedidos nas respostas HTTP :) 
	@OneToMany(mappedBy = "cliente", fetch = FetchType.LAZY)
	private List<Pedido> pedidos = new ArrayList<>();
	
	public Cliente() {
		// O construtor default é importante caso façamos MERGE do objeto!
	}
	
	public Cliente(String nome, String cpf) {
		setNome(nome);
		setCpf(cpf);
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		// Estava evitando usar o setId mas facilita N coisas
		// (ver ClientesController)
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	
	public List<Pedido> getPedidos() {
		return Collections.unmodifiableList(pedidos);
	}
	
}
