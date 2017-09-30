package org.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.jprotractor.NgBy;
import com.jprotractor.NgWebDriver;
import com.jprotractor.NgWebElement;

public class KeywordLibrary {

	private static boolean instance_flag = false;

	private Object _object = null;
	private Class<?> _class = null;

	public WebDriver driver;
	public WebDriverWait wait;
	public Actions actions;

	private NgWebDriver ngDriver;
	private NgBy ngBy;

	private WebElement element;
	private NgWebElement ng_element;

	Properties objectRepo;
	String status;
	String result;

	private String selectorType = null;
	private String selectorValue = null;
	private String selectorRow = null;
	private String selectorColumn = null;
	private String selectorContainedText = null;

	private String expectedValue = null;
	private String textData = null;
	private String visibleText = null;
	private String expectedText = null;
	private String attributeName = null;

	private String param1;
	private String param2;
	private String param3;

	public int scriptTimeout = 5;
	public int stepWait = 150;
	public int flexibleWait = 120;
	public int implicitWait = 1;
	public long pollingInterval = 500;

	private Map<String, String> methodTable = new HashMap<>();
	{
		methodTable.put("CLICK", "clickButton");
		methodTable.put("CLICK_BUTTON", "clickButton");
		methodTable.put("CLICK_CHECKBOX", "clickCheckBox");
		methodTable.put("CLICK_LINK", "clickLink");
		methodTable.put("CLICK_RADIO", "clickRadioButton");
		methodTable.put("CLOSE_BROWSER", "closeBrowser");
		methodTable.put("CREATE_BROWSER", "openBrowser");
		methodTable.put("ELEMENT_PRESENT", "elementPresent");
		methodTable.put("GET_ATTR", "getElementAttribute");
		methodTable.put("GET_TEXT", "getElementText");
		methodTable.put("GOTO_URL", "navigateTo");
		methodTable.put("SELECT_OPTION", "selectDropDown");
		methodTable.put("SET_TEXT", "enterText");
		methodTable.put("SEND_KEYS", "enterText");
		methodTable.put("SWITCH_FRAME", "switchFrame");
		methodTable.put("VERIFY_ATTR", "verifyAttribute");
		methodTable.put("VERIFY_TEXT", "verifyText");
		methodTable.put("CLEAR_TEXT", "clearText");
		methodTable.put("WAIT", "wait");
	}

	private Map<String, Method> locatorTable = new HashMap<>();

	public void closeBrowser(Map<String, String> params) {
		driver.quit();
	}

	public void navigateTo(Map<String, String> params) {
		String url = params.get("param1");
		System.err.println("Navigate to: " + url);
		driver.navigate().to(url);
	}

	private KeywordLibrary() {
		initMethods();
	}

	public String getStatus() {
		return status;
	}

	public String getResult() {
		return result;
	}

	public static KeywordLibrary Instance() {
		if (!instance_flag) {
			instance_flag = true;
			return new KeywordLibrary();
		} else
			return null;
	}

	public void finalize() {
		instance_flag = false;
	}

