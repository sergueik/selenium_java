/**
 * 
 */
package org.jenkins.plugins.audit2db.internal;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Marco Scata
 *
 */
public final class DbAuditUtil {
    private final static Logger LOGGER = Logger.getLogger(DbAuditUtil.class
	    .getName());

    private static String hostname = "UNKNOWN";
    private static String ipaddr = "UNKNOWN";

    /**
     * Cannot be instantiated by others.
     */
    private DbAuditUtil() {
    }

    static {
	try {
	    final InetAddress iaddr = InetAddress.getLocalHost();
	    ipaddr = iaddr.getHostAddress();
	    hostname = iaddr.getHostName();
	} catch (final UnknownHostException e) {
	    LOGGER.log(
		    Level.SEVERE,
		    "An error occurred while trying to resolve the master's network name and address: "
		    + e.getMessage(), e);
	}
    }

    public static String getHostName() {
	return hostname;
    }

    public static String getIpAddress() {
	return ipaddr;
    }
}
