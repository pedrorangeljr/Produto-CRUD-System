package dao;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import org.mindrot.jbcrypt.BCrypt;

import config.JPAUtil;
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

	public Usuario buscarPorEmail(String email) {

		EntityManager em = JPAUtil.getEntityManager();

		try {

			return em.createQuery("Select u FROM Usuario u WHERE u.email = :email AND u.ativo = true", Usuario.class)
					.setParameter("email", email).getSingleResult();

		} catch (NoResultException e) {

			return null;

		} finally {

			em.close();
		}
	}

	public boolean autenticar(String email, String senhaPlain) {

		Usuario usuario = buscarPorEmail(email);
		if (usuario == null)
			return false;
		return BCrypt.checkpw(senhaPlain, usuario.getSenhaHash());
	}

	public static String hashSenha(String senhaPlain) {

		return BCrypt.hashpw(senhaPlain, BCrypt.gensalt(12));
	}
}
