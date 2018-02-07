package com.mycompany.app;

import java.io.File;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import java.math.RoundingMode;
import java.util.Calendar;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.experimental.categories.Category;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.junit.runners.model.FrameworkMethod;
import org.junit.Test;
import org.junit.Ignore;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.Platform;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.internal.ProfilesIni;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;

@RunWith(DataProviderRunner.class)
public class AppTest {

	private static String emptyString = null;
	private static String notEmptyString = null;

	@BeforeClass
	public static void setup() {
		emptyString = "";
		notEmptyString = "notEmpty";
	}

	@DataProvider
	public static Object[][] dataProviderStringIsNullOrEmpty() {
		// @formatter:off
		return new Object[][] { { null }, { "" } /*, { "non-empty" } */ };
		// @formatter:on
	}

	@Test
	@UseDataProvider("dataProviderStringIsNullOrEmpty")
	public void testIsEmptyString(String input) {
		// Given:
		System.err.println(String.format("Test: input = %s", input));
		// When:
		// Then:
		assertTrue((input == null) ? true : input.isEmpty());
	}

	@DataProvider
	public static Object[][] dataProviderNotNullStringsSetInBeforeClass() {
		// @formatter:off
		return new Object[][] { { emptyString }, { notEmptyString }, };
		// @formatter:on
	}

	@Ignore
	@Test
	@UseDataProvider("dataProviderNotNullStringsSetInBeforeClass")
	public void testNotNullStringsSetInBeforeClass(String input) {
		// Given:

		// Expected:
	}

	@DataProvider(format = "%m: %p[0] * %p[1] == %p[2]")
	public static Object[][] dataProviderMultiply() {
		// @formatter:off
		return new Object[][] { { 0, 0, 0 }, { -1, 0, 0 }, { 0, 1, 0 }, { 1, 1, 1 },
				{ 1, -1, -1 }, { -1, -1, 1 }, { 1, 2, 2 }, { -1, 2, -2 }, { -1, -2, 2 },
				{ -1, -2, 2 }, { 6, 7, 42 }, };
		// @formatter:on
	}

	// @Ignore
	@Test
	@UseDataProvider("dataProviderMultiply")
	public void testMultiply(int a, int b, int c) {
		System.err.println(
				String.format("Test: inputs = %d,%d expected result = %d", a, b, c));
		// Expect:
		assertThat(a * b, is(c));
	}

	@DataProvider
	public static Object[][] dataProviderWithNonConstantObjects() {
		Calendar yesterday = Calendar.getInstance();
		yesterday.add(Calendar.DAY_OF_MONTH, -1);

		Calendar now = Calendar.getInstance();

		Calendar tomorrow = Calendar.getInstance();
		tomorrow.add(Calendar.DAY_OF_MONTH, 1);

		// @formatter:off
		return new Object[][] { { yesterday, yesterday, false },
				{ yesterday, now, true }, { yesterday, tomorrow, true },
				{ now, yesterday, false }, { now, now, false }, { now, tomorrow, true },
				{ tomorrow, yesterday, false }, { tomorrow, now, false },
				{ tomorrow, tomorrow, false }, };
		// @formatter:on
	}

	@Ignore
	@Test
	@UseDataProvider("dataProviderWithNonConstantObjects")
	public void testWithNonConstantObjects(Calendar calendar1, Calendar calendar2,
			boolean result) {
		// Given:

		// When:

		// Then:
		assertTrue(result == calendar1.before(calendar2));
	}

	@DataProvider(splitBy = "\\|", trimValues = true)
	public static String[] dataProviderFileExistence() {
		// @formatter:off
		return new String[] { "src             | true", "src/main        | true",
				"src/main/java/  | true", "src/test/java/  | true",
				"test            | false", };
		// @formatter:on
	}

	@Ignore
	@Test
	@UseDataProvider("dataProviderFileExistence")
	public void testFileExistence(File file, boolean expected) {
		// Expect:
	}

	@DataProvider
	public static List<List<Object>> dataProviderNumberFormat() {
		List<List<Object>> result = new ArrayList<List<Object>>();
		List<Object> first = new ArrayList<Object>();
		first.add(Integer.valueOf(101));
		first.add("%5d");
		first.add("  101");
		result.add(first);
		List<Object> second = new ArrayList<Object>();
		second.add(125);
		second.add("%06d");
		second.add("000125");
		result.add(second);
		return result;
	}

	// @Ignore
	@Test
	@UseDataProvider("dataProviderNumberFormat")
	public void testNumberFormat(Number value, String format, String expected) {
		// Given:
		System.err.println(String.format(
				"Test: inputs value = %d, format = '%s' expected result = %s", value,
				format, expected));

		// When:
		String result = String.format(format, value);

		// Then:
		assertThat(String.format(format, value), equalTo(expected));
	}

	@Ignore
	@Test
	// @formatter:off
	@DataProvider({ ",                 0", "a,                1",
			"abc,              3", "veryLongString,  14", })
	// @formatter:on
	public void testStringLength(String string, int length) {
		// Expect:
		assertThat(string.length(), is(length));
	}

	@Ignore
	// @formatter:off
	@Test
	@DataProvider(value = { "               |  0", "a              |  1",
			"abc            |  3",
			"veryLongString | 14", }, splitBy = "\\|", trimValues = true, convertNulls = true)
	// @formatter:on
	public void testStringLength2(String string, int length) {
		// Expect:
		assertThat(string.length(), is(length));
	}

	@Ignore
	// @formatter:off
	@Test
	@DataProvider({ "0, UP", "1, DOWN", "3, FLOOR", })
	// @formatter:on
	public void testOldModeToRoundingMode(int oldMode, RoundingMode expected) {
		// Expect:
	}

	@Ignore
	@Test
	@DataProvider({ "null", "", })
	public void testIsEmptyString2(String str) {
		// When:
		boolean isEmpty = (str == null) ? true : str.isEmpty();

		// Then:
	}

	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.METHOD)
	public @interface ExternalFile {
		public enum Format {
			CSV, XML, XLS;
		}

		Format format();

		String value();
	}

	@DataProvider
	public static Object[][] loadFromExternalFile(FrameworkMethod testMethod) {
		String testDataFile = testMethod.getAnnotation(ExternalFile.class).value();
		// Load the data from the external file here ...
		return new Object[][] { { testDataFile } };
	}

	@Ignore
	@Test
	@UseDataProvider("loadFromExternalFile")
	@ExternalFile(format = ExternalFile.Format.CSV, value = "testdata.csv")
	public void testThatUsesUniversalDataProvider(String testData) {
		// Expect:
	}

	@DataProvider
	public static Object[][] dataProviderWithStringContainingTabsNewlineAndCarriageReturn() {
		Object[][] result = { {} };
		return result;
	}

	@Ignore
	@Test
	@DataProvider({ "Do it.\nOr let it." })
	public void testWithStringContainingTabsNewlineAndCarriageReturn(
			@SuppressWarnings("unused") String string) {
		// nothing to do => Just look at the test output in Eclispe's JUnit view if
		// it is displayed correctly
	}
}
