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
import org.utils.Utils;

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

	public static int scriptTimeout = 5;
	public static int flexibleWait = 120;
	public static int implicitWait = 1;
	public static long pollingInterval = 500;

	private static HashMap<String, String> parameterTable = new HashMap<>(); // empty
	private static Map<String, String> methodTable = new HashMap<>();
	static {
		methodTable.put("CREATE_BROWSER", "openBrowser");
		methodTable.put("GOTOURL", "navigateTo");
		methodTable.put("SETTEXT", "enterText");
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

	public static void closeBrowser(String param1, String param2, String param3,
			HashMap<String, String> parameterTable) {
		driver.quit();
	}

	public static void navigateTo(String param1, String param2, String param3,
			HashMap<String, String> parameterTable) {
		String url = parameterTable.get("param1");
		System.err.println("Navigate to: " + url);
		driver.navigate().to(url);
	}

	public static void callMethod(String method, String param1, String param2,
			String param3, HashMap<String, String> parameterTable) {
		System.out.println("calling method for: " + method);
		if (methodTable.containsKey(method)) {
			String methodName = methodTable.get(method);
			try {
				Class<?> _class = Class.forName("org.utils.KeywordLibrary");
				// System.out.println("getting method: " + methodName);
				Method _method = _class.getMethod(methodName, String.class,
						String.class, String.class, HashMap.class);
				// System.out.println("Invoking method: " + methodName + " with " +
				// param1
				// + "," + param2 + "," + param3 + "," + paramJS);
				_method.invoke(null, param1, param2, param3, parameterTable);

			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("No method for: " + method);

		}
	}

	public static void loadProperties() throws IOException {
		File file = new File("ObjectRepo.Properties");
		objectRepo = new Properties();
		objectRepo.load(new FileInputStream(file));
	}

	public static void openBrowser(String selectorType, String selectorValue,
			String textData, HashMap<String, String> parameterTable)
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

	public static void enterText(String selectorType, String selectorValue,
			String textData, HashMap<String, String> parameterTable) {
		selectorType = parameterTable.get("param1");
		selectorValue = parameterTable.get("param2");
		textData = parameterTable.get("param3");
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

	public static void clickButton(String selectorType, String selectorValue,
			String param3, HashMap<String, String> parameterTable) {
		selectorType = parameterTable.get("param1");
		selectorValue = parameterTable.get("param2");
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

	public static void clickLink(String selectorType, String selectorValue,
			String param3, HashMap<String, String> parameterTable) {
		selectorType = parameterTable.get("param1");
		selectorValue = parameterTable.get("param2");
		System.err.println("Locate via " + selectorType + " " + selectorValue);
		WebElement element;
		try {
			switch (selectorType) {
			case "linkText":
				//
				driver.findElement(By.linkText(selectorValue)).click();
				break;
			case "partialLinkText":
				driver.findElement(By.partialLinkText(selectorValue)).click();
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
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// System.err.println("Ignored: " + e.toString());
		}

	}

	public static void selectDropDown(String selectorType, String selectorValue,
			String visibleText, HashMap<String, String> parameterTable) {
		selectorType = parameterTable.get("param1");
		selectorValue = parameterTable.get("param2");
		visibleText = parameterTable.get("param3");
		try {
			switch (selectorType) {
			case "name":
				Select select_name = new Select(
						driver.findElement(By.name(selectorValue)));
				select_name.selectByVisibleText(visibleText);
				break;
			case "id":
				Select select_id = new Select(driver.findElement(By.id(selectorValue)));
				select_id.selectByVisibleText(visibleText);
				break;
			case "css":
				Select select_css = new Select(
						driver.findElement(By.cssSelector(selectorValue)));
				select_css.selectByVisibleText(visibleText);
				break;
			case "xpath":
				Select select_xpath = new Select(
						driver.findElement(By.xpath(selectorValue)));
				select_xpath.selectByVisibleText(visibleText);
				break;
			}
			result = "Passed";
		} catch (Exception e) {
			result = "Failed";
		}
	}

	public static void verifyText(String selectorType, String selectorValue,
			String expectedText, HashMap<String, String> parameterTable) {
		boolean flag = false;
		selectorType = parameterTable.get("param1");
		selectorValue = parameterTable.get("param2");
		expectedText = parameterTable.get("param3");
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
				flag = driver
						.findElement(By.cssSelector(objectRepo.getProperty(selectorValue)))
						.getText().equals(expectedText);
				break;
			case "xpath":
				flag = driver
						.findElement(By.xpath(objectRepo.getProperty(selectorValue)))
						.getText().equals(expectedText);
				break;
			}

			if (flag)
				result = "Passed";
		} catch (Exception e) {
			result = "Failed";
		}
	}

	public static void clickCheckBox(String selectorType, String selectorValue,
			String expectedValue, HashMap<String, String> parameterTable) {
		selectorType = parameterTable.get("param1");
		selectorValue = parameterTable.get("param2");
		expectedValue = parameterTable.get("param3");
		try {
			if (expectedValue.equals("null")) {
				switch (selectorType) {
				case "name":
					driver.findElement(By.name(objectRepo.getProperty(selectorValue)))
							.click();
					break;
				case "id":
					driver.findElement(By.id(objectRepo.getProperty(selectorValue)))
							.click();
					break;
				case "css":
					driver
							.findElement(
									By.cssSelector(objectRepo.getProperty(selectorValue)))
							.click();
					break;
				case "xpath":
					driver.findElement(By.xpath(objectRepo.getProperty(selectorValue)))
							.click();
					break;
				}
			} else {
				List<WebElement> elements = new ArrayList<>();
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
				for (WebElement element : elements) {
					if (element.getAttribute("value").equals(expectedValue)) {
						element.click();
					}
				}
			}

			result = "Passed";
		} catch (Exception e) {
			result = "Failed";
		}
	}

	public static void clickRadioButton(String selectorType, String selectorValue,
			String expectedValue, HashMap<String, String> parameterTable) {
		selectorType = parameterTable.get("param1");
		selectorValue = parameterTable.get("param2");
		expectedValue = parameterTable.get("param3");
		try {
			if (expectedValue.equals("null")) {
				switch (selectorType) {
				case "name":
					driver.findElement(By.name(objectRepo.getProperty(selectorValue)))
							.click();
					break;
				case "id":
					driver.findElement(By.id(objectRepo.getProperty(selectorValue)))
							.click();
					break;
				case "css":
					driver
							.findElement(
									By.cssSelector(objectRepo.getProperty(selectorValue)))
							.click();
					break;
				case "xpath":
					driver.findElement(By.xpath(objectRepo.getProperty(selectorValue)))
							.click();
					break;
				}
			} else {
				List<WebElement> elements = new ArrayList<>();
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
				for (WebElement element : elements) {
					if (element.getAttribute("value").equals(expectedValue)) {
						element.click();
					}
				}
			}

			result = "Passed";
		} catch (Exception e) {
			result = "Failed";
		}
	}

	public static void switchFrame(String param1, String param2, String param3,
			HashMap<String, String> parameterTable) {
		param1 = parameterTable.get("param1");
		param2 = parameterTable.get("param2");
		param3 = parameterTable.get("param3");
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