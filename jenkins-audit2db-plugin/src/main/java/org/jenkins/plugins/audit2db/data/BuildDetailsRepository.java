/**
 *
 */
package org.jenkins.plugins.audit2db.data;

import hudson.model.AbstractBuild;

import java.util.Date;
import java.util.List;

import org.jenkins.plugins.audit2db.model.BuildDetails;
import org.jenkins.plugins.audit2db.model.BuildNode;

/**
 * @author Marco Scata
 *
 */
public interface BuildDetailsRepository {
    /**
     * Creates a new entity in the repository with the given build details.
     *
     * @param details
     *            the details to save.
     * @return the entity id.
     */
    Object saveBuildDetails(BuildDetails details);

    /**
     * Creates new entities in the repository according to the given list of
     * build details.
     *
     * @param details
     *            the collection of details to save.
     */
    void saveBuildDetailsList(List<BuildDetails> details);

    /**
     * Retrieves previously saved build details that match the given id.
     *
     * @param id
     *            the build details id.
     * @return the matching build details if found, otherwise <code>null</code>.
     */
    BuildDetails getBuildDetailsById(String id);

    /**
     * Retrieves previously saved build details that match the given name.
     *
     * @param name
     *            the build name
     * @return a list of matching build details if found, otherwise an empty
     *         list.
     */
    List<BuildDetails> getBuildDetailsByName(String name);

    /**
     * Retrieves previously saved build details that match the given full name.
     *
     * @param fullName
     * @return a list of matching build details if found, otherwise an empty
     *         list.
     */
    List<BuildDetails> getBuildDetailsByFullName(String fullName);

    /**
     * Retrieves previously saved build details whose start date or end date
     * fall between the given range. The range is inclusive.
     *
     * @param start
     *            the start date.
     * @param end
     *            the end date.
     * @return a list of matching build details if found, otherwise an empty
     *         list.
     */
    List<BuildDetails> getBuildDetailsByDateRange(Date start, Date end);

    /**
     * Retrieves previously saved build details whose duration in seconds falls
     * between the given range. The range is inclusive.
     *
     * @param min
     *            the minimum duration in seconds.
     * @param max
     *            the maximum duration in seconds.
     * @return a list of matching build details if found, otherwise an empty
     *         list.
     */
    List<BuildDetails> getBuildDetailsByDurationRange(long min, long max);

    /**
     * Retrieves previously saved build details that match the given user id.
     *
     * @param userId
     *            the user id to match.
     * @return a list of matching build details if found, otherwise an empty
     *         list.
     */
    List<BuildDetails> getBuildDetailsByUserId(String userId);

    /**
     * Retrieves previously saved build details that match the given user name.
     *
     * @param userName
     *            the user name to match.
     * @return a list of matching build details if found, otherwise an empty
     *         list.
     */
    List<BuildDetails> getBuildDetailsByUserName(String userName);

    /**
     * Retrieves previously saved build details that were executed using the
     * given parameter values.
     *
     * @param masterHostName
     *            the host name of the Jenkins master.
     * @param paramName
     *            the parameter name.
     * @param paramValue
     *            the parameter value.
     * @param fromDate
     *            the start (inclusive) of the date range.
     * @param toDate
     *            the end (inclusive) of the date range.
     * @return a list of build details matching the specified criteria. Never
     *         <code>null</code>.
     */
    List<BuildDetails> getBuildDetailsByParams(String masterHostName,
	    String paramName, String paramValue, Date fromDate, Date toDate);

    /**
     * Updates a previously saved build using the given build details.
     *
     * @param details
     *            the updated build details.
     */
    void updateBuildDetails(BuildDetails details);

    /**
     * Retrieves the build node that matches the given url.
     *
     * @param url
     *            the node url (unique).
     *
     * @return the matching node if found, otherwise <code>null</code>.
     */
    BuildNode getBuildNodeByUrl(String url);

    /**
     * Retrieves the build details for the given Jenkins build.
     *
     * @param build
     *            a reference to the Jenkins build.
     * @return the relevant build details if found, otherwise <code>null</code>.
     */
    BuildDetails getBuildDetailsForBuild(AbstractBuild<?, ?> build);

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
     * Retrieves a list of all the projects registered with the specified
     * Jenkins master that have had job instances executed between the specified
     * dates.
     *
     * @param masterHostName
     *            the host name of the Jenkins master.
     * @param pattern
     *            the pattern used to match project names.
     * @param fromDate
     *            the start (inclusive) of the date range.
     * @param toDate
     *            the end (inclusive) of the date range.
     * @return a list of project names, never <code>null</code>.
     */
    List<String> getProjectNames(String masterHostName, String pattern,
	    Date fromDate, Date toDate);

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
    List<BuildDetails> getBuildDetails(String masterHostName, Date fromDate,
	    Date toDate);

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
    List<BuildDetails> getBuildDetails(String masterHostName,
	    String projectName, Date fromDate, Date toDate);
}
