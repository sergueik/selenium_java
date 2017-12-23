/**
 *
 */
package org.jenkins.plugins.audit2db.test.integration.webpages;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.jvnet.hudson.test.HudsonTestCase.WebClient;

import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

/**
 * @author Marco Scata
 *
 */
public abstract class AbstractJenkinsPage {
    private final static Logger LOGGER = Logger
    .getLogger(AbstractJenkinsPage.class.getName());

    private final WebClient webClient;
    private final String urlPath;
    private HtmlPage page;

    public WebClient getWebClient() {
	return webClient;
    }

    public HtmlPage getPage() {
	return page;
    }

    public AbstractJenkinsPage(final WebClient client, final String urlPath) {
	if (null == client) {
	    throw new IllegalArgumentException(
		    "A valid HudsonTestCase.WebClient object must be provided");
	}

	this.webClient = client;
	this.webClient.setTimeout(60000);

	if ((null == urlPath) || urlPath.isEmpty()) {
	    throw new IllegalArgumentException("Page URL path must be provided");
	}

	this.urlPath = urlPath;
    }

    public void load() {
	LOGGER.log(Level.INFO, String.format("Loading page %s", urlPath));
	try {
	    page = webClient.goTo(urlPath);
	} catch (final Exception e) {
	    if (RuntimeException.class.isAssignableFrom(e.getClass())) {
		throw (RuntimeException) e;
	    }
	    throw new IllegalArgumentException("Error loading page", e);
	}
    }

    public void unload() {
	LOGGER.log(Level.INFO, "Closing all client windows");
	webClient.closeAllWindows();
    }

    public HtmlElement getElement(final HtmlElement container,
	    final String tagName,
	    final String textContent) {
	LOGGER.log(Level.INFO, String.format(
		"Looking into container '%s' for element '%s' with content '%s'",
		container, tagName, textContent));
	final List<HtmlElement> elements = container
	.getElementsByTagName(tagName);
	HtmlElement retval = null;
	// find the save button (it has no predictable id)
	for (final HtmlElement element : elements) {
	    if (element.getTextContent().trim().equalsIgnoreCase(textContent)) {
		retval = element;
		break;
	    }
	}
	LOGGER.log(Level.INFO, String.format("Found %s", retval));
	return retval;
    }

    public String getInputValue(final HtmlForm form, final String inputName) {
	String retval = null;
	LOGGER.log(Level.INFO, String.format(
		"Looking for input '%s' on form '%s'", inputName,
		form.getNameAttribute()));
	final HtmlInput input = form.getInputByName(inputName);
	if (null == input) {
	    throw new RuntimeException(String.format(
		    "Input '%s' cannot be found", inputName));
	} else {
	    retval = input.getValueAttribute();
	    LOGGER.log(Level.INFO,
		    String.format("Loading value %s = %s", inputName, retval));
	}

	return retval;
    }

    public void setInputValue(final HtmlForm form, final String inputName,
	    final String value) {
	LOGGER.log(Level.INFO, String.format(
		"Looking for input '%s' on form '%s'",
		inputName, form.getNameAttribute()));
	final HtmlInput input = form.getInputByName(inputName);
	LOGGER.log(Level.INFO,
		String.format("Setting value %s = %s", inputName, value));
	input.setValueAttribute(value);
    }
}