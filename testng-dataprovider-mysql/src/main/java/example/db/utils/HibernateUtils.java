package example.db.utils;

import example.enums.DataSource;
import example.enums.Schema;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public final class HibernateUtils {

	private static final Map<Schema, SessionFactory> FACTORIES = Collections.unmodifiableMap((
			new HashMap<Schema, SessionFactory>() {
				{
					put(Schema.AUTOMATION, configureSessionFactory(DataSource.AUTOMATION_SOURCE.getSource()));
				}
			}));

	private static SessionFactory configureSessionFactory(final String dataSource) {
		final Configuration configuration = new Configuration().configure(dataSource);
		final ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
				.applySettings(configuration.getProperties()).build();
		return configuration.buildSessionFactory(serviceRegistry);
	}

	public static SessionFactory getSessionFactory(final Schema schema) {
		return FACTORIES.get(schema);
	}

	public static void closeConnections() {
		FACTORIES.entrySet().forEach(entry -> entry.getValue().close());
	}

	private HibernateUtils() {
		throw new UnsupportedOperationException("Illegal access to private constructor");
	}
}
