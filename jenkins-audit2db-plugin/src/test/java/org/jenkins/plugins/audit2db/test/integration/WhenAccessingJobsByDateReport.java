/**
 * 
 */
package org.jenkins.plugins.audit2db.test.integration;

import org.jenkins.plugins.audit2db.test.integration.webpages.JobsByDateReportPage;
import org.junit.Test;

/**
 * @author Marco Scata
 *
 */
public class WhenAccessingJobsByDateReport extends WhenAccessingReportPages {
    @Test
    public void testShouldGenerateErrorForAnonymousUser() {
	shouldGenerateErrorForAnonymousUser(new JobsByDateReportPage(
		createWebClient()));
    }

    @Test
    public void testShouldGenerateErrorForNonAuditorUser() {
	shouldGenerateErrorForNonAuditorUser(new JobsByDateReportPage(
		createWebClient()));
    }

    @Test
    public void testShouldAllowAccessForValidAuditor() {
	shouldAllowAccessForValidAuditor(new JobsByDateReportPage(
		createWebClient()));
    }
}
