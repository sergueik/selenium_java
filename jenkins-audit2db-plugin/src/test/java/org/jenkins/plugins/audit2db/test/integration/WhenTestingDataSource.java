/**
 * 
 */
package org.jenkins.plugins.audit2db.test.integration;

import hudson.util.FormValidation;
import junit.framework.Assert;

import org.jenkins.plugins.audit2db.DbAuditPublisher;
import org.jenkins.plugins.audit2db.DbAuditPublisherDescriptor;
import org.jenkins.plugins.audit2db.internal.DbAuditPublisherImpl;
import org.junit.Test;
import org.jvnet.hudson.test.HudsonTestCase;

/**
 * Unit tests for the {@link DbAuditPublisherImpl} class.
 * 
 * @author Marco Scata
 *
 */
public class WhenTestingDataSource extends HudsonTestCase {
    //	private static final String jndiName = "jdbc/dbauditplugin";
    private static final String jdbcDriver = "org.hsqldb.jdbc.JDBCDriver";
    private static final String jdbcUrl = "jdbc:hsqldb:mem:test";
    private static final String jdbcUser = "SA";
    private static final String jdbcPassword = "";

    @Test
    public void testValidJdbcDatasourceShouldSucceed() throws Exception {
        final DbAuditPublisher publisher = new DbAuditPublisherImpl();
        final DbAuditPublisherDescriptor descriptor = (DbAuditPublisherDescriptor) publisher.getDescriptor();

        final FormValidation testResult = descriptor.doTestJdbcConnection(
                jdbcDriver, jdbcUrl, jdbcUser, jdbcPassword);
        Assert.assertEquals("Unexpected connection error.", FormValidation.Kind.OK, testResult.kind);
    }

    @Test
    public void testInvalidJdbcDriverShouldFail() throws Exception {
        final DbAuditPublisher publisher = new DbAuditPublisherImpl();
        final DbAuditPublisherDescriptor descriptor = (DbAuditPublisherDescriptor) publisher.getDescriptor();

        final FormValidation testResult = descriptor.doTestJdbcConnection(
                "WrongDriver", jdbcUrl, jdbcUser, jdbcPassword);
        Assert.assertEquals("Unexpected successful connection.", FormValidation.Kind.ERROR, testResult.kind);
    }
    
    @Test
    public void testGeneratingDdlWithValidJdbcDetailsShouldSucceed() throws Exception {
        final DbAuditPublisher publisher = new DbAuditPublisherImpl();
        final DbAuditPublisherDescriptor descriptor = (DbAuditPublisherDescriptor) publisher.getDescriptor();

        final FormValidation testResult = descriptor.doGenerateDdl(
                jdbcDriver, jdbcUrl, jdbcUser, jdbcPassword);
        Assert.assertEquals("Unexpected connection error.", FormValidation.Kind.OK, testResult.kind);
    }

    //	@Test
    //	public void testingValidJndiDatasourceShouldBeSuccessful() throws Exception {
    //		final SimpleNamingContextBuilder contextBuilder = SimpleNamingContextBuilder.emptyActivatedContextBuilder();
    //		contextBuilder.bind(jndiName, getDatasource());
    //
    //		final DbAuditPlugin plugin = new DbAuditPluginImpl(
    //				true, jndiName, null, null, "SA", "");
    //
    //		final boolean testResult = plugin.testDatasourceConnection();
    //		Assert.assertTrue("Failed connection", testResult);
    //	}
    //
    //	@Test
    //	public void testingInvalidJndiDatasourceShouldFail() throws Exception {
    //		final SimpleNamingContextBuilder contextBuilder = SimpleNamingContextBuilder.emptyActivatedContextBuilder();
    //		contextBuilder.bind(jndiName, getDatasource());
    //
    //		final DbAuditPlugin plugin = new DbAuditPluginImpl(
    //				true, jndiName + "WRONG", null, null, "SA", "");
    //
    //		final boolean testResult = plugin.testDatasourceConnection();
    //		Assert.assertFalse("Unexpected successful connection", testResult);
    //	}
    //
    //	@Test
    //	public void testingValidJndiDatasourceWithWrongLoginShouldFail() throws Exception {
    //		final SimpleNamingContextBuilder contextBuilder = SimpleNamingContextBuilder.emptyActivatedContextBuilder();
    //		contextBuilder.bind(jndiName, getDatasource());
    //
    //		final DbAuditPlugin plugin = new DbAuditPluginImpl(
    //				true, jndiName, null, null, "WRONG", "WRONG");
    //
    //		final boolean testResult = plugin.testDatasourceConnection();
    //		Assert.assertFalse("Unexpected successful connection", testResult);
    //	}
}
