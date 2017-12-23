/**
 * 
 */
package org.jenkins.plugins.audit2db.test.integration;

import hudson.security.AuthorizationStrategy;
import hudson.security.SecurityRealm;

import java.util.Date;

import org.jenkins.plugins.audit2db.internal.DbAuditPlugin;
import org.jenkins.plugins.audit2db.test.integration.webpages.AbstractJenkinsPage;
import org.junit.After;
import org.junit.Before;
import org.jvnet.hudson.test.HudsonTestCase;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;

/**
 * @author Marco Scata
 *
 */
public abstract class WhenAccessingReportPages extends HudsonTestCase {
    private final static String AUDITOR_NAME = String.format("AUDITOR_"
	    + (new Date()).getTime());

    private SecurityRealm securityRealm;
    private AuthorizationStrategy authStrategy;

    private SecurityRealm getSecurityRealm() {
	if (null == securityRealm) {
	    securityRealm = createDummySecurityRealm();
	}
	return securityRealm;
    }

    private AuthorizationStrategy getAuthStrategy() {
	if (null == authStrategy) {
	    authStrategy = new AuditReportsAuthorizationStrategy(
		    jenkins.getAuthorizationStrategy(), AUDITOR_NAME,
		    DbAuditPlugin.RUN);
	}
	return authStrategy;
    }

    @Before
    @Override
    public void setUp() throws Exception {
	super.setUp();
	jenkins.setSecurityRealm(getSecurityRealm());
	jenkins.setAuthorizationStrategy(getAuthStrategy());
    }

    @After
    @Override
    public void tearDown() throws Exception {
	super.tearDown();
    }

    protected void shouldGenerateErrorForAnonymousUser(
	    final AbstractJenkinsPage page) {
	try {
	    page.load();
	    fail("Unexpected successful report access. The page should be inaccessible without the valid permissions.");
	} catch (final Exception e) {
	    // expecting HTTP status 403 - forbidden
	    assertEquals(
		    String.format("Unexpected exception type: %s !",
			    e.getMessage()),
			    FailingHttpStatusCodeException.class, e.getClass());
	} finally {
	    page.unload();
	}
    }

    protected void shouldGenerateErrorForNonAuditorUser(
	    final AbstractJenkinsPage page) {
	try {
	    page.getWebClient().login("NOT_AN_AUDITOR");
	    page.load();
	    fail("Unexpected successful report access. The page load should have failed without the valid permissions.");
	} catch (final Exception e) {
	    // expecting HTTP status 403 - forbidden
	    assertEquals(
		    String.format("Unexpected exception type: %s !",
			    e.getMessage()),
			    FailingHttpStatusCodeException.class, e.getClass());
	} finally {
	    page.unload();
	}
    }

    protected void shouldAllowAccessForValidAuditor(
	    final AbstractJenkinsPage page) {
	try {
	    page.getWebClient().login(AUDITOR_NAME);
	    page.load();
	} catch (final Exception e) {
	    // expecting successful access
	    fail("Unexpected failed access. Auditors should have valid permissions.");
	} finally {
	    page.unload();
	}
    }

    public abstract void testShouldGenerateErrorForAnonymousUser();

    public abstract void testShouldGenerateErrorForNonAuditorUser();

    public abstract void testShouldAllowAccessForValidAuditor();
}
