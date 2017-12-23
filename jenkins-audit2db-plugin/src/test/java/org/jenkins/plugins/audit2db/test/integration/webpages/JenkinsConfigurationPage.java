/**
 * 
 */
package org.jenkins.plugins.audit2db.test.integration.webpages;

import java.io.IOException;
import java.util.List;

import org.jvnet.hudson.test.HudsonTestCase.WebClient;

import com.gargoylesoftware.htmlunit.html.HtmlButton;
import com.gargoylesoftware.htmlunit.html.HtmlCheckBoxInput;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlTable;
import com.gargoylesoftware.htmlunit.html.HtmlTableCell;
import com.gargoylesoftware.htmlunit.html.HtmlTableRow;

/**
 * @author Marco Scata
 *
 */
public class JenkinsConfigurationPage extends AbstractJenkinsPage {

    public static final String AUDIT2DB_JDBC_PASSWORD = "audit2db.jdbcPassword";
    public static final String AUDIT2DB_JDBC_USER = "audit2db.jdbcUser";
    public static final String AUDIT2DB_JDBC_URL = "audit2db.jdbcUrl";
    public static final String AUDIT2DB_JDBC_DRIVER = "audit2db.jdbcDriver";
    public static final String AUDIT2DB_JNDI_PASSWORD = "audit2db.jndiPassword";
    public static final String AUDIT2DB_JNDI_USER = "audit2db.jndiUser";
    public static final String AUDIT2DB_JNDI_NAME = "audit2db.jndiName";
    public static final String AUDIT2DB_DATASOURCE = "audit2db.datasource";

    private final static String urlPath = "configure";
    private HtmlForm configForm;

    public JenkinsConfigurationPage(final WebClient client) {
	super(client, urlPath);
    }

    @Override
    public void load() {
	super.load();
	configForm = getPage().getFormByName("config");
    }

    public HtmlElement getJndiDatasourceRadioButton() {
	HtmlElement retval = null;
	final List<HtmlElement> elements = getPage().getElementsByName(AUDIT2DB_DATASOURCE);
	for (final HtmlElement element : elements) {
	    if (element.getAttribute("value").equalsIgnoreCase("true")) {
		retval = element;
		break;
	    }
	}
	return retval;
    }

    public HtmlElement getJdbcDatasourceRadioButton() {
	HtmlElement retval = null;
	final List<HtmlElement> elements = getPage().getElementsByName(AUDIT2DB_DATASOURCE);
	for (final HtmlElement element : elements) {
	    if (element.getAttribute("value").equalsIgnoreCase("false")) {
		retval = element;
		break;
	    }
	}
	return retval;
    }

    public boolean isUseJndi() {
	final HtmlElement element = getJndiDatasourceRadioButton();
	final String checked = element.getAttribute("checked");
	return ((checked != null) && (!checked.isEmpty()));
    }

    public void setUseJndi(final boolean useJndi) {
	try {
	    if (useJndi) {
		getJndiDatasourceRadioButton().click();
	    } else {
		getJdbcDatasourceRadioButton().click();
	    }
	} catch (final IOException e) {
	    throw new RuntimeException(e);
	}
    }

    public String getJndiDatasource() {
	return getInputValue(configForm, AUDIT2DB_JNDI_NAME);
    }

    public void setJndiDatasource(final String datasourceName) {
	setInputValue(configForm, AUDIT2DB_JNDI_NAME, datasourceName);
    }


    public String getJndiUser() {
	return getInputValue(configForm, AUDIT2DB_JNDI_USER);
    }

    public void setJndiUser(final String user) {
	setInputValue(configForm, AUDIT2DB_JNDI_USER, user);
    }

    public String getJndiPassword() {
	return getInputValue(configForm, AUDIT2DB_JNDI_PASSWORD);
    }

    public void setJndiPassword(final String password) {
	setInputValue(configForm, AUDIT2DB_JNDI_PASSWORD, password);
    }

    public String getJdbcDriver() {
	return getInputValue(configForm, AUDIT2DB_JDBC_DRIVER);
    }

    public void setJdbcDriver(final String driver) {
	setInputValue(configForm, AUDIT2DB_JDBC_DRIVER, driver);
    }

    public String getJdbcUrl() {
	return getInputValue(configForm, AUDIT2DB_JDBC_URL);
    }

