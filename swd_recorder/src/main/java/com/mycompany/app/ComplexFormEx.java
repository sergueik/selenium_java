package com.mycompany.app;

import java.util.HashMap;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
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
	private HashMap<String, String> data = new HashMap<String, String>(); // empty
	private int width = 350;
	private int height = 280;

	ComplexFormEx(Display parentDisplay, Shell parent) {
		if (parent != null) {
			commandId = parent.getData(dataKey).toString();
		}
		display = (parentDisplay != null) ? parentDisplay : new Display();
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
		titleData.setText(
				String.format("Step %s details", (data.containsKey("ElementCodeName"))
						? data.get("ElementCodeName") : ""));
		GridLayout gl = new GridLayout();
		gl.numColumns = 4;
		shell.setLayout(gl);
		// shell.setLayout(gl);

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

		shell.setSize(width, height);

		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
	}

	public static void main(String[] arg) {
		ComplexFormEx o = new ComplexFormEx(null, null);
		o.render();
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

	private static class GridComposite extends Composite {

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
			if (data.containsKey("ElementCssSelector")) {
				cssSelectorData.setText(data.get("ElementCssSelector"));
			}

			final Button xPathRadio = new Button(this, SWT.RADIO);
			xPathRadio.setSelection(false);
			xPathRadio.setText("XPath");
			xPathRadio.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			final Text xPathData = new Text(this, SWT.SINGLE | SWT.BORDER);
			xPathData.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			if (data.containsKey("ElementXPath")) {
				xPathData.setText(data.get("ElementXPath"));
			}
			final Button idRadio = new Button(this, SWT.RADIO);
			idRadio.setSelection(false);
			idRadio.setText("ID");
			idRadio.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			final Text idData = new Text(this, SWT.SINGLE | SWT.BORDER);
			idData.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			if (data.containsKey("ElementId")) {
				idData.setText(data.get("ElementId"));
			}
			final Button textRadio = new Button(this, SWT.RADIO);
			textRadio.setSelection(false);
			textRadio.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			textRadio.setText("Text");

			final Text textData = new Text(this, SWT.SINGLE | SWT.BORDER);
			textData.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			textData.setText("Text...");
		}
	}
}