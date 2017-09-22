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

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

import org.utils.Utils;

public class KeywordLibrary {

	static WebDriver driver;
	static Properties objectRepo;
	static String result;
	private static String selectorType = null;
	private static String selectorValue = null;
	private static String expectedValue = null;
	private static String textdata = null;
	private static String visibleText = null;
	private static String expectedText = null;

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
	}

	public static void navigateTo(String param1, String param2, String param3,
			String paramJS) {
		Utils.readData(paramJS, Optional.of(parameterTable));
		// driver.navigate().to(parameterTable.get("url"));
		driver.navigate().to(param1);
	}

	public static void callMethod(String method, String param1, String param2,
			String param3, String paramJS) {
		try {
			Class<?> _class = Class.forName("org.utils.KeywordLibrary");
			Method _method = _class.getMethod(methodTable.get(method), String.class,
					String.class, String.class, String.class);
			_method.invoke(null, param1, param2, param3, paramJS);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void loadProperties() throws IOException {
		File file = new File("ObjectRepo.Properties");
		objectRepo = new Properties();
		objectRepo.load(new FileInputStream(file));
	}

	public static void openBrowser() throws IOException {
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

	public static void enterText(String param1, String param2, String param3,
			String paramJS) {
		Utils.readData(paramJS, Optional.of(parameterTable));
		// selectorType = parameterTable.get("selectorType"));
		// selectorValue = parameterTable.get("selectorValue"));
		// textdata = parameterTable.get("textdata"));
		try {
			switch (param1) {
			case "name":
				driver.findElement(By.name(objectRepo.getProperty(param2)))
						.sendKeys(param3);
				break;
			case "id":
				driver.findElement(By.id(objectRepo.getProperty(param2)))
						.sendKeys(param3);
				break;
			case "css":
				driver.findElement(By.cssSelector(objectRepo.getProperty(param2)))
						.sendKeys(param3);
				break;
			case "xpath":
				driver.findElement(By.xpath(objectRepo.getProperty(param2)))
						.sendKeys(param3);
				;
			}
			result = "Passed";
		} catch (Exception e) {
			result = "Failed";
		}
	}

	public static void clickButton(String param1, String param2, String param3,
			String paramJS) {
		Utils.readData(paramJS, Optional.of(parameterTable));
		// selectorType = parameterTable.get("selectorType"));
		// selectorValue = parameterTable.get("selectorValue"));
		try {
			switch (param1) {
			case "name":
				driver.findElement(By.name(objectRepo.getProperty(param2))).click();
				break;
			case "id":
				driver.findElement(By.id(objectRepo.getProperty(param2))).click();
				break;
			case "css":
				driver.findElement(By.cssSelector(objectRepo.getProperty(param2)))
						.click();
				break;
			case "xpath":
				driver.findElement(By.xpath(objectRepo.getProperty(param2))).click();
				;
			}
			result = "Passed";
		} catch (Exception e) {
			result = "Failed";
		}
	}

	public static void clickLink(String param1, String param2, String param3,
			String paramJS) {
		Utils.readData(paramJS, Optional.of(parameterTable));
		// selectorType = parameterTable.get("selectorType"));
		// selectorValue = parameterTable.get("selectorValue"));
		try {
			switch (param1) {
			case "linkText":
				driver.findElement(By.linkText(objectRepo.getProperty(param2))).click();
				break;
			case "partialLinkText":
				driver.findElement(By.partialLinkText(objectRepo.getProperty(param2)))
						.click();
				break;
			case "css":
				driver.findElement(By.cssSelector(objectRepo.getProperty(param2)))
						.click();
				break;
			case "xpath":
				driver.findElement(By.xpath(objectRepo.getProperty(param2))).click();
				;
			}
			result = "Passed";
		} catch (Exception e) {
			result = "Failed";
		}
	}

	public static void selectDropDown(String param1, String param2, String param3,
			String paramJS) {
		Utils.readData(paramJS, Optional.of(parameterTable));
		// selectorType = parameterTable.get("selectorType"));
		// selectorValue = parameterTable.get("selectorValue"));
		// visibleText = parameterTable.get("visibleText"));
		try {
			switch (param1) {
			case "name":
				Select select_name = new Select(
						driver.findElement(By.name(objectRepo.getProperty(param2))));
				select_name.selectByVisibleText(param3);
				break;
			case "id":
				Select select_id = new Select(
						driver.findElement(By.id(objectRepo.getProperty(param2))));
				select_id.selectByVisibleText(param3);
				break;
			case "css":
				Select select_css = new Select(
						driver.findElement(By.cssSelector(objectRepo.getProperty(param2))));
				select_css.selectByVisibleText(param3);
				break;
			case "xpath":
				Select select_xpath = new Select(
						driver.findElement(By.xpath(objectRepo.getProperty(param2))));
				select_xpath.selectByVisibleText(param3);
				break;
			}
			result = "Passed";
		} catch (Exception e) {
			result = "Failed";
		}
	}

	public static void verifyText(String param1, String param2, String param3,
			String paramJS) {
		boolean flag = false;
		Utils.readData(paramJS, Optional.of(parameterTable));
		// selectorType = parameterTable.get("selectorType"));
		// selectorValue = parameterTable.get("selectorValue"));
		// expectedText = parameterTable.get("expectedText"));
		try {
			switch (param1) {
			case "name":
				flag = driver.findElement(By.name(objectRepo.getProperty(param2)))
						.getText().equals(param3);
				break;
			case "id":
				flag = driver.findElement(By.id(objectRepo.getProperty(param2)))
						.getText().equals(param3);
				break;
			case "css":
				flag = driver
						.findElement(By.cssSelector(objectRepo.getProperty(param2)))
						.getText().equals(param3);
				break;
			case "xpath":
				flag = driver.findElement(By.xpath(objectRepo.getProperty(param2)))
						.getText().equals(param3);
				break;
			}

			if (flag)
				result = "Passed";
		} catch (Exception e) {
			result = "Failed";
		}
	}

	public static void clickCheckBox(String param1, String param2, String param3,
			String paramJS) {
		Utils.readData(paramJS, Optional.of(parameterTable));
		// selectorType = parameterTable.get("selectorType"));
		// selectorValue = parameterTable.get("selectorValue"));
		try {
			if (param3.equals("null")) {
				switch (param1) {
				case "name":
					driver.findElement(By.name(objectRepo.getProperty(param2))).click();
					break;
				case "id":
					driver.findElement(By.id(objectRepo.getProperty(param2))).click();
					break;
				case "css":
					driver.findElement(By.cssSelector(objectRepo.getProperty(param2)))
							.click();
					break;
				case "xpath":
					driver.findElement(By.xpath(objectRepo.getProperty(param2))).click();
					break;
				}
			} else {
				List<WebElement> elements = new ArrayList<>();
				switch (param1) {
				case "name":
					elements = driver
							.findElements(By.name(objectRepo.getProperty(param2)));
					break;
				case "id":
					elements = driver.findElements(By.id(objectRepo.getProperty(param2)));
					break;
				case "css":
					elements = driver
							.findElements(By.cssSelector(objectRepo.getProperty(param2)));
					break;
				case "xpath":
					elements = driver
							.findElements(By.xpath(objectRepo.getProperty(param2)));
					break;
				}
				for (WebElement element : elements) {
					if (element.getAttribute("value").equals(param3)) {
						element.click();
					}
				}
			}

			result = "Passed";
		} catch (Exception e) {
			result = "Failed";
		}

	}

	public static void clickRadioButton(String param1, String param2,
			String param3, String paramJS) {
		Utils.readData(paramJS, Optional.of(parameterTable));
		// selectorType = parameterTable.get("selectorType"));
		// selectorValue = parameterTable.get("selectorValue"));
		// expectedValue = parameterTable.get("expectedValue"));
		try {
			if (param3.equals("null")) {
				switch (param1) {
				case "name":
					driver.findElement(By.name(objectRepo.getProperty(param2))).click();
					break;
				case "id":
					driver.findElement(By.id(objectRepo.getProperty(param2))).click();
					break;
				case "css":
					driver.findElement(By.cssSelector(objectRepo.getProperty(param2)))
							.click();
					break;
				case "xpath":
					driver.findElement(By.xpath(objectRepo.getProperty(param2))).click();
					break;
				}
			} else {
				List<WebElement> elements = new ArrayList<>();
				switch (param1) {
				case "name":
					elements = driver
							.findElements(By.name(objectRepo.getProperty(param2)));
					break;
				case "id":
					elements = driver.findElements(By.id(objectRepo.getProperty(param2)));
					break;
				case "css":
					elements = driver
							.findElements(By.cssSelector(objectRepo.getProperty(param2)));
					break;
				case "xpath":
					elements = driver
							.findElements(By.xpath(objectRepo.getProperty(param2)));
					break;
				}
				for (WebElement element : elements) {
					if (element.getAttribute("value").equals(param3)) {
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
			String paramJS) {
		Utils.readData(paramJS, Optional.of(parameterTable));
		// TODO: cleanup wrong code
		// param1 = parameterTable.get("frameName"));
		// param2 = parameterTable.get("selectorValue"));
		// param3 = parameterTable.get("expectedValue"));
		try {
			switch (param1) {
			case "name":
				driver.switchTo().frame(objectRepo.getProperty(param2));
				break;
			case "id":
				driver.findElement(By.id(objectRepo.getProperty(param2))).click();
				break;
			case "css":
				driver.findElement(By.cssSelector(objectRepo.getProperty(param2)))
						.click();
				System.out.println(
						driver.findElement(By.cssSelector(objectRepo.getProperty(param2)))
								.getText());
				break;
			case "xpath":
				driver.findElement(By.xpath(objectRepo.getProperty(param2))).click();
				System.out.println(driver
						.findElement(By.xpath(objectRepo.getProperty(param2))).getText());
				break;
			}
			result = "Passed";
		} catch (Exception e) {
			result = "Failed";
		}

	}
}
