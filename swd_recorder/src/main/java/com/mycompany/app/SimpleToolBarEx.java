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

import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.layout.GridDataFactory;

import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.custom.StyledText;
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

import org.mihalis.opal.breadcrumb.*;

import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

public class SimpleToolBarEx {

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

	private RowLayout layout;
	private Composite composite;
	private static String[] Labels = { "Open application",
			"Navigate to login page", "Something else" };

	private static int width = 900;
	private static int height = 800;
	private static int step_index = 0;
	private static String osName = null;

	private Breadcrumb bc;
	private ToolBar toolBar;

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
		Shell shell = new Shell(display, SWT.CENTER | SWT.SHELL_TRIM); // (~SWT.RESIZE)));
		Rectangle boundRect = new Rectangle(0, 0, 768, 324);
		shell.setBounds(boundRect);
		Device dev = shell.getDisplay();

		try {

			launch_icon = new Image(dev, getResourcePath("launch.png"));
			find_icon = new Image(dev, getResourcePath("find.png"));
			preferences_icon = new Image(dev, getResourcePath("preferences.png"));
			shutdown_icon = new Image(dev, getResourcePath("quit.png"));
			demo_icon = new Image(dev, getResourcePath("demo.png"));
			page_icon = new Image(dev, getResourcePath("page.png"));
			flowchart_icon = new Image(dev, getResourcePath("flowchart.png"));
			open_icon = new Image(dev, getResourcePath("open.png"));
			save_icon = new Image(dev, getResourcePath("save.png"));

		} catch (Exception e) {

			System.err.println("Cannot load images: " + e.getMessage());
			System.exit(1);
		}

		bc = new Breadcrumb(shell, SWT.BORDER);
		// GridData cannot be cast to RowData
		// bc.setLayoutData(
		// new GridData(GridData.BEGINNING, GridData.CENTER, false, false));

		toolBar = new ToolBar(shell, SWT.BORDER | SWT.HORIZONTAL);

		ToolItem launch_tool = new ToolItem(toolBar, SWT.PUSH);
		launch_tool.setImage(launch_icon);
		launch_tool.setToolTipText("Launches the browser");

		ToolItem find_icon_tool = new ToolItem(toolBar, SWT.PUSH);
		find_icon_tool.setImage(find_icon);
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

		toolBar.pack();

		// preferences_tool.setEnabled(false);
		find_icon_tool.setEnabled(false);
		flowchart_tool.setEnabled(false);
		demo_tool.setEnabled(false);
		save_tool.setEnabled(false);
		layout = new RowLayout();

		layout.wrap = true;
		layout.marginLeft = 5;
		layout.marginTop = 5;
		layout.marginRight = 5;
		layout.marginBottom = 5;

		shell.setLayout(layout);

		Label status = new Label(shell, SWT.BORDER);
		status.setText("Loading");
		status.pack();
		shell.pack();
		launch_tool.addListener(SWT.Selection, event -> {
			launch_tool.setEnabled(false);
			// Currently testing with Firefox on Linux and OSX and with Chrome on
			// Windows
			// System.err.println("OS: " + getOsName());
			if (osName.startsWith("Windows")) {
				System.setProperty("webdriver.chrome.driver",
						"c:/java/selenium/chromedriver.exe");
				driver = new ChromeDriver();
			} else {
				driver = new FirefoxDriver();
			}

			driver.manage().timeouts().pageLoadTimeout(50, TimeUnit.SECONDS)
					.implicitlyWait(implicitWait, TimeUnit.SECONDS)
					.setScriptTimeout(30, TimeUnit.SECONDS);
			driver.get(baseURL);
			launch_tool.setEnabled(true);
			find_icon_tool.setEnabled(true);
			demo_tool.setEnabled(true);
			flowchart_tool.setEnabled(true);
			// driver.get(getResourceURI("blankpage.html"));
		});

		flowchart_tool.addListener(SWT.Selection, event -> {
			shell.setData("Nothing here\n yet...");
			ScrolledTextChildShellEx test = new ScrolledTextChildShellEx(
					Display.getCurrent(), shell);
		});

