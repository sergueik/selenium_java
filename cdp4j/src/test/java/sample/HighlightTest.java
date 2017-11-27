package sample;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import java.lang.reflect.Method;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.concurrent.CountDownLatch;
import java.util.stream.Collectors;
// http://tutorials.jenkov.com/java-util-concurrent/countdownlatch.html

public class HighlightTest extends BaseTest {

	private String baseURL = "https://webfolder.io";

	@BeforeMethod
	public void beforeMethod(Method method) {
		super.beforeMethod(method);
		assertThat(session, notNullValue());
		session.navigate(baseURL).waitDocumentReady();
		System.err.println("url: " + session.getLocation());
	}

	@Test(enabled = true)
	public void testBasicXPath() {
		String xpath = "/html/head/title";
		// Arrange
		session.waitUntil(o -> o.matches(xpath), 1000, 100);
		// Act
		String pageTitle = session.getText(xpath);
		// Assert
		Assert.assertEquals(pageTitle, "WebFolder");
	}

	@Test(enabled = true)
	public void testBasicCSSselector() {
		String cssSelector = "head > title";
		// Arrange
		session.waitUntil(o -> o.matches(cssSelector), 1000, 100);
		// Act
		String pageTitle = session.getText(cssSelector);
		// Assert
		Assert.assertEquals(pageTitle, "WebFolder");

	}

	@Test(enabled = true)
	public void testXPathContains() {

		String xpath = "//*[@id='nav']//a[contains(@href, 'support.html')]";
		// Arrange
		session.waitUntil(o -> isVisible(xpath), 1000, 100);
		// Act
		String text = session.getText(xpath);
		highlight(xpath, 1000);
		// Assert
		Assert.assertEquals(text, "Support");

		/*
		// Act	
		String linkSupportComputedXPath = xpathOfElement(linkSupportXPath);
		// Assert
		Assert.assertEquals(linkSupportComputedXPath, "...");
		*/
	}

	@Test(enabled = true)
	public void elementIteratorTest() {

		String cssSelector = "#nav a";

		// Arrange
		session.waitUntil(o -> o.getObjectIds(cssSelector).size() > 0, 1000, 100);
		// Act
		String id = session.getObjectIds(cssSelector).stream().filter(_id -> {
			System.err.println("object id: " + _id);
			String _href = (String) session.getPropertyByObjectId(_id, "href");
			System.err.println("href attribute: " + _href);
			return _href.matches(".*about.html$");
		}).collect(Collectors.toList()).get(0);
		// Assert
		Assert.assertEquals(session.getPropertyByObjectId(id, "href"),
				"https://webfolder.io/about.html");
		// Assert
		Assert.assertEquals(session.getPropertyByObjectId(id, "innerHTML"),
				"About");
	}

	@Test(enabled = true)
	public void testSiblingXPath() {

		String xpath = "//*[@id='nav']//a[contains(@href, 'support.html')]/../following-sibling::li/a";
		// Arrange
		session.waitUntil(o -> isVisible(xpath), 1000, 100);

		// Act
		String text = session.getText(xpath);
		highlight(xpath);
		// Assert
		Assert.assertEquals(text, "About");
		// System.err.println("xpath: " + session.getPathname());
	}

}
