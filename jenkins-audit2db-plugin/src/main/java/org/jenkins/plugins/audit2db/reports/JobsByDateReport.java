/**
 * 
 */
package org.jenkins.plugins.audit2db.reports;

import java.util.List;
import java.util.Map;

import org.jenkins.plugins.audit2db.model.BuildDetails;

/**
 * @author Marco Scata
 * 
 */
public interface JobsByDateReport extends DbAuditReport {
    /**
     * Checks the given date string and, if invalid, returns a default date.
     * 
     * @param dateString
     *            a date in string format.
     * @return a valid start date string.
     */
    String getStartDateParam(String dateString);

    /**
     * Checks the given date string and, if invalid, returns a default date.
     * 
     * @param dateString
     *            a date in string format.
     * @return a valid end date string.
     */
    String getEndDateParam(String dateString);

    /**
     * Retrieves a number of project executions that were started or have ended
     * within the given dates. Each project can have multiple executions, so
     * this method returns a map where each entry's key is a project name, and
     * its value is a list of executions for that project.
     * 
     * @param startDate
     *            a valid start date.
     * @param endDate
     *            a valid end date.
     * @return a map of execution lists, keyed by project name.
     */
    Map<String, List<BuildDetails>> getProjectExecutions(String startDate,
	    String endDate);
}
