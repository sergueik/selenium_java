package com.mycompany.app;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringWriter;
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

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Session configuration editor form for Selenium Webdriver Elementor Tool
 * 
 * @author Serguei Kouzmine (kouzmine_serguei@yahoo.com)
 */

// based on:
// http://www.java2s.com/Code/Java/SWT-JFace-Eclipse/ComplexShellExample.htm
// https://www.chrisnewland.com/swt-best-practice-single-display-multiple-shells-111
public class ConfigFormEx {

	private Shell shell;
	private Display display;
	private static HashMap<String, String> configData = new HashMap<String, String>(); // empty
	private static HashMap<String, String[]> configOptions = new HashMap<String, String[]>();
	private int formWidth = 516;
	private int formHeight = 238;
	private final static int buttonWidth = 36;
	private final static int buttonHeight = 24;
	private static Shell parentShell = null;
	private static Boolean updated = false;
	private static String result = null;

	ConfigFormEx(Display parentDisplay, Shell parent) {

		final String dirPath = String.format("%s/src/main/resources/templates",
				System.getProperty("user.dir"));
		listFilesForFolder(new File(dirPath));

		configOptions.put("Browser", new String[] { "Chrome", "Firefox",
				"Internet Explorer", "Edge", "Safari" });
    // TODO: Keep few config template inline and
		// rest up to customer
		configOptions.put("Template", new String[] { "Basic C#", "Basic Java",
				"Page Objects/C#", "Page Objects/Java", "Selenide" });

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
					"{ \"Browser\": \"Chrome\", \"Template\": \"Basic Java\", }",
					Optional.of(configData));
		}
	}

	public void render() {
		shell.open();
		shell.setText("Session Configuration");
		GridLayout gl = new GridLayout();
		gl.numColumns = 1;
		shell.setLayout(gl);

		GridComposite gc = new GridComposite(shell);
		gc.renderData(configData);
		gc.setLayoutData(new GridData(GridData.FILL_BOTH));
		gc.pack();

		RowComposite rc = new RowComposite(shell);
		rc.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		rc.pack();
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
			RowLayout rl = new RowLayout();
			rl.wrap = false;
			rl.pack = false;
			this.setLayout(rl);
			buttonSave = new Button(this, SWT.BORDER | SWT.PUSH);
			buttonSave.setText("Save");
			buttonSave.setSize(buttonWidth, buttonHeight);

			buttonSave.addListener(SWT.Selection, new Listener() {
				@Override
				public void handleEvent(Event event) {
					JSONObject json = new JSONObject();
					try {
						for (String key : configData.keySet()) {
							json.put(key, configData.get(key));
						}
						StringWriter wr = new StringWriter();
						json.write(wr);
						result = wr.toString();
						updated = true;
						System.err.println("Updating the parent shell: " + result);
						if (parentShell != null) {
							parentShell.setData("CurrentConfig", result);
							parentShell.setData("updated", true);
						}
					} catch (JSONException ex) {
					}
					composite.dispose();
				}
			});
		}
	}

	private static class GridComposite extends Composite {

		private int labelWidth = 70;

		public GridComposite(Composite c) {
			super(c, SWT.BORDER);
			GridLayout gl = new GridLayout();
			gl.numColumns = 2;
			this.setLayout(gl);
		}

		public void renderData(HashMap<String, String> data) {

			List<String> configs = Arrays.asList("Browser", "Base URL", "Template",
					"Template Directory");
			// TODO: template directory
			for (String configKey : configs) {
				if (configOptions.containsKey(configKey)) {
					final Label configLabel = new Label(this, SWT.NONE);
					configLabel.setText(configKey);

					final Combo configValue = new Combo(this, SWT.READ_ONLY);
					String items[] = configOptions.get(configKey);
					configValue.setItems(items);
					configValue
							.select(Arrays.asList(items).indexOf(configData.get(configKey)));
					configValue.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
					/*
					String value = data.get(configKey);
					if (value.length() > 0) {
						configValue.setText(data.get(configKey));
					} else {
						configValue.setEnabled(false);
					}
					*/
					configValue.setData("key", configKey);

					configValue.addSelectionListener(new SelectionAdapter() {
						public void widgetSelected(SelectionEvent event) {
							Combo o = (Combo) event.widget;
							data.replace((String) o.getData("key"), o.getText());
							// TODO: assoc.
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
					configValue.setText(String.format("%s...", configKey));
					configValue.setData("key", configKey);
					configValue.addModifyListener(new ModifyListener() {

						@Override
						public void modifyText(ModifyEvent event) {
							Text text = (Text) event.widget;
							// System.err.println(text.getText());
						}
					});

				}
			}

		}
	}

	public void listFilesForFolder(final File dir) {
		FileReader fileReader = null;
		String contents = null;
		for (final File fileEntry : dir.listFiles()) {
			contents = null;
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
            // TODO: scan the template directory and merge configOptions. 
            templateName = matcherTemplate.group(1);
						System.out.println(String.format("got a tag for %s: '%s'",
								fileEntry.getName(), templateName));
					} else {
						System.out.println(String.format("no tag: %s", fileEntry.getName()));
					}
				} else {
					System.out.println(String.format("no tag: %s", fileEntry.getName()));
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
