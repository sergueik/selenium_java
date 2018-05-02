/**
 * 
 */
package org.jenkins.plugins.audit2db.test.integration;

import org.jenkins.plugins.audit2db.test.integration.webpages.JobHistoryReportPage;
import org.junit.Ignore;
import org.junit.Test;

/**
 * @author Marco Scata
 *
 */
public class WhenAccessingJobHistoryReport extends WhenAccessingReportPages {

	@Ignore
	@SuppressWarnings("deprecation")
	@Override
	@Test
	public void testShouldGenerateErrorForAnonymousUser() {
		shouldGenerateErrorForAnonymousUser(
				new JobHistoryReportPage(createWebClient()));
	}

	@Ignore
	@SuppressWarnings("deprecation")
	@Override
	@Test
	public void testShouldGenerateErrorForNonAuditorUser() {
		shouldGenerateErrorForNonAuditorUser(
				new JobHistoryReportPage(createWebClient()));
	}

	@Ignore
	@SuppressWarnings("deprecation")
	@Override
	@Test
	public void testShouldAllowAccessForValidAuditor() {
		shouldAllowAccessForValidAuditor(
				new JobHistoryReportPage(createWebClient()));
	}
}
