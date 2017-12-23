/**
 *
 */
package org.jenkins.plugins.audit2db.test.integration;

import java.util.List;

import org.jenkins.plugins.audit2db.test.integration.webpages.AuditReportsListPage;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.jvnet.hudson.test.HudsonTestCase;

import com.gargoylesoftware.htmlunit.WebAssert;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlImage;
import com.gargoylesoftware.htmlunit.html.HtmlTableCell;
import com.gargoylesoftware.htmlunit.html.HtmlTableRow;

/**
 * @author Marco Scata
 *
 */
public class WhenBrowsingReportsList extends HudsonTestCase {
    private static AuditReportsListPage page;

    @Before
    @Override
    public void setUp() throws Exception {
	super.setUp();
	page = new AuditReportsListPage(createWebClient());
	page.load();
    }

    @After
    @Override
    public void tearDown() throws Exception {
	page.unload();
	super.tearDown();
    }

    @Test
    public void testShouldDisplayReportsListTable() {
	WebAssert.assertElementPresent(page.getPage(), "auditReportsList");
    }

    private void checkReportIcon(final HtmlTableRow row) {
	final HtmlTableCell cell = row.getCell(0);
	final List<HtmlElement> elements = cell.getHtmlElementsByTagName("img");
	Assert.assertEquals("Unexpected number of image elements", 1,
		elements.size());
	final HtmlImage img = (HtmlImage) elements.get(0);
	final String iconPath = img.getSrcAttribute().toUpperCase();
	Assert.assertTrue("Unexpected report icon: ",
		iconPath.endsWith("/DOCUMENT.GIF"));
    }

    private void checkReportTitle(final HtmlTableRow row) {
	final HtmlTableCell cell = row.getCell(1);
	final String text = cell.getTextContent();
	Assert.assertFalse("Unexpected blank text in report title",
		text.isEmpty());
    }

    private void checkReportDescription(final HtmlTableRow row) {
	final HtmlTableCell cell = row.getCell(2);
	final String text = cell.getTextContent();
	Assert.assertFalse("Unexpected blank text in report description",
		text.isEmpty());
    }

    private void checkReportInfo(final HtmlTableRow row) {
	final HtmlTableCell cell = row.getCell(3);
	final String text = cell.getTextContent();
	Assert.assertFalse("Unexpected blank text in report info",
		text.isEmpty());
    }

    private void checkReportHyperlinks(final HtmlTableRow row) {
	List<HtmlElement> elements = row.getCell(0).getHtmlElementsByTagName(
	"a");
	Assert.assertEquals(
		"Unexpected number of anchor elements for report icon", 1,
		elements.size());
	final HtmlAnchor iconAnchor = (HtmlAnchor) elements.get(0);

	elements = row.getCell(1).getHtmlElementsByTagName("a");
	Assert.assertEquals(
		"Unexpected number of anchor elements for report title", 1,
		elements.size());
	final HtmlAnchor titleAnchor = (HtmlAnchor) elements.get(0);

	Assert.assertEquals(
		"Hyperlinks must not be different between report icon and report title",
		iconAnchor.getHrefAttribute(), titleAnchor.getHrefAttribute());
    }

    @Test
    public void testShouldDisplayReportDetailsCorrectly() {
	// each row in the reports list must have the report icon,
	// the report title, the report description, and report info
	final List<HtmlTableRow> rows = page.getReportsList();
	// first row contains the headers and can be skipped
	for (int rowCtr = 1; rowCtr < rows.size(); rowCtr++) {
	    final HtmlTableRow reportData = rows.get(rowCtr);
	    Assert.assertEquals("Unexpected number of cells", 4, reportData
		    .getCells().size());
	    checkReportIcon(reportData);
	    checkReportTitle(reportData);
	    checkReportHyperlinks(reportData);
	    checkReportDescription(reportData);
	    checkReportInfo(reportData);
	}
    }
}
