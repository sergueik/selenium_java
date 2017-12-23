/**
 * 
 */
package org.jenkins.plugins.audit2db.model;

/**
 * Data model to map node details.
 * 
 * @author Marco Scata
 * 
 */
public interface BuildNode {
    String getMasterAddress();

    void setMasterAddress(String address);

    String getMasterHostName();

    void setMasterHostName(String hostName);

    String getDisplayName();

    void setDisplayName(String displayName);

    String getUrl();

    void setUrl(String url);

    String getName();

    void setName(String name);

    String getDescription();

    void setDescription(String description);

    String getLabel();

    void setLabel(String label);
}
