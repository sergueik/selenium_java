package example;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;

import org.apache.log4j.Category;
import org.apache.log4j.PropertyConfigurator;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.json.JSONException;
import org.json.JSONObject;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.NativeMapped;
import com.sun.jna.PointerType;
import com.sun.jna.win32.W32APIFunctionMapper;
import com.sun.jna.win32.W32APITypeMapper;

/**
 * YAML Configuration browser  
 * @author: Serguei Kouzmine (kouzmine_serguei@yahoo.com)
 * 
 */

public class ConfigFormEx {

	private static Shell shell;
	private static Shell parentShell = null;
	private static Display display;

	private final static int formWidth = 476;
	private final static int formHeight = 308;
	private final static int buttonWidth = 120;
	private final static int buttonHeight = 28;
	private final static int labelWidth = 150;

	private static Boolean debug = false;

	private static String osName = getOsName();
	private static Map<String, String> configData = new HashMap<>();

	// NOTE: use the same DOM for Browser config options to simplify code
	// the values for "Browser" hash are not used
	private static Map<String, Map<String, String>> configOptions = new HashMap<>();
	private static Map<String, String> roles = new HashMap<>();

	@SuppressWarnings("deprecation")
	static final Category logger = Category.getInstance(ConfigFormEx.class);
	private static StringBuilder loggingSb = new StringBuilder();
	private static Formatter formatter = new Formatter(loggingSb, Locale.US);

	ConfigFormEx(Display parentDisplay, Shell parent) {

		initializeLogger();
		logger.info("Initialized logger.");

		Map<String, String> browserOptions = new HashMap<>();
		for (String value : new ArrayList<String>(Arrays.asList(
				new String[] { "Chrome", "Firefox", "Internet Explorer", "Safari" }))) {
			browserOptions.put(value, "unused");
		}
		configOptions.put("Browser", browserOptions);

		// TODO: dynamic
		Map<String, String> dataCenterOptions = new HashMap<>();
		for (String value : new ArrayList<String>(Arrays
				.asList(new String[] { "st.louis", "oxmoor", "shoreview", "wec" }))) {
			dataCenterOptions.put(value, "unused");
		}
		configOptions.put("Datacenter", dataCenterOptions);

		// TODO: dynamic
		Map<String, String> environmentOptions = new HashMap<>();
		for (String value : new ArrayList<String>(
				Arrays.asList(new String[] { "cert", "prod", "sandbox" }))) {
			environmentOptions.put(value, "unused");
		}
		configOptions.put("Environment", environmentOptions);

		// offer consul the default
		// configData.put("Role", "discovery server");

		configOptions.put("Role", new HashMap<String, String>());
		roles = configOptions.get("Role");
		// Scan the template directory and build the hash of role name / ""
		// options.
		configOptions.replace("Role", roles);

		display = (parentDisplay != null) ? parentDisplay : new Display();
		// shell = new Shell(display);
		shell = new Shell(display, SWT.CENTER | SWT.SHELL_TRIM/* | ~SWT.RESIZE */);
		if (parent != null) {
			parentShell = parent;
		}
		// http://stackoverflow.com/questions/585534/what-is-the-best-way-to-find-the-users-home-directory-in-java
		String dirPath = null;

		dirPath = "c:\\developer\\sergueik\\powershell_ui_samples";
		if (dirPath.isEmpty()) {
			dirPath = osName.startsWith("windows") ? getDesktopPath()
					: System.getProperty("user.home");
		}
		readData(parent != null ? parentShell.getData("CurrentConfig").toString()
				: "{ \"Browser\": \"Chrome\", " + "\"Role\": \"discovery server\", "
						+ "\"Environment\": \"prod\", " + "\"Datacenter\": \"wec\", "
						+ String.format("\"Template Directory\": \"%s\", ",
								dirPath.replace("/", "\\").replace("\\", "\\\\"))
						+ "\"Template Path\": \"\"}",
				Optional.of(configData));
		if (configData.containsKey("Template Directory")) {
			dirPath = configData.get("Template Directory");
			if (dirPath != "") {
				logger.info("Loading enc from: " + dirPath);
				roles = configOptions.get("Role");
				// TODO: Scan the template directory and rebuild the options hash with
				// template
				// name / absolute path
				configOptions.replace("Role", roles);
			}
		}
	}

