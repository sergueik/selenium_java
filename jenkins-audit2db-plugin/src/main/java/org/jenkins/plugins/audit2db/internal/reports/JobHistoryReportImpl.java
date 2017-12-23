/**
 *
 */
package org.jenkins.plugins.audit2db.internal.reports;

import hudson.Extension;
import hudson.model.Descriptor;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jenkins.model.Jenkins;

import org.jenkins.plugins.audit2db.Messages;
import org.jenkins.plugins.audit2db.internal.DbAuditPlugin;
import org.jenkins.plugins.audit2db.model.BuildDetails;
import org.jenkins.plugins.audit2db.reports.DbAuditReport;
import org.jenkins.plugins.audit2db.reports.JobHistoryReport;

/**
 * @author Marco Scata
 *
 */
@Extension
public class JobHistoryReportImpl extends AbstractDbAuditReport implements
JobHistoryReport {
    @Extension
    public static final class DescriptorImpl extends Descriptor<DbAuditReport> {
	@Override
	public String getDisplayName() {
	    return Messages.DbAuditReportsJobHistory_ReportTitle();
	}
    }


    @Override
    public String getDateGenerated() {
	return DbAuditReportUtils.dateAsString(new Date(), true);
    }

    @Override
    public String getStartDateParam(final String dateString) {
	return DbAuditReportUtils.getStartDateParam(dateString);
    }

    @Override
    public String getEndDateParam(final String dateString) {
	return DbAuditReportUtils.getEndDateParam(dateString);
    }

    @Override
    public String getReportDescription() {
	return Messages.DbAuditReportsJobHistory_ReportDescription();
    }

    @Override
    public String getReportDisplayedInfo() {
	return Messages.DbAuditReportsJobHistory_ReportDisplayedInfo();
    }

    @Override
    public String getDisplayName() {
	return Messages.DbAuditReportsJobHistory_ReportTitle();
    }

    @Override
    public String getUrlName() {
	return "jobHistory";
    }

    @Override
    public Map<String, List<BuildDetails>> getProjectExecutions(
	    final String jobName, final String startDateString,
	    final String endDateString) {
	final Jenkins jenkins = Jenkins.getInstance();
	if (jenkins != null) {
	    // unit tests won't have a Jenkins instance
	    jenkins.checkPermission(DbAuditPlugin.RUN);
	}
	final Map<String, List<BuildDetails>> retval = new HashMap<String, List<BuildDetails>>();
	final Date startDate = DbAuditReportUtils.stringToDate(startDateString);
	final Date endDate = DbAuditReportUtils.stringToDate(endDateString);
	final String jenkinsHost = getJenkinsHostname();
	final List<String> projectNames = getRepository().getProjectNames(
		jenkinsHost, jobName, startDate, endDate);
	for (final String projectName : projectNames) {
	    final List<BuildDetails> buildDetails = getRepository()
	    .getBuildDetails(
		    jenkinsHost, projectName, startDate, endDate);
	    if (!buildDetails.isEmpty()) {
		retval.put(projectName, buildDetails);
	    }
	}

	return retval;
    }

}
