package dao;

import java.util.List;

import javax.persistence.EntityManager;

import config.JPAUtil;
import model.Produto;
import model.Usuario;

public class ProdutoDAO {

	// ✅ SALVAR - APENAS INSERÇÃO
	public void salvar(Produto produto) {
		EntityManager em = JPAUtil.getEntityManager();
		try {
			em.getTransaction().begin();

			// Verificação de segurança
			if (produto.getId() != null) {
				throw new RuntimeException(
						"Para novo produto, o ID deve ser nulo. Use o método atualizar para edição.");
			}

			em.persist(produto); // Só INSERT, nunca DELETE

			em.getTransaction().commit();
			System.out.println("✅ Produto inserido com sucesso. ID gerado: " + produto.getId());

		} catch (Exception e) {
			if (em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
			System.err.println("❌ Erro ao inserir produto: " + e.getMessage());
			throw new RuntimeException("Erro ao salvar produto", e);
		} finally {
			em.close();
		}
	}

	// ✅ ATUALIZAR - APENAS UPDATE
	public void atualizar(Produto produto) {
		EntityManager em = JPAUtil.getEntityManager();
		try {
			em.getTransaction().begin();

			// Buscar o produto existente
			Produto existing = em.find(Produto.class, produto.getId());

			if (existing == null) {
				throw new RuntimeException("Produto ID " + produto.getId() + " não encontrado para atualização");
			}

			// Atualizar campos
			existing.setNome(produto.getNome());
			existing.setDescricao(produto.getDescricao());
			existing.setPreco(produto.getPreco());
			existing.setQuantidadeEstoque(produto.getQuantidadeEstoque());

			em.merge(existing); // UPDATE

			em.getTransaction().commit();
			System.out.println("✅ Produto ID " + produto.getId() + " atualizado com sucesso");

		} catch (Exception e) {
			if (em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
			System.err.println("❌ Erro ao atualizar produto: " + e.getMessage());
			throw new RuntimeException("Erro ao atualizar produto", e);
		} finally {
			em.close();
		}
	}

	// ✅ LISTAR
	public List<Produto> listarPorUsuario(Usuario usuario) {
		EntityManager em = JPAUtil.getEntityManager();
		try {
			return em
					.createQuery("SELECT p FROM Produto p WHERE p.usuario = :usuario ORDER BY p.id DESC", Produto.class)
					.setParameter("usuario", usuario).getResultList();
		} finally {
			em.close();
		}
	}

	// ✅ BUSCAR POR ID
	public Produto buscarPorId(Long id, Usuario usuario) {
		EntityManager em = JPAUtil.getEntityManager();
		try {
			return em.createQuery("SELECT p FROM Produto p WHERE p.id = :id AND p.usuario = :usuario", Produto.class)
					.setParameter("id", id).setParameter("usuario", usuario).getSingleResult();
		} catch (Exception e) {
			return null;
		} finally {
			em.close();
		}
	}

	// ✅ EXCLUIR - ÚNICO MÉTODO QUE PODE DELETAR
	public void excluir(Long id, Usuario usuario) {
		EntityManager em = JPAUtil.getEntityManager();
		try {
			em.getTransaction().begin();

			Produto produto = buscarPorId(id, usuario);
			if (produto != null) {
				Produto managed = em.merge(produto);
				em.remove(managed); // Só AQUI pode ter DELETE
				System.out.println("✅ Produto ID " + id + " excluído com sucesso");
			}

			em.getTransaction().commit();

		} catch (Exception e) {
			if (em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
			System.err.println("❌ Erro ao excluir produto: " + e.getMessage());
			throw new RuntimeException("Erro ao excluir produto", e);
		} finally {
			em.close();
		}
	}
}