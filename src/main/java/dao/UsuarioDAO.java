package dao;

import config.JPAUtil;
import jakarta.persistence.EntityManager;
import model.Usuario;

public class UsuarioDAO {

	public void salvar(Usuario usuario) {

		EntityManager em = JPAUtil.getEntityManager();

		try {

			em.getTransaction().begin();
			em.persist(usuario);
			em.getTransaction().commit();

		} catch (Exception e) {

			if (em.getTransaction().isActive()) {

				em.getTransaction().rollback();
			}

			throw new RuntimeException("Erro ao salvar usuário", e);

		} finally {

			em.close();
		}
	}
}
