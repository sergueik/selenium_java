/**
 *
 */
package org.jenkins.plugins.audit2db.internal.data;

import hudson.model.AbstractBuild;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.jenkins.plugins.audit2db.data.BuildDetailsRepository;
import org.jenkins.plugins.audit2db.internal.model.BuildDetailsImpl;
import org.jenkins.plugins.audit2db.internal.model.BuildNodeImpl;
import org.jenkins.plugins.audit2db.model.BuildDetails;
import org.jenkins.plugins.audit2db.model.BuildNode;
import org.springframework.transaction.TransactionStatus;

/**
 * Hibernate-based implementation of the {@link BuildDetailsRepository}
 * interface.
 *
 * @author Marco Scata
 *
 */
public class BuildDetailsHibernateRepository extends
	AbstractHibernateRepository implements BuildDetailsRepository {

    private final static Logger LOGGER = Logger
	    .getLogger(BuildDetailsHibernateRepository.class.getName());

    public BuildDetailsHibernateRepository(final SessionFactory sessionFactory) {
	super(sessionFactory);
    }

    /**
     * @see org.jenkins.plugins.audit2db.data.BuildDetailsRepository#getBuildNodeByUrl(String)
     */
    @Override
    public BuildNode getBuildNodeByUrl(final String url) {
	if (null == url) {
	    throw new IllegalArgumentException("Invalid url: cannot be null.");
	}

	BuildNode retval = null;

	final DetachedCriteria criteria = DetachedCriteria
		.forClass(BuildNodeImpl.class);
	criteria.add(Restrictions.eq("url", url).ignoreCase());

	try {
	    @SuppressWarnings("unchecked")
	    final List<BuildNode> nodes = getHibernateTemplate()
		    .findByCriteria(criteria);
	    if ((nodes != null) && !nodes.isEmpty()) {
		retval = nodes.get(0);
	    }
	} catch (final Throwable t) {
	    LOGGER.log(Level.SEVERE, t.getMessage(), t);
	}
	return retval;
    }

    /**
     * @see org.jenkins.plugins.audit2db.data.BuildDetailsRepository#saveBuildDetails(org.jenkins.plugins.audit2db.model.BuildDetails)
     */
    @Override
    public Object saveBuildDetails(final BuildDetails details) {
	if (null == details) {
	    throw new IllegalArgumentException(
		    "Invalid build details: cannot be null.");
	}

	// check if the build node details are already persisted
	final String url = details.getNode().getUrl();
	final BuildNode node = getBuildNodeByUrl(url);
	if (node != null) {
	    details.setNode(node);
	}

	return getHibernateTemplate().save(details);
    }

    /**
     * @see org.jenkins.plugins.audit2db.data.BuildDetailsRepository#saveBuildDetailsList(List)
     */
    @Override
    public void saveBuildDetailsList(final List<BuildDetails> details) {
	if (null == details) {
	    throw new IllegalArgumentException(
		    "Invalid build details: cannot be null.");
	}

	final TransactionStatus tx = getTransactionManager().getTransaction(
		null);
	try {
	    for (final BuildDetails detail : details) {
		saveBuildDetails(detail);
	    }
	    getTransactionManager().commit(tx);
	} catch (final Exception e) {
	    getTransactionManager().rollback(tx);
	    LOGGER.log(Level.SEVERE,
		    "An error occurred while saving the build details.", e);
	}
    }

    /**
     * @see org.jenkins.plugins.audit2db.data.BuildDetailsRepository#getBuildDetailsById(String)
     */
    @Override
    public BuildDetails getBuildDetailsById(final String id) {
	return getHibernateTemplate().get(BuildDetailsImpl.class, id);
    }

    /**
     * @see org.jenkins.plugins.audit2db.data.BuildDetailsRepository#getBuildDetailsByName(java.lang.String)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<BuildDetails> getBuildDetailsByName(final String name) {
	final DetachedCriteria criteria = DetachedCriteria
		.forClass(BuildDetails.class);
	criteria.add(Restrictions.ilike("name", name, MatchMode.EXACT));
	return getHibernateTemplate().findByCriteria(criteria);
    }

    /**
     * @see org.jenkins.plugins.audit2db.data.BuildDetailsRepository#getBuildDetailsByFullName(java.lang.String)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<BuildDetails> getBuildDetailsByFullName(final String fullName) {
	final DetachedCriteria criteria = DetachedCriteria
		.forClass(BuildDetails.class);
	criteria.add(
		Restrictions.ilike("fullName", fullName, MatchMode.EXACT));
	return getHibernateTemplate().findByCriteria(criteria);
    }

    /**
     * @see org.jenkins.plugins.audit2db.data.BuildDetailsRepository#getBuildDetailsByDateRange(java.util.Date,
     *      java.util.Date)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<BuildDetails> getBuildDetailsByDateRange(final Date start,
	    final Date end) {
	final DetachedCriteria criteria = DetachedCriteria
		.forClass(BuildDetails.class);

	// we need this funny-looking complex criteria because the
	// semantics of the 'between' criteria can vary across
	// db providers and we want a predictable inclusive behaviour.
	final Date inclusiveStartDate = getInclusiveStartDate(start);
	final Date inclusiveEndDate = getInclusiveEndDate(end);
	criteria.add(
		Restrictions.or(
			Restrictions.and(
                		Restrictions.ge("startDate", inclusiveStartDate),
                		Restrictions.le("startDate", inclusiveEndDate)),
                	Restrictions.and(
                		Restrictions.ge("endDate", inclusiveStartDate),
                		Restrictions.le("endDate", inclusiveEndDate))
                )
        );
	return getHibernateTemplate().findByCriteria(criteria);
    }

    private Date getInclusiveEndDate(final Date end) {
	final Calendar inclusiveEndDate = Calendar.getInstance();
	inclusiveEndDate.setTime(end);
	inclusiveEndDate.set(Calendar.HOUR_OF_DAY, 23);
	inclusiveEndDate.set(Calendar.MINUTE, 59);
	inclusiveEndDate.set(Calendar.SECOND, 59);
	inclusiveEndDate.set(Calendar.MILLISECOND, 999);
	return inclusiveEndDate.getTime();
    }

    private Date getInclusiveStartDate(final Date start) {
	final Calendar inclusiveStartDate = Calendar.getInstance();
	inclusiveStartDate.setTime(start);
	inclusiveStartDate.set(Calendar.HOUR_OF_DAY, 0);
	inclusiveStartDate.set(Calendar.MINUTE, 0);
	inclusiveStartDate.set(Calendar.SECOND, 0);
	inclusiveStartDate.set(Calendar.MILLISECOND, 0);
	return inclusiveStartDate.getTime();
    }

    /**
     * @see org.jenkins.plugins.audit2db.data.BuildDetailsRepository#getBuildDetailsByDuration(long)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<BuildDetails> getBuildDetailsByDurationRange(final long min,
	    final long max) {
	final DetachedCriteria criteria = DetachedCriteria
		.forClass(BuildDetails.class);
	// we need this funny-looking complex criteria because the
	// semantics of the 'between' criteria can vary across
	// db providers and we want a predictable inclusive behaviour.
	criteria.add(
		Restrictions.and(
			Restrictions.ge("duration", min),
			Restrictions.le("duration", max)
		)
	);
	return getHibernateTemplate().findByCriteria(criteria);
    }

    /**
     * @see org.jenkins.plugins.audit2db.data.BuildDetailsRepository#getBuildDetailsByUserId(java.lang.String)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<BuildDetails> getBuildDetailsByUserId(final String userId) {
	final DetachedCriteria criteria = DetachedCriteria
		.forClass(BuildDetails.class);
	criteria.add(Restrictions.ilike("userId", userId, MatchMode.EXACT));
	return getHibernateTemplate().findByCriteria(criteria);
    }

    /**
     * @see org.jenkins.plugins.audit2db.data.BuildDetailsRepository#getBuildDetailsByUserName(java.lang.String)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<BuildDetails> getBuildDetailsByUserName(final String userName) {
	final DetachedCriteria criteria = DetachedCriteria
		.forClass(BuildDetails.class);
	criteria.add(Restrictions.ilike("userName", userName, MatchMode.EXACT));
	return getHibernateTemplate().findByCriteria(criteria);
    }

    /**
     * @see org.jenkins.plugins.audit2db.data.BuildDetailsRepository#getBuildDetailsByParams(String, String, String, Date, Date)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<BuildDetails> getBuildDetailsByParams(
	    final String masterHostName,
	    final String paramName, final String paramValue,
	    final Date fromDate, final Date toDate) {

	// we need to specifically state >=startdate AND <=enddate
	// because the "between" semantics vary between database
	// implementations and we want to use an inclusive filter every time
	final DetachedCriteria criteria = DetachedCriteria
		.forClass(BuildDetails.class)
		.createAlias("node", "node")
		.add(Restrictions.eq("node.masterHostName", masterHostName))
		.add(Restrictions.and(
			Restrictions.ge("startDate", getInclusiveStartDate(fromDate)),
			Restrictions.le("endDate", getInclusiveEndDate(toDate))))
		.addOrder(Property.forName("startDate").asc());

	if ((null == paramName) || paramName.isEmpty()) {
	    criteria.add(Restrictions.isEmpty("parameters"));
	} else {
	    criteria.createAlias("parameters", "param")
	    	.add(Restrictions.and(
		    Restrictions.ilike("param.name", paramName),
		    Restrictions.ilike("param.value", paramValue)
	    ));
	}

	final List<BuildDetails> retval = new ArrayList<BuildDetails>();
	try {
	    final List<BuildDetails> buildDetails = getHibernateTemplate()
		    .findByCriteria(criteria);
	    if ((buildDetails != null) && !buildDetails.isEmpty()) {
		retval.addAll(buildDetails);
	    }
	} catch (final Throwable t) {
	    LOGGER.log(Level.SEVERE, t.getMessage(), t);
	}

	return retval;
    }

    /**
     * @see org.jenkins.plugins.audit2db.data.BuildDetailsRepository#updateBuildDetails(org.jenkins.plugins.audit2db.model.BuildDetails)
     */
    @Override
    public void updateBuildDetails(final BuildDetails details) {
	if (null == details) {
	    throw new IllegalArgumentException(
		    "Invalid build details: cannot be null.");
	}
	getHibernateTemplate().update(details);
    }

    /**
     * @see org.jenkins.plugins.audit2db.data.BuildDetailsRepository#getBuildDetailsForBuild(AbstractBuild)
     */
    @Override
    public BuildDetails getBuildDetailsForBuild(final AbstractBuild<?, ?> build) {
	final String id = new BuildDetailsImpl(build).getId();
	return getBuildDetailsById(id);
    }

    @Override
    public List<String> getProjectNames(final String masterHostName,
	    final Date fromDate, final Date toDate) {
	return getProjectNames(masterHostName, null, fromDate, toDate);
    }

    /**
     * Retrieves the names of all projects on the given Jenkins master, filtered
     * by name pattern and date range. The name pattern accepts wildcards. A
     * <code>null</code> name pattern will match all names.
     */
    @Override
    public List<String> getProjectNames(final String masterHostName,
	    final String pattern, final Date fromDate, final Date toDate) {
	final List<String> retval = new ArrayList<String>();

	DetachedCriteria criteria = DetachedCriteria
		.forClass(BuildDetails.class)
		.add(Restrictions.and(
			Restrictions.ge("startDate", getInclusiveStartDate(fromDate)),
			Restrictions.le("endDate", getInclusiveEndDate(toDate))
		));

	if ((pattern != null) && !pattern.isEmpty()
		&& !pattern.trim().equals("%")) {
	    criteria = criteria.add(Restrictions.ilike("name", pattern));
	}

	criteria = criteria.createCriteria("node").add(
		Restrictions.ilike("masterHostName", masterHostName));

	try {
	    @SuppressWarnings("unchecked")
	    final List<BuildDetails> buildDetails = getHibernateTemplate()
		    .findByCriteria(criteria);
	    if ((buildDetails != null) && !buildDetails.isEmpty()) {
		for (final BuildDetails detail : buildDetails) {
		    final String projectName = detail.getName();
		    if (!retval.contains(projectName)) {
			retval.add(projectName);
		    }
		}
	    }
	} catch (final Throwable t) {
	    LOGGER.log(Level.SEVERE, t.getMessage(), t);
	}

	return retval;
    }

    @Override
    public List<BuildDetails> getBuildDetails(final String masterHostName,
	    final Date fromDate, final Date toDate) {
	return getBuildDetails(masterHostName, null, fromDate, toDate);
    }

    /**
     * @see org.jenkins.plugins.audit2db.data.AuditReportsRepository#getBuildDetails(java.lang.String,
     *      java.sql.Date, java.sql.Date)
     */
    @Override
    public List<BuildDetails> getBuildDetails(final String masterHostName,
	    final String projectName, final Date fromDate, final Date toDate) {
	final List<BuildDetails> retval = new ArrayList<BuildDetails>();

	// we need to specifically state >=startdate AND <=enddate
	// because the "between" semantics vary between database
	// implementations and we want to use an inclusive filter every time
	DetachedCriteria criteria = DetachedCriteria
		.forClass(BuildDetails.class);
	if (projectName != null) {
	    criteria = criteria.add(Restrictions.ilike("name", projectName));
	}
	criteria = criteria
		.add(Restrictions.and(
			Restrictions.ge("startDate", getInclusiveStartDate(fromDate)),
			Restrictions.le("endDate", getInclusiveEndDate(toDate))))
		.addOrder(Property.forName("startDate").asc())
		.createAlias("node", "node")
		.add(Restrictions.ilike("node.masterHostName", masterHostName));

	try {
	    @SuppressWarnings("unchecked")
	    final List<BuildDetails> buildDetails = getHibernateTemplate()
		    .findByCriteria(criteria);
	    if ((buildDetails != null) && !buildDetails.isEmpty()) {
		retval.addAll(buildDetails);
	    }
	} catch (final Throwable t) {
	    LOGGER.log(Level.SEVERE, t.getMessage(), t);
	}

	return retval;
    }
}