    public void setJdbcUrl(final String url) {
	setInputValue(configForm, AUDIT2DB_JDBC_URL, url);
    }

    public String getJdbcUser() {
	return getInputValue(configForm, AUDIT2DB_JDBC_USER);
    }

    public void setJdbcUser(final String user) {
	setInputValue(configForm, AUDIT2DB_JDBC_USER, user);
    }

    public String getJdbcPassword() {
	return getInputValue(configForm, AUDIT2DB_JDBC_PASSWORD);
    }

    public void setJdbcPassword(final String password) {
	setInputValue(configForm, AUDIT2DB_JDBC_PASSWORD, password);
    }

    public HtmlElement getSaveButton() {
	return getElement(configForm, "button", "save");
    }

    public HtmlElement getTestConnectionButton() {
	return getElement(configForm, "button", "test connection");
    }

    public void saveChanges() {
	final HtmlElement saveButton = getSaveButton();

	if (null == saveButton) {
	    throw new RuntimeException("Save button not found on config form!");
	}

	try {
	    configForm.submit((HtmlButton) saveButton);
	} catch (final IOException e) {
	    throw new RuntimeException(e);
	}
    }

    public void testConnection() {
	final HtmlElement testConnectionButton = getTestConnectionButton();

	if (null == testConnectionButton) {
	    throw new RuntimeException("Test connection button not found on config form!");
	}

	try {
	    testConnectionButton.click();
	} catch (final IOException e) {
	    throw new RuntimeException(e);
	}
    }

    private HtmlTable getSecurityMatrix() {
	// enable security
	final HtmlElement useSecurity = configForm.getInputByName("_.useSecurity");
	try {
	    useSecurity.click();
	} catch (final IOException e) {
	    throw new RuntimeException(e);
	}

	// use matrix-based security
	final List<HtmlInput> authorizationInputs = configForm
		.getInputsByName("authorization");
	HtmlInput useMatrixSecurityRadioBtn = null;
	for (final HtmlInput input : authorizationInputs) {
	    if (input.getValueAttribute().equals("3")) {
		useMatrixSecurityRadioBtn = input;
		break;
	    }
	}

	try {
	    useMatrixSecurityRadioBtn.click();
	} catch (IOException e) {
	    throw new RuntimeException(e);
	}

	return (HtmlTable) configForm
	.getElementById("hudson-security-GlobalMatrixAuthorizationStrategy");
    }

    public int getAuditReportsPermissionColumnNumber() {
	int retval = -1;
	// the auth matrix' header is actually the first row
	final HtmlTableRow firstRow = getSecurityMatrix().getRow(0);
	for (int cellCtr = 0; cellCtr < firstRow.getCells().size(); cellCtr++) {
	    final HtmlTableCell cell = firstRow.getCell(cellCtr);
	    if (cell.getTextContent().equalsIgnoreCase("Audit Reports")) {
		retval = cellCtr;
		break;
	    }
	}
	return retval;
    }

    public int getUserPermissionRowNumber(final String userName) {
	int retval = -1;
	final HtmlTable securityMatrix = getSecurityMatrix();
	for (int rowCtr = 0; rowCtr < securityMatrix.getRowCount(); rowCtr++) {
	    final HtmlTableRow row = securityMatrix.getRow(rowCtr);
	    if (row.getAttribute("name").equalsIgnoreCase(userName)) {
		retval = rowCtr;
		break;
	    }
	}
	return retval;
    }

    private HtmlCheckBoxInput getAuditReportsPermissionCheckbox(
	    final String userName) {
	final HtmlTableCell cell = getSecurityMatrix().getCellAt(
		getUserPermissionRowNumber(userName),
		getAuditReportsPermissionColumnNumber());

	return (HtmlCheckBoxInput) cell.getElementsByTagName("input");
    }

    public boolean getAuditReportsPermissionState(final String userName) {
	final HtmlCheckBoxInput input = getAuditReportsPermissionCheckbox(userName);
	return input.isChecked();
    }

    public void setAuditReportsPermissionState(final String userName,
	    final boolean state) {
	final HtmlCheckBoxInput input = getAuditReportsPermissionCheckbox(userName);
	input.setChecked(state);
    }
}
