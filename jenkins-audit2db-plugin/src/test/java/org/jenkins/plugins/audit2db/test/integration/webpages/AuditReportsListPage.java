/**
 * 
 */
package org.jenkins.plugins.audit2db.test.integration.webpages;

import java.util.List;

import org.jvnet.hudson.test.HudsonTestCase.WebClient;

import com.gargoylesoftware.htmlunit.html.HtmlTable;
import com.gargoylesoftware.htmlunit.html.HtmlTableRow;

/**
 * @author Marco Scata
 *
 */
public class AuditReportsListPage extends AbstractJenkinsPage {
    private final static String urlPath = "audit2db.reports";
    private HtmlTable reportsList;

    public AuditReportsListPage(final WebClient client) {
	super(client, urlPath);
    }

    @Override
    public void load() {
	super.load();
	reportsList = (HtmlTable) getPage().getElementById("auditReportsList");
    }

    public List<HtmlTableRow> getReportsList() {
	return reportsList.getRows();
    }
}
