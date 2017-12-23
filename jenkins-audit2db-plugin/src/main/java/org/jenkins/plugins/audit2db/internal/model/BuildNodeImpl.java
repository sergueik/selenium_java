/**
 * 
 */
package org.jenkins.plugins.audit2db.internal.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.jenkins.plugins.audit2db.model.BuildNode;

/**
 * @author Marco Scata
 * 
 */
@Entity(name = "JENKINS_BUILD_NODE")
public class BuildNodeImpl implements BuildNode {
    private String address;
    private String hostName;
    private String displayName;
    private String url;
    private String name;
    private String description;
    private String label;

    /**
     * @see org.jenkins.plugins.audit2db.model.BuildNode#getMasterAddress()
     */
    @Column(nullable = false, unique = false)
    @Override
    public String getMasterAddress() {
	return address;
    }

    /**
     * @see org.jenkins.plugins.audit2db.model.BuildNode#setMasterAddress(String)
     */
    @Override
    public void setMasterAddress(final String address) {
	this.address = address;
    }

    /**
     * @see org.jenkins.plugins.audit2db.model.BuildNode#getMasterHostName()
     */
    @Column(nullable = false, unique = false)
    @Override
    public String getMasterHostName() {
	return hostName;
    }

    /**
     * @see org.jenkins.plugins.audit2db.model.BuildNode#setMasterHostName(String)
     */
    @Override
    public void setMasterHostName(final String hostName) {
	this.hostName = hostName;
    }

    /**
     * @see org.jenkins.plugins.audit2db.model.BuildNode#getDisplayName()
     */
    @Column(nullable = false, unique = false)
    @Override
    public String getDisplayName() {
	return displayName;
    }

    /**
     * @see org.jenkins.plugins.audit2db.model.BuildNode#setDisplayName(java.lang.String)
     */
    @Override
    public void setDisplayName(final String displayName) {
	this.displayName = displayName;
    }

    /**
     * @see org.jenkins.plugins.audit2db.model.BuildNode#getUrl()
     */
    @Id
    @Column(nullable = false, unique = true)
    @Override
    public String getUrl() {
	return url;
    }

    /**
     * @see org.jenkins.plugins.audit2db.model.BuildNode#setUrl(java.lang.String)
     */
    @Override
    public void setUrl(final String url) {
	this.url = url;
    }

    /**
     * @see org.jenkins.plugins.audit2db.model.BuildNode#getName()
     */
    @Column(nullable = false, unique = false)
    @Override
    public String getName() {
	return name;
    }

    /**
     * @see org.jenkins.plugins.audit2db.model.BuildNode#setName(java.lang.String)
     */
    @Override
    public void setName(final String name) {
	this.name = name;
    }

    /**
     * @see org.jenkins.plugins.audit2db.model.BuildNode#getDescription()
     */
    @Column(nullable = true, unique = false)
    @Override
    public String getDescription() {
	return description;
    }

    /**
     * @see org.jenkins.plugins.audit2db.model.BuildNode#setDescription(java.lang.String)
     */
    @Override
    public void setDescription(final String description) {
	this.description = description;
    }

    /**
     * @see org.jenkins.plugins.audit2db.model.BuildNode#getLabel()
     */
    @Column(nullable = true, unique = false)
    @Override
    public String getLabel() {
	return label;
    }

    /**
     * @see org.jenkins.plugins.audit2db.model.BuildNode#setLabel(java.lang.String)
     */
    @Override
    public void setLabel(final String label) {
	this.label = label;
    }

    @Override
    public String toString() {
	return this.url;
    }

    @Override
    public int hashCode() {
	return this.toString().toUpperCase().hashCode();
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
	if (!(obj instanceof BuildNode)) {
	    return false;
	}

	final BuildNode other = (BuildNode) obj;

	return other.hashCode() == this.hashCode();
    }

    /**
     * Default constructor.
     */
    public BuildNodeImpl() {
	super();
    }

    /**
     * Constructs a new object with the given properties.
     * 
     * @param address
     *            the IP address of the Jenkins master.
     * @param hostName
     *            the hostName of the Jenkins master.
     * @param displayName
     *            the node name as displayed on Jenkins.
     * @param url
     *            the node url.
     * @param name
     *            the node name.
     * @param description
     *            the node description.
     * @param label
     *            the node label.
     */
    public BuildNodeImpl(final String address, final String hostName,
	    final String displayName, final String url, final String name,
	    final String description, final String label) {
	this.address = address;
	this.hostName = hostName;
	this.displayName = displayName;
	this.url = url;
	this.name = name;
	this.description = description;
	this.label = label;
    }
}
