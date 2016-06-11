package com.jprotractor.integration;

import org.apache.commons.lang.exception.ExceptionUtils;
import static org.hamcrest.CoreMatchers.*;
import org.hamcrest.MatcherAssert;
// import static com.jcabi.matchers.RegexMatchers;
import static com.jcabi.matchers.RegexMatchers.*;
import static org.junit.Assert.*;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Formatter;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.experimental.categories.Category;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.junit.Test;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.internal.ProfilesIni;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.Platform;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;

import com.jprotractor.NgBy;
import com.jprotractor.NgWebDriver;
import com.jprotractor.NgWebElement;

/**
 * Local file Integration tests
 * 
 * @author Serguei Kouzmine (kouzmine_serguei@yahoo.com)
 */

public class NgSvgTest {
	private static NgWebDriver ngDriver;
	private static WebDriver seleniumDriver;
	static WebDriverWait wait;
	static Actions actions;
	static Alert alert;
	static boolean state = false;
	static int implicitWait = 10;
	static int flexibleWait = 5;
	static long pollingInterval = 500;
	static int width = 600;
	static int height = 400;
	// set to true for Desktop, false for headless browser testing
	static boolean isCIBuild = false;
	static StringBuilder sb;
	static Formatter formatter;
	public static String localFile;

	@BeforeClass
	public static void setup() throws IOException {
		sb = new StringBuilder();
		// Send all output to the Appendable object sb
		formatter = new Formatter(sb, Locale.US);
		isCIBuild = CommonFunctions.checkEnvironment();
		seleniumDriver = CommonFunctions.getSeleniumDriver();
		seleniumDriver.manage().window().setSize(new Dimension(width, height));
		seleniumDriver.manage().timeouts().pageLoadTimeout(50, TimeUnit.SECONDS)
				.implicitlyWait(implicitWait, TimeUnit.SECONDS)
				.setScriptTimeout(10, TimeUnit.SECONDS);
		wait = new WebDriverWait(seleniumDriver, flexibleWait);
		wait.pollingEvery(pollingInterval, TimeUnit.MILLISECONDS);
		actions = new Actions(seleniumDriver);
		ngDriver = new NgWebDriver(seleniumDriver);
	}

	@Test
	public void testCircles() throws Exception {
		// if (isCIBuild) { // Alert not handled by PhantomJS
		// return;
		// }
		getPageContent("ng_svg_ex1.htm");
		Enumeration<WebElement> circles = Collections.enumeration(ngDriver
				.findElements(NgBy.repeater("circle in circles")));
		while (circles.hasMoreElements()) {
			WebElement circle = circles.nextElement();
			// if (circle.getText().isEmpty()){
			// break;
			// }
			Object x = new NgWebElement(ngDriver, circle).evaluate("circle.x");
			Object y = new NgWebElement(ngDriver, circle).evaluate("circle.y");
			Object r = new NgWebElement(ngDriver, circle).evaluate("circle.r");

			highlight(circle);
			formatter.format("x = %1$2d y = %2$2d r = %3$2d\n", x, y, r);

			formatter.format("Location: x = %3d", circle.getLocation().x);
			formatter.format(" y = %3d", circle.getLocation().y);
			System.err.println(sb.toString());
			sb.setLength(0);
			try {
				WebElement element = seleniumDriver.findElement(By
						.xpath(xpath_of(circle)));
				System.err.println("Located by xpath " + xpath_of(circle));
				formatter.format("Location: x = %3d", element.getLocation().x);
				formatter.format(" y = %3d", element.getLocation().y);
				System.err.println(sb.toString());
				sb.setLength(0);
				highlight(element);
			} catch (NoSuchElementException ex) {
				// at <anonymous class>.FirefoxDriver.prototype.findElementInternal_
				System.err.println("Cannot locate by xpath: " + xpath_of(circle));
			}
			try {
				WebElement element = seleniumDriver.findElement(By
						.cssSelector(css_selector_of(circle)));
				System.err.println("Located by cssSelector " + css_selector_of(circle));
				highlight(element);
				System.err.println("innerHTML:" + element.getAttribute("innerHTML"));
				// WebDriverException: cannot forward the request Software caused
				// connection abort
				System.err.println("Fill: "
						+ CommonFunctions.executeScript(
								"return arguments[0].getAttribute('fill')", element));
			} catch (NoSuchElementException ex) {
				System.err.println("Cannot locate by cssSelector: "
						+ css_selector_of(circle));
			}
		}
		Thread.sleep(1000);
	}

