/**
 *
 */
package org.jenkins.plugins.audit2db.test;

import java.util.List;
import java.util.Map;

import org.jenkins.plugins.audit2db.internal.DbAuditUtil;
import org.jenkins.plugins.audit2db.internal.reports.JobsByParamReportImpl;
import org.jenkins.plugins.audit2db.model.BuildDetails;
import org.jenkins.plugins.audit2db.model.BuildParameter;
import org.jenkins.plugins.audit2db.reports.JobsByParamReport;
import org.junit.Assert;
import org.junit.Test;

/**
 * Contains tests for the {@link JobsByParamReportImpl} class.
 *
 * @author Marco Scata
 *
 */
public class JobsByParamReportTests {
    @Test
    public void matchingParametersShouldReturnNonEmptyResults() {
	final JobsByParamReport report = new JobsByParamReportImpl();
	report.setRepository(TestUtils.getTestRepository());

	final Map<String, List<BuildDetails>> dataset = TestUtils
		.createRandomDataset(DbAuditUtil.getHostName());
	// no need to use transactions because the mem db will be dumped
	// after each test run
	for (final List<BuildDetails> detailsList : dataset.values()) {
	    TestUtils.getTestRepository().saveBuildDetailsList(detailsList);
	}

	final BuildParameter param = dataset.values().iterator().next()
		.get(0).getParameters().get(0);

	final Map<String, List<BuildDetails>> results = report
		.getProjectExecutions(param.getName(),
			param.getValue(),
			TestUtils.NOW, TestUtils.NOW);

	Assert.assertFalse("Unexpected empty results", results.isEmpty());
    }

    @Test
    public void paramNameWithWildcardShouldReturnNonEmptyResults() {
	final JobsByParamReport report = new JobsByParamReportImpl();
	report.setRepository(TestUtils.getTestRepository());

	final Map<String, List<BuildDetails>> dataset = TestUtils
		.createRandomDataset(DbAuditUtil.getHostName());
	// no need to use transactions because the mem db will be dumped
	// after each test run
	for (final List<BuildDetails> detailsList : dataset.values()) {
	    TestUtils.getTestRepository().saveBuildDetailsList(detailsList);
	}

	final BuildParameter param = dataset.values().iterator().next()
	.get(0).getParameters().get(0);

        final Map<String, List<BuildDetails>> results = report
        	.getProjectExecutions(param.getName().substring(0) + "%",
        		param.getValue(),
        		TestUtils.NOW, TestUtils.NOW);

	Assert.assertFalse("Unexpected empty results", results.isEmpty());
    }

    @Test
    public void paramValueWithWildcardShouldReturnNonEmptyResults() {
	final JobsByParamReport report = new JobsByParamReportImpl();
	report.setRepository(TestUtils.getTestRepository());

	final Map<String, List<BuildDetails>> dataset = TestUtils
		.createRandomDataset(DbAuditUtil.getHostName());
	// no need to use transactions because the mem db will be dumped
	// after each test run
	for (final List<BuildDetails> detailsList : dataset.values()) {
	    TestUtils.getTestRepository().saveBuildDetailsList(detailsList);
	}

	final BuildParameter param = dataset.values().iterator().next()
	.get(0).getParameters().get(0);

        final Map<String, List<BuildDetails>> results = report
        	.getProjectExecutions(param.getName(),
        		param.getValue().substring(0) + "%",
        		TestUtils.NOW, TestUtils.NOW);

	Assert.assertFalse("Unexpected empty results", results.isEmpty());
    }

    @Test
    public void invalidParamNameShouldReturnEmptyResults() {
	final JobsByParamReport report = new JobsByParamReportImpl();
	report.setRepository(TestUtils.getTestRepository());

	final Map<String, List<BuildDetails>> dataset = TestUtils
		.createRandomDataset(DbAuditUtil.getHostName());
	// no need to use transactions because the mem db will be dumped
	// after each test run
	for (final List<BuildDetails> detailsList : dataset.values()) {
	    TestUtils.getTestRepository().saveBuildDetailsList(detailsList);
	}

	final BuildParameter param = dataset.values().iterator().next()
	.get(0).getParameters().get(0);

        final Map<String, List<BuildDetails>> results = report
        	.getProjectExecutions(param.getName() + " INVALID",
        		param.getValue(),
        		TestUtils.NOW, TestUtils.NOW);

	Assert.assertTrue("Unexpected results collection", results.isEmpty());
    }

    @Test
    public void invalidParamValueShouldReturnEmptyResults() {
	final JobsByParamReport report = new JobsByParamReportImpl();
	report.setRepository(TestUtils.getTestRepository());

	final Map<String, List<BuildDetails>> dataset = TestUtils
		.createRandomDataset(DbAuditUtil.getHostName());
	// no need to use transactions because the mem db will be dumped
	// after each test run
	for (final List<BuildDetails> detailsList : dataset.values()) {
	    TestUtils.getTestRepository().saveBuildDetailsList(detailsList);
	}

	final BuildParameter param = dataset.values().iterator().next()
	.get(0).getParameters().get(0);

        final Map<String, List<BuildDetails>> results = report
        	.getProjectExecutions(param.getName(),
        		param.getValue() + " INVALID",
        		TestUtils.NOW, TestUtils.NOW);

	Assert.assertTrue("Unexpected results collection", results.isEmpty());
    }
}