	public void render() {
		shell.open();
		shell.setText("Session Configuration");

		GridLayoutFactory.swtDefaults().numColumns(1).applyTo(shell);

		GridComposite gridComposite = new GridComposite(shell);
		gridComposite.renderData(configData);
		gridComposite.setLayoutData(new GridData(GridData.FILL_BOTH));
		gridComposite.pack();

		Composite rowComposite = new Composite(shell, SWT.NO_FOCUS);

		GridDataFactory.swtDefaults().align(SWT.BEGINNING, SWT.TOP)
				.applyTo(rowComposite);

		GridLayoutFactory.swtDefaults().margins(2, 2).equalWidth(false)
				.numColumns(2).applyTo(rowComposite);

		Button launchButton = new Button(rowComposite, SWT.BORDER | SWT.PUSH);
		launchButton.setText("Launch");

		GridDataFactory.swtDefaults().align(SWT.BEGINNING, SWT.CENTER)
				.hint(buttonWidth, buttonHeight).grab(false, false)
				.applyTo(launchButton);

		launchButton.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event event) {
				String templateLabel = configData.get("Role");
				String configKey = null;
				String data = templateLabel;
				/*
				if (templateLabel != ""
						&& !(templateLabel.matches(".*\\(embedded\\)"))) {
					data = configOptions.get("Role").get(templateLabel);
					configKey = "Template Path";
					logger.info(String.format(
							"Saving the selected user template path \"%s\": \"%s\"",
							templateLabel, data));
				} else {
					configKey = "Role";
					data = templateLabel;
					logger.info(String.format(
							"Saving the selected embedded template name: \"%s\"",
							templateLabel));
				
				}
				if (configData.containsKey(configKey)) {
					configData.replace(configKey, data);
				} else {
					configData.put(configKey, data);
				}
				*/
				logger.info("Saving the selections: " + configData);
				String result = writeDataJSON(configData, "{}");
				if (parentShell != null) {
					parentShell.setData("CurrentConfig", result);
					parentShell.setData("updated", true);
				}
			}
		});

		Button closeButton = new Button(rowComposite, SWT.PUSH);
		closeButton.setText("Close");

		GridDataFactory.swtDefaults().align(SWT.BEGINNING, SWT.CENTER)
				.hint(buttonWidth, buttonHeight).grab(false, false)
				.applyTo(closeButton);

		closeButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				rowComposite.dispose();
				shell.dispose();
			}
		});

		rowComposite.pack();
		shell.pack();
		shell.setSize(formWidth, formHeight);

		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
	}

	private static class GridComposite extends Composite {

		public GridComposite(Composite composite) {
			super(composite, SWT.BORDER);
			GridLayout gridLayout = new GridLayout();
			gridLayout.numColumns = 2;
			this.setLayout(gridLayout);
		}

		public void renderData(Map<String, String> data) {
			for (String configKey : Arrays.asList("Datacenter", "Environment", "Role",
					"Template Directory", "Browser")) {
				if (configOptions.containsKey(configKey)) {
					logger.info("Processing " + configKey);
					final Label configLabel = new Label(this, SWT.NONE);
					configLabel.setText(configKey);
					// http://www.codejava.net/java-se/swing/jcombobox-basic-tutorial-and-examples
					// http://stackoverflow.com/questions/19800033/java-swt-list-make-it-unselectable
					final Combo configValue = new Combo(this, SWT.READ_ONLY);
					String[] items = configOptions.get(configKey).keySet()
							.toArray(new String[0]);
					configValue.setItems(items);
					logger.info(new ArrayList<String>(configData.keySet()));
					logger.info(String.format("Setting index of %s to %d",
							configData.get(configKey),
							Arrays.asList(items).indexOf(configData.get(configKey))));
					configValue
							.select(Arrays.asList(items).indexOf(configData.get(configKey)));
					configValue.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
					configValue.setData("key", configKey);

					configValue.addSelectionListener(new SelectionAdapter() {
						public void widgetSelected(SelectionEvent event) {
							Combo o = (Combo) event.widget;
							data.replace((String) o.getData("key"), o.getText());
							// TODO: validation process
							if (configValue.getText().equals("Safari")) {
							} else {
								/*
								configValue.add("Not Applicable");
								configValue.setText("Not Applicable");
								*/
							}
						}
					});
				} else {

					if (configKey.indexOf("Directory") >= 0) {

						DirBrowseComposite dirBrowseComposite3 = new DirBrowseComposite(
								this);
						GridData gridData = new GridData(GridData.FILL, GridData.FILL, true,
								true);
						gridData.horizontalSpan = 2;
						dirBrowseComposite3.setLayoutData(gridData);
						dirBrowseComposite3.renderData(data, configKey);
						dirBrowseComposite3.pack();
					} else {

						final Label configLabel = new Label(this, SWT.NONE);
						configLabel.setText(configKey);
						final Text configValue;
						configValue = new Text(this, SWT.SINGLE | SWT.BORDER);
						configValue.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
						if (data.containsKey(configKey)) {
							configValue.setText(data.get(configKey));
						} else {
							// configValue.setText(String.format("%s...", configKey));
						}
						configValue.setData("key", configKey);
						// see also:
						// http://gamedev.sleptlate.org/blog/107-swt-listeners-are-incompatible-with-java-lambdas/
						configValue.addListener(SWT.FocusOut, event -> {
							Text text = (Text) event.widget;
							logger.info(String.format("%s = %s", (String) text.getData("key"),
									text.getText()));
						});
						// TODO: defer to FocusEvent
						configValue.addModifyListener(new ModifyListener() {
							@Override
							public void modifyText(ModifyEvent event) {
								Text text = (Text) event.widget;
								String key = (String) text.getData("key");
								String value = text.getText();
								if (data.containsKey(key)) {
									data.replace(key, value);
								} else {
									data.put(key, value);
								}
							}
						});

					}
				}
			}
		}
	}

	private static class DirBrowseComposite extends Composite {

		private Shell shell;
		private int browseButtonWidth = 60;

		public DirBrowseComposite(Composite composite) {
			super(composite, SWT.NONE);
			shell = composite.getShell();
			this.setSize(this.getParent().getBounds().width,
					this.getParent().getBounds().height /* buttonHeight*/ );

			GridDataFactory.swtDefaults().align(SWT.BEGINNING, SWT.TOP)
					.grab(true, false).applyTo(this);
			GridLayoutFactory.swtDefaults().equalWidth(false).numColumns(3)
					.applyTo(this);
		}

		public void renderData(Map<String, String> data, String configKey) {

			Label label = new Label(this, SWT.NONE);
			label.setLayoutData(new GridData(labelWidth, SWT.DEFAULT));
			label.setText(configKey);

			final Text directory = new Text(this, SWT.SINGLE | SWT.BORDER);

			GridData gridData = new GridData(GridData.FILL, GridData.CENTER, true,
					false);
			gridData.widthHint = this.getBounds().width - browseButtonWidth;
			gridData.heightHint = buttonHeight;
			directory.setLayoutData(gridData);

			directory.setData("key", configKey);
			if (data.containsKey(configKey)) {
				directory.setText(data.get(configKey));
			}

			directory.addFocusListener(new FocusListener() {
				public void focusLost(FocusEvent event) {
					Text text = (Text) event.widget;
					logger.info(String.format("%s = %s", (String) text.getData("key"),
							text.getText()));
				}

				@Override
				public void focusGained(FocusEvent event) {
				}
			});

			directory.addModifyListener(new ModifyListener() {
				@Override
				public void modifyText(ModifyEvent event) {
					Text text = (Text) event.widget;
					String key = (String) text.getData("key");
					String value = text.getText();
					if (data.containsKey(key)) {
						data.replace(key, value);
					} else {
						data.put(key, value);
					}
				}
			});

			final Button browse = new Button(this, SWT.PUSH);
			gridData = new GridData(GridData.FILL, GridData.CENTER, false, false);
			gridData.widthHint = browseButtonWidth;
			gridData.heightHint = buttonHeight;
			browse.setLayoutData(gridData);
			browse.setText("Browse");
			browse.addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent event) {
					DirectoryDialog dialog = new DirectoryDialog(shell);

					dialog.setFilterPath(directory.getText());
					dialog.setText(String.format("%s Dialog", configKey));
					dialog.setMessage("Select a directory");

					String dir = dialog.open();
					Text text = directory;
					if (dir != null) {
						text.setText(dir);
						// dataCenterOptions = new HashMap<>();

						logger.info(String.format("Browser: %s = %s",
								(String) text.getData("key"), text.getText()));
					}
				}
			});
		}
	}

	public void setData(String key, String value) {
		configData.put(key, value);
	}

	public static void main(String[] arg) {
		ConfigFormEx configFormEx = new ConfigFormEx(null, null);
		ConfigFormEx.debug = true;
		configFormEx.render();
	}

	public void initializeLogger() {
		Properties logProperties = new Properties();
		String log4J_properties = String.format("%s/%s/%s",
				System.getProperty("user.dir"), "src/main/resources", "log4j.xml");
		try {
			logProperties.load(new FileInputStream(log4J_properties));
			PropertyConfigurator.configure(logProperties);
		} catch (IOException e) {
			throw new RuntimeException("Fail to load: " + log4J_properties);
		}
	}

	public String readData(Optional<Map<String, String>> parameters) {
		return readData(null, parameters);
	}

	public String readData(String payload,
			Optional<Map<String, String>> parameters) {

		logger.info("Reading data: " + payload);
		Map<String, String> collector = (parameters.isPresent()) ? parameters.get()
				: new HashMap<>();

		String data = (payload == null)
				? "{ \"Url\": \"http://www.google.com\", \"ElementCodeName\": \"Name of the element\", \"CommandId\": \"d5be4ea9-c51f-4e61-aefc-e5c83ba00be8\", \"ElementCssSelector\": \"html div.home-logo_custom > img\", \"ElementId\": \"\", \"ElementXPath\": \"/html//img[1]\" }"
				: payload;
		try {
			JSONObject elementObj = new JSONObject(data);
			Iterator<String> propIterator = elementObj.keys();
			while (propIterator.hasNext()) {
				String propertyKey = propIterator.next();
				String propertyVal = elementObj.getString(propertyKey);
				// logger.info(propertyKey + ": " + propertyVal);
				System.err.println("readData: " + propertyKey + ": " + propertyVal);
				collector.put(propertyKey, propertyVal);
			}
		} catch (JSONException e) {
			System.err.println("Exception (ignored): " + e.toString());
			return null;
		}
		return collector.get("ElementCodeName");
	}

	public static String getOsName() {
		if (osName == null) {
			osName = System.getProperty("os.name").toLowerCase();
			if (osName.startsWith("windows")) {
				osName = "windows";
			}
		}
		return osName;
	}

	static class HANDLE extends PointerType implements NativeMapped {
	}

	static class HWND extends HANDLE {
	}

	public static String getDesktopPath() {
		HWND hwndOwner = null;
		int nFolder = Shell32.CSIDL_DESKTOPDIRECTORY;
		HANDLE hToken = null;
		int dwFlags = Shell32.SHGFP_TYPE_CURRENT;
		char[] pszPath = new char[Shell32.MAX_PATH];
		int hResult = Shell32.INSTANCE.SHGetFolderPath(hwndOwner, nFolder, hToken,
				dwFlags, pszPath);
		if (Shell32.S_OK == hResult) {
			String path = new String(pszPath);
			return (path.substring(0, path.indexOf('\0')));
		} else {
			return String.format("%s\\Desktop", System.getProperty("user.home"));
		}
	}

	private static Map<String, Object> OPTIONS = new HashMap<>();
	static {
		OPTIONS.put(Library.OPTION_TYPE_MAPPER, W32APITypeMapper.UNICODE);
		OPTIONS.put(Library.OPTION_FUNCTION_MAPPER, W32APIFunctionMapper.UNICODE);
	}

	static interface Shell32 extends Library {

		public static final int MAX_PATH = 260;
		// https://sourceforge.net/u/cstrauss/w32api/ci/7805df8efec130f582b131b8c0d75e1b6ce0993b/tree/include/shlobj.h?format=raw
		public static final int CSIDL_DESKTOPDIRECTORY = 0x0010;
		public static final int SHGFP_TYPE_CURRENT = 0;
		public static final int SHGFP_TYPE_DEFAULT = 1;
		public static final int S_OK = 0;
		static Shell32 INSTANCE = Native.loadLibrary("shell32", Shell32.class,
				OPTIONS);

		public int SHGetFolderPath(HWND hwndOwner, int nFolder, HANDLE hToken,
				int dwFlags, char[] pszPath);
	}

	public String writeDataJSON(Map<String, String> data, String defaultPayload) {
		String payload = defaultPayload;
		JSONObject json = new JSONObject();
		try {
			for (String key : data.keySet()) {
				json.put(key, data.get(key));
			}
			StringWriter wr = new StringWriter();
			json.write(wr);
			payload = wr.toString();
		} catch (JSONException e) {
			System.err.println("Exception (ignored): " + e);
		}
		return payload;
	}

}