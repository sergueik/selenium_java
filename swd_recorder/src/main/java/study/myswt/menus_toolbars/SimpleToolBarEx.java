package study.myswt.menus_toolbars;

import java.io.InputStream;
import java.io.IOException;
import java.net.URI;

import java.net.URISyntaxException;
import java.net.URL;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.concurrent.TimeUnit;
import java.util.Enumeration;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;

import org.hamcrest.CoreMatchers;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.endsWith;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.CoreMatchers.startsWith;
import static org.hamcrest.MatcherAssert.assertThat;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.HasInputDevices;
import org.openqa.selenium.interactions.internal.Coordinates;
import org.openqa.selenium.interactions.Mouse;
import org.openqa.selenium.internal.Locatable;
import org.openqa.selenium.remote.UnreachableBrowserException;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;

// based on:
public class SimpleToolBarEx {

	private Image newi;
	private Image opei;
	private Image quii;

	private WebDriver driver;
	private WebDriverWait wait;
	private int flexibleWait = 5;
	private int implicitWait = 1;
	private long pollingInterval = 500;
	private String baseURL = "about:blank";
	private final String getCommand = "return document.swdpr_command === undefined ? '' : document.swdpr_command;";

	public SimpleToolBarEx(Display display) {

		initUI(display);
	}

	@SuppressWarnings("unused")
	public void initUI(Display display) {

		Shell shell = new Shell(display, SWT.SHELL_TRIM | SWT.CENTER);

		Device dev = shell.getDisplay();

		try {
			newi = new Image(dev, System.getProperty("user.dir")
					+ "/src/main/resources/" + "toolbar_new.png");
			opei = new Image(dev, System.getProperty("user.dir")
					+ "/src/main/resources/" + "toolbar_open.png");
			quii = new Image(dev, System.getProperty("user.dir")
					+ "/src/main/resources/" + "toolbar_quit.png");

		} catch (Exception e) {

			System.err.println("Cannot load images: " + e.getMessage());
			System.exit(1);
		}

		ToolBar toolBar = new ToolBar(shell, SWT.BORDER);

		ToolItem item1 = new ToolItem(toolBar, SWT.PUSH);
		item1.setImage(newi);

		ToolItem item2 = new ToolItem(toolBar, SWT.PUSH);
		item2.setImage(opei);

		ToolItem separator = new ToolItem(toolBar, SWT.SEPARATOR);

		ToolItem item3 = new ToolItem(toolBar, SWT.PUSH);
		item3.setImage(quii);

		toolBar.pack();

		item1.addListener(SWT.Selection, event -> {

			/*
			 * System.setProperty("webdriver.chrome.driver",
			 * "c:/java/selenium/chromedriver.exe"); driver = new ChromeDriver();
			 */
			// keymaster.js does not work with Firefox
			driver = new FirefoxDriver();
			driver.manage().timeouts().setScriptTimeout(30, TimeUnit.SECONDS);
			wait = new WebDriverWait(driver, flexibleWait);
			wait.pollingEvery(pollingInterval, TimeUnit.MILLISECONDS);
			driver.get(baseURL);
			driver.get("https://www.ryanair.com/ie/en/");
			// getPageContent does not work with standalone app.
			// driver.get(getPageContent("blankpage.html"));
		});

		item2.addListener(SWT.Selection, event -> {
			if (driver != null) {
				injectKeyMaster(Optional.<String> empty());
				// injectKeyMaster(Optional.of("key('o, enter, left', function(event,
				// handler){ window.alert('o, enter or left pressed on target = ' +
				// event.target.toString() + ' srcElement = ' +
				// event.srcElement.toString() + ' !');});"));
			}
		});
		item3.addListener(SWT.Selection, event -> {
			if (driver != null) {
				driver.quit();
			}
			shell.getDisplay().dispose();
			System.exit(0);
		});

		shell.setText("Simple toolbar");
		shell.setSize(300, 250);
		shell.open();

		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
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

	private void injectKeyMaster(Optional<String> script) {
		ArrayList<String> scripts = new ArrayList<String>(Arrays
				.asList(getScriptContent("ElementSearch.js" /* "keymaster.js" */ )));
		if (script.isPresent()) {
			scripts.add(script.get());
		}
		for (String s : scripts) {
			if (s != null)
				System.err.println(
						String.format("Adding the script: %s...", s.substring(0, 100)));
			executeScript(s);
		}
	}

	private String getScriptContent(String scriptName) {
		System.err.println("Getting script content: " + scriptName);
		try {
			final InputStream stream = this.getClass().getClassLoader()
					.getResourceAsStream(scriptName);
			final byte[] bytes = new byte[stream.available()];
			stream.read(bytes);
			return new String(bytes, "UTF-8");
		} catch (IOException e) {
			throw new RuntimeException(scriptName);
		}
	}

	// does not work in the standalone app
	private String getPageContent(String pageName) {
		try {
			URI uri = this.getClass().getClassLoader().getResource(pageName).toURI();
			System.err.println(
					String.format("Getting URL to %s : %s", pageName, uri.toString()));
			return uri.toString();
		} catch (URISyntaxException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void finalize() {

		newi.dispose();
		opei.dispose();
		quii.dispose();
	}

	public static void main(String[] args) {

		Display display = new Display();
		SimpleToolBarEx ex = new SimpleToolBarEx(display);
		ex.finalize();
		display.dispose();
	}
}