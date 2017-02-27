package com.mycompany.app;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Optional;

import org.json.JSONException;
// import org.json.*;
import org.json.JSONObject;

import org.eclipse.swt.SWT;
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
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

// based on: 
// http://www.java2s.com/Code/Java/SWT-JFace-Eclipse/ComplexShellExample.htm 
// https://www.chrisnewland.com/swt-best-practice-single-display-multiple-shells-111
public class ComplexFormEx {

	private Shell shell;
	private String commandId;
	private Display display;
	private String dataKey = "CurrentCommandId";
	private static HashMap<String, String> elementData = new HashMap<String, String>(); // empty
	private int width = 350;
	private int height = 280;
	private static Shell parentShell = null;
	private static Boolean updated = false;
	private static String result = null;
	private int step_num = 1;

	ComplexFormEx(Display parentDisplay, Shell parent) {
		if (parent != null) {
			// System.err.println("Detected parent shell " + parent.toString());
			parentShell = parent;
			commandId = parent.getData(dataKey).toString();
		} else {
			// System.err.println("No parent shell");
		}
		readData(Optional.of(elementData));
		display = (parentDisplay != null) ? parentDisplay : new Display();
		shell = new Shell(display);
	}

	public void render() {
		shell.open();
		shell.setText(String.format("Element Locators", commandId));
		GridLayout gl = new GridLayout();
		gl.numColumns = 1;
		shell.setLayout(gl);
		final Label titleData = new Label(shell, SWT.SINGLE | SWT.NONE);
		titleData
				.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
		titleData.setText((elementData.containsKey("ElementCodeName"))
				? elementData.get("ElementCodeName")
				: String.format("Step %s details", step_num));

		GridComposite gc = new GridComposite(shell);
		gc.renderData(elementData);
		GridData gd = new GridData(GridData.FILL_BOTH);
		gc.setLayoutData(gd);
		gc.pack();

		RowComposite rc = new RowComposite(shell);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		rc.setLayoutData(gd);
		rc.pack();
		shell.pack();
		shell.addListener(SWT.Close, new Listener() {
			@Override
			public void handleEvent(Event event) {
				if (updated) {
					if (parentShell != null) {
						// NOTE: not currently reached
						System.err
								.println("Handle Close: updating the parent shell: " + result);
						parentShell.setData("result", result);
						parentShell.setData("updated", true);
					}
				}
				shell.dispose();
			}
		});

		shell.setSize(width, height);

		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
	}

