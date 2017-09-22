package org.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class KeywordLibrary {

	public static WebDriver driver;
	public static WebDriverWait wait;
	public Actions actions;
	static Properties objectRepo;
	static String result;

	private static String selectorType = null;
	private static String selectorValue = null;
	private static String expectedValue = null;
	private static String textData = null;
	private static String visibleText = null;
	private static String expectedText = null;
	private static String attributeName = null;

	static private String param1;
	static private String param2;
	static private String param3;

	public static int scriptTimeout = 5;
	public static int stepWait = 150;
	public static int flexibleWait = 120;
	public static int implicitWait = 1;
	public static long pollingInterval = 500;

	private static Map<String, String> methodTable = new HashMap<>();
	static {
		methodTable.put("CREATE_BROWSER", "openBrowser");
		methodTable.put("GOTOURL", "navigateTo");
		methodTable.put("SETTEXT", "enterText");
		methodTable.put("GETTEXT", "getText");
		methodTable.put("GETATTR", "getElementAttribute");
		methodTable.put("CLICK", "clickButton");
		methodTable.put("CLICKBUTTON", "clickButton");
		methodTable.put("CLICKLINK", "clickLink");
		methodTable.put("CLICKRADIO", "clickRadioButton");
		methodTable.put("CLICKCHECKBOX", "clickCheckBox");
		methodTable.put("SELECTOPTION", "selectDropDown");
		methodTable.put("VERIFYTEXT", "verifyText");
		methodTable.put("SWITHFRAME", "switchFrame");
		methodTable.put("CLOSE_BROWSER", "closeBrowser");

	}

	public static void closeBrowser(Map<String, String> params) {
		driver.quit();
	}

	public static void navigateTo(Map<String, String> params) {
		String url = params.get("param1");
		System.err.println("Navigate to: " + url);
		driver.navigate().to(url);
	}

	public static void callMethod(String keyword, Map<String, String> params) {
		if (methodTable.containsKey(keyword)) {
			String methodName = methodTable.get(keyword);
			try {
				Class<?> _class = Class.forName("org.utils.KeywordLibrary");
				Method _method = _class.getMethod(methodName, Map.class);
				System.out.println(keyword + " call method: " + methodName + " with "
						+ String.join(",", params.values()));
				Object _result = _method.invoke(null, params);
				System.out.println(
						String.format("%s returned %s", methodName, (String) _result));

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
			File file = new File("Config.properties");
			Properties config = new Properties();
			config.load(new FileInputStream(file));
			if (config.getProperty("browser").equalsIgnoreCase("Chrome")) {
				System.setProperty("webdriver.chrome.driver",
						"C:\\java\\selenium\\chromedriver.exe");
				driver = new ChromeDriver();
				driver.get(config.getProperty("url"));
			}
			driver.manage().window().maximize();
			result = "Passed";
		} catch (Exception e) {
			result = "Failed";
		}

	}

	public static void enterText(Map<String, String> params) {
		selectorType = params.get("param1");
		selectorValue = params.get("param2");
		textData = params.get("param3");
		try {
			switch (selectorType) {
			case "name":
				driver.findElement(By.name(selectorValue)).sendKeys(textData);
				break;
			case "id":
				driver.findElement(By.id(selectorValue)).sendKeys(textData);
				break;
			case "css":
				driver.findElement(By.cssSelector(selectorValue)).sendKeys(textData);
				break;
			case "xpath":
				driver.findElement(By.xpath(selectorValue)).sendKeys(textData);
				break;
			}
			result = "Passed";
		} catch (Exception e) {
			result = "Failed";
		}
	}

	public static void clickButton(Map<String, String> params) {
		selectorType = params.get("param1");
		selectorValue = params.get("param2");
		try {
			switch (selectorType) {
			case "name":
				driver.findElement(By.name(selectorValue)).click();
				break;
			case "id":
				driver.findElement(By.id(selectorValue)).click();
				break;
			case "css":
				driver.findElement(By.cssSelector(selectorValue)).click();
				break;
			case "xpath":
				driver.findElement(By.xpath(selectorValue)).click();
				break;
			}
			result = "Passed";
		} catch (Exception e) {
			result = "Failed";
		}
	}

	public static void clickLink(Map<String, String> params) {
		selectorType = params.get("param1");
		selectorValue = params.get("param2");
		System.err.println("Locate via " + selectorType + " " + selectorValue);
		WebElement element;
		try {
			switch (selectorType) {
			case "linkText":
				element = driver.findElement(By.linkText(selectorValue));
				highlight(element);
				element.click();
				break;
			case "partialLinkText":
				element = driver.findElement(By.partialLinkText(selectorValue));
				highlight(element);
				element.click();
				break;
			case "css":
				element = driver.findElement(By.cssSelector(selectorValue));
				highlight(element);
				element.click();
				break;
			case "xpath":
				element = driver.findElement(By.xpath(selectorValue));
				highlight(element);
				element.click();
				break;
			}
			result = "Passed";
		} catch (Exception e) {
			result = "Failed";
		}
		try {
			Thread.sleep(stepWait);
		} catch (InterruptedException e) {
			// System.err.println("Ignored: " + e.toString());
		}

	}

	public static void selectDropDown(Map<String, String> params) {
		selectorType = params.get("param1");
		selectorValue = params.get("param2");
		visibleText = params.get("param3");
		Select select;
		try {
			switch (selectorType) {
			case "name":
				select = new Select(driver.findElement(By.name(selectorValue)));
				select.selectByVisibleText(visibleText);
				break;
			case "id":
				select = new Select(driver.findElement(By.id(selectorValue)));
				select.selectByVisibleText(visibleText);
				break;
			case "css":
				select = new Select(driver.findElement(By.cssSelector(selectorValue)));
				select.selectByVisibleText(visibleText);
				break;
			case "xpath":
				select = new Select(driver.findElement(By.xpath(selectorValue)));
				select.selectByVisibleText(visibleText);
				break;
			}
			result = "Passed";
		} catch (Exception e) {
			result = "Failed";
		}
	}

	public static void verifyText(Map<String, String> params) {
		boolean flag = false;
		selectorType = params.get("param1");
		selectorValue = params.get("param2");
		expectedText = params.get("param3");
		try {
			switch (selectorType) {
			case "name":
				flag = driver.findElement(By.name(selectorValue)).getText()
						.equals(expectedText);
				break;
			case "id":
				flag = driver.findElement(By.id(selectorValue)).getText()
						.equals(expectedText);
				break;
			case "css":
				flag = driver.findElement(By.cssSelector(selectorValue)).getText()
						.equals(expectedText);
				break;
			case "xpath":
				flag = driver.findElement(By.xpath(selectorValue)).getText()
						.equals(expectedText);
				break;
			}

			if (flag)
				result = "Passed";
		} catch (Exception e) {
			result = "Failed";
		}
	}

	public static String getText(Map<String, String> params) {
		String text = null;
		selectorType = params.get("param1");
		selectorValue = params.get("param2");
		try {
			switch (selectorType) {
			case "name":
				text = driver.findElement(By.name(selectorValue)).getText();
				break;
			case "id":
				text = driver.findElement(By.id(selectorValue)).getText();
				break;
			case "css":
				text = driver.findElement(By.cssSelector(selectorValue)).getText();
				break;
			case "xpath":
				text = driver.findElement(By.xpath(selectorValue)).getText();
				break;
			}

			if (text != null)
				result = "Passed";
		} catch (Exception e) {
			result = "Failed";
		}
		System.err.println("gettext -> '" + text + "'");
		return (text);
	}

	public static String getElementAttribute(Map<String, String> params) {
		selectorType = params.get("param1");
		selectorValue = params.get("param2");
		attributeName = params.get("param3");
		WebElement element = null;
		String value = null;
		try {
			switch (selectorType) {
			case "linkText":
				element = driver.findElement(By.linkText(selectorValue));
				highlight(element);
				break;
			case "partialLinkText":
				element = driver.findElement(By.partialLinkText(selectorValue));
				highlight(element);
				break;
			case "css":
				element = driver.findElement(By.cssSelector(selectorValue));
				highlight(element);
				break;
			case "xpath":
				element = driver.findElement(By.xpath(selectorValue));
				highlight(element);
				break;
			}
			value = element.getAttribute(attributeName);
		} catch (Exception e) {
			result = "Failed";
		}
		System.err.println("getAttribute -> '" + value + "'");
		return (value);
	}

	public static void clickCheckBox(HashMap<String, String> params) {
		WebElement element;
		List<WebElement> elements = new ArrayList<>();
		selectorType = params.get("param1");
		selectorValue = params.get("param2");
		expectedValue = params.get("param3");
		try {
			if (expectedValue.equals("null")) {
				switch (selectorType) {
				case "name":
					element = driver.findElement(By.name(selectorValue));
					highlight(element);
					element.click();
					break;
				case "id":
					element = driver.findElement(By.id(selectorValue));
					highlight(element);
					element.click();
					break;
				case "css":
					element = driver.findElement(By.cssSelector(selectorValue));
					highlight(element);
					element.click();
					break;
				case "xpath":
					element = driver.findElement(By.xpath(selectorValue));
					highlight(element);
					element.click();
					break;
				}
			} else {
				switch (selectorType) {
				case "name":
					elements = driver.findElements(By.name(selectorValue));
					break;
				case "id":
					elements = driver.findElements(By.id(selectorValue));
					break;
				case "css":
					elements = driver.findElements(By.cssSelector(selectorValue));
					break;
				case "xpath":
					elements = driver.findElements(By.xpath(selectorValue));
					break;
				}
				for (WebElement e : elements) {
					if (e.getAttribute("value").equals(expectedValue)) {
						highlight(e);
						e.click();
					}
				}
			}

			result = "Passed";
		} catch (Exception e) {
			result = "Failed";
		}
	}

	public static void clickRadioButton(Map<String, String> params) {
		selectorType = params.get("param1");
		selectorValue = params.get("param2");
		expectedValue = params.get("param3");
		List<WebElement> elements = new ArrayList<>();
		WebElement element;
		try {
			if (expectedValue.equals("null")) {
				switch (selectorType) {
				case "name":
					element = driver
							.findElement(By.name(objectRepo.getProperty(selectorValue)));
					highlight(element);
					element.click();
					break;
				case "id":
					element = driver
							.findElement(By.id(objectRepo.getProperty(selectorValue)));
					highlight(element);
					element.click();
					break;
				case "css":
					element = driver.findElement(
							By.cssSelector(objectRepo.getProperty(selectorValue)));
					highlight(element);
					element.click();
					break;
				case "xpath":
					element = driver
							.findElement(By.xpath(objectRepo.getProperty(selectorValue)));
					highlight(element);
					element.click();
					break;
				}
			} else {
				switch (selectorType) {
				case "name":
					elements = driver
							.findElements(By.name(objectRepo.getProperty(selectorValue)));
					break;
				case "id":
					elements = driver
							.findElements(By.id(objectRepo.getProperty(selectorValue)));
					break;
				case "css":
					elements = driver.findElements(
							By.cssSelector(objectRepo.getProperty(selectorValue)));
					break;
				case "xpath":
					elements = driver
							.findElements(By.xpath(objectRepo.getProperty(selectorValue)));
					break;
				}
				for (WebElement e : elements) {
					if (e.getAttribute("value").equals(expectedValue)) {
						highlight(e);
						e.click();
					}
				}
			}

			result = "Passed";
		} catch (Exception e) {
			result = "Failed";
		}
	}

	public static void switchFrame(Map<String, String> params) {
		param1 = params.get("param1");
		param2 = params.get("param2");
		param3 = params.get("param3");
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
			result = "Passed";
		} catch (Exception e) {
			result = "Failed";
		}
	}

	public static void highlight(WebElement element) {
		highlight(element, 100);
	}

	public static void highlight(WebElement element, long highlight_interval) {
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