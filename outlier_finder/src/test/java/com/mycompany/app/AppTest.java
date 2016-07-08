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
import org.eclipse.jetty.util.security.Constraint;
import org.eclipse.jetty.util.security.Credential;
import org.eclipse.jetty.util.security.Password;
import org.eclipse.jetty.util.thread.QueuedThreadPool;
import org.eclipse.jetty.security.LoginService;

import static org.openqa.selenium.By.*;

import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.seleniumhq.selenium.fluent.*;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import org.testng.annotations.Test;
import org.eclipse.jetty.security.ConstraintMapping;
import org.eclipse.jetty.security.ConstraintSecurityHandler;
import org.eclipse.jetty.security.HashLoginService;
import org.eclipse.jetty.security.SecurityHandler;
import org.eclipse.jetty.security.authentication.BasicAuthenticator;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import java.util.Collections;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
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
import static org.testng.Assert.assertNotEquals;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;
import static org.testng.AssertJUnit.fail;

// not using jUnit in this project
// import static org.junit.Assert.assertThat;

public class AppTest {

	private FirefoxDriver driver;
	private Server webServer;
	private String hashesFinderScript = " var table_selector = 'html body div table.sortable';\n"
			+ " var row_selector = 'tbody tr';\n"
			+ " var column_selector = 'td:nth-child(3)';\n"
			+ " // var table_selector = '${table_css_selector}';\n"
			+ " // var row_selector = '${row_css_selector}';\n"
			+ " // var column_selector = '${hash_column_css_selector}';\n"
			+ " col_num = 0;\n"
			+ " var tables = document.querySelectorAll(table_selector);\n"
			+ " var git_hashes = {};\n"
			+ " for (table_cnt = 0; table_cnt != tables.length; table_cnt++) {\n"
			+ " var table = tables[table_cnt];\n"
			+ " if (table instanceof Element) {\n"
			+ " var rows = table.querySelectorAll(row_selector);\n"
			+ " // skip first row\n"
			+ " for (row_cnt = 1; row_cnt != rows.length; row_cnt++) {\n"
			+ " var row = rows[row_cnt];\n"
			+ " if (row instanceof Element) {\n"
			+ " var cols = row.querySelectorAll(column_selector);\n"
			+ " if (cols.length > 0) {\n"
			+ " data = cols[0].innerHTML;\n"
			+ " data = data.replace(/\\s+/g, '');\n"
			+ " if (!git_hashes[data]) {\n"
			+ " git_hashes[data] = 0;\n"
			+ " }\n"
			+ " git_hashes[data]++;\n"
			+ " }\n"
			+ " }\n"
			+ " }\n"
			+ " }\n"
			+ " }\n"
			+ " var sortNumber = function(a, b) {\n"
			+ " // reverse numeric sort\n"
			+ " return b - a;\n"
			+ " }\n"
			+ " var removeFrequentKey = function(datahash) {\n"
			+ " var array_keys = [];\n"
			+ " var array_values = [];\n"
			+ " for (var key in datahash) {\n"
			+ " array_keys.push(key);\n"
			+ " array_values.push(0 + datahash[key]);\n"
			+ " }\n"
			+ " max_freq = array_values.sort(sortNumber)[0]\n"
			+ " for (var key in datahash) {\n"
			+ " if (datahash[key] === max_freq) {\n"
			+ " delete datahash[key]\n"
			+ " }\n"
			+ " }\n"
			+ " return datahash;\n"
			+ " }\n"
			+ " \n"
			+ " git_hashes = removeFrequentKey(git_hashes);\n"
			+ " \n"
			+ " var array_keys = [];\n"
			+ " for (var key in git_hashes) {\n"
			+ " array_keys.push(key);\n"
			+ " }\n"
			+ " \n"
			+ " return array_keys.join(); ";
      
