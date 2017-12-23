/**
 *
 */
package org.jenkins.plugins.audit2db.test;

import java.util.List;
import java.util.Map;

import org.jenkins.plugins.audit2db.internal.DbAuditUtil;
import org.jenkins.plugins.audit2db.internal.reports.JobHistoryReportImpl;
import org.jenkins.plugins.audit2db.model.BuildDetails;
import org.jenkins.plugins.audit2db.reports.JobHistoryReport;
import org.junit.Assert;
import org.junit.Test;

/**
 * Contains tests for the {@link JobHistoryReportImpl} class.
 *
 * @author Marco Scata
 *
 */
public class JobHistoryReportTests {
    @Test
    public void matchingParametersShouldReturnNonEmptyResults() {
	final JobHistoryReport report = new JobHistoryReportImpl();
	report.setRepository(TestUtils.getTestRepository());

	final Map<String, List<BuildDetails>> dataset = TestUtils
		.createRandomDataset(DbAuditUtil.getHostName());
	// no need to use transactions because the mem db will be dumped
	// after each test run
	for (final List<BuildDetails> detailsList : dataset.values()) {
	    TestUtils.getTestRepository().saveBuildDetailsList(detailsList);
	}

	final String projectName = dataset.keySet().iterator().next();
	final Map<String, List<BuildDetails>> results = report
		.getProjectExecutions(projectName, TestUtils.NOW, TestUtils.NOW);

	Assert.assertFalse("Unexpected empty results", results.isEmpty());
    }

    @Test
    public void projectNameWithWildcardShouldReturnNonEmptyResults() {
	final JobHistoryReport report = new JobHistoryReportImpl();
	report.setRepository(TestUtils.getTestRepository());

	final Map<String, List<BuildDetails>> dataset = TestUtils
		.createRandomDataset(DbAuditUtil.getHostName());
	// no need to use transactions because the mem db will be dumped
	// after each test run
	for (final List<BuildDetails> detailsList : dataset.values()) {
	    TestUtils.getTestRepository().saveBuildDetailsList(detailsList);
	}

	final String projectName = dataset.keySet().iterator().next();
	final Map<String, List<BuildDetails>> results = report
		.getProjectExecutions(projectName.substring(0) + "%",
			TestUtils.NOW, TestUtils.NOW);

	Assert.assertFalse("Unexpected empty results", results.isEmpty());
    }

    @Test
    public void invalidProjectNameShouldReturnEmptyResults() {
	final JobHistoryReport report = new JobHistoryReportImpl();
	report.setRepository(TestUtils.getTestRepository());

	final Map<String, List<BuildDetails>> dataset = TestUtils
		.createRandomDataset(DbAuditUtil.getHostName());
	// no need to use transactions because the mem db will be dumped
	// after each test run
	for (final List<BuildDetails> detailsList : dataset.values()) {
	    TestUtils.getTestRepository().saveBuildDetailsList(detailsList);
	}

	final String projectName = dataset.keySet().iterator().next();
	final Map<String, List<BuildDetails>> results = report
		.getProjectExecutions(projectName.substring(0) + " INVALID",
			TestUtils.NOW, TestUtils.NOW);

	Assert.assertTrue("Unexpected results collection", results.isEmpty());
    }
}
