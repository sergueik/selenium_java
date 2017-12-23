/**
 * 
 */
package org.jenkins.plugins.audit2db.internal;

import hudson.Extension;

import java.util.List;

import jenkins.model.Jenkins;
import jenkins.model.ModelObjectWithContextMenu.ContextMenu;

import org.jenkins.plugins.audit2db.DbAuditReportsDashboard;
import org.jenkins.plugins.audit2db.Messages;
import org.jenkins.plugins.audit2db.reports.DbAuditReport;
import org.kohsuke.stapler.StaplerRequest;
import org.kohsuke.stapler.StaplerResponse;

/**
 * @author Marco Scata
 * 
 */
@Extension
public class DbAuditReportsDashboardImpl implements DbAuditReportsDashboard {

    @Override
    public String getDisplayName() {
	return Messages.DbAuditReportsDashboard_DisplayName();
    }

    @Override
    public String getIconFileName() {
	if (Jenkins.getInstance().hasPermission(DbAuditPlugin.RUN)) {
	    return "folder.gif";
	} else {
	    return null;
	}
    }

    @Override
    public String getUrlName() {
	return "/audit2db.reports";
    }

    /**
     * Checks all registered reports and returns the one whose url name matches
     * the given name in the Stapler request. For example, a request for
     * "./myReport" will return the report whose getUrlName() methods returns
     * "myReport".
     * 
     * @param name
     *            the name parameter in the Stapler request.
     * @return the matching report, or <code>null</code> if none is found.
     */
    public DbAuditReport getDynamic(final String name) {
	Jenkins.getInstance().checkPermission(DbAuditPlugin.RUN);
	for (final DbAuditReport report : getAllReports()) {
	    if (report.getUrlName().equals(name)) {
		return report;
	    }
	}
	return null;
    }

    /**
     * @return all registered implementations of {@link DbAuditReport}.
     */
    public List<DbAuditReport> getAllReports() {
	final List<DbAuditReport> retval = Jenkins.getInstance()
	.getExtensionList(DbAuditReport.class);

	return retval;
    }

    /**
     * 
     * @param request
     *            the Stapler request object.
     * @param response
     *            the Stapler response object.
     * @return a new context menu with all reports.
     * @throws Exception
     *             if something goes wrong.
     */
    public ContextMenu doContextMenu(final StaplerRequest request,
	    final StaplerResponse response) throws Exception {
	Jenkins.getInstance().checkPermission(DbAuditPlugin.RUN);
	final ContextMenu retval = new ContextMenu();
	for (final DbAuditReport report : getAllReports()) {
	    retval.add(
		    String.format("%s/%s/", this.getUrlName(),
			    report.getUrlName()),
			    report.getDisplayName());
	}
	return retval;
    }
}
