package sample;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

import io.webfolder.cdp.Launcher;
import io.webfolder.cdp.session.Session;
import io.webfolder.cdp.session.SessionFactory;
import io.webfolder.cdp.type.runtime.CallFunctionOnResult;
import io.webfolder.cdp.type.runtime.RemoteObject;

public class BaseTest {

	public String baseURL = "about:blank";
	public Session session;

	@BeforeClass
	public void beforeClass() throws IOException {
		Launcher launcher = new Launcher();
		SessionFactory factory = launcher.launch();
		session = factory.create();
		// install extensions
		session.installSizzle();
		session.useSizzle();
		session.setUserAgent(
				"Mozilla/5.0 (Windows NT 6.1) AppleWebKit/534.34 (KHTML, like Gecko) PhantomJS/1.9.7 Safari/534.34");
		session.navigate(baseURL);
		System.err.println("Location:" + session.getLocation());
	}

	@AfterClass
	public void afterClass() {
		if (session != null) {
			session.stop();
			session.close();
		}
	}

	@BeforeMethod(alwaysRun = true)
	public void beforeMethod(Method method) {
		String methodName = method.getName();
		System.err.println("Test Name: " + methodName + "\n");
	}

	@AfterMethod
	public void afterMethod() {
		session.navigate(baseURL);
	}

	@AfterTest(alwaysRun = true)
	public void afterTest() {
	}

	protected void sleep(long seconds) {
		try {
			Thread.sleep(seconds);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	protected void sleep(Integer seconds) {
		long secondsLong = (long) seconds;
		try {
			Thread.sleep(secondsLong);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	protected void highlight(String selectorOfElement) {
		highlight(selectorOfElement, session, 100);
	}

	protected void highlight(String selectorOfElement, int interval) {
		highlight(selectorOfElement, session, (long) interval);
	}

	protected void highlight(String selectorOfElement, Session session) {
		highlight(selectorOfElement, session, 100);
	}

	// NOTE: alternative solution:
	// https://stackoverflow.com/questions/45985234/how-to-highlight-elements-in-a-chrome-extension-similar-to-how-devtools-does-it
	// https://github.com/gorhill/uBlock/blob/5cc7a3a8524371ce8d7ab669813b80ef09276c7f/src/js/scriptlets/element-picker.js#L228
	// https://github.com/kdzwinel/DevToolsVoiceCommands/blob/26d2f8a78bb681a121bddd62e4cb57225e726304/scripts/lib/commands/node-inspection.js#L29
	protected void highlight(String selectorOfElement, Session session,
			long interval) {
		String objectId = session.getObjectId(selectorOfElement);
		Integer nodeId = session.getNodeId(selectorOfElement);
		CallFunctionOnResult functionResult = null;
		RemoteObject result = null;
		// TODO: alternative solution:
		// https://stackoverflow.com/questions/45985234/how-to-highlight-elements-in-a-chrome-extension-similar-to-how-devtools-does-it
		// https://github.com/gorhill/uBlock/blob/5cc7a3a8524371ce8d7ab669813b80ef09276c7f/src/js/scriptlets/element-picker.js#L228
		// https://github.com/kdzwinel/DevToolsVoiceCommands/blob/26d2f8a78bb681a121bddd62e4cb57225e726304/scripts/lib/commands/node-inspection.js#L29

		executeScript("function() { this.style.border='3px solid yellow'; }",
				selectorOfElement);
		sleep(interval);
		executeScript("function() { this.style.border=''; }", selectorOfElement);
	}

	protected Object executeScript(Session session, String script,
			String selectorOfElement) {
		if (!session.matches(selectorOfElement)) {
			return null;
		}
		String objectId = session.getObjectId(selectorOfElement);
		Integer nodeId = session.getNodeId(selectorOfElement);
		CallFunctionOnResult functionResult = null;
		RemoteObject result = null;
		Object value = null;
		try {
			functionResult = session.getCommand().getRuntime().callFunctionOn(script,
					objectId, null, null, null, null, null, null, nodeId, null);
			if (functionResult != null) {
				result = functionResult.getResult();
				if (result != null) {
					value = result.getValue();
					session.releaseObject(result.getObjectId());
				}
			}
		} catch (Exception e) {
			System.err.println("Exception (ignored): " + e.getMessage());
		}
		// System.err.println("value: " + value);
		return value;
	}

	protected Object executeScript(String script, String selectorOfElement) {
		return executeScript(session, script, selectorOfElement);
	}

	// https://stackoverflow.com/questions/1343237/how-to-check-elements-visibility-via-javascript
	protected boolean isVisible(String selectorOfElement) {
		return (boolean) (session.matches(selectorOfElement)
				&& (boolean) executeScript(
						"function() { return(this.offsetWidth > 0 || this.offsetHeight > 0); }",
						selectorOfElement));
	}

	// NOTE: broken
	protected String xpathOfElement(String selectorOfElement) {
		session.evaluate(getScriptContent("xpathOfElement.js"));
		return (String) executeScript("return xpathOfElement(this)",
				selectorOfElement);
	}

	// NOTE: broken
	protected String cssSelectorOfElement(String selectorOfElement) {
		session.evaluate(getScriptContent("cssSelectorOfElement.js"));
		return (String) executeScript("return cssSelectorOfElement(this)",
				selectorOfElement);
	}

	// NOTE: broken
	protected String altTextOfElement(String selectorOfElement) {
		session.evaluate(getScriptContent("getText.js"));
		return (String) executeScript("return getText(this)", selectorOfElement);
	}

	protected static String getScriptContent(String scriptName) {
		try {
			final InputStream stream = BaseTest.class.getClassLoader()
					.getResourceAsStream(scriptName);
			final byte[] bytes = new byte[stream.available()];
			stream.read(bytes);
			// System.err.println("Loaded:\n" + new String(bytes, "UTF-8"));
			return new String(bytes, "UTF-8");
		} catch (IOException e) {
			throw new RuntimeException("Cannot load file: " + scriptName);
		}
	}
}
