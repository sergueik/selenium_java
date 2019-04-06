package com.github.sergueik.example;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.exparity.hamcrest.date.DateMatchers.sameOrBefore;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.core.AnyOf.anyOf;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.github.sergueik.jprotractor.NgBy;
import com.github.sergueik.jprotractor.NgWebDriver;
import com.github.sergueik.jprotractor.NgWebElement;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

// dependency version compatibility issue:
// java.lang.IllegalAccessError: tried to access class org.openqa.selenium.os.ExecutableFinder from class org.openqa.selenium.phantomjs.PhantomJSDriverService
/*
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
*/
public class YellowClueTest {

	private static NgWebDriver ngDriver;
	private static WebDriver seleniumDriver;
	static WebDriverWait wait;
	static Actions actions;
	static Alert alert;
	static int implicitWait = 10;
	static int flexibleWait = 5;
	static long pollingInterval = 500;
	static int width = 600;
	static int height = 800;
	// set to true for Desktop, false for headless browser testing
	static boolean isCIBuild = false;
	// "https://datatables.net/examples/api/form.html";
	private static String baseUrl = "https://datatables.net/examples/api/highlight.html";

	@BeforeClass
	public static void setup() throws IOException {
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
	public void beforeEach() {
		ngDriver.navigate().to(baseUrl);
	}

	@Ignore
	@Test
	public void test1() {
		String cellText = "Software Engineer";
		// Arrange
		try {
			WebElement tableElement = wait.until(
					ExpectedConditions.visibilityOfElementLocated(By.id("example")));
			actions.moveToElement(tableElement).build().perform();
		} catch (RuntimeException timeoutException) {
			return;
		}

		List<WebElement> cellElements = seleniumDriver.findElements(
				By.cssSelector("#example > tbody > tr > td:nth-child(2)"));
		// Assert
		assertThat(cellElements.size(), greaterThan(0));
		// Act
		cellElements.stream()
				.filter(o -> o.getText().contains((CharSequence) cellText))
				.forEach(CommonFunctions::highlight);
		// Assert
		cellElements.stream()
				.filter(o -> o.getText().contains((CharSequence) cellText))
				.map(o -> o.getText()).forEach(System.err::println);
	}

	@Ignore
	@Test
	public void test2() {
		String cellText = "Software Engineer";
		WebElement tableElement = null;
		// Arrange
		try {
			tableElement = wait.until(
					ExpectedConditions.visibilityOfElementLocated(By.id("example")));
		} catch (RuntimeException timeoutException) {
			return;
		}
		// Assert
		assertThat(tableElement, notNullValue());

		actions.moveToElement(tableElement).build().perform();
		List<WebElement> cellElements = tableElement
				.findElements(By.cssSelector("tbody > tr > td:nth-child(2)"));
		// Assert
		assertThat(cellElements.size(), greaterThan(0));
		// Act
		cellElements.stream()
				.filter(o -> o.getText().contains((CharSequence) cellText))
				.forEach(CommonFunctions::highlight);
		// Assert
		cellElements.stream()
				.filter(o -> o.getText().contains((CharSequence) cellText))
				.map(o -> o.getText()).forEach(System.err::println);
	}

	@Ignore
	@Test
	public void test3() {
		// Arrange
		try {
			WebElement tableElement = wait.until(
					ExpectedConditions.visibilityOfElementLocated(By.id("example")));
			actions.moveToElement(tableElement).build().perform();
		} catch (RuntimeException timeoutException) {
			return;
		}

		// Assert
		List<WebElement> cellElements = ngDriver.findElements(
				NgBy.cssContainingText("#example > tbody > tr > td:nth-child(2)",
						"Software Engineer"));
		assertThat(cellElements.size(), greaterThan(0));
		cellElements.stream().forEach(CommonFunctions::highlight);
		cellElements.stream().map(o -> o.getText()).forEach(System.err::println);
	}

	@Ignore
	@Test
	public void test4() {
		NgWebElement ngTableElement = null;
		// Arrange
		try {
			WebElement tableElement = wait.until(
					ExpectedConditions.visibilityOfElementLocated(By.id("example")));
			ngTableElement = new NgWebElement(ngDriver, tableElement);
		} catch (RuntimeException timeoutException) {
			return;
		}
		// Assert
		assertThat(ngTableElement, notNullValue());

		actions.moveToElement(ngTableElement.getWrappedElement()).build().perform();

		List<WebElement> cellElements = ngTableElement
				.findElements(NgBy.cssContainingText("tbody > tr > td:nth-child(2)",
						"Software Engineer"));
		assertThat(cellElements.size(), greaterThan(0));
		cellElements.stream().forEach(CommonFunctions::highlight);
		cellElements.stream().map(o -> o.getText()).forEach(System.err::println);
	}

	@Ignore
	@Test
	public void test5() {
		Object[] results = new Object[] { "Software Engineer",
				"Senior Javascript Developer" };
		String cellTextPackedRegexp = "__REGEXP__/(?:Software Engineer|Senior Javascript Developer)/i";
		NgWebElement ngTableElement = null;
		// Arrange
		try {
			WebElement tableElement = wait.until(
					ExpectedConditions.visibilityOfElementLocated(By.id("example")));
			ngTableElement = new NgWebElement(ngDriver, tableElement);
		} catch (RuntimeException timeoutException) {
			return;
		}
		// Assert
		assertThat(ngTableElement, notNullValue());

		actions.moveToElement(ngTableElement.getWrappedElement()).build().perform();

		List<WebElement> cellElements = ngTableElement
				.findElements(NgBy.cssContainingText("tbody > tr > td:nth-child(2)",
						cellTextPackedRegexp));
		assertThat(cellElements.size(), greaterThan(0));
		cellElements.stream().forEach(CommonFunctions::highlight);
		cellElements.stream().map(o -> o.getText()).forEach(System.err::println);

		assertThat(
				cellElements.stream().map(o -> o.getText()).collect(Collectors.toSet()),
				hasItems(results));

	}

	// GET request
	// based on:
	// http://www.java2s.com/Tutorial/Java/0320__Network/DownloadingawebpageusingURLandURLConnectionclasses.htm
	@Test
	public void test7() throws Exception {

		// URLConnection url = new URL(
		// "https://www.yellowpages.com.au/search/listings?clue=restaurant&locationClue=melbourne&lat=&lon=")
		// .openConnection();

		// TODO: set "pageref": "www.yellowpages.com.au"
		URL pageURL = new URL("https://www.yellowpages.com.au/search/listings");

		HttpURLConnection urlConnection = (HttpURLConnection) pageURL
				.openConnection();
		urlConnection.setRequestProperty("pageref", "www.yellowpages.com.au");

		BufferedInputStream buffer = new BufferedInputStream(
				urlConnection.getInputStream());
		int byteRead;
		StringBuffer processOutput = new StringBuffer();
		while ((byteRead = buffer.read()) != -1) {
			// System.err.println((char) byteRead);
			processOutput.append((char) byteRead);
		}
		System.err.println("test7 response: " + processOutput.toString());
	}

	@Ignore
	// POST request
	// https://www.yellowpages.com.au/search/listings?clue=restaurant&locationClue=melbourne&lat=&lon=
	// based on:
	// http://www.java2s.com/Tutorial/Java/0320__Network/SendingaPOSTRequestwithParametersFromaJavaClass.htm
	@Test
	public void test6() throws Exception {

		URL url = new URL("https://www.yellowpages.com.au/search/listings");
		URLConnection conn = url.openConnection();
		conn.setDoOutput(true);
		OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream());

		writer.write("clue=restaurant&locationClue=melbourne&lat=&lon=");
		writer.flush();
		String line;
		BufferedReader reader = new BufferedReader(
				new InputStreamReader(conn.getInputStream()));
		StringBuffer processOutput = new StringBuffer();
		while ((line = reader.readLine()) != null) {
			processOutput.append(line);
		}
		// 302 Moved Temporarily
		// see also:
		// http://www.java2s.com/Tutorial/Java/0320__Network/PreventingAutomaticRedirectsinaHTTPConnection.htm
		if (processOutput.toString().matches(".*302 Found.*")) {
			System.err.println("Matched redirect in the response body");
			System.err.println(
					"Response headers: " + conn.getHeaderFields().keySet().toString());

			conn.getHeaderFields().keySet().stream().forEach(h -> System.err
					.println(String.format("%s: %s", h, conn.getHeaderField(h))));

			System.err.println("Response: " + processOutput.toString());

			// open new post request
			URL url2 = new URL("http://www.yellowpages.com.au/dataprotection");
			URLConnection conn2 = url2.openConnection();
			conn2.setDoOutput(true);
			// cannot reuse writer:
			// java.net.ProtocolException: Cannot write output after reading input.
			OutputStreamWriter writer2 = new OutputStreamWriter(
					conn2.getOutputStream());

			writer2.write(
					"path=/search/listings&clue=restaurant&locationClue=melbourne&lat=&lon=");
			writer2.flush();
			BufferedReader reader2 = new BufferedReader(
					new InputStreamReader(conn2.getInputStream()));
			processOutput.setLength(0);
			// 301 Moved Permanently
			while ((line = reader2.readLine()) != null) {
				processOutput.append(line);
			}
			System.err.println("Response (2): " + processOutput.toString());
			System.err.println("Response(2) headers: "
					+ conn2.getHeaderFields().keySet().toString());

			conn2.getHeaderFields().keySet().stream().forEach(h -> System.err
					.println(String.format("%s: %s", h, conn2.getHeaderField(h))));

			// We value the quality of content provided to our customers, and to
			// maintain this, we would like to ensure real humans are accessing our
			// information.
		}
		// origin:
		// http://www.java2s.com/Tutorial/Java/0320__Network/GettingtheCookiesfromanHTTPConnection.htm
		// collect cookie
		for (int i = 0;; i++) {
			String headerName = conn.getHeaderFieldKey(i);
			String headerValue = conn.getHeaderField(i);

			if (headerName == null && headerValue == null) {
				break;
			}
			if ("Set-Cookie".equalsIgnoreCase(headerName)) {
				String[] fields = headerValue.split(";\\s*");
				for (int j = 1; j < fields.length; j++) {
					if ("secure".equalsIgnoreCase(fields[j])) {
						System.out.println("secure=true");
					} else if (fields[j].indexOf('=') > 0) {
						String[] f = fields[j].split("=");
						if ("expires".equalsIgnoreCase(f[0])) {
							System.out.println("expires" + f[1]);
						} else if ("domain".equalsIgnoreCase(f[0])) {
							System.out.println("domain" + f[1]);
						} else if ("path".equalsIgnoreCase(f[0])) {
							System.out.println("path" + f[1]);
						}
					}
				}
			}
		}
		writer.close();
		reader.close();
	}

	@AfterClass
	public static void teardown() {
		ngDriver.close();
		seleniumDriver.quit();
	}

}
