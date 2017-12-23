/**
 * 
 */
package org.jenkins.plugins.audit2db.internal.model;

import hudson.model.AbstractBuild;
import hudson.model.Cause;
import hudson.model.Cause.UserIdCause;
import hudson.model.CauseAction;
import hudson.model.Computer;
import hudson.model.Node;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.jenkins.plugins.audit2db.model.BuildDetails;
import org.jenkins.plugins.audit2db.model.BuildNode;
import org.jenkins.plugins.audit2db.model.BuildParameter;

/**
 * Data class for build details.
 * 
 * @author Marco Scata
 * 
 */
@Entity(name = "JENKINS_BUILD_DETAILS")
public class BuildDetailsImpl implements BuildDetails {
    private final static Logger LOGGER = Logger
	    .getLogger(BuildDetailsImpl.class.getName());

    private String id;
    private String name;
    private String fullName;
    private Date startDate = new Date();
    private Date endDate;
    private Long duration;
    private String result;
    private String userId;
    private String userName;
    private final List<BuildParameter> parameters = new ArrayList<BuildParameter>();
    private BuildNode node = new BuildNodeImpl();

    /**
     * @see org.jenkins.plugins.audit2db.model.BuildDetails#getId()
     */
    @Id
    @Column(nullable = false, unique = true)
    @Override
    public String getId() {
	return id;
    }

    /**
     * @see org.jenkins.plugins.audit2db.model.BuildDetails#setId(java.lang.String)
     */
    @Override
    public void setId(final String id) {
	this.id = id;
    }

    /**
     * @see org.jenkins.plugins.audit2db.model.BuildDetails#getName()
     */
    @Override
    @Column(nullable = false, unique = false)
    public String getName() {
	return name;
    }

    /**
     * @see org.jenkins.plugins.audit2db.model.BuildDetails#setName(java.lang.String)
     */
    @Override
    public void setName(final String name) {
	this.name = name;
    }

    /**
     * @see org.jenkins.plugins.audit2db.model.BuildDetails#getFullName()
     */
    @Column(nullable = false, unique = false)
    @Override
    public String getFullName() {
	return fullName;
    }

    /**
     * @see org.jenkins.plugins.audit2db.model.BuildDetails#setFullName(java.lang.String)
     */
    @Override
    public void setFullName(final String fullName) {
	this.fullName = fullName;
    }

    /**
     * @see org.jenkins.plugins.audit2db.model.BuildDetails#getStartDate()
     */
    @Column(nullable = false, unique = false)
    @Override
    public Date getStartDate() {
	return startDate;
    }

    /**
     * @see org.jenkins.plugins.audit2db.model.BuildDetails#setStartDate(java.util.Date)
     */
    @Override
    public void setStartDate(final Date start) {
	this.startDate = start;
    }

    /**
     * @see org.jenkins.plugins.audit2db.model.BuildDetails#getEndDate()
     */
    @Column(nullable = true, unique = false)
    @Override
    public Date getEndDate() {
	return endDate;
    }

    /**
     * @see org.jenkins.plugins.audit2db.model.BuildDetails#setEndDate(java.util.Date)
     */
    @Override
    public void setEndDate(final Date end) {
	this.endDate = end;
    }

    /**
     * @see org.jenkins.plugins.audit2db.model.BuildDetails#getDuration()
     */
    @Column(nullable = true, unique = false)
    @Override
    public Long getDuration() {
	return duration;
    }

    /**
     * @see org.jenkins.plugins.audit2db.model.BuildDetails#setDuration(java.lang.Long)
     */
    @Override
    public void setDuration(final Long duration) {
	this.duration = duration;
    }

    /**
     * @see org.jenkins.plugins.audit2db.model.BuildDetails#getResult()
     */

    @Column(nullable = true, unique = false)
    @Override
    public String getResult() {
	return result;
    }

    /**
     * @see org.jenkins.plugins.audit2db.model.BuildDetails#setResult(java.lang.String)
     */
    @Override
    public void setResult(final String result) {
	if (result != null) {
	    this.result = result.toString();
	}
    }

    /**
     * @see org.jenkins.plugins.audit2db.model.BuildDetails#getUserId()
     */
    @Column(nullable = true, unique = false)
    @Override
    public String getUserId() {
	return userId;
    }

    /**
     * @see org.jenkins.plugins.audit2db.model.BuildDetails#setUserId(java.lang.String)
     */
    @Override
    public void setUserId(final String userId) {
	this.userId = userId;
    }

    /**
     * @see org.jenkins.plugins.audit2db.model.BuildDetails#getUserName()
     */
    @Column(nullable = true, unique = false)
    @Override
    public String getUserName() {
	return userName;
    }

    /**
     * @see org.jenkins.plugins.audit2db.model.BuildDetails#setUserName(java.lang.String)
     */
    @Override
    public void setUserName(final String userName) {
	this.userName = userName;

    }

    /**
     * @see org.jenkins.plugins.audit2db.model.BuildDetails#getParameters()
     */
    @OneToMany(cascade = CascadeType.ALL, targetEntity = BuildParameterImpl.class, mappedBy = "buildDetails")
    @Column(nullable = true, unique = false)
    @Override
    public List<BuildParameter> getParameters() {
	return parameters;
    }

