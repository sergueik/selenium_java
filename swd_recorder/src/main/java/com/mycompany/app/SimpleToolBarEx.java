package com.mycompany.app;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.lang.RuntimeException;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.concurrent.TimeUnit;
import java.util.Date;
import java.util.Enumeration;
import java.util.Formatter;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;
import java.util.Scanner;
import java.util.Set;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.lang.Boolean.*;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;

import org.hamcrest.CoreMatchers;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.endsWith;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.CoreMatchers.startsWith;
// import static org.hamcrest.MatcherAssert.assertThat;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoAlertPresentException;
// import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.internal.ProfilesIni;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.HasInputDevices;
import org.openqa.selenium.interactions.internal.Coordinates;
import org.openqa.selenium.interactions.Mouse;
import org.openqa.selenium.internal.Locatable;
import org.openqa.selenium.remote.UnreachableBrowserException;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import org.eclipse.swt.SWT;

import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.layout.GridDataFactory;

import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.MenuDetectEvent;
import org.eclipse.swt.events.MenuDetectListener;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.ExpandBar;
import org.eclipse.swt.widgets.ExpandItem;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Item;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.wb.swt.SWTResourceManager;

import org.mihalis.opal.breadcrumb.*;

import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

import com.mycompany.app.Utils;

public class SimpleToolBarEx {

	private Shell shell;

	private Image launch_icon;
	private Image find_icon;
	private Image shutdown_icon;
	private Image preferences_icon;
	private Image demo_icon;
	private Image page_icon;
	private Image flowchart_icon;
	private Image open_icon;
	private Image save_icon;

	private Configuration config = null;
	private static String configFilePath;
	private WebDriver driver;
	private WebDriverWait wait;
	private Actions actions;
	private int flexibleWait = 5;
	private int implicitWait = 1;
	private long pollingInterval = 500;
	private String baseURL = "about:blank";
	private final String getCommand = "return document.swdpr_command === undefined ? '' : document.swdpr_command;";
	private ArrayList<String> stepKeys = new ArrayList<String>();
	private HashMap<String, HashMap<String, String>> testData = new HashMap<String, HashMap<String, String>>();
	private Label status;

	private static int width = 900;
	private static int height = 800;
	private static int step_index = 0;
	private static String osName = null;

	private Breadcrumb bc;

	public SimpleToolBarEx(Display display) {
		initUI(display);
	}

	public static String getOsName() {
		if (osName == null) {
			osName = System.getProperty("os.name");
		}
		return osName;
	}

