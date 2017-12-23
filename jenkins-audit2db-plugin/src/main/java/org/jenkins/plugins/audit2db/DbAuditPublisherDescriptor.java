package org.jenkins.plugins.audit2db;

import hudson.util.FormValidation;

import java.io.IOException;

import javax.servlet.ServletException;

import org.kohsuke.stapler.QueryParameter;

public interface DbAuditPublisherDescriptor {

    /**
     * @return <code>true</code> if using a JNDI datasource, or <code>false</code> for JDBC.
     */
    boolean getUseJndi();

    /**
     * @param useJndi
     *            set to <code>true</code> to use a JNDI datasource, or <code>false</code> for JDBC.
     */
    void setUseJndi(final boolean useJndi);

    /**
     * @return the name of the JNDI datasource.
     */
    String getJndiName();

    /**
     * @param jndiName
     *            <code>true</code> if using a JNDI datasource, or <code>false</code> for JDBC.
     */
    void setJndiName(final String jndiName);

    /**
     * @return the name of the JDBC driver class.
     */
    String getJdbcDriver();

    /**
     * @param jdbcDriver
     *            the name of the JDBC driver class.
     */
    void setJdbcDriver(final String jdbcDriver);

    /**
     * @return the JDBC URL.
     */
    String getJdbcUrl();

    /**
     * @param jdbcUrl
     *            the JDBC URL.
     */
    void setJdbcUrl(final String jdbcUrl);

    /**
     * @return the user for the JNDI datasource.
     */
    String getJndiUser();

    /**
     * @param username
     *            the user for the JNDI datasource.
     */
    void setJndiUser(final String username);

    /**
     * @param password
     *            the password for the JNDI datasource.
     */
    void setJndiPassword(final String password);

    /**
     * @return the user for the JDBC datasource.
     */
    String getJdbcUser();

    /**
     * @param username
     *            the user for the JDBC datasource.
     */
    void setJdbcUser(final String username);

    /**
     * @return the password for the specified user.
     */
    String getJdbcPassword();

    /**
     * @param password
     *            the password for the JDBC datasource.
     */
    void setJdbcPassword(final String password);

    /**
     * Checks the JDBC connection.
     * 
     * @param jdbcDriver
     *            the JDBC driver class.
     * @param jdbcUrl
     *            the JDBC URL.
     * @param username
     *            the JDBC user.
     * @param password
     *            the JDBC password.
     * @return the validation result.
     * @throws IOException
     *             if a problem occurs while connecting to the datasource.
     * @throws ServletException
     *             if a problem occurs while processing the request.
     */
    FormValidation doTestJdbcConnection(@QueryParameter("audit2db.jdbcDriver") final String jdbcDriver,
            @QueryParameter("audit2db.jdbcUrl") final String jdbcUrl, @QueryParameter("audit2db.jdbcUser") final String username,
            @QueryParameter("audit2db.jdbcPassword") final String password) throws IOException, ServletException;

    /**
     * Generates the DDL.
     * 
     * @param jdbcDriver
     *            the JDBC driver class.
     * @param jdbcUrl
     *            the JDBC URL.
     * @param username
     *            the JDBC user.
     * @param password
     *            the JDBC password.
     * @return the validation result.
     * @throws IOException
     *             if a problem occurs while connecting to the datasource.
     * @throws ServletException
     *             if a problem occurs while processing the request.
     */
    FormValidation doGenerateDdl(@QueryParameter("audit2db.jdbcDriver") final String jdbcDriver,
            @QueryParameter("audit2db.jdbcUrl") final String jdbcUrl, @QueryParameter("audit2db.jdbcUser") final String username,
            @QueryParameter("audit2db.jdbcPassword") final String password) throws IOException, ServletException;
}