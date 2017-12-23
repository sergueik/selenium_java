/**
 * 
 */
package org.jenkins.plugins.audit2db.internal.reports;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Marco Scata
 *
 */
public abstract class DbAuditReportUtils {
    private final static SimpleDateFormat DATE_FORMAT_NOTIME = new SimpleDateFormat(
    "yyyy-MM-dd");

    private final static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat(
    "yyyy-MM-dd HH:mm:ss");

    private final static transient Logger LOGGER = Logger
    .getLogger(DbAuditReportUtils.class.getName());

    /**
     * Formats the given date as a string.
     * 
     * @param showTime whether the time part should be shown or not.
     * @return the string equivalent of the given date.
     */
    public static String dateAsString(final Date date, final boolean showTime) {
	if (null == date) {
	    throw new IllegalArgumentException("Cannot format a null date.");
	}

	if (showTime) {
	    return DATE_FORMAT_NOTIME.format(date);
	} else {
	    return DATE_FORMAT.format(date);
	}
    }

    /**
     * @param dateString
     *            a valid date string.
     * @return the equivalent {@link Date} object, or <code>null</code> if the
     *         date string cannot be parsed.
     */
    public static Date stringToDate(final String dateString) {
	Date retval = null;
	if ((dateString != null) && !dateString.isEmpty()) {
	    try {
		retval = DATE_FORMAT_NOTIME.parse(dateString);
	    } catch (final ParseException e) {
		LOGGER.log(Level.WARNING, "Unable to parse date string "
			+ dateString);
	    }
	}
	return retval;
    }

    private static Date getDefaultStartDate() {
	final Calendar cal = Calendar.getInstance();
	// start date = first day of this month
	cal.set(Calendar.DAY_OF_MONTH, 1);
	cal.set(Calendar.HOUR_OF_DAY, 0);
	cal.set(Calendar.MINUTE, 0);
	cal.set(Calendar.SECOND, 0);
	cal.set(Calendar.MILLISECOND, 0);

	return cal.getTime();
    }

    private static Date getDefaultEndDate() {
	final Calendar cal = Calendar.getInstance();
	// end date = tonight
	cal.set(Calendar.HOUR_OF_DAY, 23);
	cal.set(Calendar.MINUTE, 59);
	cal.set(Calendar.SECOND, 59);
	cal.set(Calendar.MILLISECOND, 999);

	return cal.getTime();
    }

    public static String getStartDateParam(final String dateString) {
	Date date = stringToDate(dateString);
	if (null == date) {
	    date = getDefaultStartDate();
	}
	return DATE_FORMAT_NOTIME.format(date);
    }

    public static String getEndDateParam(final String dateString) {
	Date date = stringToDate(dateString);
	if (null == date) {
	    date = getDefaultEndDate();
	}
	return DATE_FORMAT_NOTIME.format(date);
    }
}
