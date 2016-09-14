package com.mycompany.app;

import java.awt.Toolkit;

import java.io.IOException;
import java.io.InputStream;
import java.io.IOException;

import java.lang.RuntimeException;

import java.net.URLDecoder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Formatter;
import java.util.Formatter;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import java.util.concurrent.TimeUnit;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.exception.ExceptionUtils;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.experimental.categories.Category;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.junit.Test;

import org.junit.experimental.categories.Category;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.Platform;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.internal.ProfilesIni;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import static java.lang.Boolean.*;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

public class AppTest {

	private static WebDriver driver;
	private static WebDriver frame;
	public static WebDriverWait wait;
	public static Actions actions;
	private WebElement element = null;
	private String selector = null;
	private static long implicitWait = 3;
	private static int flexibleWait = 5;
	private static long polling = 500;
	private static long highlight = 100;
	private static long afterTest = 10000;
	private static String baseURL = "https://ya.ru/";
  
	private static final StringBuffer verificationErrors = new StringBuffer();
	private final String username = "";
	private final String password = "";

	@BeforeClass
	public static void setUp() throws Exception {
		driver = new FirefoxDriver();
		wait = new WebDriverWait(driver, flexibleWait);
		wait.pollingEvery(polling, TimeUnit.MILLISECONDS);
		driver.manage().timeouts().implicitlyWait(implicitWait, TimeUnit.SECONDS);
		driver.get(baseURL);
	}

	@Before
	public void beforeTest() throws InterruptedException {
		driver.get(baseURL);
		WebElement mail_link_element = driver
				.findElement(By
						.cssSelector("table.layout__table tr.layout__header div.personal div.b-inline"));
		highlight(mail_link_element);
		mail_link_element.click();
	}

	@After
	public void afterTest() {
	}

	@AfterClass
	public static void tearDown() throws Exception {
		Thread.sleep(afterTest);
		driver.close();
		driver.quit();
		if (verificationErrors.length() != 0) {
			throw new Exception(verificationErrors.toString());
		}
	}

	@Test
	public void getCookieTest() throws Exception {
		WebElement username_element = driver.findElement(By
				.xpath("//form/div[1]/label/span/input"));
		highlight(username_element);
		username_element.clear();
		username_element.sendKeys(username);
		WebElement username_login_element = driver.findElement(By
				.cssSelector("form.new-auth-form input[name='login']"));
		System.err.println("Username: "
				+ username_login_element.getAttribute("value"));
		WebElement password_element = driver.findElement(By
				.xpath("//form/div[2]/label/span/input"));
		highlight(password_element);
		password_element.clear();
		password_element.sendKeys(password);
		WebElement password_login_element = driver.findElement(By
				.cssSelector("form.new-auth-form input[name='passwd']"));
		System.err.println("Password: "
				+ password_login_element.getAttribute("value"));
		WebElement login_link_element = driver.findElement(By
				.cssSelector("form.new-auth-form span.new-auth-submit a.nb-button"));
		String login_href = login_link_element.getAttribute("href");
		System.err.println("Login href: " + login_href);

		Pattern pattern = Pattern.compile("https://passport.yandex.ru/auth/\\?mode=qr&retpath=(.+)$");
		Matcher matcher = pattern.matcher(login_href);
		String retpath = null;
		if (matcher.find()) {
			retpath = java.net.URLDecoder
					.decode(matcher.group(1).toString(), "UTF-8");
		}
		System.err.println("Login retpath: " + retpath);
		WebElement login_button_element = driver.findElement(By
				.cssSelector("form.new-auth-form span.new-auth-submit button"));
		highlight(login_button_element);
		login_button_element.click();
		wait.until(ExpectedConditions.urlContains(retpath));

		String currentURL = driver.getCurrentUrl();
		System.out.println("Page url: " + currentURL);
		// https://seleniumhq.github.io/selenium/docs/api/java/org/openqa/selenium/support/ui/ExpectedConditions.html
		try {
			new WebDriverWait(driver, 60).until(ExpectedConditions
					.visibilityOfElementLocated(By
							.cssSelector("div.mail-App-Header div.mail-User")));
		} catch (Exception e) { // TimeoutException
			System.err.println(e.toString());
			// ignore
		}

		Set<Cookie> cookies = driver.manage().getCookies();
		System.err.println("Cookies:");
		for (Cookie cookie : cookies) {
			System.err.println("name: " + cookie.getName());
			System.err.println("value: " + cookie.getValue());
			System.err.println("domain: " + cookie.getDomain());
			System.err.println("path: " + cookie.getPath());
		}

		WebElement user_element = driver.findElement(By
				.cssSelector("div.mail-App-Header div.mail-User div.mail-User-Name"));
		highlight(user_element);
		user_element.click();

		WebElement logout_element = driver
				.findElement(By
						.cssSelector("body.mail-Page-Body div.ui-dialog div._nb-popup-content div.b-user-dropdown-content-with-exit div.b-mail-dropdown__item a.ns-action"));
		highlight(logout_element);
		logout_element.click();

		WebElement confirm_logout_element = driver.findElement(By
				.xpath("//div[5]/div[2]/table/tbody/tr/td/div[3]/div/a"));
    String logout_href = confirm_logout_element.getAttribute("href");
    System.err.println("Logout href: " + logout_href );    
		highlight(confirm_logout_element);
		confirm_logout_element.click();
    retpath = null;
    pattern = Pattern.compile("https://passport.yandex.ru/passport?\\?mode=.+&retpath=(.+)$");  

    matcher = pattern.matcher(logout_href);
		if (matcher.find()) {
			retpath = java.net.URLDecoder
					.decode(matcher.group(1).toString(), "UTF-8");
		}    
    System.err.println("Logout relpath: " + retpath );    
		// NOTE: do not wait for retpath
    String finalUrl = "https://www.yandex.ru/";
		wait.until(ExpectedConditions.urlContains(finalUrl));
	}

	private void highlight(WebElement element) throws InterruptedException {
		highlight(element, highlight);
	}

	private void highlight(WebElement element, long highlight)
			throws InterruptedException {
		wait.until(ExpectedConditions.visibilityOf(element));
		executeScript("arguments[0].style.border='3px solid yellow'", element);
		Thread.sleep(highlight);
		executeScript("arguments[0].style.border=''", element);
	}

	private Object executeScript(String script, Object... arguments) {
		if (driver instanceof JavascriptExecutor) {
			JavascriptExecutor javascriptExecutor = JavascriptExecutor.class
					.cast(driver);
			return javascriptExecutor.executeScript(script, arguments);
		} else {
			throw new RuntimeException("Script execution failed.");
		}
	}
}
