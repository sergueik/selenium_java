/**
 *
 */
package org.jenkins.plugins.audit2db.test.integration;

import org.jenkins.plugins.audit2db.test.integration.webpages.AuditReportsListPage;
import org.junit.Ignore;
import org.junit.Test;

/**
 * @author Marco Scata
 *
 */
public class WhenAccessingReportsList extends WhenAccessingReportPages {

	@Ignore
	@SuppressWarnings("deprecation")
	@Test
	public void testShouldGenerateErrorForAnonymousUser() {
		shouldGenerateErrorForAnonymousUser(
				new AuditReportsListPage(createWebClient()));
	}

	@Ignore
	@SuppressWarnings("deprecation")
	@Test
	public void testShouldGenerateErrorForNonAuditorUser() {
		shouldGenerateErrorForNonAuditorUser(
				new AuditReportsListPage(createWebClient()));
	}

	@Ignore
	@SuppressWarnings("deprecation")
	@Test
	public void testShouldAllowAccessForValidAuditor() {
		shouldAllowAccessForValidAuditor(
				new AuditReportsListPage(createWebClient()));
	}
}
