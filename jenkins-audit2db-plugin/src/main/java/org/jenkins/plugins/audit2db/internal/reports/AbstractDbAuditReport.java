package org.jenkins.plugins.audit2db.internal.reports;

import hudson.model.Descriptor;
import jenkins.model.Jenkins;

import org.jenkins.plugins.audit2db.data.BuildDetailsRepository;
import org.jenkins.plugins.audit2db.internal.DbAuditPublisherImpl;
import org.jenkins.plugins.audit2db.internal.DbAuditUtil;
import org.jenkins.plugins.audit2db.internal.data.BuildDetailsHibernateRepository;
import org.jenkins.plugins.audit2db.reports.DbAuditReport;

public abstract class AbstractDbAuditReport implements DbAuditReport {
    private transient BuildDetailsRepository repository;

    public AbstractDbAuditReport() {
	super();
    }

    @Override
    public String getJenkinsHostname() {
	return DbAuditUtil.getHostName();
    }

    @Override
    public String getJenkinsIpAddr() {
	return DbAuditUtil.getIpAddress();
    }

    @Override
    public String getIconFileName() {
	return "document.gif";
    }

    @Override
    public BuildDetailsRepository getRepository() {
	if (null == repository) {
	    repository = new BuildDetailsHibernateRepository(
		    DbAuditPublisherImpl.getSessionFactory());
	}
	return repository;
    }

    @Override
    public void setRepository(final BuildDetailsRepository repository) {
	if (repository != null) {
	    this.repository = repository;
	}
    }

    @Override
    @SuppressWarnings("unchecked")
    public Descriptor<DbAuditReport> getDescriptor() {
	return Jenkins.getInstance().getDescriptorOrDie(getClass());
    }
}