/**
 * 
 */
package org.jenkins.plugins.audit2db.test.integration;

import org.jenkins.plugins.audit2db.test.integration.webpages.JenkinsConfigurationPage;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.jvnet.hudson.test.HudsonTestCase;

import com.gargoylesoftware.htmlunit.WebAssert;

/**
 * System tests. Plugin configuration.
 * 
 * NOTE: the names of test methods for classes extending HudsonTestCase must begin with the word 'test'.
 * 
 * @author Marco Scata
 * 
 */
public class WhenConfiguringPlugin extends HudsonTestCase {
    private static JenkinsConfigurationPage page;

    @Before
    @Override
    public void setUp() throws Exception {
	super.setUp();
	page = new JenkinsConfigurationPage(createWebClient());
	page.load();
    }

    @After
    @Override
    public void tearDown() throws Exception {
	page.unload();
	super.tearDown();
    }

    // @Test
    // public void testShouldSaveJndiDatasourceDetails() {
    // final String datasourceName = "MyJndiDatasource";
    // final String user = "MyJndiUser";
    // final String password = "MyJndiPassword";
    //
    // page.setUseJndi(true);
    // page.setJndiDatasource(datasourceName);
    // page.setJndiUser(user);
    // page.setJndiPassword(password);
    // page.saveChanges();
    // page.load();
    //
    // Assert.assertTrue("The useJndi flag was not set to true.", page.isUseJndi());
    // Assert.assertEquals("Mismatched datasource name", datasourceName, page.getJndiDatasource());
    // Assert.assertEquals("Mismatched user", user, page.getJndiUser());
    // Assert.assertTrue("Mismatched password", page.getJndiPassword().isEmpty());
    // }

    @Test
    public void testShouldSaveJdbcDatasourceDetails() {
	final String jdbcDriver = "MyJdbcDriver";
	final String jdbcUrl = "MyJdbcUrl";
	final String user = "MyJdbcUser";
	final String password = "MyJdbcPassword";

	page.setJdbcDriver(jdbcDriver);
	page.setJdbcUrl(jdbcUrl);
	page.setJdbcUser(user);
	page.setJdbcPassword(password);
	page.saveChanges();
	page.load();

	WebAssert.assertInputContainsValue(page.getPage(),
		JenkinsConfigurationPage.AUDIT2DB_JDBC_DRIVER, jdbcDriver);
	WebAssert.assertInputContainsValue(page.getPage(),
		JenkinsConfigurationPage.AUDIT2DB_JDBC_URL, jdbcUrl);
	WebAssert.assertInputContainsValue(page.getPage(),
		JenkinsConfigurationPage.AUDIT2DB_JDBC_USER, user);
	WebAssert.assertInputContainsValue(page.getPage(),
		JenkinsConfigurationPage.AUDIT2DB_JDBC_PASSWORD, password);
    }

    @Test
    public void testSecurityMatrixShouldIncludeAuditReportsPermissions() {
	Assert.assertTrue(
		"Audit reports permission options seem to be missing",
		page.getAuditReportsPermissionColumnNumber() >= 0);
    }
}