	public void initMethods() {
		try {
			_class = Class.forName("org.utils.KeywordLibrary");
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}

		for (String keyword : methodTable.keySet()
				.toArray(new String[methodTable.keySet().size()])) {
			if (methodTable.get(keyword).isEmpty()) {
				// System.err.println("Removing keyword: " + keyword);
				methodTable.remove(keyword);
			} else {
				try {
					_class.getMethod(methodTable.get(keyword), Map.class);
				} catch (NoSuchMethodException e) {
					// System.err.println(
					// "Removing keyword: " + keyword + " exception: " + e.toString());
					methodTable.remove(keyword);
				} catch (SecurityException e) {
					// ignore
				}
			}

		}
		// reciprocal references
		for (String methodName : methodTable.values()
				.toArray(new String[methodTable.values().size()])) {
			// System.err.println("Adding keyword for method: " + methodName);
			if (!methodTable.containsKey(methodName)) {
				methodTable.put(methodName, methodName);
			}
		}
		try {
			Class<?> _locatorHelper = Class.forName("org.openqa.selenium.By");
			Method[] _locatorMethods = _locatorHelper.getMethods();
			for (Method _locatorMethod : _locatorMethods) {
				// System.err.println("Adding locator of org.openqa.selenium.By: "
				// + _locatorMethod.toString());
			}
		} catch (ClassNotFoundException | SecurityException e) {

		}

		try {
			Class<?> _locatorHelper = Class.forName("com.jprotractor.NgBy");
			Method[] _locatorMethods = _locatorHelper.getMethods();
			for (Method _locatorMethod : _locatorMethods) {
				// System.err.println("Adding locator of com.jprotractor.NgBy: "
				// + _locatorMethod.toString());
			}
		} catch (ClassNotFoundException | SecurityException e) {
			System.out.println("Execption (ignored): " + e.toString());
		}

		try {
			locatorTable.put("css", By.class.getMethod("cssSelector", String.class));
			locatorTable.put("xpath", By.class.getMethod("xpath", String.class));
			locatorTable.put("id", By.class.getMethod("id", String.class));
			locatorTable.put("name", By.class.getMethod("name", String.class));
		} catch (NoSuchMethodException e) {
		}
		try {
			locatorTable.put("binding",
					NgBy.class.getMethod("binding", String.class));
			locatorTable.put("buttontext",
					NgBy.class.getMethod("buttonText", String.class));
			locatorTable.put("cssContainingText", NgBy.class
					.getMethod("cssContainingText", String.class, String.class));
			locatorTable.put("model", NgBy.class.getMethod("model", String.class));
			locatorTable.put("options",
					NgBy.class.getMethod("options", String.class));
			locatorTable.put("repeater",
					NgBy.class.getMethod("repeater", String.class));
			locatorTable.put("repeaterColumn",
					NgBy.class.getMethod("repeaterColumn", String.class, String.class));
			locatorTable.put("repeaterElement", NgBy.class.getMethod(
					"repeaterElement", String.class, Integer.class, String.class));
			locatorTable.put("repeaterRows",
					NgBy.class.getMethod("repeaterRows", String.class, Integer.class));
			locatorTable.put("selectedOption",
					NgBy.class.getMethod("selectedOption", String.class));
			locatorTable.put("selectedRepeaterOption",
					NgBy.class.getMethod("selectedRepeaterOption", String.class));
		} catch (NoSuchMethodException e) {
			System.out.println("Execption (ignored): " + e.toString());
		}
	}

