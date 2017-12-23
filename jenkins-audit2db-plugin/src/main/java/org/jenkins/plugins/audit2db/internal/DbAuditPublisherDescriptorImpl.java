/**
 * 
 */
package org.jenkins.plugins.audit2db.internal;

import hudson.model.AbstractProject;
import hudson.tasks.BuildStepDescriptor;
import hudson.tasks.Publisher;
import hudson.util.FormValidation;
import hudson.util.Scrambler;

import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;

import net.sf.json.JSONObject;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.jenkins.plugins.audit2db.DbAuditPublisherDescriptor;
import org.jenkins.plugins.audit2db.Messages;
import org.jenkins.plugins.audit2db.internal.data.HibernateUtil;
import org.kohsuke.stapler.QueryParameter;
import org.kohsuke.stapler.StaplerRequest;

/**
 * @author Marco Scata
 *
 */
public class DbAuditPublisherDescriptorImpl extends
BuildStepDescriptor<Publisher> implements DbAuditPublisherDescriptor {
    private final static Logger LOGGER = Logger.getLogger(DbAuditPublisherDescriptorImpl.class.getName());

    private boolean useJndi;
    private String jndiName;
    private String jndiUser;
    private String jndiPassword;

    private String jdbcDriver;
    private String jdbcUrl;
    private String jdbcUser;
    private String jdbcPassword;

    /**
     * @see org.jenkins.plugins.audit2db.DbAuditPublisherDescriptor#getUseJndi()
     */
    @Override
    public boolean getUseJndi() {
	return useJndi;
    }

    /**
     * @see org.jenkins.plugins.audit2db.DbAuditPublisherDescriptor#setUseJndi(boolean)
     */
    @Override
    public void setUseJndi(final boolean useJndi) {
	this.useJndi = useJndi;
    }

    /**
     * @see org.jenkins.plugins.audit2db.DbAuditPublisherDescriptor#getJndiName()
     */
    @Override
    public String getJndiName() {
	return jndiName;
    }

    /**
     * @see org.jenkins.plugins.audit2db.DbAuditPublisherDescriptor#setJndiName(java.lang.String)
     */
    @Override
    public void setJndiName(final String jndiName) {
	this.jndiName = jndiName;
    }

    /**
     * @see org.jenkins.plugins.audit2db.DbAuditPublisherDescriptor#getJdbcDriver()
     */
    @Override
    public String getJdbcDriver() {
	return jdbcDriver;
    }

    /**
     * @see org.jenkins.plugins.audit2db.DbAuditPublisherDescriptor#setJdbcDriver(java.lang.String)
     */
    @Override
    public void setJdbcDriver(final String jdbcDriver) {
	this.jdbcDriver = jdbcDriver;
    }

    /**
     * @see org.jenkins.plugins.audit2db.DbAuditPublisherDescriptor#getJdbcUrl()
     */
    @Override
    public String getJdbcUrl() {
	return jdbcUrl;
    }

    /**
     * @see org.jenkins.plugins.audit2db.DbAuditPublisherDescriptor#setJdbcUrl(java.lang.String)
     */
    @Override
    public void setJdbcUrl(final String jdbcUrl) {
	this.jdbcUrl = jdbcUrl;
    }

    /**
     * @see org.jenkins.plugins.audit2db.DbAuditPublisherDescriptor#getJndiUser()
     */
    @Override
    public String getJndiUser() {
	return jndiUser;
    }

    /**
     * @see org.jenkins.plugins.audit2db.DbAuditPublisherDescriptor#setJndiUser(java.lang.String)
     */
    @Override
    public void setJndiUser(final String username) {
	this.jndiUser = username;
    }

    /**
     * @return the password for the specified user.
     */
    String getJndiPassword() {
	return Scrambler.descramble(jndiPassword);
    }

    /**
     * @see org.jenkins.plugins.audit2db.DbAuditPublisherDescriptor#setJndiPassword(java.lang.String)
     */
    @Override
    public void setJndiPassword(final String password) {
	this.jndiPassword = Scrambler.scramble(password);
    }

    /**
     * @see org.jenkins.plugins.audit2db.DbAuditPublisherDescriptor#getJdbcUser()
     */
    @Override
    public String getJdbcUser() {
	return jdbcUser;
    }

    /**
     * @see org.jenkins.plugins.audit2db.DbAuditPublisherDescriptor#setJdbcUser(java.lang.String)
     */
    @Override
    public void setJdbcUser(final String username) {
	this.jdbcUser = username;
    }

    /**
     * @see org.jenkins.plugins.audit2db.DbAuditPublisherDescriptor#getJdbcPassword()
     */
    @Override
    public String getJdbcPassword() {
	return Scrambler.descramble(jdbcPassword);
    }

    /**
     * @see org.jenkins.plugins.audit2db.DbAuditPublisherDescriptor#setJdbcPassword(java.lang.String)
     */
    @Override
    public void setJdbcPassword(final String password) {
	this.jdbcPassword = Scrambler.scramble(password);
    }

    public DbAuditPublisherDescriptorImpl() {
	this(DbAuditPublisherImpl.class);
	LOGGER.log(Level.FINE, "init()");
    }

    public DbAuditPublisherDescriptorImpl(final Class<? extends DbAuditPublisherImpl>clazz) {
	super(clazz);
	load();
    }

    @Override
    public boolean configure(final StaplerRequest req, final JSONObject json)
    throws hudson.model.Descriptor.FormException {
	LOGGER.log(Level.FINE, "configure() <- " + json.toString());
	final JSONObject datasourceDetails = json; //.getJSONObject("datasource");
	//		this.useJndi = datasourceDetails.getBoolean("value");

	if (this.useJndi) {
	    this.jndiName = datasourceDetails.getString("jndiName");
	    this.jndiUser = datasourceDetails.getString("jndiUser");
	    this.jndiPassword = Scrambler.scramble(datasourceDetails
		    .getString("jndiPassword"));
	} else {
	    this.jdbcDriver = datasourceDetails.getString("jdbcDriver");
	    this.jdbcUrl = datasourceDetails.getString("jdbcUrl");
	    this.jdbcUser = datasourceDetails.getString("jdbcUser");
	    this.jdbcPassword = Scrambler.scramble(datasourceDetails
		    .getString("jdbcPassword"));
	}
	save();
	return super.configure(req, json);
    }

    @Override
    public String getId() {
	return "audit2db";
    }

    @Override
    public boolean isApplicable(@SuppressWarnings("rawtypes") final Class<? extends AbstractProject> jobType) {
	// applies to all kinds of project
	return true;
    }

    @Override
    public String getDisplayName() {
	return Messages.DbAuditPublisherDescriptor_DisplayName();
    }

    /**
     * @see org.jenkins.plugins.audit2db.internal.DbAuditPublisherDescriptor#doTestJdbcConnection(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
     */
    @Override
    public FormValidation doTestJdbcConnection(
	    @QueryParameter("audit2db.jdbcDriver") final String jdbcDriver,
	    @QueryParameter("audit2db.jdbcUrl") final String jdbcUrl,
	    @QueryParameter("audit2db.jdbcUser") final String username,
	    @QueryParameter("audit2db.jdbcPassword") final String password)
    throws IOException, ServletException {
	LOGGER.log(Level.FINE, String.format(
		"doTestJdbcConnection('%s','%s','%s','*****'",
		jdbcDriver, jdbcUrl, username));
	FormValidation retval = FormValidation.ok(
		Messages.DbAuditPublisherDescriptor_ConnectionOk());

	try {
	    final Properties props = HibernateUtil.getExtraProperties(
		    jdbcDriver, jdbcUrl, username, password);

	    final Session session = HibernateUtil.getSessionFactory(props).getCurrentSession();
	    final Transaction tx = session.beginTransaction();
	    tx.rollback();

	    this.jdbcDriver = jdbcDriver;
	    this.jdbcUrl = jdbcUrl;
	    this.jdbcUser = username;
	    this.jdbcPassword = Scrambler.scramble(password);
	} catch (final Exception e) {
	    LOGGER.log(Level.SEVERE, e.getMessage(), e);
	    retval = FormValidation.error(e.getMessage());
	}

	return retval;
    }

    @Override
    public FormValidation doGenerateDdl(
	    @QueryParameter("audit2db.jdbcDriver") final String jdbcDriver,
	    @QueryParameter("audit2db.jdbcUrl") final String jdbcUrl,
	    @QueryParameter("audit2db.jdbcUser") final String username,
	    @QueryParameter("audit2db.jdbcPassword") final String password)
    throws IOException, ServletException {
	LOGGER.log(Level.FINE, String.format(
		"doGenerateDdl('%s','%s','%s','*****'",
		jdbcDriver, jdbcUrl, username));
	FormValidation retval;
	try {
	    final String ddlText = HibernateUtil.getSchemaDdl(
		    jdbcDriver, jdbcUrl, username, password);
	    retval = FormValidation.ok(ddlText);
	} catch (final Exception e) {
	    LOGGER.log(Level.SEVERE, e.getMessage(), e);
	    retval = FormValidation.error(e.getMessage());
	}

	return retval;
    }
}