		open_tool.addListener(SWT.Selection, event -> {
			FileDialog dialog = new FileDialog(shell, SWT.OPEN);
			String[] filterNames = new String[] { "YAML sources", "All Files (*)" };
			String[] filterExtensions = new String[] { "*.yaml", "*" };
			dialog.setFilterNames(filterNames);
			dialog.setFilterExtensions(filterExtensions);
			configFilePath = dialog.open();
      System.err.println("Loading " + configFilePath );
			config = YamlHelper.loadConfiguration(configFilePath);
			// TODO:  better method names
			YamlHelper.saveConfiguration(config);
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
					wait = new WebDriverWait(driver, flexibleWait);
					wait.pollingEvery(pollingInterval, TimeUnit.MILLISECONDS);
					actions = new Actions(driver);
					injectElementSearch(Optional.<String> empty());
					String name = "";
					HashMap<String, String> elementData = new HashMap<String, String>(); // empty
					Boolean waitingForData = true;
					while (waitingForData) {
						String payload = executeScript(getCommand).toString();
						if (!payload.isEmpty()) {
							// objects cannot suicide
							elementData = new HashMap<String, String>();
							name = readVisualSearchResult(payload, Optional.of(elementData));
							if (name == null || name.isEmpty()) {
								System.err.println("Rejected visual search");

							} else {
								System.err.println(String
										.format("Received element data of the step: '%s'", name));
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
					final BreadcrumbItem item = new BreadcrumbItem(bc,
							SWT.CENTER | SWT.TOGGLE);
					String commandId = elementData.get("CommandId");
					System.err.println("Sending : " + commandId);
					testData.put(commandId, elementData);
					stepKeys.add(commandId);
					item.setData("CommandId", commandId);
					item.setText(
							String.format("Step %d: %s", (int) (step_index + 1), name));
					item.setImage(page_icon);
					item.setSelectionImage(page_icon);
					item.addSelectionListener(new SelectionAdapter() {
						@Override
						public void widgetSelected(final SelectionEvent e) {
							String commandId = e.item.getData("CommandId").toString();
							assertThat(stepKeys, hasItem(commandId));
							assertTrue(testData.containsKey(commandId));
							HashMap<String, String> elementData = testData.get(commandId);
							System.out.println(
									String.format("Clicked %s / %s", item.getText(), commandId));
							shell.setData("CurrentCommandId", commandId);
							ChildShell cs = new ChildShell(Display.getCurrent(), shell);
							for (String key : elementData.keySet()) {
								// System.err.println(key + ": " + elementData.get(key));
								cs.setData(key, elementData.get(key));
							}
							cs.render();
						}
					});
					step_index++;
					shell.layout(true, true);
					final Point newSize = shell.computeSize(SWT.DEFAULT, SWT.DEFAULT,
							true);
					if (newSize.x > 500) {
						shell.setBounds(boundRect);
					}
					shell.pack();
					find_icon_tool.setEnabled(true);
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
					demo_tool.setEnabled(false);
					wait = new WebDriverWait(driver, flexibleWait);
					wait.pollingEvery(pollingInterval, TimeUnit.MILLISECONDS);
					actions = new Actions(driver);
					driver.manage().window()
							.setPosition(new org.openqa.selenium.Point(600, 0));
					driver.manage().window().setSize(new Dimension(width, height));
					driver.get("https://www.ryanair.com/ie/en/");
					wait.until(
							ExpectedConditions.visibilityOf(driver.findElement(By.cssSelector(
									"#home div.specialofferswidget h3 > span:nth-child(1)"))));
					injectElementSearch(Optional.<String> empty());
					// NOTE: with FF the CONTROL modifier appears to not be sent
					try {
						Thread.sleep(500);
					} catch (InterruptedException exception) {
					}
					WebElement element = wait.until(
							ExpectedConditions.visibilityOf(driver.findElement(By.cssSelector(
									"#home div.specialofferswidget h3 > span:nth-child(1)"))));
					highlight(element);
					executeScript(String.format("scroll(0, %d);", -100));
					actions.keyDown(Keys.CONTROL).build().perform();
					actions.moveToElement(element).contextClick().build().perform();
					actions.keyUp(Keys.CONTROL).build().perform();
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
					}

					completeVisualSearch("element name");
					String payload = executeScript(getCommand).toString();
					assertFalse(payload.isEmpty());
					// String name = readVisualSearchResult(payload);

					HashMap<String, String> data = new HashMap<String, String>();
					String name = readVisualSearchResult(payload, Optional.of(data));

					closeVisualSearch();
					flushVisualSearchResult();
					Button button = new Button(shell, SWT.PUSH);
					button.setText(
							String.format("Step %d: %s", (int) (step_index + 1), name));
					button.setData("origin", data);
					button.setData("text",
							String.format("Step %d: %s", (int) (step_index + 1), name));

					button.addListener(SWT.Selection, new Listener() {
						@Override
						public void handleEvent(Event event) {
							Object into = event.widget.getData("origin");
							String text = (String) event.widget.getData("text");
							boolean answer = MessageDialog.openConfirm(shell,
									button.getText(), String.format("Details of %s...", text));
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

					final Point newSize = shell.computeSize(SWT.DEFAULT, SWT.DEFAULT,
							true);
					if (newSize.x > 500) {
						shell.setBounds(boundRect);
					}
					shell.pack();
					demo_tool.setEnabled(true);
				}
			}
		});

		shutdown_tool.addListener(SWT.Selection, event -> {
			if (driver != null) {
				shutdown_tool.setEnabled(false);
				try {
					driver.close();
					// driver.quit();
				} catch (Exception e) {
					System.err.println("Ignored exception: " + e.toString());
					// WARNING: Process refused to die after 10 seconds,
					// and couldn't taskkill it
					// java.lang.NullPointerException: Unable to find executable for:
					// taskkill
					// when run from Powershell
				}
				shutdown_tool.setEnabled(true);
			}
			shell.getDisplay().dispose();
			System.exit(0);
		});

		status.setText("Ready");
		status.pack();
		shell.pack();
		shell.setText("Selenium WebDriver Eclipse Toolkit");
		// shell.setSize(500, 250);
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

	private void flushVisualSearchResult() {
		executeScript("document.swdpr_command = undefined;");
	}

	private String readVisualSearchResult(final String payload,
			Optional<HashMap<String, String>> parameters) {
		System.err.println("Processing payload: " + payload);
		Boolean collectResults = parameters.isPresent();
		HashMap<String, String> collector = (collectResults) ? parameters.get()
				: new HashMap<String, String>();
		try {
			JSONObject payloadObj = new JSONObject(payload);
			Iterator<String> payloadKeyIterator = payloadObj.keys();
			while (payloadKeyIterator.hasNext()) {

				String itemKey = payloadKeyIterator.next();
				String itemVal = payloadObj.getString(itemKey);
				collector.put(itemKey, itemVal);
				/*
				 * JSONArray dataArray = resultObj.getJSONArray(key); for (int cnt = 0;
				 * cnt < dataArray.length(); cnt++) { System.err.println(key + " " +
				 * dataArray.get(cnt)); }
				 */
			}
		} catch (JSONException e) {

		}
		assertTrue(collector.containsKey("ElementId"));
		// NOTE: elementCodeName will not be set if
		// user clicked the SWD Table Close Button
		// ElementId is always set
		return collector.get("ElementCodeName");
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
			return javascriptExecutor.executeScript(script, arguments);
		} else {
			throw new RuntimeException("Script execution failed.");
		}
	}

	private void injectElementSearch(Optional<String> script) {
		ArrayList<String> scripts = new ArrayList<String>(
				Arrays.asList(getScriptContent("ElementSearch.js")));
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

	// getResourceURI does not work with standalone app.
	private String getResourceURI(String resourceFileName) {
		try {
			URI uri = this.getClass().getClassLoader().getResource(resourceFileName)
					.toURI();
			System.err.println(String.format("Getting URI to %s : %s",
					resourceFileName, uri.toString()));
			return uri.toString();
		} catch (URISyntaxException e) {
			throw new RuntimeException(e);
		}
	}

	private String getResourcePath(String resourceFileName) {
		return String.format("%s/src/main/resources/%s",
				System.getProperty("user.dir"), resourceFileName);
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
	}

	public static void main(String[] args) {

		Display display = new Display();
		SimpleToolBarEx ex = new SimpleToolBarEx(display);
		ex.finalize();
		display.dispose();
	}

	private static class TestConfigurationParser {

		private static Scanner loadTestData(final String fileName) {
			Scanner scanner = null;
			System.err
					.println(String.format("Reading configuration file: '%s'", fileName));
			try {
				scanner = new Scanner(new File(fileName));
			} catch (FileNotFoundException e) {
				// fail(String.format("File '%s' was not found.", fileName));
				System.err.println(
						String.format("Configuration file was not found: '%s'", fileName));
				e.printStackTrace();
			}
			return scanner;
		}

		public static List<String[]> getConfiguration(final String fileName) {
			ArrayList<String[]> listOfData = new ArrayList<>();
			Scanner scanner = loadTestData(fileName);
			String separator = "|";
			while (scanner.hasNext()) {
				String line = scanner.next();
				String[] data = line.split(Pattern.compile("(\\||\\|/)")
						.matcher(separator).replaceAll("\\\\$1"));
				for (String entry : data) {
					System.err.println("data entry: " + entry);
				}
				listOfData.add(data);
			}
			scanner.close();
			return listOfData;
		}
	}

	private static class PropertiesParser {
		public static HashMap<String, String> getProperties(final String fileName) {
			Properties p = new Properties();
			HashMap<String, String> propertiesMap = new HashMap<String, String>();
			System.err
					.println(String.format("Reading properties file: '%s'", fileName));
			try {
				p.load(new FileInputStream(fileName));
				Enumeration<String> e = (Enumeration<String>) p.propertyNames();
				for (; e.hasMoreElements();) {
					String key = e.nextElement();
					String val = p.get(key).toString();
					System.out.println(String.format("Reading: '%s' = '%s'", key, val));
					propertiesMap.put(key, val);
				}

			} catch (FileNotFoundException e) {
				System.err.println(
						String.format("Properties file was not found: '%s'", fileName));
				e.printStackTrace();
			} catch (IOException e) {
				System.err.println(
						String.format("Properties file is not readable: '%s'", fileName));
				e.printStackTrace();
			}
			return (propertiesMap);

		}
	}

	private static class YamlHelper {

		private static DumperOptions options = new DumperOptions();
		private static Yaml yaml = null;

		private static Configuration loadConfiguration(String fileName) {
			if (yaml == null) {
				options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
				yaml = new Yaml(options);
			}
			Configuration config = null;
			try (InputStream in = Files.newInputStream(Paths.get(fileName))) {
				config = yaml.loadAs(in, Configuration.class);
				// TODO: better method naming
				YamlHelper.saveConfiguration(config);

			} catch (IOException e) {
				e.printStackTrace();
			}
			return config;
		}

		private static void saveConfiguration(Configuration config) {
			saveConfiguration(config, null);
		}

		@SuppressWarnings("deprecation")
		private static void saveConfiguration(Configuration config,
				String fileName) {
			if (yaml == null) {
				options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
				yaml = new Yaml(options);
			}
			String pattern = "yyyy-MM-dd";
			SimpleDateFormat format = new SimpleDateFormat(pattern);
			Date date = new Date();
			try {
				config.updated = format.parse(String.format("%4d-%2d-%2d",
						date.getYear(), date.getMonth(), date.getDay()));
			} catch (java.text.ParseException e) {
				config.updated = date;
			}
			if (fileName != null) {
				try {
					Writer out = new OutputStreamWriter(new FileOutputStream(fileName),
							"UTF8");
					yaml.dump(config, out);
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {
				System.err.println(yaml.dump(config));
			}
		}
	}
}

// https://www.chrisnewland.com/swt-best-practice-single-display-multiple-shells-111
class ChildShell {

	private Shell shell;
	private String commandId;
	private Display display;
	private HashMap<String, String> data = new HashMap<String, String>(); // empty

	ChildShell(Display parentDisplay, Shell parent) {
		commandId = parent.getData("CurrentCommandId").toString();
		display = parentDisplay;
		shell = new Shell(display);
	}

	public void setData(String key, String value) {
		data.put(key, value);
	}

	public void render() {
		for (String key : data.keySet()) {
			System.err.println(key + ": " + data.get(key));
		}
		shell.setSize(20, 20);
		shell.open();
		// shell.setText(String.format("Step %s details", commandId));
		final Label titleData = new Label(shell, SWT.SINGLE | SWT.BORDER);
		titleData
				.setText(String.format("Step %s details", data.get("ElementCodeName")));
		GridLayout gl = new GridLayout();
		gl.numColumns = 4;
		shell.setLayout(gl);
		shell.setLayout(gl);

		GridComposite gc = new GridComposite(shell);
		gc.renderData(data);
		GridData gd = new GridData(GridData.FILL_BOTH);
		gd.horizontalSpan = 4;
		gc.setLayoutData(gd);
		gd = new GridData();

		RowComposite rc = new RowComposite(shell);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		rc.setLayoutData(gd);
		shell.addListener(SWT.Close, new Listener() {
			@Override
			public void handleEvent(Event event) {
				shell.dispose();
			}
		});

		shell.setSize(350, 280);

		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}

	}
}

class RowComposite extends Composite {

	final Button buttonOK;
	final Button buttonCancel;

	public RowComposite(Composite composite) {

		super(composite, SWT.NO_FOCUS);
		RowLayout rl = new RowLayout();
		rl.wrap = false;
		rl.pack = false;
		this.setLayout(rl);
		buttonOK = new Button(this, SWT.BORDER | SWT.PUSH);
		buttonOK.setText("OK");
		buttonOK.setSize(30, 20);
		buttonCancel = new Button(this, SWT.PUSH);
		buttonCancel.setText("Cancel");
		buttonCancel.setSize(30, 20);
		buttonCancel.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				// System.out.println("Cancel was clicked");
				composite.dispose();
			}
		});
		buttonOK.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event e) {
				composite.dispose();
			}
		});

	}
}

