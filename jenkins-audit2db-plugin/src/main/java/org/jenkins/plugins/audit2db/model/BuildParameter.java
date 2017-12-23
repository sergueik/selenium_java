/**
 * 
 */
package org.jenkins.plugins.audit2db.model;

/**
 * Data model to map build parameters.
 * 
 * @author Marco Scata
 *
 */
public interface BuildParameter {
    String getId();
    void setId(String id);
    String getName();
    void setName(String name);
    String getValue();
    void setValue(String value);
    BuildDetails getBuildDetails();
    void setBuildDetails(BuildDetails buildId);
}