	@SuppressWarnings("unused")
	public void initUI(Display display) {

		testData = new HashMap<String, HashMap<String, String>>();
		getOsName();
		shell = new Shell(display, SWT.CENTER | SWT.SHELL_TRIM); // (~SWT.RESIZE)));
		Rectangle boundRect = new Rectangle(0, 0, 768, 324);
		shell.setBounds(boundRect);
		shell.setImage(
				SWTResourceManager.getImage(this.getClass(), "/magnifier.ico"));
		Device dev = shell.getDisplay();

		try {

			launch_icon = new Image(dev, new Utils().getResourcePath("launch.png"));
			find_icon = new Image(dev, new Utils().getResourcePath("find.png"));
			preferences_icon = new Image(dev,
					new Utils().getResourcePath("preferences.png"));
			shutdown_icon = new Image(dev, new Utils().getResourcePath("quit.png"));
			demo_icon = new Image(dev, new Utils().getResourcePath("demo.png"));
			page_icon = new Image(dev, new Utils().getResourcePath("page.png"));
			flowchart_icon = new Image(dev,
					new Utils().getResourcePath("flowchart.png"));
			open_icon = new Image(dev, new Utils().getResourcePath("open.png"));
			save_icon = new Image(dev, new Utils().getResourcePath("save.png"));

		} catch (Exception e) {

			System.err.println("Cannot load images: " + e.getMessage());
			System.exit(1);
		}

		shell.setText(String.format("Selenium Webdriver Elementor Toolkit"));
		GridLayout gl = new GridLayout();
		gl.numColumns = 1;
		shell.setLayout(gl);

		ToolBar toolBar = new ToolBar(shell, SWT.BORDER | SWT.HORIZONTAL);

		ToolItem launch_tool = new ToolItem(toolBar, SWT.PUSH);
		launch_tool.setImage(launch_icon);
		launch_tool.setToolTipText("Launches the browser");

		ToolItem find_icon_tool = new ToolItem(toolBar, SWT.PUSH);
		find_icon_tool.setImage(find_icon);
		// setDisabledImage
		find_icon_tool.setToolTipText("Injects the script");

		ToolItem flowchart_tool = new ToolItem(toolBar, SWT.PUSH);
		flowchart_tool.setImage(flowchart_icon);
		flowchart_tool.setToolTipText("Generates the script");

		new ToolItem(toolBar, SWT.SEPARATOR);

		ToolItem open_tool = new ToolItem(toolBar, SWT.PUSH);
		open_tool.setImage(open_icon);
		open_tool.setToolTipText("Reads the saved session");

		ToolItem save_tool = new ToolItem(toolBar, SWT.PUSH);
		save_tool.setImage(save_icon);
		save_tool.setToolTipText("Saves the session");

		new ToolItem(toolBar, SWT.SEPARATOR);

		ToolItem demo_tool = new ToolItem(toolBar, SWT.PUSH);
		demo_tool.setImage(demo_icon);
		demo_tool.setToolTipText("Demonstrates the app");

		new ToolItem(toolBar, SWT.SEPARATOR);

		ToolItem preferences_tool = new ToolItem(toolBar, SWT.PUSH);
		preferences_tool.setImage(preferences_icon);
		preferences_tool.setToolTipText("Configures the app (disabled)");

		ToolItem shutdown_tool = new ToolItem(toolBar, SWT.PUSH);
		shutdown_tool.setImage(shutdown_icon);
		shutdown_tool.setToolTipText("Quits the app");

		// preferences_tool.setEnabled(false);
		find_icon_tool.setEnabled(false);
		flowchart_tool.setEnabled(false);
		demo_tool.setEnabled(false);
		// demo_tool.setGrayed(true);
		save_tool.setEnabled(false);

		toolBar.pack();

		Composite composite = new Composite(shell, SWT.BORDER);

		GridLayout gridLayout = new GridLayout();
		gridLayout.marginLeft = 5;
		gridLayout.marginTop = 5;
		gridLayout.marginRight = 5;
		gridLayout.marginBottom = 5;
		gridLayout.numColumns = 1;
		gridLayout.makeColumnsEqualWidth = false;
		composite.setLayout(gridLayout);

		Breadcrumb bc1 = new Breadcrumb(composite, SWT.BORDER);
		bc = bc1;
		composite.pack();

		status = new Label(shell, SWT.BORDER);
		status.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		status.setText("Loading ...");
		status.pack();
		shell.pack();
		launch_tool.addListener(SWT.Selection, event -> {
			launch_tool.setEnabled(false);
			status.setText("Launching the browser ...");
			status.pack();
			shell.pack();
			if (osName.toLowerCase().startsWith("windows")) {
				driver = BrowserDriver.initialize("chrome");
				/*
				// IE 10 works, IE 11 does not			
				driver = new InternetExplorerDriver(capabilities);
				*/
			} else if (osName.startsWith("Mac")) {
				driver = BrowserDriver.initialize("safari");
			} else {
				driver = BrowserDriver.initialize("firefox");
			}

			driver.manage().timeouts().pageLoadTimeout(50, TimeUnit.SECONDS)
					.implicitlyWait(implicitWait, TimeUnit.SECONDS)
					.setScriptTimeout(30, TimeUnit.SECONDS);
			driver.get(baseURL);
			// prevent the customer from launching multiple instances
			// launch_tool.setEnabled(true);
			find_icon_tool.setEnabled(true);
			if (!osName.startsWith("Mac")) {
				// TODO: add a sorry dialog for Mac / Safari, any OS / Firefox
				// combinations
				demo_tool.setEnabled(true);
			}
			flowchart_tool.setEnabled(true);
			// driver.get(getResourceURI("blankpage.html"));
			status.setText("Ready ...");
			status.pack();
		});

		flowchart_tool.addListener(SWT.Selection, event -> {
			// TODO: context menu
			// System.err.println("Button: " + event.button);
			// if (event.button )
			shell.setData("Nothing here\n yet...");
			ScrolledTextEx test = new ScrolledTextEx(Display.getCurrent(), shell);
		});

		open_tool.addListener(SWT.Selection, event -> {
			FileDialog dialog = new FileDialog(shell, SWT.OPEN);
			String[] filterNames = new String[] { "YAML sources", "All Files (*)" };
			String[] filterExtensions = new String[] { "*.yaml", "*" };
			dialog.setFilterNames(filterNames);
			dialog.setFilterExtensions(filterExtensions);
			configFilePath = dialog.open();
			System.err.println("Loading " + configFilePath);
			config = YamlHelper.loadConfiguration(configFilePath);
			YamlHelper.printConfiguration(config);
		});

		save_tool.addListener(SWT.Selection, event -> {
			FileDialog dialog = new FileDialog(shell, SWT.SAVE);
			dialog.setFilterNames(new String[] { "YAML Files", "All Files (*.*)" });
			dialog.setFilterExtensions(new String[] { "*.yaml", "*.*" });
			String homeDir = System.getProperty("user.home");
			dialog.setFilterPath(homeDir); // Windows path
			dialog.setFileName(configFilePath);
			String path = dialog.open();
			System.out.println("Save to: " + path);
      if (config == null) {
        config = new Configuration();
      }
      config.setelements(testData);
			YamlHelper.saveConfiguration(config, path);
		});

		preferences_tool.addListener(SWT.Selection, event -> {

		});

		find_icon_tool.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetDefaultSelected(SelectionEvent event) {
			}

			@Override
			public void widgetSelected(SelectionEvent event) {
				if (driver != null) {
					find_icon_tool.setEnabled(false);
					status.setText("Injecting the script ...");
					status.pack();
					wait = new WebDriverWait(driver, flexibleWait);
					wait.pollingEvery(pollingInterval, TimeUnit.MILLISECONDS);
					actions = new Actions(driver);
					injectElementSearch(Optional.<String> empty());

					status.setText("Waiting for data ...");
					status.pack();
					HashMap<String, String> elementData = addElement();

					// 'paginate' the breadcrump
					if (bc.getBounds().width > 765) {
						Breadcrumb bc2 = new Breadcrumb(composite, SWT.BORDER);
						bc = bc2;
					}
					String commandId = elementData.get("CommandId");

					testData.put(commandId, elementData);
					stepKeys.add(commandId);
					addBreadCrumpItem(elementData.get("ElementCodeName"), commandId,
							elementData, bc);
					shell.layout(true, true);
					shell.pack();
					find_icon_tool.setEnabled(true);
					status.setText("Ready ...");
					status.pack();
					save_tool.setEnabled(true);
				}
			}
		});

		demo_tool.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetDefaultSelected(SelectionEvent event) {
			}

			@Override
			public void widgetSelected(SelectionEvent event) {
				if (driver != null) {
					status.setText("Running the demo ...");
					status.pack();
					demo_tool.setEnabled(false);
					wait = new WebDriverWait(driver, flexibleWait);
					wait.pollingEvery(pollingInterval, TimeUnit.MILLISECONDS);
					actions = new Actions(driver);
					driver.manage().window()
							.setPosition(new org.openqa.selenium.Point(600, 0));
					driver.manage().window().setSize(new Dimension(width, height));
					// TODO: debug timeoutException in closeVisualSearch
					HashMap<String, String> elementData = demoAddElement(
							"https://www.ryanair.com/ie/en/", By.cssSelector(
									"#home div.specialofferswidget h3 > span:nth-child(1)"));
					String name = elementData.get("ElementCodeName");
					addButton(name, elementData, composite);
					final Point newSize = shell.computeSize(SWT.DEFAULT, SWT.DEFAULT,
							true);
					if (newSize.x > 500) {
						shell.setBounds(boundRect);
					}
					status.setText("Ready ...");
					status.pack();
					shell.pack();
					demo_tool.setEnabled(true);
				}
			}
		});

		shutdown_tool.addListener(SWT.Selection, event -> {
			status.setText("Shutting down ...");
			status.pack();
			if (driver != null) {
				shutdown_tool.setEnabled(false);
				try {
					BrowserDriver.close();
				} catch (Exception e) {
					System.err.println("Ignored exception: " + e.toString());
				}
				shutdown_tool.setEnabled(true);
			}
			shell.getDisplay().dispose();
			System.exit(0);
		});

		status.setText("Ready ...");
		status.pack();
		shell.pack();
		shell.setText("Selenium WebDriver Eclipse Toolkit");
		shell.open();

		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	String readVisualSearchResult(String payload) {
		return readVisualSearchResult(payload,
				Optional.<HashMap<String, String>> empty());
	}

	private String readVisualSearchResult(final String payload,
			Optional<HashMap<String, String>> parameters) {
		System.err.println("Processing payload: " + payload);
		Boolean collectResults = parameters.isPresent();
		HashMap<String, String> collector = (collectResults) ? parameters.get()
				: new HashMap<String, String>();
		String result = new Utils().readData(payload, Optional.of(collector));
		assertTrue(collector.containsKey("ElementId"));
		// NOTE: elementCodeName will not be set if
		// user clicked the SWD Table Close Button
		// ElementId is always set
		return result;
	}

	private void flushVisualSearchResult() {
		executeScript("document.swdpr_command = undefined;");
	}

	private String getCurrentUrl() {
		return driver.getCurrentUrl();
	}

	private HashMap<String, String> addElement() {

		HashMap<String, String> elementData = new HashMap<String, String>(); // empty
		Boolean waitingForData = true;
		String name = null;
		while (waitingForData) {
			String payload = executeScript(getCommand).toString();
			if (!payload.isEmpty()) {
				// objects cannot suicide
				elementData = new HashMap<String, String>();
				name = readVisualSearchResult(payload, Optional.of(elementData));
				if (name == null || name.isEmpty()) {
					System.err.println("Rejected unfinished visual search");
				} else {
					System.err.println(String
							.format("Received element data of the element: '%s'", name));
					elementData.put("ElementPageURL", getCurrentUrl());
					waitingForData = false;
					break;
				}
			}
			if (waitingForData) {
				try {
					// TODO: add the code to
					// check if waited long enough already
					// test17
					System.err.println("Waiting: ");
					Thread.sleep(1000);
				} catch (InterruptedException exception) {
				}
			}
		}
		// clear results on the page
		flushVisualSearchResult();
		closeVisualSearch();
		return elementData;
	}

	private HashMap<String, String> demoAddElement(String URL, By by) {
		driver.get(URL);
		wait.until(ExpectedConditions.visibilityOf(driver.findElement(by)));
		injectElementSearch(Optional.<String> empty());
		// NOTE: with FF the CONTROL mouse pointer appears to be misplaced
		try {
			Thread.sleep(500);
		} catch (InterruptedException exception) {
		}
		WebElement element = wait
				.until(ExpectedConditions.visibilityOf(driver.findElement(by)));
		highlight(element);
		if (osName.startsWith("Mac")) {
			// "Demo" functionality appears to be currently broken on Mac with
			// not passing the Keys.COMMAND
			try {
				actions.keyDown(Keys.COMMAND).build().perform();
				actions.moveToElement(element).contextClick().build().perform();
				actions.keyUp(Keys.COMMAND).build().perform();
			} catch (WebDriverException e) {
				// TODO: print a message box
				System.err.println("Ignoring exception: " + e.toString());
			}
		} else {
			actions.keyDown(Keys.CONTROL).build().perform();
			actions.moveToElement(element).contextClick().build().perform();
			actions.keyUp(Keys.CONTROL).build().perform();
		}

		executeScript(String.format("scroll(0, %d);", -400));
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
		}

		completeVisualSearch("element name");
		String payload = executeScript(getCommand).toString();
		assertFalse(payload.isEmpty());
		HashMap<String, String> data = new HashMap<String, String>();
		String name = readVisualSearchResult(payload, Optional.of(data));
		closeVisualSearch();
		flushVisualSearchResult();
		return data;
	}

	private void highlight(WebElement element) {
		highlight(element, 100);
	}

	private void highlight(WebElement element, long highlight_interval) {
		if (wait == null) {
			wait = new WebDriverWait(driver, flexibleWait);
		}
		wait.pollingEvery(pollingInterval, TimeUnit.MILLISECONDS);
		try {
			wait.until(ExpectedConditions.visibilityOf(element));
			executeScript("arguments[0].style.border='3px solid yellow'", element);
			Thread.sleep(highlight_interval);
			executeScript("arguments[0].style.border=''", element);
		} catch (InterruptedException e) {
			System.err.println("Ignored: " + e.toString());
		}
	}

	private void completeVisualSearch(String elementCodeName) {
		WebElement swdControl = wait.until(
				ExpectedConditions.visibilityOf(driver.findElement(By.id("SWDTable"))));
		assertThat(swdControl, notNullValue());

		// System.err.println("Swd Control:" +
		// swdControl.getAttribute("innerHTML"));
		WebElement swdCodeID = wait.until(ExpectedConditions
				.visibilityOf(swdControl.findElement(By.id("SwdPR_PopUp_CodeIDText"))));
		assertThat(swdCodeID, notNullValue());
		swdCodeID.sendKeys(elementCodeName);
		WebElement swdAddElementButton = wait
				.until(ExpectedConditions.visibilityOf(swdControl.findElement(
						By.xpath("//input[@type='button'][@value='Add element']"))));
		assertThat(swdAddElementButton, notNullValue());
		highlight(swdAddElementButton);
		// Act
		swdAddElementButton.click();
	}

	private void closeVisualSearch() {
		WebElement swdControl = wait.until(
				ExpectedConditions.visibilityOf(driver.findElement(By.id("SWDTable"))));
		assertThat(swdControl, notNullValue());

		WebElement swdCloseButton = wait.until(ExpectedConditions.visibilityOf(
				swdControl.findElement(By.id("SwdPR_PopUp_CloseButton"))));
		assertThat(swdCloseButton, notNullValue());
		highlight(swdCloseButton);
		swdCloseButton.click();
	}

	private Object executeScript(String script, Object... arguments) {
		if (driver instanceof JavascriptExecutor) {
			JavascriptExecutor javascriptExecutor = JavascriptExecutor.class
					.cast(driver);
			// IE: org.openqa.selenium.NoSuchWindowException
			// Chrome: Exception in thread "main"
			// org.openqa.selenium.WebDriverException: disconnected: not connected to
			// DevTools
			return javascriptExecutor.executeScript(script, arguments);
		} else {
			throw new RuntimeException("Script execution failed.");
		}
	}

	private void injectElementSearch(Optional<String> script) {
		ArrayList<String> scripts = new ArrayList<String>(
				Arrays.asList(new Utils().getScriptContent("ElementSearch.js")));
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

	// adds a bredCrump item to BreadCrump canvas with attached Shell / Form for
	// editing
	private void addBreadCrumpItem(String name, String commandId,
			HashMap<String, String> data, Breadcrumb bc) {
		Rectangle rect = bc.getBounds();
		// System.err.println("Bound rect width: " + rect.width);
		final BreadcrumbItem item = new BreadcrumbItem(bc, SWT.CENTER | SWT.TOGGLE);
		item.setData("CommandId", commandId);
		item.setText(String.format("Step %d: %s", (int) (step_index + 1), name));
		item.setImage(page_icon);
		item.setSelectionImage(page_icon);

		// NOTE: MouseDown event is not received
		item.addListener(SWT.MouseDown, new Listener() {
			@Override
			public void handleEvent(Event event) {
				System.err.println("MouseDown Button: " + event.button);
				if (event.button == 3) {
				}
			}

		});

		item.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(final SelectionEvent e) {
				String commandId = e.item.getData("CommandId").toString();
				assertThat(stepKeys, hasItem(commandId));
				assertTrue(testData.containsKey(commandId));
				HashMap<String, String> elementData = testData.get(commandId);
				System.err.println(
						String.format("Clicked %s / %s", item.getText(), commandId));
				shell.setData("CurrentCommandId", commandId);
				shell.setData("updated", false);
				// spawn a separate shell for editing the element attributes
				ComplexFormEx cs = new ComplexFormEx(Display.getCurrent(), shell);
				for (String key : elementData.keySet()) {
					// System.err.println(key + ": " + elementData.get(key));
					cs.setData(key, elementData.get(key));
				}
				cs.render();
				if ((Boolean) shell.getData("updated")) {
					HashMap<String, String> data = new HashMap<String, String>();
					// form sets result to the modified element attributes JSON
					String name = new Utils().readData((String) shell.getData("result"),
							Optional.of(data));
					if (name != null) {
						testData.replace(commandId, data);
						// or an empty JSON when element is deleted,
					} else {
						// Clear test data
						testData.remove(commandId);
						// Remove the current item
						item.getParent().removeItem(item);
					}
				}
			}
		});
		step_index++;
	}

	// Adds a button with attached dialog
	private void addButton(String name, HashMap<String, String> data,
			Composite composite) {

		Button button = new Button(composite, SWT.PUSH | SWT.BORDER);
		button.setText(String.format("Step %d: %s", (int) (step_index + 1), name));
		button.setData("origin", data);
		button.setData("text",
				String.format("Step %d: %s", (int) (step_index + 1), name));

		button.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event event) {
				Object into = event.widget.getData("origin");
				String text = (String) event.widget.getData("text");
				boolean answer = MessageDialog.openConfirm(shell, button.getText(),
						String.format("Details of %s...", text));
			}
		});

		step_index++;
		Control[] children = shell.getChildren();
		if (children.length == 0) {
			button.setParent(shell);
		} else {
			button.moveBelow(children[children.length - 1]);
		}
		shell.layout(new Control[] { button });
	}

	@Override
	public void finalize() {
		launch_icon.dispose();
		find_icon.dispose();
		shutdown_icon.dispose();
		preferences_icon.dispose();
		page_icon.dispose();
		demo_icon.dispose();
		flowchart_icon.dispose();
		open_icon.dispose();
		save_icon.dispose();
	}

	public static void main(String[] args) {

		Display display = new Display();
		SimpleToolBarEx ex = new SimpleToolBarEx(display);
		ex.finalize();
		display.dispose();
	}
}
