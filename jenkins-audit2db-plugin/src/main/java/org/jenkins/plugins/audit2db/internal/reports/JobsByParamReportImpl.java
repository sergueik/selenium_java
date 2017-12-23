/**
 *
 */
package org.jenkins.plugins.audit2db.internal.reports;

import hudson.Extension;
import hudson.model.Descriptor;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jenkins.model.Jenkins;

import org.jenkins.plugins.audit2db.Messages;
import org.jenkins.plugins.audit2db.internal.DbAuditPlugin;
import org.jenkins.plugins.audit2db.model.BuildDetails;
import org.jenkins.plugins.audit2db.reports.DbAuditReport;
import org.jenkins.plugins.audit2db.reports.JobsByParamReport;

/**
 * @author Marco Scata
 *
 */
@Extension
public class JobsByParamReportImpl extends AbstractDbAuditReport implements JobsByParamReport {

    @Extension
    public static final class DescriptorImpl extends Descriptor<DbAuditReport> {
	@Override
	public String getDisplayName() {
	    return Messages.DbAuditReportsJobsByParam_ReportTitle();
	}
    }

    /**
     * @see org.jenkins.plugins.audit2db.reports.DbAuditReport#getDateGenerated()
     */
    @Override
    public String getDateGenerated() {
	return DbAuditReportUtils.dateAsString(new Date(), true);
    }

    /**
     * @see org.jenkins.plugins.audit2db.reports.DbAuditReport#getReportDescription()
     */
    @Override
    public String getReportDescription() {
	return Messages.DbAuditReportsJobsByParam_ReportDescription();
    }

    /**
     * @see org.jenkins.plugins.audit2db.reports.DbAuditReport#getReportDisplayedInfo()
     */
    @Override
    public String getReportDisplayedInfo() {
	return Messages.DbAuditReportsJobsByParam_ReportDisplayedInfo();
    }

    /**
     * @see hudson.model.Action#getDisplayName()
     */
    @Override
    public String getDisplayName() {
	return Messages.DbAuditReportsJobsByParam_ReportTitle();
    }

    /**
     * @see hudson.model.Action#getUrlName()
     */
    @Override
    public String getUrlName() {
	return "jobsByParam";
    }

    /**
     * @see org.jenkins.plugins.audit2db.reports.JobsByParamReport#getStartDateParam(java.lang.String)
     */
    @Override
    public String getStartDateParam(final String dateString) {
	return DbAuditReportUtils.getStartDateParam(dateString);
    }

    /**
     * @see org.jenkins.plugins.audit2db.reports.JobsByParamReport#getEndDateParam(java.lang.String)
     */
    @Override
    public String getEndDateParam(final String dateString) {
	return DbAuditReportUtils.getEndDateParam(dateString);
    }

    /**
     * @see org.jenkins.plugins.audit2db.reports.JobsByParamReport#getProjectExecutions(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
     */
    @Override
    public Map<String, List<BuildDetails>> getProjectExecutions(
	    final String paramName, final String paramValue,
	    final String startDateString,final String endDateString) {
	final Jenkins jenkins = Jenkins.getInstance();
	if (jenkins != null) {
	    // unit tests won't have a Jenkins instance
	    jenkins.checkPermission(DbAuditPlugin.RUN);
	}
	final Map<String, List<BuildDetails>> retval = new HashMap<String, List<BuildDetails>>();
	final Date startDate = DbAuditReportUtils.stringToDate(startDateString);
	final Date endDate = DbAuditReportUtils.stringToDate(endDateString);
	final String jenkinsHost = getJenkinsHostname();
	final List<BuildDetails> buildDetails = getRepository().getBuildDetailsByParams(
		jenkinsHost, paramName, paramValue, startDate, endDate);

	for (final BuildDetails details : buildDetails) {
	    final String projectName = details.getName();
	    if (!retval.containsKey(projectName)) {
		retval.put(projectName, new ArrayList<BuildDetails>());
	    }
	    retval.get(projectName).add(details);
	}

	return retval;
    }

}
