package org.jenkins.plugins.audit2db.internal.data;

import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.HibernateTransactionManager;

public abstract class AbstractHibernateRepository {

    private final HibernateTemplate hibernateTemplate = new HibernateTemplate();
    private final HibernateTransactionManager transactionManager = new HibernateTransactionManager();
    private final SessionFactory sessionFactory;

    public AbstractHibernateRepository(final SessionFactory sessionFactory) {
	this.sessionFactory = sessionFactory;
	this.hibernateTemplate.setSessionFactory(sessionFactory);
	this.transactionManager.setSessionFactory(sessionFactory);
    }

    public HibernateTemplate getHibernateTemplate() {
	return hibernateTemplate;
    }

    public SessionFactory getSessionFactory() {
	return sessionFactory;
    }

    public HibernateTransactionManager getTransactionManager() {
	return transactionManager;
    }
}