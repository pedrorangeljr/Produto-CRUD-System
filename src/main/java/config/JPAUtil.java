package config;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

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

	private JPAUtil() {
	} // Construtor privado (utility class)

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