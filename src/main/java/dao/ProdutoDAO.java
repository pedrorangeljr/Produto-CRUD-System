package dao;

import java.util.List;

import config.JPAUtil;
import jakarta.persistence.EntityManager;
import model.Produto;
import model.Usuario;

public class ProdutoDAO {

	public void salvar(Produto produto) {

		EntityManager em = JPAUtil.getEntityManager();

		try {

			em.getTransaction().begin();

			if (produto.getId() == null) { // só salva se o Id for nulo

				em.persist(produto);

			} else {

				em.merge(produto); // se não for vou dar update

			}

			em.getTransaction().commit();

		} catch (Exception e) {

			if (em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}

			throw new RuntimeException("Erro ao salvar produto", e);

		} finally {

			em.close();
		}
	}

	public List<Produto> listarPorUsuario(Usuario usuario) {

		EntityManager em = JPAUtil.getEntityManager();

		try {

			return em.createQuery("SELECT p FROM Produto P WHERE p.usuario = :usuario ORDER" + "BY p.id DESC",
					Produto.class).setParameter("usuario", usuario).getResultList();

		} finally {

			em.close();
		}
	}

	public Produto buscarPorId(Long id, Usuario usuario) {

		EntityManager em = JPAUtil.getEntityManager();

		try {

			return em.createQuery("SELECT p FROM Produto P WHERE p.id = :id AND p.usuario = :usuario", Produto.class)
					.setParameter("id", id).setParameter("Usuario", usuario).getSingleResult();

		} catch (Exception e) {

			return null;

		} finally {

			em.close();
		}
	}

	public void excluir(Long id, Usuario usuario) {

		EntityManager em = JPAUtil.getEntityManager();

		try {

			em.getTransaction().begin();

			Produto produto = buscarPorId(id, usuario);

			if (produto != null) {

				em.remove(em.contains(produto) ? produto : em.merge(produto));
			}

			em.getTransaction().commit();

		} catch (Exception e) {

			if (em.getTransaction().isActive()) {

				em.getTransaction().rollback();
			}
			
            throw new RuntimeException("Erro ao excluir produto", e);
            
		}finally {
			
			em.close();
		}
	}

}
