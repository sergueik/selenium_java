/**
 * 
 */
package org.jenkins.plugins.audit2db.data;

import java.util.Date;
import java.util.List;

import org.jenkins.plugins.audit2db.model.BuildDetails;

/**
 * @author Marco Scata
 * 
 */
public interface AuditReportsRepository {
    /**
     * Retrieves a list of all the projects registered with the specified
     * Jenkins master that have had job instances executed between the specified
     * dates.
     * 
     * @param masterHostName
     *            the host name of the Jenkins master.
     * @param fromDate
     *            the start (inclusive) of the date range.
     * @param toDate
     *            the end (inclusive) of the date range.
     * @return a list of project names, never <code>null</code>.
     */
    List<String> getProjectNames(String masterHostName, Date fromDate,
	    Date toDate);

    /**
     * Retrieves the build details for all job instances that ran on the
     * specified Jenkins master or all the slaves registered with that master,
     * between two dates for al projects.
     * 
     * @param masterHostName
     *            the host name of the Jenkins master.
     * @param fromDate
     *            the start (inclusive) of the date range.
     * @param toDate
     *            the end (inclusive) of the date range.
     * @return a list of build details matching the specified criteria. Never
     *         <code>null</code>.
     */
    List<BuildDetails> getBuildDetails(String masterHostName,
	    Date fromDate, Date toDate);

    /**
     * Retrieves the build details for all job instances that ran on the
     * specified Jenkins master or all the slaves registered with that master,
     * between two dates and for a specific project.
     * 
     * @param masterHostName
     *            the host name of the Jenkins master.
     * @param projectName
     *            the name of the project that was executed.
     * @param fromDate
     *            the start (inclusive) of the date range.
     * @param toDate
     *            the end (inclusive) of the date range.
     * @return a list of build details matching the specified criteria. Never
     *         <code>null</code>.
     */
    List<BuildDetails> getBuildDetails(
	    String masterHostName, String projectName, Date fromDate,
	    Date toDate);
}
