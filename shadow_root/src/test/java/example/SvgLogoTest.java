package example;

import static java.lang.System.err;
import static java.lang.System.out;

import java.io.File;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;

import java.io.IOException;
import java.net.MalformedURLException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

/*

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

*/

// https://www.baeldung.com/junit-before-beforeclass-beforeeach-beforeall

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assume;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.JavascriptException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import example.ShadowDriver;
import io.github.bonigarcia.wdm.WebDriverManager;

public class SvgLogoTest extends BaseTest {

	private final static String baseUrl = "https://qna.habr.com/";
	// Element with CSS #js-canvas svg.icon_svg.icon_logo is not present on screen
	// private static final String urlLocator = "#js-canvas a.logo > *[viewBox] >
	// ";
	private static final String urlLocator = "#js-canvas a.logo > *[viewBox]";

	@Before
	public void beforMethod() {
		driver.navigate().to(baseUrl);
		shadowDriver.waitForPageLoaded();
	}

	public int flexibleWait = 10;
	public int implicitWait = 1;
	public int pollingInterval = 500;

	@Test
	public void testShadow() {
		Assume.assumeTrue(super.getBrowser().equals("chrome"));
		WebDriverWait wait = new WebDriverWait(driver, flexibleWait);
		WebElement element = wait.until(ExpectedConditions
				.visibilityOf(driver.findElement(By.cssSelector(urlLocator))));
		System.err.println(element.getAttribute("outerHTML"));
		shadowDriver.setDebug(true);
		WebElement element2 = shadowDriver.getShadowElement(element, "*");
		System.err.println(element2.getAttribute("outerHTML"));
		List<WebElement> elements = shadowDriver.getAllShadowElement(element, "*");
		assertThat(elements, notNullValue());
		assertThat(elements.size(), greaterThan(0));
		err.println(String.format("Located %d svg elements:", elements.size()));
		elements.stream()
				.map(o -> String.format("outerHTML: %s", o.getAttribute("outerHTML")))
				.forEach(err::println);
	}

	@Test
	public void testNoShadow() {
		Assume.assumeTrue(super.getBrowser().equals("firefox"));
		WebDriverWait wait = new WebDriverWait(driver, flexibleWait);
		WebElement element = wait.until(ExpectedConditions
				.visibilityOf(driver.findElement(By.cssSelector(urlLocator))));
		System.err.println(element.getAttribute("outerHTML"));
		shadowDriver.setDebug(true);
		WebElement element2 = shadowDriver.getShadowElement(element, "*");
		System.err.println(element2.getAttribute("outerHTML"));
		List<WebElement> elements = shadowDriver.getAllShadowElement(element, "*");
		assertThat(elements, notNullValue());
		assertThat(elements.size(), greaterThan(0));
		err.println(String.format("Located %d svg elements:", elements.size()));
		elements.stream()
				.map(o -> String.format("outerHTML: %s", o.getAttribute("outerHTML")))
				.forEach(err::println);
	}
}