	@AfterClass
	public static void teardown() {
		ngDriver.close();
		seleniumDriver.quit();
	}

	private static void getPageContent(String pagename)
			throws InterruptedException {
		String baseUrl = CommonFunctions.getPageContent(pagename);
		ngDriver.navigate().to(baseUrl);
		Thread.sleep(500);
	}

	private static void highlight(WebElement element) throws InterruptedException {
		CommonFunctions.highlight(element);
	}

	private static String getIdentity(WebElement element)
			throws InterruptedException {
		String script = "return angular.identity(angular.element(arguments[0])).html();";
		// returns too little HTML information in Java
		return CommonFunctions.executeScript(script, element).toString();
	}

	private static String xpath_of(WebElement element) {
		String script = "function get_xpath_of(element) {\n"
				+ " var elementTagName = element.tagName.toLowerCase();\n"
				+ "     if (element.id != '') {\n"
				+ "         return '//' + elementTagName + '[@id=\"' + element.id + '\"]';\n"
				+ "     } else if (element.name && document.getElementsByName(element.name).length === 1) {\n"
				+ "         return '//' + elementTagName + '[@name=\"' + element.name + '\"]';\n"
				+ "     }\n"
				+ "     if (element === document.body) {\n"
				+ "         return '/html/' + elementTagName;\n"
				+ "     }\n"
				+ "     var sibling_count = 0;\n"
				+ "     var siblings = element.parentNode.childNodes;\n"
				+ "     siblings_length = siblings.length;\n"
				+ "     for (cnt = 0; cnt < siblings_length; cnt++) {\n"
				+ "         var sibling_element = siblings[cnt];\n"
				+ "         if (sibling_element.nodeType !== 1) { // not ELEMENT_NODE\n"
				+ "             continue;\n"
				+ "         }\n"
				+ "         if (sibling_element === element) {\n"
				+ "             return sibling_count > 0 ? get_xpath_of(element.parentNode) + '/*[name() = \"'+ elementTagName+ '\"]' + '[' + (sibling_count + 1) + ']' : get_xpath_of(element.parentNode) + '/*[name() = \"'+ elementTagName+ '\"]' ;\n"
				+ "         }\n"
				+ "         if (sibling_element.nodeType === 1 && sibling_element.tagName.toLowerCase() === elementTagName) {\n"
				+ "             sibling_count++;\n" + "         }\n" + "     }\n"
				+ "     return;\n" + " };\n" + " return get_xpath_of(arguments[0]);\n";
		return (String) execute_script(script, element);
	}

	private static String css_selector_of(WebElement element) {
		String script = "function get_css_selector_of(element) {\n"
				+ "if (!(element instanceof Element))\n"
				+ "return;\n"
				+ "var path = [];\n"
				+ "while (element.nodeType === Node.ELEMENT_NODE) {\n"
				+ "var selector = element.nodeName.toLowerCase();\n"
				+ "if (element.id) {\n"
				+ "if (element.id.indexOf('-') > -1) {\n"
				+ "selector += '[id = \"' + element.id + '\"]';\n"
				+ "} else {\n"
				+ "selector += '#' + element.id;\n"
				+ "}\n"
				+ "path.unshift(selector);\n"
				+ "break;\n"
				+ "} else {\n"
				+ "var element_sibling = element;\n"
				+ "var sibling_cnt = 1;\n"
				+ "while (element_sibling = element_sibling.previousElementSibling) {\n"
				+ "if (element_sibling.nodeName.toLowerCase() == selector)\n"
				+ "sibling_cnt++;\n" + "}\n" + "if (sibling_cnt != 1)\n"
				+ "selector += ':nth-of-type(' + sibling_cnt + ')';\n" + "}\n"
				+ "path.unshift(selector);\n" + "element = element.parentNode;\n"
				+ "}\n" + "return path.join(' > ');\n" + "} \n"
				+ "return get_css_selector_of(arguments[0]);\n";
		return (String) execute_script(script, element);

	}

	// http://www.programcreek.com/java-api-examples/index.php?api=org.openqa.selenium.JavascriptExecutor
	public static Object execute_script(String script, Object... args) {
		if (seleniumDriver instanceof JavascriptExecutor) {
			JavascriptExecutor javascriptExecutor = (JavascriptExecutor) seleniumDriver;
			return javascriptExecutor.executeScript(script, args);
		} else {
			throw new RuntimeException(
					"Script execution is only available for WebDrivers that implement "
							+ "the JavascriptExecutor interface.");
		}
	}

}
