/**
 *
 */
package org.jenkins.plugins.audit2db.test.integration;

import org.jenkins.plugins.audit2db.test.integration.webpages.JobsByDateReportPage;
import org.junit.Ignore;
import org.junit.Test;

/**
 * @author Marco Scata
 *
 */
public class WhenAccessingJobsByDateReport extends WhenAccessingReportPages {

	@Ignore
	@SuppressWarnings("deprecation")
	@Test
	public void testShouldGenerateErrorForAnonymousUser() {
		shouldGenerateErrorForAnonymousUser(
				new JobsByDateReportPage(createWebClient()));
	}

	@Ignore
	@SuppressWarnings("deprecation")
	@Test
	public void testShouldGenerateErrorForNonAuditorUser() {
		shouldGenerateErrorForNonAuditorUser(
				new JobsByDateReportPage(createWebClient()));
	}

	@SuppressWarnings("deprecation")
	@Test
	public void testShouldAllowAccessForValidAuditor() {
		shouldAllowAccessForValidAuditor(
				new JobsByDateReportPage(createWebClient()));
	}
}