class GridComposite extends Composite {

	public GridComposite(Composite c) {
		super(c, SWT.BORDER);
		GridLayout gl = new GridLayout();
		gl.numColumns = 2;
		this.setLayout(gl);
	}

	public void renderData(HashMap<String, String> data) {
		final Button cssSelectorRadio = new Button(this, SWT.RADIO);
		cssSelectorRadio.setSelection(true);
		cssSelectorRadio.setText("Css Selector");
		cssSelectorRadio.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		final Text cssSelectorData = new Text(this, SWT.SINGLE | SWT.BORDER);
		cssSelectorData.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		cssSelectorData.setText(data.get("ElementCssSelector"));

		final Button xPathRadio = new Button(this, SWT.RADIO);
		xPathRadio.setSelection(false);
		xPathRadio.setText("XPath");
		xPathRadio.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		final Text xPathData = new Text(this, SWT.SINGLE | SWT.BORDER);
		xPathData.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		xPathData.setText(data.get("ElementXPath"));

		final Button idRadio = new Button(this, SWT.RADIO);
		idRadio.setSelection(false);
		idRadio.setText("ID");
		idRadio.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		final Text idData = new Text(this, SWT.SINGLE | SWT.BORDER);
		idData.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		idData.setText(data.get("ElementId"));

		final Button textRadio = new Button(this, SWT.RADIO);
		textRadio.setSelection(false);
		textRadio.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		textRadio.setText("Text");

		final Text textData = new Text(this, SWT.SINGLE | SWT.BORDER);
		textData.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		textData.setText("Text...");
	}

}