    /**
     * @see org.jenkins.plugins.audit2db.model.BuildDetails#setParameters(java.util.List)
     */
    @Override
    public void setParameters(final List<BuildParameter> params) {
	if (null != params) {
	    // need a temporary array otherwise hibernate
	    // will clear the property bag too
	    final BuildParameter[] tempParams = params
		    .toArray(new BuildParameter[] {});
	    this.parameters.clear();
	    Collections.addAll(this.parameters, tempParams);
	}
    }

    /**
     * @see org.jenkins.plugins.audit2db.model.BuildDetails#getNode()
     */
    @ManyToOne(targetEntity = BuildNodeImpl.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(nullable = true, unique = false)
    @Override
    public BuildNode getNode() {
	return node;
    }

    /**
     * @see org.jenkins.plugins.audit2db.model.BuildDetails#setNode(BuildNode)
     */
    @Override
    public void setNode(final BuildNode node) {
	this.node = node;
    }

    @Override
    public String toString() {
	return String.format("%s [%s]", this.fullName, this.id);
    }

    @Override
    public int hashCode() {
	return this.id.hashCode();
    }

    @Override
    public boolean equals(final Object obj) {
	// fail-fast logic
	if (this == obj) {
	    return true;
	}
	if (null == obj) {
	    return false;
	}
	if (!(obj instanceof BuildDetails)) {
	    return false;
	}

	final BuildDetails other = (BuildDetails) obj;

	return other.hashCode() == this.hashCode();
    }

    private List<BuildParameter> resolveBuildParameters(
	    final Map<String, String> buildVariables) {
	final List<BuildParameter> retval = new ArrayList<BuildParameter>();
	if (buildVariables != null) {
	    for (final Map.Entry<String, String> buildVariable : buildVariables
		    .entrySet()) {
		retval.add(new BuildParameterImpl(String.format("%s@%s",
			this.id, buildVariable.getKey()), buildVariable
			.getKey(), buildVariable.getValue(), this));
	    }
	}

	return retval;
    }

    private BuildNode resolveBuildNode(final Node node) {
	String address = "UNKNOWN";
	String hostname = "UNKNOWN";
	try {
	    final InetAddress iaddr = InetAddress.getLocalHost();
	    address = iaddr.getHostAddress();
	    hostname = iaddr.getHostName();
	} catch (final UnknownHostException e) {
	    LOGGER.log(
		    Level.SEVERE,
		    "An error occurred while trying to resolve the master's network name and address: "
			    + e.getMessage(), e);
	}
	final Computer computer = node.toComputer();
	final BuildNode retval = new BuildNodeImpl(address, hostname,
		computer.getDisplayName(), String.format("%s/%s", hostname,
			computer.getUrl()), node.getNodeName(),
		node.getNodeDescription(), node.getLabelString());
	return retval;
    }

    /**
     * Default constructor.
     */
    public BuildDetailsImpl() {
    }

    /**
     * Constructs a new object with the specified properties.
     * 
     * @param id
     *            the build id.
     * @param name
     *            the build name.
     * @param fullName
     *            the build full name.
     * @param startDate
     *            the build start date.
     * @param endDate
     *            the build end date.
     * @param duration
     *            the build duration.
     * @param userId
     *            the id of the user who started the build.
     * @param userName
     *            the name of the user who started the build.
     * @param parameters
     *            the build parameters (if any).
     */
    public BuildDetailsImpl(final String id, final String name,
	    final String fullName, final Date startDate, final Date endDate,
	    final long duration, final String userId, final String userName,
	    final List<BuildParameter> parameters, final BuildNode node) {
	this.id = id;
	this.name = name;
	this.fullName = fullName;
	this.startDate = startDate;
	this.endDate = endDate;
	this.duration = duration;
	this.userId = userId;
	this.userName = userName;
	if ((parameters != null) && !parameters.isEmpty()) {
	    this.parameters.addAll(parameters);
	}
	this.node = node;
    }

    /**
     * Constructs a new BuildDetailsImpl object using the details of the given
     * Jenkins build.
     * 
     * @param build
     *            a valid Jenkins build object.
     */
    public BuildDetailsImpl(final AbstractBuild<?, ?> build) {
	// this.id = build.getId();
	this.name = build.getRootBuild().getProject().getDisplayName();
	this.fullName = build.getFullDisplayName();
	this.startDate = build.getTime();

	final List<CauseAction> actions = build.getActions(CauseAction.class);
	boolean userFound = false;
	for (final CauseAction action : actions) {
	    for (final Cause cause : action.getCauses()) {
		if (cause instanceof UserIdCause) {
		    userFound = true;
		    this.userId = ((UserIdCause) cause).getUserId();
		    this.userName = ((UserIdCause) cause).getUserName();
		    break;
		}
	    }
	    if (userFound) {
			break;
	    }
	}

	this.node = resolveBuildNode(build.getBuiltOn());
	this.id = String
		.format("%s/%s/%s", this.node, this.name, build.getId());
	this.parameters
		.addAll(resolveBuildParameters(build.getBuildVariables()));
    }
}
