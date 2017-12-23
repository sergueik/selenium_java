/**
 *
 */
package org.jenkins.plugins.audit2db.test.integration;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.jenkins.plugins.audit2db.internal.DbAuditPublisherImpl;
import org.jenkins.plugins.audit2db.internal.DbAuditUtil;
import org.jenkins.plugins.audit2db.internal.data.BuildDetailsHibernateRepository;
import org.jenkins.plugins.audit2db.model.BuildDetails;
import org.jenkins.plugins.audit2db.reports.JobHistoryReport;
import org.jenkins.plugins.audit2db.test.TestUtils;
import org.jenkins.plugins.audit2db.test.integration.webpages.JobHistoryReportPage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.jvnet.hudson.test.HudsonTestCase;

import com.gargoylesoftware.htmlunit.WebAssert;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

/**
 * @author Marco Scata
 *
 */
public class WhenRunningJobsHistoryReport extends HudsonTestCase {
    private static final Logger LOGGER = Logger.getLogger(
	    WhenRunningJobsHistoryReport.class.getName());

    private final SimpleDateFormat DATE_FORMAT_NOTIME = new SimpleDateFormat(
	"yyyy-MM-dd");

    private final String jdbcDriver = "org.hsqldb.jdbc.JDBCDriver";
    private final String jdbcUrl = "jdbc:hsqldb:mem:test";
    private final String user = "SA";
    private final String password = "";

    private final String now;
    private final String yesterday;
    private final String tomorrow;

    private JobHistoryReportPage page;

    public WhenRunningJobsHistoryReport() {
	final Calendar cal = Calendar.getInstance();

	cal.add(Calendar.DAY_OF_MONTH, -1);
	yesterday = DATE_FORMAT_NOTIME.format(cal.getTime());

	cal.add(Calendar.DAY_OF_MONTH, 1);
	now = DATE_FORMAT_NOTIME.format(cal.getTime());

	cal.add(Calendar.DAY_OF_MONTH, 1);
	tomorrow = DATE_FORMAT_NOTIME.format(cal.getTime());
    }

    @Before
    @Override
    public void setUp() throws Exception {
	super.setUp();
	DbAuditPublisherImpl.descriptor.setJdbcDriver(jdbcDriver);
	DbAuditPublisherImpl.descriptor.setJdbcUrl(jdbcUrl);
	DbAuditPublisherImpl.descriptor.setJdbcUser(user);
	DbAuditPublisherImpl.descriptor.setJdbcPassword(password);

	page = new JobHistoryReportPage(createWebClient());
    }

    @After
    @Override
    public void tearDown() throws Exception {
	page.unload();
	super.tearDown();
    }

    @Test
    public void testShouldDisplayDefaultDateRangeForRequestWithoutParams() {
	final Calendar expectedStartDate = Calendar.getInstance();
	expectedStartDate.set(Calendar.DAY_OF_MONTH, 1);
	expectedStartDate.set(Calendar.HOUR_OF_DAY, 0);
	expectedStartDate.set(Calendar.MINUTE, 0);
	expectedStartDate.set(Calendar.SECOND, 0);
	expectedStartDate.set(Calendar.MILLISECOND, 0);

	final Calendar expectedEndDate = Calendar.getInstance();
	expectedEndDate.set(Calendar.HOUR_OF_DAY, 23);
	expectedEndDate.set(Calendar.MINUTE, 59);
	expectedEndDate.set(Calendar.SECOND, 59);
	expectedEndDate.set(Calendar.MILLISECOND, 999);

	try {
	    page.load();
	    WebAssert.assertInputContainsValue(page.getPage(), "startDate",
		    DATE_FORMAT_NOTIME.format(expectedStartDate.getTime()));
	    WebAssert.assertInputContainsValue(page.getPage(), "endDate",
		    DATE_FORMAT_NOTIME.format(expectedEndDate.getTime()));
	    WebAssert.assertInputContainsValue(page.getPage(), "jobName", "");
	} catch (final Exception e) {
	    // expecting successful access
	    e.printStackTrace();
	    fail("Unexpected error. " + e.getMessage());
	}
    }

    @Test
    public void testShouldDisplayNoRecordsForNonMatchingSelection() {
	final JobHistoryReport report = TestUtils.getReportExtension(JobHistoryReport.class);

	final BuildDetailsHibernateRepository repository = (BuildDetailsHibernateRepository) report.getRepository();
	final Map<String, List<BuildDetails>> dataset = TestUtils
	.createRandomDataset(DbAuditUtil.getHostName());
	// no need to use transactions because the mem db will be dumped
	// after each test run
	for (final List<BuildDetails> detailsList : dataset.values()) {
	    repository.saveBuildDetailsList(detailsList);
	}

	String pageText = null; //used for debugging
	try {
	    page.load();
	    page.setStartDate(yesterday);
	    page.setEndDate(tomorrow);
	    page.setJobName("A JOB THAT DOES NOT EXIST");

	    final HtmlPage resultPage = page.submit();
	    pageText = resultPage.asText();
	    WebAssert.assertElementPresent(resultPage, "noDataWarning");
	} catch (final Exception e) {
	    // expecting successful run
	    LOGGER.log(Level.SEVERE, pageText, e);
	    fail("Unexpected error.");
	}
    }

    @Test
    public void testShouldDisplaySomeRecordsForMatchingSelection() {
	final JobHistoryReport report = TestUtils.getReportExtension(JobHistoryReport.class);

	final BuildDetailsHibernateRepository repository = (BuildDetailsHibernateRepository) report.getRepository();
	final Map<String, List<BuildDetails>> dataset = TestUtils
	.createRandomDataset(DbAuditUtil.getHostName());

	// no need to use transactions because the mem db will be dumped
	// after each test run
	for (final List<BuildDetails> detailsList : dataset.values()) {
	    repository.saveBuildDetailsList(detailsList);
	}

	final String projectName = dataset.keySet().iterator().next();

	String pageText = null; //used for debugging
	try {
	    page.load();
	    page.setStartDate(now);
	    page.setEndDate(tomorrow);
	    page.setJobName(projectName + "%"); // test wildcard

	    final HtmlPage resultPage = page.submit();
	    pageText = resultPage.asText();
	    WebAssert.assertElementPresent(resultPage, "reportResults");
	} catch (final Exception e) {
	    // expecting successful run
	    LOGGER.log(Level.SEVERE, pageText, e);
	    fail("Unexpected error.");
	}
    }


    @Test
    public void testShouldDisplayWarningForNoJobName() {

    }

}
