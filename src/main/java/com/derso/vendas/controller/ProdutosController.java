package com.derso.vendas.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
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

import com.derso.vendas.domain.Produto;
import com.derso.vendas.repository.ClientesRepository;
import com.derso.vendas.repository.ProdutosRepository;

/*
 *  DESAFIO!
 *  
 *  Criar SOZINHO a API de produtos.
 *  
 *  https://www.youtube.com/watch?v=yb9GOOXN_kU
 *  
 *  Decisões de projeto:
 *  - código MAIS SIMPLES possível
 *  - código MAIS EFICIENTE possível (nada de fazer find antes de update ou delete)
 *    - rodou a query sem erro, base de dados atualizada e não se fala mais nisso
 */



@RestController
@RequestMapping("/produtos")
public class ProdutosController {
	
	private ProdutosRepository repositorio;
	
	@Autowired
	public ProdutosController(ProdutosRepository repositorio) {
		this.repositorio = repositorio;
	}
	
	@GetMapping
	private List<Produto> listagem(
			@RequestParam(name = "busca", required = false) String buscaDescricao) {
		return buscaDescricao != null
				? repositorio.findByDescricaoLikeIgnoreCaseOrderByDescricao(
						"%" + buscaDescricao + "%")
				: repositorio.findAll(Sort.by("descricao"));
	}
	
	@GetMapping("/{id}")
	private ResponseEntity<Produto> porId(@PathVariable("id") long id) {
		Optional<Produto> produto = repositorio.findById(id);
		return produto.isPresent()
				? ResponseEntity.ok(produto.get())
				: ResponseEntity.notFound().build();
	}
	
	@PostMapping
	@Transactional
	public Produto novo(@RequestBody Produto produto) {
		repositorio.save(produto);
		return produto;
	}
	
	@PutMapping("/{id}")
	@Transactional
	public void atualizar(@PathVariable("id") long id, @RequestBody Produto produto) {
		produto.setId(id);
		repositorio.save(produto);
	}
	
	@DeleteMapping("/{id}")
	@Transactional
	public void remover(@PathVariable("id") long id) {
		repositorio.deleteById(id);
	}

}
