/**
 *
 */
package org.jenkins.plugins.audit2db.test;

import java.util.List;
import java.util.Map;

import org.jenkins.plugins.audit2db.internal.DbAuditUtil;
import org.jenkins.plugins.audit2db.internal.reports.JobsByDateReportImpl;
import org.jenkins.plugins.audit2db.model.BuildDetails;
import org.jenkins.plugins.audit2db.reports.JobsByDateReport;
import org.junit.Assert;
import org.junit.Test;

/**
 * Contains tests for the {@link JobsByDateReportImpl} class.
 *
 * @author Marco Scata
 *
 */
public class JobsByDateReportTests {
    @Test
    public void matchingParametersShouldReturnNonEmptyResults() {
	final JobsByDateReport report = new JobsByDateReportImpl();
	report.setRepository(TestUtils.getTestRepository());

	final Map<String, List<BuildDetails>> dataset = TestUtils
		.createRandomDataset(DbAuditUtil.getHostName());
	// no need to use transactions because the mem db will be dumped
	// after each test run
	for (final List<BuildDetails> detailsList : dataset.values()) {
	    TestUtils.getTestRepository().saveBuildDetailsList(detailsList);
	}

	final Map<String, List<BuildDetails>> results = report
		.getProjectExecutions(
			TestUtils.NOW, TestUtils.NOW);

	Assert.assertFalse("Unexpected empty results", results.isEmpty());
    }

    @Test
    public void invalidDateRangeShouldReturnEmptyResults() {
	final JobsByDateReport report = new JobsByDateReportImpl();
	report.setRepository(TestUtils.getTestRepository());

	final Map<String, List<BuildDetails>> dataset = TestUtils
		.createRandomDataset(DbAuditUtil.getHostName());
	// no need to use transactions because the mem db will be dumped
	// after each test run
	for (final List<BuildDetails> detailsList : dataset.values()) {
	    TestUtils.getTestRepository().saveBuildDetailsList(detailsList);
	}

	Map<String, List<BuildDetails>> results = report
		.getProjectExecutions(
			TestUtils.YESTERDAY, TestUtils.YESTERDAY);
	Assert.assertTrue("Unexpected results collection", results.isEmpty());

	results = report
		.getProjectExecutions(
			TestUtils.TOMORROW, TestUtils.TOMORROW);
	Assert.assertTrue("Unexpected results collection", results.isEmpty());
    }
}
