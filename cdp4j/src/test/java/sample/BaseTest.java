package sample;

import java.io.IOException;
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
		// Go to URL
		session.navigate(baseURL);
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

	protected void highlight(String selectorOfElement, Session session) {
		highlight(selectorOfElement, session, 100);
	}

	protected void highlight(String selectorOfElement, Session session,
			long interval) {
		boolean hasElement = true;
		/*
		hasElement = session.waitUntil(s -> {
			return s.matches(selectorOfElement);
		}, 1000);
		*/
		if (hasElement) {
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
	}

	private Object executeScript(String script, String `) {
		String objectId = session.getObjectId(selectorOfElement);
		Integer nodeId = session.getNodeId(selectorOfElement);
		CallFunctionOnResult functionResult = null;
		RemoteObject result = null;
		Object value = null;
		try {
			// NOTE: alternative solution:
			// https://stackoverflow.com/questions/45985234/how-to-highlight-elements-in-a-chrome-extension-similar-to-how-devtools-does-it
			// https://github.com/gorhill/uBlock/blob/5cc7a3a8524371ce8d7ab669813b80ef09276c7f/src/js/scriptlets/element-picker.js#L228
			// https://github.com/kdzwinel/DevToolsVoiceCommands/blob/26d2f8a78bb681a121bddd62e4cb57225e726304/scripts/lib/commands/node-inspection.js#L29
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
		return value;
	}
}
