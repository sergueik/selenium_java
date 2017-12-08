package com.github.sergueik.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import io.webfolder.cdp.Launcher;
import io.webfolder.cdp.session.Session;
import io.webfolder.cdp.session.SessionFactory;
import io.webfolder.cdp.type.runtime.CallFunctionOnResult;
import io.webfolder.cdp.type.runtime.RemoteObject;

/**
 * Keyword Driven Library for Chrome Devkit Protocol 4 Java
 * @author: Serguei Kouzmine (kouzmine_serguei@yahoo.com)
 */

public final class KeywordLibrary {

	private static Class<?> _class = null;
	public static Session session;
	private static Pattern pattern;
	private static Matcher matcher;
	private static String selector;
	private static long timeout;

	private static Properties objectRepo;
	private static String status;
	private static String result;
	private static String selectorType = null;
	private static String selectorValue = null;
	private static String expectedValue = null;
	private static String textData = null;
	private static String expectedText = null;
	private static String attributeName = null;

	private static String elementID;
	// private static String param3;

	public static int scriptTimeout = 5;
	public static int stepWait = 150;
	public static int flexibleWait = 120;
	public static int implicitWait = 1;
	public static long pollingInterval = 500;

	private static Map<String, String> methodTable = new HashMap<>();
	static {
		methodTable.put("CLICK", "clickElement");
		methodTable.put("CLOSE_BROWSER", "closeBrowser");
		methodTable.put("CREATE_BROWSER", "openBrowser");
		methodTable.put("ELEMENT_PRESENT", "elementPresent");
		methodTable.put("GET_ATTR", "getElementAttribute");
		methodTable.put("GET_TEXT", "getElementText");
		methodTable.put("GOTO_URL", "navigateTo");
		methodTable.put("SET_TEXT", "enterText");
		methodTable.put("SEND_KEYS", "enterText");
		methodTable.put("VERIFY_ATTR", "verifyAttribute");
		methodTable.put("VERIFY_TEXT", "verifyText");
		methodTable.put("CLEAR_TEXT", "clearText");
		methodTable.put("WAIT", "wait");
		methodTable.put("WAIT_URL_CHANGE", "waitURLChange");
	}
	private static Map<String, Method> locatorTable = new HashMap<>();

	public static void closeBrowser(Map<String, String> params) {
		if (session != null) {
			session.stop();
			session.close();
		}
	}

	public static void navigateTo(Map<String, String> params) {
		String url = params.get("param1");
		System.err.println("Navigate to: " + url);
		session.navigate(url);
	}

	public static String getStatus() {
		return status;
	}

	public String getResult() {
		return result;
	}

	// https://stackoverflow.com/questions/7486012/static-classes-in-java
	// https://github.com/sergueik/selenium_java/commit/57724dafc4fa33339

	// A top-level Java class mimicking static class behavior
	// All methods are static
	private KeywordLibrary() { // private constructor
		_class = null;
	}

