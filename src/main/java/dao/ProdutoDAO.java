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
}
