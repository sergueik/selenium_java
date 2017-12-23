/**
 * 
 */
package org.jenkins.plugins.audit2db.test.integration;

import hudson.security.ACL;
import hudson.security.AuthorizationStrategy;
import hudson.security.Permission;

import java.util.Collection;

import org.acegisecurity.Authentication;

/**
 * @author Marco Scata
 *
 */
public class AuditReportsAuthorizationStrategy extends AuthorizationStrategy {
    private final AuthorizationStrategy template;
    private final String user;
    private final Permission permission;

    public AuditReportsAuthorizationStrategy(
	    final AuthorizationStrategy template, final String user,
	    final Permission permission) {
	this.template = template;
	this.user = user;
	this.permission = permission;
    }

    @Override
    public Collection<String> getGroups() {
	return template.getGroups();
    }

    @Override
    public ACL getRootACL() {
	return new ACL() {
	    @Override
	    public boolean hasPermission(final Authentication auth,
		    final Permission requestedPermission) {
		final String requestedUser = auth.getName();
		final ACL originalACL = template.getRootACL();
		boolean retval = originalACL.hasPermission(auth, requestedPermission);

		if (retval) {
		    if (0 == Permission.ID_COMPARATOR.compare(
			    requestedPermission,
			    AuditReportsAuthorizationStrategy.this.permission)) {
			retval = retval
			&& requestedUser.equalsIgnoreCase(
				AuditReportsAuthorizationStrategy.this.user);
		    }
		}

		return retval;
	    }
	};
    }
}
