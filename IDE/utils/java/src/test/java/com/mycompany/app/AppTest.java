package com.mycompany.app;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.HttpConnectionFactory;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.handler.DefaultHandler;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.MovedContextHandler;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.StdErrLog;
import org.eclipse.jetty.util.thread.QueuedThreadPool;

import java.io.IOException;
import java.io.InputStream;

import org.openqa.selenium.*;
import static org.openqa.selenium.By.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.seleniumhq.selenium.fluent.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import java.util.concurrent.TimeUnit;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.startsWith;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.endsWith;
import static org.hamcrest.Matchers.equalTo;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
import static org.testng.AssertJUnit.fail;

import org.json.*;

import java.util.concurrent.TimeUnit;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.startsWith;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.endsWith;
import static org.hamcrest.Matchers.equalTo;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;
import static org.testng.AssertJUnit.fail;

// not using jUnit in this project
// import static org.junit.Assert.assertThat;

public class AppTest {

	private FirefoxDriver driver;
	private WebDriverWait wait;
	private int flexibleWait = 5;
	private long pollingInterval = 500;

	private Server webServer;

	private String hashesFinderScript = null;
	private String resultFinderScript = null;

	@BeforeSuite
	public void before_suite() throws Exception {
		((StdErrLog) Log.getRootLogger()).setLevel(StdErrLog.LEVEL_OFF);
		webServer = new Server(new QueuedThreadPool(5));
		ServerConnector connector = new ServerConnector(webServer,
				new HttpConnectionFactory());
		connector.setPort(8080);
		webServer.addConnector(connector);
		ResourceHandler resource_handler = new ResourceHandler();
		resource_handler.setDirectoriesListed(true);
		resource_handler.setWelcomeFiles(new String[] { "index.html" });
		resource_handler.setResourceBase("src/test/webapp");
		HandlerList handlers = new HandlerList();
		handlers
				.setHandlers(new Handler[] { resource_handler, new DefaultHandler() });
		webServer.setHandler(handlers);
		webServer.start();

		driver = new FirefoxDriver();
		driver.manage().timeouts().setScriptTimeout(30, TimeUnit.SECONDS);
		wait = new WebDriverWait(driver, flexibleWait);
		wait.pollingEvery(pollingInterval, TimeUnit.MILLISECONDS);
		hashesFinderScript = getScriptContent("hashesFinder.js");
		resultFinderScript = getScriptContent("resultFinder.js");
	}

	@AfterSuite
	public void after_suite() throws Exception {
		Thread.sleep(1000);
		driver.quit();
		webServer.stop();
	}

	@BeforeMethod
	public void loadPage() {
		driver.get("http://user:password@localhost:8080/");
	}

	@AfterMethod
	public void resetBrowser() {
		driver.get("about:blank");
	}

	@Test(enabled = true)
	public void pageLaded() {
		String tableCssSelector = "html body div table.sortable";
		wait.until(ExpectedConditions.visibilityOf(driver.findElement(By
				.cssSelector(tableCssSelector))));

		assertTrue(driver.findElements(cssSelector(tableCssSelector)).size() > 0);
	}

	@Test(enabled = true)
	public void testRunScript() {
		String result = storeEval(hashesFinderScript, "");
		assertFalse(result.isEmpty());
		result = storeEval(hashesFinderScript);
		assertFalse(result.isEmpty());
	}

	// https://processing.org/reference/JSONArray.html
	@Test(enabled = true)
	public void findHashes() {
		ArrayList<String> hashes = new ArrayList<String>();
		JSONArray hashesDataArray = new JSONArray(storeEval(hashesFinderScript));
		for (int i = 0; i < hashesDataArray.length(); i++) {
			hashes.add(hashesDataArray.getString(i));
		}
		assertTrue(hashes.size() > 0);
		for (String hash : hashes) {
			System.err.println("Hash: " + hash);
		}
	}

	// https://processing.org/reference/JSONObject.html
	@Test(enabled = true)
	public void findResultsDetails() {
		JSONObject resultObj = new JSONObject(storeEval(resultFinderScript,
				storeEval(hashesFinderScript)));
		Iterator<String> masterServerIterator = resultObj.keys();
		while (masterServerIterator.hasNext()) {
			String masterServer = masterServerIterator.next();
			JSONArray resultDataArray = resultObj.getJSONArray(masterServer);
			for (int cnt = 0; cnt < resultDataArray.length(); cnt++) {
				System.err.println(masterServer + " " + resultDataArray.get(cnt));
			}
		}
	}

	private String storeEval(String script, String... input) {
		String result = null;
		if (driver instanceof JavascriptExecutor) {
			result = (String) ((JavascriptExecutor) driver).executeScript(script,
					input);
		}
		return result;
	}

	protected String getScriptContent(String scriptName) throws Exception {
		try {
			final InputStream stream = AppTest.class.getClassLoader()
					.getResourceAsStream(scriptName);
			final byte[] bytes = new byte[stream.available()];
			stream.read(bytes);
			return new String(bytes, "UTF-8");
		} catch (IOException e) {
			throw new Exception(scriptName);
		}
	}
}