	private String serverFinderScript = "// var table_selector = '${table_css_selector}';\n"
			+ " // var row_selector = '${row_css_selector}';\n"
			+ " // var hash_column_selector = '${hash_column_css_selector}';\n"
			+ " // var master_server_column_selector = '${master_server_column_css_selector}';\n"
			+ " var git_hashes_str = arguments[0];\n"
			+ " \n"
			+ " var table_selector = 'html body div table.sortable';\n"
			+ " var row_selector = 'tbody tr';\n"
			+ " var hash_column_selector = 'td:nth-child(3)';\n"
			+ " var master_server_column_selector = 'td:nth-child(1)';\n"
			+ " // var git_hashes_str = '259c762,25bad25,2bad762,b26e5f1,bade5f1,d1bad8d,d158d8d,533acf2,533ace2,1b24bca,1b24bc2,d3c1652,d3aaa52,7538e12,7000e12';\n"
			+ " var result = {};\n"
			+ " var col_num = 0;\n"
			+ " var git_hashes = {};\n"
			+ " var git_hashes_keys = git_hashes_str.split(',');\n"
			+ " for (var key in git_hashes_keys) {\n"
			+ " git_hashes[git_hashes_keys[key]] = 1;\n"
			+ " }\n"
			+ " var tables = document.querySelectorAll(table_selector);\n"
			+ " \n"
			+ " \n"
			+ " for (table_cnt = 0; table_cnt != tables.length; table_cnt++) {\n"
			+ " var table = tables[table_cnt];\n"
			+ " if (table instanceof Element) {\n"
			+ " var rows = table.querySelectorAll(row_selector);\n"
			+ " // skip first row\n"
			+ " for (row_cnt = 1; row_cnt != rows.length; row_cnt++) {\n"
			+ " var row = rows[row_cnt];\n"
			+ " if (row instanceof Element) {\n"
			+ " var hash_cols = row.querySelectorAll(hash_column_selector);\n"
			+ " if (hash_cols.length > 0) {\n"
			+ " data = hash_cols[0].innerHTML;\n"
			+ " data = data.replace(/\\s+/g, '');\n"
			+ " if (git_hashes[data]) {\n"
			+ " var master_server_cols = row.querySelectorAll(master_server_column_selector);\n"
			+ " if (master_server_cols.length > 0) {\n"
			+ " data = master_server_cols[0].innerHTML;\n"
			+ " data = data.replace(/\\s+/g, '');\n" + " if (!result[data]) {\n"
			+ " result[data] = 0;\n" + " }\n" + " result[data]++;\n" + " }\n"
			+ " }\n" + " }\n" + " }\n" + " }\n" + " }\n" + " }\n"
			+ " var array_keys = [];\n" + " for (var key in result) {\n"
			+ " array_keys.push(key);\n" + " }\n"
			+ " // TODO: collect 'module' column\n" + " return array_keys.join();";

	@BeforeSuite
	// http://www.javased.com/index.php?api=org.eclipse.jetty.util.security.Constraint
	// http://www.programcreek.com/java-api-examples/index.php?api=org.eclipse.jetty.security.LoginService
	public void before_suite() throws Exception {

		((StdErrLog) Log.getRootLogger()).setLevel(StdErrLog.LEVEL_DEBUG);
		webServer = new Server(new QueuedThreadPool(5));
		ServerConnector connector = new ServerConnector(webServer,
				new HttpConnectionFactory());
		connector.setPort(8080);
		webServer.addConnector(connector);


		Constraint constraint = new Constraint("default", Constraint.ANY_ROLE);
		constraint.setAuthenticate(true);
		ConstraintMapping constraintMapping = new ConstraintMapping();
		constraintMapping.setPathSpec("/*");
		constraintMapping.setConstraint(constraint);
		ConstraintSecurityHandler security_handler = new ConstraintSecurityHandler();
		security_handler.setAuthenticator(new BasicAuthenticator());
		security_handler.addConstraintMapping(constraintMapping);

		// HTTP ERROR 403
		//  Problem accessing /. Reason:
		//  !role
		final String userName = "user";
		final String password = "password";
		LoginService loginService = new HashLoginService("default");
		webServer.addBean(loginService);
		((HashLoginService) loginService).putUser(userName,
				Credential.getCredential(password),
				new String[] { Constraint.ANY_ROLE });

		security_handler.setLoginService(loginService);

		/*
		 * security_handler.setConstraintMappings( Collections.singletonList(
		 * mapping ) ); security_handler.setAuthenticator( new BasicAuthenticator()
		 * ); security_handler.setLoginService( loginService );
		 */
		ResourceHandler resource_handler = new ResourceHandler();
		resource_handler.setDirectoriesListed(true);
		resource_handler.setWelcomeFiles(new String[] { "index.html" });
		resource_handler.setResourceBase("src/test/webapp");
		security_handler.setHandler(resource_handler);

		webServer.setHandler(security_handler);
		webServer.start();

		driver = new FirefoxDriver();
		driver.manage().timeouts().setScriptTimeout(30, TimeUnit.SECONDS);
	}

	@AfterSuite
	public void after_suite() throws Exception {
		Thread.sleep(10000);
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

	@Test
	public void pageLaded() {
    // TODO: wait		
		assertTrue(driver.findElements(cssSelector("html body div table.sortable"))
				.size() > 0);
	}

	@Test
	public void testRunScript() {
		String result = storeEval(hashesFinderScript, "");
		assertFalse(result.isEmpty());
	}

	@Test
	public void findHashes() {
		List<String> accounts = Arrays.asList(storeEval(hashesFinderScript, "").split("\\s*,\\s*")); 
		assertTrue(accounts.size() > 0);
	}

	@Test
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