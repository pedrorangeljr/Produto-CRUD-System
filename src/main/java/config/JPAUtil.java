package config;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class JPAUtil {

	private static final String PERSISTENCE_UNIT_NAME = "produtoPU";
	private static volatile EntityManagerFactory emf;

	static {

		try {

			emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);

		} catch (Exception e) {

			throw new RuntimeException("Falha ao inicializar JPA", e);
		}
	}

	public JPAUtil() {
	}

	public static EntityManager getEntityManager() {

		if (emf == null) {

			throw new IllegalStateException("JPA não inicializado");
		}

		return emf.createEntityManager();
	}

	public static void close() {

		if (emf != null && emf.isOpen()) {

			emf.close();
		}
	}
}
