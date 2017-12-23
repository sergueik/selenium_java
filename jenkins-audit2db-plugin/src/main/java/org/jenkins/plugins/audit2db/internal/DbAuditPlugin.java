/**
 * 
 */
package org.jenkins.plugins.audit2db.internal;

import hudson.Extension;
import hudson.Plugin;
import hudson.security.Permission;
import hudson.security.PermissionGroup;
import hudson.security.PermissionScope;
import jenkins.model.Jenkins;

import org.jenkins.plugins.audit2db.Messages;

/**
 * @author Marco Scata
 *
 */
@Extension
public class DbAuditPlugin extends Plugin {
    private static final PermissionGroup GROUP = new PermissionGroup(
	    DbAuditPlugin.class, Messages._DbAuditPlugin_PermissionGroup());

    public static final Permission RUN = new Permission(GROUP,
	    Messages.DbAuditPlugin_RunPermission(),
	    Messages._DbAuditPlugin_RunPermission(), Jenkins.ADMINISTER,
	    PermissionScope.JENKINS);
}
