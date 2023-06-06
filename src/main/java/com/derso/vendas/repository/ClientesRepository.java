package com.derso.vendas.repository;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.derso.vendas.domain.Cliente;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;

// @Repository = @Component + exception translator
@Repository
public class ClientesRepository {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	// Nem sempre vou querer usar o @Transactional direto no repositório...
	// Para fins didáticos, aqui está bom.
	@Transactional(readOnly = true)
	public Cliente buscarPorId(Long id) {
		return entityManager.find(Cliente.class, id);
	}
	
	@Transactional(readOnly = true)
	public List<Cliente> buscarPorNome(String nome) {
		// JPQL: linguagem de consulta referenciando as entidades
		String jpql = "SELECT c FROM Cliente c WHERE c.nome LIKE :nome";
		TypedQuery<Cliente> query = entityManager.createQuery(jpql, Cliente.class);
		query.setParameter("nome", "%" + nome + "%");
		return query.getResultList();
	}
	
	@Transactional(readOnly = true)
	public List<Cliente> obterTodos() {
		return entityManager
				.createQuery("FROM Cliente", Cliente.class)
				.getResultList();
	}
	
	@Transactional
	public void salvar(Cliente cliente) {
		// Coloca o objeto no estado MANAGED
		entityManager.persist(cliente);
	}
	
	@Transactional
	public Cliente atualizar(Cliente cliente) {
		// Dado o objeto, devolve um (talvez o mesmo, talvez outro)
		// no estado MANAGED 
		return entityManager.merge(cliente);
	}
	
	@Transactional
	public void deletar(Cliente cliente) {
		if (!entityManager.contains(cliente)) {
			// Tem que ser uma instância que o JPA/Hibernate conhece
			cliente = entityManager.merge(cliente);
		}
		entityManager.remove(cliente);
	}
	
	@Transactional
	public void deletar(Long id) {
		// Jeito óbvio porém ineficiente
		// entityManager.remove(buscarPorId(id));
		
		Query delete = entityManager.createQuery(
				"DELETE FROM Cliente c WHERE c.id = :id");
		delete.setParameter("id", id);
		
		// int quantosForamDeletados = 
		delete.executeUpdate();
	}

}