	public void callMethod(String keyword, Map<String, String> params) {

		if (_object == null) {

			try {
				_object = _class.newInstance();
			} catch (IllegalAccessException | InstantiationException e) {
				throw new RuntimeException(e);
			}

		}
		if (methodTable.containsKey(keyword)) {
			String methodName = methodTable.get(keyword);
			try {
				System.err.println(keyword + " call method: " + methodName + " with "
						+ String.join(",", params.values()));
				_class.getMethod(methodName, Map.class).invoke(_object, params);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			throw new RuntimeException("No method found for keyword: " + keyword);
		}
	}

	public void loadProperties() throws IOException {
		File file = new File("ObjectRepo.Properties");
		objectRepo = new Properties();
		objectRepo.load(new FileInputStream(file));
	}

	public void openBrowser(Map<String, String> params) throws IOException {
		try {
			File file = new File("Config.properties");
			Properties config = new Properties();
			config.load(new FileInputStream(file));
			if (config.getProperty("browser").equalsIgnoreCase("Chrome")) {
				System.setProperty("webdriver.chrome.driver",
						"C:\\java\\selenium\\chromedriver.exe");
				driver = new ChromeDriver();
				ngDriver = new NgWebDriver(driver);
				driver.get(config.getProperty("url"));
			}
			driver.manage().window().maximize();
			status = "Passed";
		} catch (Exception e) {
			status = "Failed";
		}
	}

	public void enterText(Map<String, String> params) {
		element = _findElement(params);
		textData = params.get("param5");
		if (element != null) {
			highlight(element);
			element.sendKeys(textData);
			status = "Passed";
		} else {
			status = "Failed";
		}
	}

	public void clickButton(Map<String, String> params) {
		element = _findElement(params);
		if (element != null) {
			highlight(element);
			element.click();
			status = "Passed";
		} else {
			status = "Failed";
		}
	}

	public void clickLink(Map<String, String> params) {
		element = _findElement(params);
		if (element != null) {
			highlight(element);
			element.click();
			status = "Passed";
		} else {
			status = "Failed";
		}
		try {
			Thread.sleep(stepWait);
		} catch (InterruptedException e) {
			// System.err.println("Ignored: " + e.toString());
		}

	}

	public void selectDropDown(Map<String, String> params) {
		Select select;
		visibleText = params.get("param5");
		element = _findElement(params);
		if (element != null) {
			highlight(element);
			select = new Select(element);
			try {
				select.selectByVisibleText(visibleText);
				status = "Passed";
			} catch (Exception e) {
				status = "Failed";
			}
		} else {
			status = "Failed";
		}
	}

	public void verifyAttribute(Map<String, String> params) {
		boolean flag = false;
		attributeName = params.get("param5");
		expectedValue = params.get("param4");
		element = _findElement(params);
		if (element != null) {
			flag = element.getAttribute(attributeName).equals(expectedValue);
		}
		if (flag)
			status = "Passed";
		else
			status = "Failed";
	}

	public void verifyText(Map<String, String> params) {
		boolean flag = false;
		expectedText = params.get("param5");
		element = _findElement(params);
		if (element != null) {
			highlight(element);
			flag = element.getText().equals(expectedText);
		}
		if (flag)
			status = "Passed";
		else
			status = "Failed";
	}

	public void getElementText(Map<String, String> params) {
		String text = null;
		element = _findElement(params);
		if (element != null) {
			highlight(element);
			text = element.getText();
			status = "Passed";
			result = text;
			System.err.println(
					String.format("%s returned \"%s\"", "getElementText", result));
		} else {
			status = "Failed";
		}
	}

	public void getElementAttribute(Map<String, String> params) {
		attributeName = params.get("param5");
		String value = null;
		element = _findElement(params);
		if (element != null) {
			highlight(element);
			value = element.getAttribute(attributeName);
			status = "Passed";
			result = value;
			System.err.println(
					String.format("%s returned \"%s\"", "getElementAttribute", result));
		} else {
			status = "Failed";
		}
	}

	public void elementPresent(Map<String, String> params) {
		Boolean flag = false;
		element = _findElement(params);
		if (element != null) {
			flag = element.isDisplayed();
		}
		if (flag) {
			highlight(element);
			status = "Passed";
			result = "true";
		} else {
			status = "Failed";
			result = "false";
		}
	}

	public void clickCheckBox(Map<String, String> params) {
		expectedValue = params.get("param5");
		if (expectedValue.equals("null")) {
			element = _findElement(params);
			if (element != null) {
				highlight(element);
				element.click();
			}
		} else {
			element = null;
			for (WebElement e : _findElements(params)) {
				if (e.getAttribute("value").equals(expectedValue)) {
					element = e;
				}
			}
		}

		if (element != null) {
			highlight(element);
			element.click();
			status = "Passed";
		} else {
			status = "Failed";
		}
	}

	public void clickRadioButton(Map<String, String> params) {
		expectedValue = params.get("param5");
		if (expectedValue.equals("null")) {
			element = _findElement(params);
		} else {
			element = null;
			for (WebElement e : _findElements(params)) {
				if (e.getAttribute("value").equals(expectedValue)) {
					element = e;
				}
			}
		}
		if (element != null) {
			highlight(element);
			element.click();
			status = "Passed";
		} else {
			status = "Failed";
		}
	}

	// TODO:
	public void switchFrame(Map<String, String> params) {
		param1 = params.get("param1");
		param2 = params.get("param2");
		param3 = params.get("param5");
		try {
			switch (param1) {
			case "name":
				driver.switchTo().frame(param2);
				break;
			case "id":
				driver.findElement(By.id(param2)).click();
				break;
			case "css":
				driver.findElement(By.cssSelector(param2)).click();
				System.out
						.println(driver.findElement(By.cssSelector(param2)).getText());
				break;
			case "xpath":
				driver.findElement(By.xpath(param2)).click();
				System.out.println(driver.findElement(By.xpath(param2)).getText());
				break;
			}
			status = "Passed";
		} catch (Exception e) {
			status = "Failed";
		}
	}

	public WebElement _findElement(Map<String, String> params) {
		selectorType = params.get("param1");
		if (!locatorTable.containsKey(selectorType)) {
			throw new RuntimeException("Unknown Selector Type: " + selectorType);
		}
		// TODO: objectRepo.getProperty(selectorValue) || selectorValue
		selectorValue = params.get("param2");
		if (params.containsKey("param3")) {
			selectorRow = params.get("param3");
		}
		if (params.containsKey("param4")) {
			selectorColumn = params.get("param4");
		}
		// TODO: ambiguity
		if (params.containsKey("param5")) {
			selectorContainedText = params.get("param5");
		}
		WebElement _element = null;
		try {
			switch (selectorType) {
			case "binding":
				_element = ngDriver.findElement(NgBy.binding(selectorValue));
				break;
			case "buttontext":
				_element = ngDriver.findElement(NgBy.buttonText(selectorValue));
				break;
			case "css":
				_element = driver.findElement(By.cssSelector(selectorValue));
				break;
			case "cssContainingText":
				_element = ngDriver.findElement(
						NgBy.cssContainingText(selectorValue, selectorContainedText));
				break;
			case "cssSelector":
				_element = driver.findElement(By.cssSelector(selectorValue));
				break;
			case "id":
				_element = driver.findElement(By.id(selectorValue));
				break;
			case "model":
				_element = ngDriver.findElement(NgBy.model(selectorValue));
				break;
			case "linkText":
				_element = driver.findElement(By.linkText(selectorValue));
				break;
			case "name":
				_element = driver.findElement(By.name(selectorValue));
				break;
			case "options":
				_element = ngDriver.findElement(NgBy.options(selectorValue));
				break;
			case "partialLinkText":
				_element = driver.findElement(By.partialLinkText(selectorValue));
				break;
			case "repeater":
				_element = ngDriver.findElement(NgBy.repeater(selectorValue));
				break;
			case "repeaterColumn":
				_element = ngDriver
						.findElement(NgBy.repeaterColumn(selectorValue, selectorColumn));
				break;
			case "repeatereElement":
				_element = ngDriver.findElement(NgBy.repeaterElement(selectorValue,
						Integer.parseInt(selectorRow), selectorColumn));
				break;
			case "repeaterRows":
				_element = ngDriver.findElement(
						NgBy.repeaterRows(selectorValue, Integer.parseInt(selectorRow)));
				break;
			// repeatereElement
			case "selectedOption":
				_element = ngDriver.findElement(NgBy.selectedOption(selectorValue));
				break;
			case "selectedRepeaterOption":
				_element = ngDriver
						.findElement(NgBy.selectedRepeaterOption(selectorValue));
				break;
			case "xpath":
				_element = driver.findElement(By.xpath(selectorValue));
				break;
			}
		} catch (Exception e) {
			// TODO: logging
		}
		return _element;
	}

	public List<WebElement> _findElements(Map<String, String> params) {
		selectorType = params.get("param1");
		if (!locatorTable.containsKey(selectorType)) {
			throw new RuntimeException("Unknown Selector Type: " + selectorType);
		}
		// TODO: objectRepo.getProperty(selectorValue) || selectorValue
		selectorValue = params.get("param2");
		// TODO: ambiguity
		if (params.containsKey("param5")) {
			selectorContainedText = params.get("param5");
		}
		List<WebElement> _elements = new ArrayList<>();
		try {
			switch (selectorType) {
			case "binding":
				_elements = ngDriver.findElements(NgBy.binding(selectorValue));
				break;
			case "buttontext":
				_elements = ngDriver.findElements(NgBy.buttonText(selectorValue));
				break;
			case "css":
				_elements = driver.findElements(By.cssSelector(selectorValue));
				break;
			case "cssContainingText":
				_elements = ngDriver.findElements(
						NgBy.cssContainingText(selectorValue, selectorContainedText));
				break;
			case "cssSelector":
				_elements = driver.findElements(By.cssSelector(selectorValue));
				break;
			case "id":
				_elements = driver.findElements(By.id(selectorValue));
				break;
			case "model":
				_elements = ngDriver.findElements(NgBy.model(selectorValue));
				break;
			case "linkText":
				_elements = driver.findElements(By.linkText(selectorValue));
				break;
			case "name":
				_elements = driver.findElements(By.name(selectorValue));
				break;
			case "options":
				_elements = ngDriver.findElements(NgBy.options(selectorValue));
				break;
			case "partialLinkText":
				_elements = driver.findElements(By.partialLinkText(selectorValue));
				break;
			case "repeater":
				_elements = ngDriver.findElements(NgBy.repeater(selectorValue));
				break;
			// repeaterColumn
			// repeatereElement
			// selectedOption
			// selectedRepeaterOption
			// are unlikely to be used here
			case "xpath":
				_elements = driver.findElements(By.xpath(selectorValue));
				break;
			}
		} catch (Exception e) {
			// TODO: logging
		}
		return _elements;
	}

	public void wait(Map<String, String> params) {
		Long wait = Long.parseLong(params.get("param1"));
		try {
			Thread.sleep(wait);
		} catch (InterruptedException e) {
		}
	}

	public void highlight(WebElement element) {
		highlight(element, 100);
	}

	public void highlight(WebElement element, long highlight_interval) {
		if (wait == null) {
			wait = new WebDriverWait(driver, flexibleWait);
		}
		wait.pollingEvery(pollingInterval, TimeUnit.MILLISECONDS);
		try {
			wait.until(ExpectedConditions.visibilityOf(element));
			if (driver instanceof JavascriptExecutor) {
				((JavascriptExecutor) driver).executeScript(
						"arguments[0].style.border='3px solid yellow'", element);
			}
			Thread.sleep(highlight_interval);
			if (driver instanceof JavascriptExecutor) {
				((JavascriptExecutor) driver)
						.executeScript("arguments[0].style.border=''", element);
			}
		} catch (InterruptedException e) {
			// System.err.println("Ignored: " + e.toString());
		}
	}
}