	public static void initMethods() {
		try {
			_class = Class.forName("com.github.sergueik.utils.KeywordLibrary");
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
		for (String keyword : methodTable.keySet()
				.toArray(new String[methodTable.keySet().size()])) {
			if (methodTable.get(keyword).isEmpty()) {
				System.err.println("Removing keyword:" + keyword);
				methodTable.remove(keyword);
			} else {
				try {
					_class.getMethod(methodTable.get(keyword), Map.class);
				} catch (NoSuchMethodException e) {
					System.err.println(
							"Removing  keyword:" + keyword + " exception: " + e.toString());
					methodTable.remove(keyword);
				} catch (SecurityException e) {
					/* ignore*/
				}
			}
		}
		for (String methodName : methodTable.values()
				.toArray(new String[methodTable.values().size()])) {
			System.err.println("Adding keyword for method: " + methodName);
			if (!methodTable.containsKey(methodName)) {
				methodTable.put(methodName, methodName);
			}
		}
		/*
		// optional: list all methods
		try {
			Class<?> _locatorHelper = Class.forName("org.openqa.selenium.By");
			Method[] _locatorMethods = _locatorHelper.getMethods();
			for (Method _locatorMethod : _locatorMethods) {
				System.err.println("Adding locator of org.openqa.selenium.By: "
						+ _locatorMethod.toString());
			}
		} catch (ClassNotFoundException | SecurityException e) {
		}
		*/
		/*
		// optional: list all methods
		try {
			Class<?> _locatorHelper = Class.forName("com.jprotractor.NgBy");
			Method[] _locatorMethods = _locatorHelper.getMethods();
			for (Method _locatorMethod : _locatorMethods) {
				System.err.println("Adding locator of com.jprotractor.NgBy:"
						+ _locatorMethod.toString());
			}
		} catch (ClassNotFoundException | SecurityException e) {
			System.out.println("Exception (ignored): " + e.toString());
		}
		*/
		// there is no By's
		// use phony method
		Method methodMissing = null;
		try {
			// do we ever want to send correct arguments ?
			@SuppressWarnings("rawtypes")
			Class[] arguments = new Class[] { String.class, String.class };
			Constructor methodConstructor = Method.class
					.getDeclaredConstructor(arguments);
			methodConstructor.setAccessible(true);
			methodMissing = (Method) methodConstructor.newInstance();
		} catch (NoSuchMethodException | IllegalAccessException
				| InstantiationException | IllegalArgumentException
				| InvocationTargetException e) {
			System.out.println("Exception (ignored): " + e.toString());

		}
		// put synthetic selectors explicitly
		for (String locatorKind : new ArrayList<String>(
				Arrays.asList(new String[] { "css", "id", "className", "cssSelector",
						"linkText", "name", "tagName", "text", "xpath" }))) {
			locatorTable.put(locatorKind, methodMissing);
		}
	}

	public static void callMethod(String keyword, Map<String, String> params) {
		if (_class == null) {
			initMethods();
		}
		if (methodTable.containsKey(keyword)) {
			String methodName = methodTable.get(keyword);
			try {
				System.out.println(keyword + " call method: " + methodName + " with "
						+ String.join(",", params.values()));
				_class.getMethod(methodName, Map.class).invoke(null, params);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("No method found for keyword: " + keyword);
		}
	}

	public static void loadProperties() throws IOException {
		File file = new File("ObjectRepo.Properties");
		objectRepo = new Properties();
		objectRepo.load(new FileInputStream(file));
	}

	public static void openBrowser(Map<String, String> params)
			throws IOException {
		try {
			Launcher launcher = new Launcher();
			SessionFactory factory = launcher.launch();
			session = factory.create();
			status = "Passed";
			session.installSizzle();
			session.useSizzle();
			session.clearCookies();
			session.clearCache();
			session.setUserAgent(
					"Mozilla/5.0 (Windows NT 6.1) AppleWebKit/534.34 (KHTML, like Gecko) PhantomJS/1.9.7 Safari/534.34");
			System.err.println("Location:" + session.getLocation());
		} catch (Exception e) {
			status = "Failed";
		}
	}

	public static void enterText(Map<String, String> params) {
		elementID = _findElement(params);
		selectorValue = params.get("param2");
		textData = params.get("param5");
		if (elementID != null) {
			// highlight(element)
			session.focus(selectorValue);
			session.sendKeys(textData);
			status = "Passed";
		} else {
			status = "Failed";
		}
	}

	public static void clickElement(Map<String, String> params) {
		selectorValue = params.get("param2");
		elementID = _findElement(params);
		if (elementID != null) {
			highlight(selectorValue);
			System.err.println("click");
			session.click(selectorValue);
			status = "Passed";
		} else {
			System.err.println("Can't click");
			status = "Failed";
		}
		try {
			Thread.sleep(stepWait);
		} catch (InterruptedException e) {
			// System.err.println("Ignored: " + e.toString());
		}

	}

	public static void verifyAttribute(Map<String, String> params) {
		boolean flag = false;
		attributeName = params.get("param5");
		expectedValue = params.get("param6");
		selectorValue = params.get("param2");
		elementID = _findElement(params);
		if (elementID != null) {
			highlight(selectorValue);
			flag = session.getAttribute(selectorValue, attributeName)
					.equals(expectedValue);
		}
		if (flag)
			status = "Passed";
		else
			status = "Failed";
	}

	public static void verifyText(Map<String, String> params) {
		boolean flag = false;

		expectedText = params.get("param5");
		selectorValue = params.get("param2");
		elementID = _findElement(params);
		if (elementID != null) {
			highlight(selectorValue);
			flag = session.getText(selectorValue).equals(expectedText);
		}
		if (flag)
			status = "Passed";
		else
			status = "Failed";
	}

	public static void getElementText(Map<String, String> params) {
		String text = null;
		selectorValue = params.get("param2");
		String value = null;
		elementID = _findElement(params);
		if (elementID != null) {
			highlight(selectorValue);
			value = session.getText(selectorValue);
			status = "Passed";
			result = text;
			System.err.println(
					String.format("%s returned \"%s\"", "getElementText", result));
		} else {
			status = "Failed";
		}
	}

	public static void getElementAttribute(Map<String, String> params) {
		attributeName = params.get("param5");
		selectorValue = params.get("param2");
		String value = null;
		elementID = _findElement(params);
		if (elementID != null) {
			highlight(selectorValue);
			value = session.getAttribute(selectorValue, attributeName);
			status = "Passed";
			result = value;
			System.err.println(
					String.format("%s returned \"%s\"", "getElementAttribute", result));
		} else {
			status = "Failed";
		}
	}

	public static void elementPresent(Map<String, String> params) {
		Boolean flag = false;
		selectorValue = params.get("param2");
		elementID = _findElement(params);
		if (elementID != null) {
			flag = isVisible(selectorValue);
		}
		if (flag) {
			highlight(selectorValue);
			status = "Passed";
			result = "true";
		} else {
			status = "Failed";
			result = "false";
		}
	}

	public static String _findElement(Map<String, String> params) {
		selectorType = params.get("param1");
		if (!locatorTable.containsKey(selectorType)) {
			throw new RuntimeException("Unknown Selector Type: " + selectorType);
		}
		/* TODO: objectRepo.getProperty(selectorValue) || selectorValue */
		selectorValue = params.get("param2");
		if (params.containsKey("param5")) {
			selectorContainedText = params.get("param5");
		}
		if (params.containsKey("param6")) {
			selectorTagName = params.get("param6");
		}

		String _elementID = null;
		switch (selectorType) {
		case "cssContainingText":
			// TODO:
			break;
		case "text":
			// TODO:
			break;

		default:
			_elementID = session.getObjectId(selectorValue);
			break;
		}
		return _elementID;
	}

	public static List<String> _findElements(Map<String, String> params) {
		selectorType = params.get("param1");
		if (!locatorTable.containsKey(selectorType)) {
			throw new RuntimeException("Unknown Selector Type: " + selectorType);
		}
		/* TODO: objectRepo.getProperty(selectorValue) || selectorValue */
		selectorValue = params.get("param2");
		if (params.containsKey("param5")) {
			selectorContainedText = params.get("param5");
		}
		if (params.containsKey("param6")) {
			selectorTagName = params.get("param6");
		}

		List<String> _elementIDs = new ArrayList<>();
		switch (selectorType) {
		case "cssContainingText":
			// TODO:
			break;
		case "text":
			// TODO:
			break;

		default:
			_elementIDs = session.getObjectIds(selectorValue);
			break;
		}
		return _elementIDs;
	}

	// wait for the page url to change to contain expectedURL
	public static void waitURLChange(Map<String, String> params) {
		final String expectedURL = params.get("param1");
		final int timeout = (int) (Float.parseFloat(params.get("param7")));
		session.waitUntil(o -> {
			String url = o.getLocation();
			return (boolean) url.matches(String.format("^%s.*", expectedURL));
		}, timeout, 100);
	}

	public static void wait(Map<String, String> params) {
		timeout = (long) (Float.parseFloat(params.get("param7")));
		try {
			Thread.sleep(timeout);
		} catch (InterruptedException e) {
		}
	}

	protected static void sleep(long seconds) {
		try {
			Thread.sleep(seconds);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	protected static void sleep(Integer seconds) {
		long secondsLong = (long) seconds;
		try {
			Thread.sleep(secondsLong);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	protected static void highlight(String selectorOfElement) {
		highlight(selectorOfElement, session, 100);
	}

	protected static void highlight(String selectorOfElement, int interval) {
		highlight(selectorOfElement, session, (long) interval);
	}

	protected static void highlight(String selectorOfElement, Session session) {
		highlight(selectorOfElement, session, 100);
	}

	protected static void highlight(String selectorOfElement, Session session,
			long interval) {
		String objectId = session.getObjectId(selectorOfElement);
		Integer nodeId = session.getNodeId(selectorOfElement);
		CallFunctionOnResult functionResult = null;
		RemoteObject result = null;
		executeScript("function() { this.style.border='3px solid yellow'; }",
				selectorOfElement);
		sleep(interval);
		executeScript("function() { this.style.border=''; }", selectorOfElement);
	}

	protected static Object executeScript(Session session, String script,
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

	protected static Object executeScript(String script,
			String selectorOfElement) {
		return executeScript(session, script, selectorOfElement);
	}

	// https://stackoverflow.com/questions/1343237/how-to-check-elements-visibility-via-javascript
	protected static boolean isVisible(String selectorOfElement) {
		return (boolean) (session.matches(selectorOfElement)
				&& (boolean) executeScript(
						"function() { return(this.offsetWidth > 0 || this.offsetHeight > 0); }",
						selectorOfElement));
	}

	// NOTE: limited testing performed so far.
	protected static String xpathOfElement(String selectorOfElement) {
		session.evaluate(getScriptContent("xpathOfElement.js"));
		return (String) executeScript("function() { return xpathOfElement(this); }",
				selectorOfElement);
	}

	protected static String cssSelectorOfElement(String selectorOfElement) {
		session.evaluate(getScriptContent("cssSelectorOfElement.js"));
		return (String) executeScript(
				"function() { return cssSelectorOfElement(this); }", selectorOfElement);
	}

	protected static String textOfElement(String selectorOfElement) {
		session.evaluate(getScriptContent("getText.js"));
		return (String) executeScript("function() { return getText(this);}",
				selectorOfElement);
	}

	protected static String getScriptContent(String scriptName) {
		try {
			final InputStream stream = KeywordLibrary.class.getClassLoader()
					.getResourceAsStream(scriptName);
			final byte[] bytes = new byte[stream.available()];
			stream.read(bytes);
			// System.err.println("Loaded:\n" + new String(bytes, "UTF-8"));
			return new String(bytes, "UTF-8");
		} catch (IOException e) {
			throw new RuntimeException("Cannot load file: " + scriptName);
		}
	}

	protected static void click(String selector) {
		executeScript(session, "function() { this.click(); }", selector);
	}
}
