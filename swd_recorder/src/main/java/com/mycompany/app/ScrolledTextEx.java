// http://stackoverflow.com/questions/10145547/enabling-scroll-bars-in-a-java-swt-window

package com.mycompany.app;

import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;

import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.custom.ScrolledComposite;

public class ScrolledTextEx {

	protected Shell shlWhois;
	private Text text;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			ScrolledTextEx window = new ScrolledTextEx();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shlWhois.open();
		shlWhois.layout();
		while (!shlWhois.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shlWhois = new Shell();
		shlWhois.setSize(450, 300);
		shlWhois.setText("Whois");
		shlWhois.setLayout(new GridLayout(2, false));

		Label lblDomain = new Label(shlWhois, SWT.NONE);
		lblDomain.setLayoutData(GridDataFactory.fillDefaults().create());
		lblDomain.setText("Domain");

		text = new Text(shlWhois, SWT.BORDER);
		text.setLayoutData(
				GridDataFactory.fillDefaults().grab(true, false).create());

		final StyledText styledText = new StyledText(shlWhois,
				SWT.BORDER | SWT.V_SCROLL | SWT.MULTI | SWT.WRAP);
		styledText.setLayoutData(
				GridDataFactory.fillDefaults().grab(true, true).span(2, 1).create());

		Button btnWhois = new Button(shlWhois, SWT.NONE);
		btnWhois.setText("Search");
	}

}