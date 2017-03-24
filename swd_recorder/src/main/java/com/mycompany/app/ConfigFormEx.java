package com.mycompany.app;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MenuDetectEvent;
import org.eclipse.swt.events.MenuDetectListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

/**
 * Session configuration editor form for Selenium Webdriver Elementor Tool
 *
 * @author Serguei Kouzmine (kouzmine_serguei@yahoo.com)
 */

// based on:
// http://www.java2s.com/Code/Java/SWT-JFace-Eclipse/ComplexShellExample.htm
// https://www.chrisnewland.com/swt-best-practice-single-display-multiple-shells-111
public class ConfigFormEx {

	private static Shell shell;
	private static Shell parentShell = null;
	private static Display display;
	private final static int formWidth = 516;
	private final static int formHeight = 238;
	private final static int buttonWidth = 36;
	private final static int buttonHeight = 24;
	private static HashMap<String, String> configData = new HashMap<String, String>(); // empty
	private static HashMap<String, List<String>> configOptions = new HashMap<String, List<String>>();

	ConfigFormEx(Display parentDisplay, Shell parent) {

		configOptions.put("Browser",
				new ArrayList<String>(Arrays.asList(new String[] { "Chrome", "Firefox",
						"Internet Explorer", "Edge", "Safari" })));
		// TODO: Keep few twig templates embedded in the application jar and
		// make rest up to customer
		configOptions.put("Template",
				new ArrayList<String>(
						Arrays.asList(new String[] {  })));
		String dirPath = String.format("%s/src/main/resources/templates",
				System.getProperty("user.dir"));
		listFilesForFolder(new File(dirPath), "embedded", configOptions);
		display = (parentDisplay != null) ? parentDisplay : new Display();
		shell = new Shell(display);
		if (parent != null) {
			parentShell = parent;
		}
		if (parent != null) {
			new Utils().readData(parentShell.getData("CurrentConfig").toString(),
					Optional.of(configData));
		} else {
			new Utils().readData(
					"{ \"Browser\": \"Chrome\", \"Template\": \"Basic Java (embedded)\", \"Template Directory\": \"c:\\\\Users\\\\Serguei\\\\desktop\\\\templates\"}",
					Optional.of(configData));
		}
		if (configData.containsKey("Template Directory")) {
			dirPath = configData.get("Template Directory");
			if (dirPath != "") {
				listFilesForFolder(new File(dirPath), "user defined", configOptions);
			}
		}
	}

	public void render() {
		shell.open();
		shell.setText("Session Configuration");
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 1;
		shell.setLayout(gridLayout);

		GridComposite gridComposite = new GridComposite(shell);
		gridComposite.renderData(configData);
		gridComposite.setLayoutData(new GridData(GridData.FILL_BOTH));
		gridComposite.pack();

		RowComposite rowComposite = new RowComposite(shell);
		rowComposite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		rowComposite.pack();
		shell.pack();
		shell.setSize(formWidth, formHeight);

		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
	}

	private static class RowComposite extends Composite {

		final Button buttonSave;

		public RowComposite(Composite composite) {
			super(composite, SWT.NO_FOCUS);
			RowLayout rowLayout = new RowLayout();
			rowLayout.wrap = false;
			rowLayout.pack = false;
			this.setLayout(rowLayout);
			buttonSave = new Button(this, SWT.BORDER | SWT.PUSH);
			buttonSave.setText("Save");
			buttonSave.setSize(buttonWidth, buttonHeight);

			buttonSave.addListener(SWT.Selection, new Listener() {
				@Override
				public void handleEvent(Event event) {
					String result = new Utils().writeDataJSON(configData, "{}");

					if (parentShell != null) {
						parentShell.setData("CurrentConfig", result);
						parentShell.setData("updated", true);
					} else {
						System.err.println("Updating the parent shell: " + result);
					}
          composite.dispose();
				}
			});
		}
	}

	private static class GridComposite extends Composite {

		public GridComposite(Composite composite) {
			super(composite, SWT.BORDER);
			GridLayout gridLayout = new GridLayout();
			gridLayout.numColumns = 2;
			this.setLayout(gridLayout);
		}

		public void renderData(HashMap<String, String> data) {
			for (String configKey : Arrays.asList("Browser", "Base URL", "Template",
					"Template Directory")) {
				if (configOptions.containsKey(configKey)) {
					final Label configLabel = new Label(this, SWT.NONE);
					configLabel.setText(configKey);

					final Combo configValue = new Combo(this, SWT.READ_ONLY);
					List<String> itemsList = configOptions.get(configKey);

					String[] items = new String[itemsList.size()];
					itemsList.toArray(items);
					configValue.setItems(items);
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
					/*
						configValue.addModifyListener(new ModifyListener() {
						@Override
						public void modifyText(ModifyEvent event) {
							Combo o = (Combo) event.widget;
							data.replace((String) o.getData("key"), o.getText());
						}
					});
					
					*/
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
					configValue.addModifyListener(new ModifyListener() {
						@Override
						public void modifyText(ModifyEvent event) {
							Text text = (Text) event.widget;
							System.err.println(String.format("%s = %s",
									(String) text.getData("key"), text.getText()));
							if (data.containsKey((String) text.getData("key"))) {
								data.replace((String) text.getData("key"), text.getText());
							} else {
								data.put((String) text.getData("key"), text.getText());
							}
						}
					});
				}
			}
		}
	}

	// scan the template directory and merge options.
	public void listFilesForFolder(final File dir, String note,
			HashMap<String, List<String>> options) {
		FileReader fileReader = null;
		String contents = null;
		if (dir.listFiles().length == 0) {
			return;
		}
		for (final File fileEntry : dir.listFiles()) {
			contents = null;
			if (fileEntry.getName().endsWith(".twig")) {
				if (fileEntry.isFile()) {
					try {
						fileReader = new FileReader(fileEntry);
						char[] template = new char[(int) fileEntry.length()];
						fileReader.read(template);
						contents = new String(template);
					} catch (Exception e) {
						e.printStackTrace();
					} finally {
						if (fileReader != null) {
							try {
								fileReader.close();
							} catch (IOException e) {
							}
						}
					}
				}
				if (contents != null) {
					// find comment containing the template name
					String twigCommentMatcher = "\\{#(?:\\r?\\n)?(.*)(?:\\r?\\n)?#\\}";
					String templateMatcher = "template: (.*)$";
					Pattern patternTwigComment = Pattern.compile(twigCommentMatcher,
							Pattern.MULTILINE);
					Matcher matcherTwigComment = patternTwigComment.matcher(contents);
					if (matcherTwigComment.find()) {
						String comment = matcherTwigComment.group(1);
						String templateName = null;
						Pattern patternTemplate = Pattern.compile(templateMatcher,
								Pattern.MULTILINE);
						Matcher matcherTemplate = patternTemplate.matcher(comment);
						if (matcherTemplate.find()) {

							templateName = matcherTemplate.group(1);
							System.out.println(String.format("got a tag for %s: '%s'",
									fileEntry.getName(), templateName));
							List<String> items = options.get("Template");
							items.add(String.format("%s (%s)", templateName,
									(note == null) ? "unknown" : note));
							options.put("Template", items);
						} else {
							System.out
									.println(String.format("no tag: %s", fileEntry.getName()));
						}
					} else {
						System.out
								.println(String.format("no tag: %s", fileEntry.getName()));
					}
				}
			}
		}
	}

	public void setData(String key, String value) {
		configData.put(key, value);
	}

	public static void main(String[] arg) {
		ConfigFormEx o = new ConfigFormEx(null, null);
		o.render();
	}
}
