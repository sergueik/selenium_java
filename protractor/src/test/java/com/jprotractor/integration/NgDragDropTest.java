package com.jprotractor.integration;

import org.apache.commons.lang.exception.ExceptionUtils;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.io.IOException;
import static java.lang.Boolean.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Formatter;
import java.util.Hashtable;
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
import org.junit.Ignore;

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
import org.openqa.selenium.Point;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;

import com.jprotractor.NgBy;
import com.jprotractor.NgWebDriver;
import com.jprotractor.NgWebElement;

/**
 * Kanban Board Drag and Drop tests for
 * https://a5hik.github.io/ng-sortable/#/kanban
 * 
 * @author Serguei Kouzmine (kouzmine_serguei@yahoo.com)
 */

public class NgDragDropTest {
	private static NgWebDriver ngDriver;
	private static WebDriver seleniumDriver;
	static WebDriverWait wait;
	static Actions actions;
	static Alert alert;
	static int implicitWait = 10;
	static int flexibleWait = 5;
	static long pollingInterval = 500;
	static int width = 1000;
	static int height = 600;
	static boolean isCIBuild = false;
	public static String localFile;
	static StringBuilder sb;
	static Formatter formatter;
	private static String fullStackTrace;
	public static String baseUrl = "https://a5hik.github.io/ng-sortable/#/kanban";

	@BeforeClass
	public static void setup() throws IOException {
		sb = new StringBuilder();
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

	@Before
	public void beforeEach() throws InterruptedException {
		ngDriver.navigate().to(baseUrl);
	}

	@AfterClass
	public static void teardown() {
		ngDriver.close();
		seleniumDriver.quit();
	}

	// @Ignore
	@Test
	public void testDragAndDrop() throws InterruptedException, Exception {

		NgWebElement ng_board = ngDriver.findElement(NgBy.model("kanbanBoard"));

		assertThat(ng_board, notNullValue());
		highlight(ng_board);
		WebElement column3 = ng_board.findElement(By.id("column3"));
		assertThat(column3, notNullValue());
		highlight(column3);
		NgWebElement ng_column3 = new NgWebElement(ngDriver, column3);
		// alternative search
		Enumeration<WebElement> elements = Collections.enumeration(ngDriver
				.findElements(NgBy.repeater("column in kanbanBoard.columns")));
		while (elements.hasMoreElements()) {
			WebElement element = elements.nextElement();
			System.err.println("id: " + element.getAttribute("id"));
			if (element.getAttribute("id").equalsIgnoreCase("column3")) {
				System.err.println("id: " + element.getAttribute("id") + " found");
				ng_column3 = new NgWebElement(ngDriver, element);
				highlight(element);
			}
		}

		Enumeration<WebElement> cards = Collections.enumeration(ng_column3
				.findElement(NgBy.model("column.cards")).findElements(
						NgBy.repeater("card in column.cards")));

		WebElement source_card = null;
		WebElement target_card = null;

		while (cards.hasMoreElements()) {
			WebElement card = cards.nextElement();
			if (card.getText().isEmpty()) {
				break;
			}
			if (card.getText().equalsIgnoreCase("Test user module")) {
				System.err.println("source card: \"" + card.getText() + "\" found");
				source_card = card;
			}
			if (card.getText().equalsIgnoreCase("CI for user module")) {
				System.err.println("target card: \"" + card.getText() + "\" found");
				target_card = card;
			}
		}

		highlight(source_card);
		Point source_card_location = source_card.getLocation();
		highlight(target_card);
		Point target_card_location = target_card.getLocation();
		actions.moveToElement(source_card).build().perform();
		// does not appear to work
		actions
				.dragAndDropBy(source_card, 0,
						target_card_location.y - source_card_location.y).build().perform();
		Thread.sleep(1000);
		// works
		actions.clickAndHold(source_card).moveToElement(target_card).release()
				.build().perform();
		ngDriver.waitForAngular();
		Thread.sleep(1000);
	}

	private static void highlight(WebElement element) throws InterruptedException {
		CommonFunctions.highlight(element);
	}

}