class ScrolledTextChildShellEx {

	protected Shell shell;
	private Text text;
	private String payload;

	ScrolledTextChildShellEx(Display display, Shell parent) {

		shell = new Shell(display);
		payload = (String) parent.getData();
		shell.setSize(20, 20);
		shell.open();

		createContents();
		shell.setSize(450, 300);
		shell.setText("Configuration");
		shell.addListener(SWT.Close, new Listener() {

			@Override
			public void handleEvent(Event event) {
				shell.dispose();
			}
		});

		try {
			while (!shell.isDisposed()) {
				if (!display.readAndDispatch()) {
					display.sleep();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void createContents() {
		shell.setText("Generated QA source");
		shell.setLayout(new GridLayout(2, false));

		Label lblDomain = new Label(shell, SWT.NONE);
		lblDomain.setLayoutData(GridDataFactory.fillDefaults().create());
		lblDomain.setText("Name of the script");

		text = new Text(shell, SWT.BORDER);
		text.setLayoutData(
				GridDataFactory.fillDefaults().grab(true, false).create());

		final StyledText styledText = new StyledText(shell,
				SWT.BORDER | SWT.V_SCROLL | SWT.MULTI | SWT.WRAP);
		styledText.setLayoutData(
				GridDataFactory.fillDefaults().grab(true, true).span(2, 1).create());
		styledText.setText(payload);
		Button btnWhois = new Button(shell, SWT.NONE);
		btnWhois.setText("Save");
	}
}
