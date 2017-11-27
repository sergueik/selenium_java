package sample;

import java.lang.reflect.Method;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import io.webfolder.cdp.type.runtime.CallFunctionOnResult;
import io.webfolder.cdp.type.runtime.RemoteObject;

public class HighlightTest extends BaseTest {

	private String baseURL = "https://webfolder.io";

	@BeforeMethod
	public void beforeMethod(Method method) {
		super.beforeMethod(method);
		session.navigate(baseURL).waitDocumentReady();
	}

	@Test(enabled = false)
	public void xPath1Test() {
		// Arrange
		session.waitUntil(o -> o.matches("/html/head/title"), 1000, 100);
		// Act
		String pageTitle = session.getText("/html/head/title");

		// Assert
		Assert.assertEquals(pageTitle, "WebFolder");

	}

	@Test(enabled = true)
	public void xPath2Test() {
		String linkSupportXPath = "//*[@id='nav']//a[contains(@href, 'support.html')]";

		// Arrange
		session.waitUntil(o -> {
			return isVisible(linkSupportXPath);
		}, 1000, 100);

		// Act
		String linkSupport = session.getText(linkSupportXPath);

		// Assert
		Assert.assertEquals(linkSupport, "Support");
		highlight(linkSupportXPath, session, 1000);

	}

	@Test(enabled = false)
	public void xPath3Test() {

		String linkAboutXPath = "//*[@id='nav']//a[contains(@href, 'support.html')]/../following-sibling::li/a";

		// Arrange
		session.waitUntil(o -> {
			return isVisible(linkAboutXPath);
		}, 1000, 100);

		// Act
		String linkAbout = session.getText(linkAboutXPath);
		// Assert
		Assert.assertEquals(linkAbout, "About");
	}

	@Test(enabled = true)
	public void xPath4Test() {
		String linkSupportXPath = "//*[@id='nav']//a[contains(@href, 'support.html')]";

		// Arrange
		session.waitUntil(o -> {
			return isVisible(linkSupportXPath);
		}, 1000, 100);

		// Act
		String linkSupportComputedXPath = xpathOfElement(linkSupportXPath);

		// Assert
		Assert.assertEquals(linkSupportComputedXPath, "Support");
		highlight(linkSupportXPath, session, 1000);

	}

	@Test(enabled = false)
	public void xPath5Test() {

		String linkAboutXPath = "//*[@id='nav']//a[contains(@href, 'support.html')]/../following-sibling::li/a";

		// Arrange
		session.waitUntil(o -> {
			return isVisible(linkAboutXPath);
		}, 1000, 100);

		// Act
		String linkAbout = session.getText(linkAboutXPath);
		// Assert
		Assert.assertEquals(linkAbout, "About");
	}

}
