package com.mycompany.app;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.File;
import java.io.FileReader;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import java.math.RoundingMode;
import java.util.Calendar;
import java.util.Collection;
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

// Java 8  part
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

// Selenium
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

@RunWith(Parameterized.class)

// origin:
// http://automated-testing.info/t/kak-peredelat-parametrizovannyj-test-na-junit-v-testng/10970/11
public class App2Test {

	public static String url;

	public App2Test(String url) {
		this.url = url;
	}

	@Test
	public void test() {
		System.err.println(String.format("Url = '%s'", url));
	}

	@Parameters(name = "url = {0}")
	public static Iterable<Object[]> data() {
		List<Object[]> testData = new ArrayList<Object[]>();
		List<Object> dataRow = new ArrayList<Object>();
		String separator = ",";
		BufferedReader reader = null;
		String line;
		try {
			reader = new BufferedReader(new FileReader("./data.txt"));
			while ((line = reader.readLine()) != null) {
				// load comma-separated columns
				testData.add(line.split(separator));
				// this alwo works for single item per line 
        // no need to 
				// testData.add(new Object[] { line });
			}
			reader.close();
		} catch (IOException e) {
			// cat process multiple exception type,
			// SomeExceptionType|AnotherExceptionType
			e.printStackTrace();
		}
		return testData;
	}

	// java 8 style, somewhat excessive ?
	@Parameters(name = "url = {0}")
	public static Collection<Object[]> data8() {
		List<String> lines = new ArrayList<>();
		BufferedReader reader = null;
		String line;
		try {
			reader = new BufferedReader(new FileReader("./data.txt"));
			while ((line = reader.readLine()) != null) {
				lines.add(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return lines.stream().map(o -> new String[] { o })
				.collect(Collectors.toList());
	}
}
