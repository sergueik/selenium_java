package example.db.dao;

import example.db.utils.HibernateUtils;
import example.enums.Schema;
import org.hibernate.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

public class DAO<T> {

	private Class<T> entity;
	private Session session;
	private Schema schema;

	public DAO(final Class<T> entity, final Schema schema) {
		this.entity = entity;
		this.schema = schema;
	}

	private Session createNewSession() {
		return HibernateUtils.getSessionFactory(schema).getCurrentSession();
	}

	private void startNewTransaction(final Session session) {
		session.beginTransaction();
	}

	private void createNewSessionAndTransaction() {
		session = createNewSession();

		if (session != null) {
			startNewTransaction(session);
		}
	}

	private void commitTransaction() {
		if (session != null) {
			session.getTransaction().commit();
		}
	}

	private void rollbackTransaction() {
		if (session != null && session.getTransaction().isActive()) {
			session.getTransaction().rollback();
		}
	}

	@SuppressWarnings("unchecked")
	public List<T> selectAll() {
		List<T> objects;

		try {
			createNewSessionAndTransaction();
			objects = selectAllFrom(entity.getSimpleName());
			commitTransaction();
		} catch (HibernateException e) {
			rollbackTransaction();
			objects = new ArrayList<>();
		}

		return objects;
	}

	public List<T[]> selectAllAndTransform() {
		return transformContentToArray(selectAll());
	}

	@SuppressWarnings("unchecked")
	public List<T[]> transformContentToArray(final List<T> content) {
		return content.stream()
				.map(ob -> (T[]) new Object[]{ob})
				.collect(toList());
	}

	@SuppressWarnings("unchecked")
	private List<T> selectAllFrom(final String entity) {
		return Optional.ofNullable(session)
				.map(s -> (List<T>) s
						.createQuery(String.format("from %s", entity)).list())
				.orElse(new ArrayList<>());
	}

	public void save(final T entity) {
		try {
			createNewSessionAndTransaction();
			if (session != null && entity != null) {
				session.merge(entity);
			}
			commitTransaction();
		} catch (HibernateException ignored) {
			rollbackTransaction();
		}
	}
}
