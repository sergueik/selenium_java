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
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.seleniumhq.selenium.fluent.*;
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
import static org.openqa.selenium.By.*;
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
	private Server webServer;
	private String hashesFinderScript = " var table_selector = 'html body div table.sortable';"
			+ " var row_selector = 'tbody tr';"
			+ " var column_selector = 'td:nth-child(3)';"
			+ " col_num = 0;"
			+ " var tables = document.querySelectorAll(table_selector);"
			+ " var git_hashes = {};"
			+ " for (table_cnt = 0; table_cnt != tables.length; table_cnt++) {"
			+ " var table = tables[table_cnt];"
			+ " if (table instanceof Element) {"
			+ " var rows = table.querySelectorAll(row_selector);"
			+ " // skip first row"
			+ " for (row_cnt = 1; row_cnt != rows.length; row_cnt++) {"
			+ " var row = rows[row_cnt];"
			+ " if (row instanceof Element) {"
			+ " var cols = row.querySelectorAll(column_selector);"
			+ " if (cols.length > 0) {"
			+ " data = cols[0].innerHTML;"
			+ " data = data.replace(/\\s+/g, '');"
			+ " if (!git_hashes[data]) {"
			+ " git_hashes[data] = 0;"
			+ " }"
			+ " git_hashes[data]++;"
			+ " }"
			+ " }"
			+ " }"
			+ " }"
			+ " }"
			+ " var sortNumber = function(a, b) {"
			+ " // reverse numeric sort"
			+ " return b - a;"
			+ " }"
			+ " var removeFrequentKey = function(datahash) {"
			+ " var array_keys = [];"
			+ " var array_values = [];"
			+ " for (var key in datahash) {"
			+ " array_keys.push(key);"
			+ " array_values.push(0 + datahash[key]);"
			+ " }"
			+ " max_freq = array_values.sort(sortNumber)[0]"
			+ " for (var key in datahash) {"
			+ " if (datahash[key] === max_freq) {"
			+ " delete datahash[key]"
			+ " }"
			+ " }"
			+ " return datahash;"
			+ " }"
			+ " "
			+ " git_hashes = removeFrequentKey(git_hashes);"
			+ " "
			+ " var array_keys = [];"
			+ " for (var key in git_hashes) {"
			+ " array_keys.push(key);"
			+ " }"
			+ " "
			+ " return array_keys.join(); ";
      
	private String serverFinderScript = "// var table_selector = '${table_css_selector}';"
			+ " var git_hashes_str = arguments[0];"
			+ " var table_selector = 'html body div table.sortable';"
			+ " var row_selector = 'tbody tr';"
			+ " var hash_column_selector = 'td:nth-child(3)';"
			+ " var master_server_column_selector = 'td:nth-child(1)';"
			+ " var module_column_selector = 'td:nth-child(2)';"
			+ " var result = {};"
			+ " var col_num = 0;"
			+ " var git_hashes = {};"
			+ " var git_hashes_keys = git_hashes_str.split(',');"
			+ " for (var key in git_hashes_keys) {"
			+ " git_hashes[git_hashes_keys[key]] = 1;"
			+ " }"
			+ " var tables = document.querySelectorAll(table_selector);"
			+ " "
			+ " "
			+ " for (table_cnt = 0; table_cnt != tables.length; table_cnt++) {"
			+ " var table = tables[table_cnt];"
			+ " if (table instanceof Element) {"
			+ " var rows = table.querySelectorAll(row_selector);"
			+ " // skip first row"
			+ " for (row_cnt = 1; row_cnt != rows.length; row_cnt++) {"
			+ " var row = rows[row_cnt];"
			+ " if (row instanceof Element) {"
			+ " var hash_cols = row.querySelectorAll(hash_column_selector);"
			+ " if (hash_cols.length > 0) {"
			+ " var hash_data = hash_cols[0].innerHTML;"
			+ " hash_data = hash_data.replace(/\\s+/g, '');"
			+ " if (git_hashes[hash_data]) {"
			+ " var master_server_cols = row.querySelectorAll(master_server_column_selector);"
			+ " if (master_server_cols.length > 0) {"
			+ " var master_server_data = master_server_cols[0].innerHTML;"
			+ " master_server_data = master_server_data.replace(/\\s+/g, '');"
      + " if (!result[master_server_data]) {"
			+ " result[master_server_data] = hash_data;"
      + " }"
      + " }"
			+ " }"
      + " }"
      + " }"
      + " }"
      + " }"
      + " }"
      + " return JSON.stringify(result);";

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
		// MovedContextHandler effective_symlink = new
		// MovedContextHandler(webServer, "/lib/angular", "/lib/angular_v1.2.9");
		handlers
				.setHandlers(new Handler[] { resource_handler, new DefaultHandler() });
		webServer.setHandler(handlers);
		webServer.start();

		driver = new FirefoxDriver();
		driver.manage().timeouts().setScriptTimeout(30, TimeUnit.SECONDS);
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

	@Test(enabled = false)
	public void pageLaded() {
    // TODO: wait		
		assertTrue(driver.findElements(cssSelector("html body div table.sortable"))
				.size() > 0);
	}

	@Test(enabled = false)
	public void testRunScript() {
		String result = storeEval(hashesFinderScript, "");
		assertFalse(result.isEmpty());
	}

	@Test(enabled = false)
	public void findHashes() {
		List<String> hashes = Arrays.asList(storeEval(hashesFinderScript, "").split("\\s*,\\s*")); 
		assertTrue(hashes.size() > 0);
	}

  
	@Test(enabled = true)
	public void findServersDetails() {
      JSONObject resultObj = new JSONObject(storeEval(serverFinderScript, storeEval(hashesFinderScript, "")));
      Iterator<String> masterServerIterator = resultObj.keys();
  		while (masterServerIterator.hasNext()) {
  			String masterServer = masterServerIterator.next();
        System.err.println(masterServer + " " + resultObj.getString(masterServer));
      }
		// assertTrue(servers.size() > 0);
	}

	@Test(enabled = false)
	public void findServers() {
		List<String> servers = Arrays.asList( storeEval(serverFinderScript, storeEval(hashesFinderScript, "")) );
		assertTrue(servers.size() > 0);
	}

	private String storeEval(String script, String input) {
		String result = null;
		if (driver instanceof JavascriptExecutor) {
			result = (String ) ((JavascriptExecutor) driver).executeScript(script,
					input);
		}
		return result;
	}
}