/**
 * 
 */
package org.jenkins.plugins.audit2db.internal.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.jenkins.plugins.audit2db.model.BuildDetails;
import org.jenkins.plugins.audit2db.model.BuildParameter;

/**
 * Data class for build parameters.
 * 
 * @author Marco Scata
 *
 */
@Entity(name="JENKINS_BUILD_PARAMS")
public class BuildParameterImpl implements BuildParameter {
    private String id;
    private String name;
    private String value;
    private BuildDetails buildDetails;

    public BuildParameterImpl() {
        super();
    }

    public BuildParameterImpl(final String id, final String name, final String value, final BuildDetails buildDetails) {
        this.id = id;
        this.name = name;
        this.value = value;
        this.buildDetails = buildDetails;
    }

    /**
     * @see org.jenkins.plugins.audit2db.model.BuildParameter#getId()
     */
    @Id
    //    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable=false, unique=true)
    @Override
    public String getId() {
        return id;
    }

    /**
     * @see org.jenkins.plugins.audit2db.model.BuildParameter#setId(java.lang.String)
     */
    @Override
    public void setId(final String id) {
        this.id = id;
    }

    /**
     * @see org.jenkins.plugins.audit2db.model.BuildParameter#getName()
     */
    @Column(nullable=false, unique=false)
    @Override
    public String getName() {
        return name;
    }

    /**
     * @see org.jenkins.plugins.audit2db.model.BuildParameter#setName(java.lang.String)
     */
    @Override
    public void setName(final String name) {
        this.name = name;
    }

    /**
     * @see org.jenkins.plugins.audit2db.model.BuildParameter#getValue()
     */
    @Column(nullable=true, unique=false)
    @Override
    public String getValue() {
        return value;
    }

    /**
     * @see org.jenkins.plugins.audit2db.model.BuildParameter#setValue(java.lang.String)
     */
    @Override
    public void setValue(final String value) {
        this.value = value;
    }

    /**
     * @see org.jenkins.plugins.audit2db.model.BuildParameter#getBuildDetails()
     */
    @ManyToOne(targetEntity=BuildDetailsImpl.class)
    @JoinColumn(nullable=false, unique=false)
    @Override
    public BuildDetails getBuildDetails() {
        return buildDetails;
    }

    /**
     * @see org.jenkins.plugins.audit2db.model.BuildParameter#setBuildDetails(java.lang.String)
     */
    @Override
    public void setBuildDetails(final BuildDetails buildDetails) {
        this.buildDetails = buildDetails;
    }

    @Override
    public String toString() {
        return String.format("%s=%s",
                this.id, this.value);
    }

    @Override
    public int hashCode() {
        return this.id.hashCode();
    }

    @Override
    public boolean equals(final Object obj) {
        // fail-fast logic
        if (this == obj) { return true; }
        if (null == obj) { return false; }
        if (!(obj instanceof BuildParameter)) { return false; }

        final BuildParameter other = (BuildParameter) obj;

        return other.hashCode() == this.hashCode();
    }
}
