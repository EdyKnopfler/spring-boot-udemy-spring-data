package com.derso.vendas.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import com.derso.vendas.domain.ItemPedido;
import com.derso.vendas.domain.Pedido;
import com.derso.vendas.domain.Produto;

import jakarta.persistence.EntityManager;

// REFERÊNCIA: quando fiz o curso da Alura em 2021
// https://github.com/EdyKnopfler/alura-spring-api-rest/blob/main/src/test/java/br/com/pip/forum/repositorio/CursoRepositoryTest.java

// Cada teste começa com uma base nova e limpa!

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@ActiveProfiles({"test"})
class VendasIntegratedTest {
	
	@Autowired
	private EntityManager em;

	@BeforeEach
	public void criandoPedido() {
		// Produtos (@ManyToOne no Pedido) NÃO foram persistidos junto com o pedido!
		Produto geladeira = new Produto("Geladeira", 5900.0);
		em.persist(geladeira);
		
		Produto televisor = new Produto("Televisor", 4000.0);
		em.persist(televisor);
		
		Produto microondas = new Produto("Microondas", 1500.0);
		em.persist(microondas);
		
		// Os itens (@OneToMany no Pedido) foram :)
		Pedido pedido = new Pedido();
		pedido.novoItem(new ItemPedido(geladeira, 1));
		pedido.novoItem(new ItemPedido(microondas, 2));
		em.persist(pedido);
	}
	
	@Test
	public void produtosEstaoPersistidos() {
		List<Produto> produtos = em
				.createQuery("SELECT p FROM Produto p ORDER BY p.descricao", Produto.class)
				.getResultList();
		assertEquals(3, produtos.size());
		
		conferirProduto(produtos.get(0), "Geladeira", 5900.0);
		conferirProduto(produtos.get(1), "Microondas", 1500.0);
		conferirProduto(produtos.get(2), "Televisor", 4000.0);
	}
	
	private void conferirProduto(Produto produto, String descricao, double preco) {
		assertEquals(descricao, produto.getDescricao());
		assertEquals(preco, produto.getPreco().floatValue(), 0.001);
	}
	
	@Test
	public void pedidoEstaPersistido() {
		Pedido pedido = lerPedido();
		
		assertEquals(8900.0, pedido.getTotal().floatValue(), 0.001);
		
		List<ItemPedido> itens = pedido.getItens();
		
		assertEquals(2, itens.size());
		
		conferirItem(itens.get(0), "Geladeira", 5900.0, 1, 5900.0);
		conferirItem(itens.get(1), "Microondas", 1500.0, 2, 3000.0);
	}

	private Pedido lerPedido() {
		List<Pedido> pedidos = em
				.createQuery("SELECT p FROM Pedido p", Pedido.class)
				.getResultList();
		assertEquals(1, pedidos.size());
		return pedidos.get(0);
	}
	
	@Test
	public void alterandoItem() {
		Produto televisor = em
			.createQuery(
				"SELECT p FROM Produto p WHERE p.descricao = 'Televisor'", Produto.class)
			.getSingleResult();
			
		Pedido pedido = lerPedido();
		
		pedido.removerItem(0);
		
		assertEquals(1, pedido.getItens().size());
		conferirItem(pedido.getItens().get(0), "Microondas", 1500.0, 2, 3000.0);
		assertEquals(3000.0, pedido.getTotal().floatValue(), 0.001);
		
		// Fazer MERGE aqui estava produzindo problemas
		// MERGE != ATUALIZAR
		// O objeto já estava MANAGED, logo basta enviar as alterações.
		pedido.novoItem(new ItemPedido(televisor, 3));
		em.flush();
		
		Pedido alterado = lerPedido();
		assertEquals(2, pedido.getItens().size());
		conferirItem(alterado.getItens().get(0), "Microondas", 1500.0, 2, 3000.0);
		conferirItem(alterado.getItens().get(1), "Televisor", 4000.0, 3, 12000.0);
		assertEquals(15000.0, alterado.getTotal().floatValue(), 0.001);
		
	}

	private void conferirItem(
			ItemPedido item, String nome, double unitario, int quantidade, double total) {
		assertEquals(nome, item.getProduto().getDescricao());
		assertEquals(unitario, item.getPrecoCorrente().floatValue(), 0.001);
		assertEquals(quantidade, item.getQuantidade());
		assertEquals(total, item.getTotalItem().floatValue(), 0.001);
	}

}