	private static class RowComposite extends Composite {

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
				public void widgetSelected(SelectionEvent event) {
					composite.dispose();
				}
			});
			buttonOK.addListener(SWT.Selection, new Listener() {
				@Override
				public void handleEvent(Event event) {
					JSONObject json = new JSONObject();
					try {
						for (String key : elementData.keySet()) {
							json.put(key, elementData.get(key));
						}
						StringWriter wr = new StringWriter();
						json.write(wr);
						result = wr.toString();
						updated = true;
						if (parentShell != null) {
							System.err.println("[2]Updating the parent with: " + result);
							System.err.println("Updating the parent");
							parentShell.setData("result", result);
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

		private static void doSelection(Button button) {
			if (button.getSelection()) {
				// System.out.println("process selection " + button);
				System.out.println(button.getText() + " selected");
				// } else {
				// System.out.println("do work for deselection " + button);
			}
		}

		public void renderData(HashMap<String, String> data) {
			Listener listener = new Listener() {
				public void handleEvent(Event event) {
					doSelection(((Button) event.widget));
				}
			};

			final Button cssSelectorRadio = new Button(this, SWT.RADIO);
			cssSelectorRadio.setSelection(true);
			cssSelectorRadio.setText("Css");
			cssSelectorRadio.setLayoutData(new GridData(labelWidth, SWT.DEFAULT));
			cssSelectorRadio.addListener(SWT.Selection, listener);

			final Text cssSelectorData = new Text(this, SWT.SINGLE | SWT.BORDER);
			cssSelectorData.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			if (data.containsKey("ElementCssSelector")) {
				cssSelectorData.setText(data.get("ElementCssSelector"));
			}
			cssSelectorData.setData("key", "ElementCssSelector");
			cssSelectorData.addModifyListener(new ModifyListener() {
				@Override
				public void modifyText(ModifyEvent event) {
					Text text = (Text) event.widget;
					data.replace((String) text.getData("key"), text.getText());
				}
			});

			final Button xPathRadio = new Button(this, SWT.RADIO);
			xPathRadio.setSelection(false);
			xPathRadio.setText("XPath");
			xPathRadio.setLayoutData(new GridData(labelWidth, SWT.DEFAULT));
			xPathRadio.addListener(SWT.Selection, listener);

			final Text xPathData = new Text(this, SWT.SINGLE | SWT.BORDER);
			xPathData.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			if (data.containsKey("ElementXPath")) {
				xPathData.setText(data.get("ElementXPath"));
			}
			xPathData.setData("key", "ElementXPath");
			xPathData.addModifyListener(new ModifyListener() {
				@Override
				public void modifyText(ModifyEvent event) {
					Text text = (Text) event.widget;
					data.replace((String) text.getData("key"), text.getText());
				}
			});

			final Button idRadio = new Button(this, SWT.RADIO);
			idRadio.setSelection(false);
			idRadio.setText("ID");
			idRadio.setLayoutData(new GridData(labelWidth, SWT.DEFAULT));
			idRadio.addListener(SWT.Selection, listener);

			/*
						idRadio.addListener(SWT.Selection, new Listener() {
							public void handleEvent(Event event) {
								switch (event.type) {
								case SWT.Selection:
									Button button = ((Button) event.widget);
									if (button.getSelection()) {
										System.out.println(button.getText() + " selected (*)");
									}
									break;
								}
							}
						});
			*/
			final Text idData = new Text(this, SWT.SINGLE | SWT.BORDER);
			idData.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			if (data.containsKey("ElementId")) {
				idData.setText(data.get("ElementId"));
			}
			idData.setData("key", "ElementId");

			idData.addModifyListener(new ModifyListener() {
				@Override
				public void modifyText(ModifyEvent event) {
					Text text = (Text) event.widget;
					data.replace((String) text.getData("key"), text.getText());
				}
			});

			final Button textRadio = new Button(this, SWT.RADIO);
			textRadio.setSelection(false);
			textRadio.setLayoutData(new GridData(labelWidth, SWT.DEFAULT));
			textRadio.setText("Text");
			textRadio.addListener(SWT.Selection, listener);

			final Text textData;
			textData = new Text(this, SWT.SINGLE | SWT.BORDER);
			textData.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			textData.setText("Text...");
			textData.addModifyListener(new ModifyListener() {
				@Override
				public void modifyText(ModifyEvent event) {
					Text text = (Text) event.widget;
					// System.err.println(text.getText());
				}
			});
		}
	}

	public String readData(Optional<HashMap<String, String>> parameters) {

		Boolean collectResults = parameters.isPresent();
		HashMap<String, String> collector = (collectResults) ? parameters.get()
				: new HashMap<String, String>();
		String data = "{ \"Url\": \"http://www.google.com\", \"ElementCodeName\": \"Name of the element\", \"CommandId\": \"d5be4ea9-c51f-4e61-aefc-e5c83ba00be8\", \"ElementCssSelector\": \"html div.home-logo_custom > img\", \"ElementId\": \"id\", \"ElementXPath\": \"/html//img[1]\" }";
		try {
			JSONObject elementObj = new JSONObject(data);
			@SuppressWarnings("unchecked")
			Iterator<String> propIterator = elementObj.keys();
			while (propIterator.hasNext()) {
				String propertyKey = propIterator.next();

				String propertyVal = elementObj.getString(propertyKey);
				System.err.println(propertyKey + ": " + propertyVal);
				collector.put(propertyKey, propertyVal);
			}
		} catch (JSONException e) {
			System.err.println("Ignored exception: " + e.toString());
			return null;
		}
		return collector.get("ElementCodeName");
	}

	public void setData(String key, String value) {
		elementData.put(key, value);
	}

	public static void main(String[] arg) {
		ComplexFormEx o = new ComplexFormEx(null, null);
		o.render();
	}

}
