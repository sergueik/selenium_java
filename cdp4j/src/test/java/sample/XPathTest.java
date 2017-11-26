package sample;

import java.lang.reflect.Method;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import io.webfolder.cdp.type.runtime.CallFunctionOnResult;
import io.webfolder.cdp.type.runtime.RemoteObject;

public class XPathTest extends BaseTest {

	private String baseURL = "https://webfolder.io";

	@BeforeMethod
	public void beforeMethod(Method method) {
		super.beforeMethod(method);
		session.navigate(baseURL).waitDocumentReady();
	}

	@Test(enabled = true)
	public void xPath1Test() {
		// Arrange

		// Act
		String pageTitle = session.getText("/html/head/title");

		// Assert
		Assert.assertEquals(pageTitle, "WebFolder");

	}

	@Test(enabled = true)
	public void xPath2Test() {

		// Act

		String linkSupportXPath = "//*[@id='nav']//a[contains(@href, 'support.html')]";
		String linkSupport = session.getText(linkSupportXPath);

		// Assert
		Assert.assertEquals(linkSupport, "Support");
		highlight(linkSupportXPath,session, 1000);

	}

	@Test(enabled = true)
	public void xPath3Test() {

		String linkAboutXPath = "//*[@id='nav']//a[contains(@href, 'support.html')]/../following-sibling::li/a";
		String linkAbout = session.getText(linkAboutXPath);
		// Assert
		Assert.assertEquals(linkAbout, "About");

		Integer nodeId = session.getNodeId(linkAboutXPath);
		System.err.println("node ID: " + nodeId);
		String objectId = session.getObjectId(linkAboutXPath);
		System.err.println("object ID: " + objectId);
		// String highlight = "window.highlight = function(dom) { return dom; }";
		// session.evaluate(highlight);
		// String result = session.callFunction("highlight", String.class,
		// new Object[] { objectId });
		// session.getAttribute(xpath, "");
		try {
			// https://stackoverflow.com/questions/45985234/how-to-highlight-elements-in-a-chrome-extension-similar-to-how-devtools-does-it
			// https://github.com/gorhill/uBlock/blob/5cc7a3a8524371ce8d7ab669813b80ef09276c7f/src/js/scriptlets/element-picker.js#L228
			CallFunctionOnResult functionResult = session.getCommand().getRuntime()
					.callFunctionOn(
							"function() { this.style.border='3px solid yellow'; }", objectId,
							null, null, null, null, null, null, nodeId, null);
			if (functionResult != null) {
				RemoteObject result = functionResult.getResult();
				if (result != null) {
					session.releaseObject(result.getObjectId());
				}
			}
			sleep(1000);
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}

	}

}
