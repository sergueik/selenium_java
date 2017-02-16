package com.mycompany.app;

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
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

// based on: http://www.java2s.com/Code/Java/SWT-JFace-Eclipse/ComplexShellExample.htm 
public class ComplexFormEx {
	Display d;

	Shell s;

	ComplexFormEx() {

		d = new Display();
		s = new Shell(d);
		s.setSize(250, 275);

		s.setText("A Shell Composite Example");

		GridLayout gl = new GridLayout();
		gl.numColumns = 4;
		s.setLayout(gl);
		s.setLayout(gl);

		GridComposite gc = new GridComposite(s);
		GridData gd = new GridData(GridData.FILL_BOTH);
		gd.horizontalSpan = 4;
		gc.setLayoutData(gd);
		gd = new GridData();

		RowComposite rc = new RowComposite(s);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		rc.setLayoutData(gd);
		s.open();
		while (!s.isDisposed()) {
			if (!d.readAndDispatch())
				d.sleep();
		}
		d.dispose();
	}

	public static void main(String[] arg) {
		new ComplexFormEx();
	}
}

class RowComposite_RENAME_OR_MAKE_STATIC extends Composite {
	final Button okBtn;

	final Button cancelBtn;

	public RowComposite_RENAME_OR_MAKE_STATIC(Composite c) {
		super(c, SWT.NO_FOCUS);
		RowLayout rl = new RowLayout();
		rl.wrap = false;
		rl.pack = false;
		this.setLayout(rl);
		okBtn = new Button(this, SWT.BORDER | SWT.PUSH);
		okBtn.setText("OK");
		okBtn.setSize(30, 20);
		cancelBtn = new Button(this, SWT.PUSH);
		cancelBtn.setText("Cancel");
		cancelBtn.setSize(30, 20);
		cancelBtn.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				System.out.println("Cancel was clicked");
			}
		});

	}
}

class GridComposite_RENAME_OR_MAKE_STATIC extends Composite {

	public GridComposite_RENAME_OR_MAKE_STATIC(Composite c) {
		super(c, SWT.BORDER);
		GridLayout gl = new GridLayout();
		gl.numColumns = 2;
		this.setLayout(gl);
		final Button cssSelectorRadio = new Button(this, SWT.RADIO);
		cssSelectorRadio.setSelection(true);
		cssSelectorRadio.setText("Css Selector");
		cssSelectorRadio.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		final Text cssSelectorData = new Text(this, SWT.SINGLE | SWT.BORDER);
		cssSelectorData.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		cssSelectorData.setText("...");

		final Button xPathRadio = new Button(this, SWT.RADIO);
		xPathRadio.setSelection(false);
		xPathRadio.setText("XPath");
		xPathRadio.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		final Text xPathData = new Text(this, SWT.SINGLE | SWT.BORDER);
		xPathData.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		xPathData.setText("...");

		final Button idRadio = new Button(this, SWT.RADIO);
		idRadio.setSelection(false);
		idRadio.setText("ID");
		idRadio.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		final Text idData = new Text(this, SWT.SINGLE | SWT.BORDER);
		idData.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		idData.setText("...");
		idData.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		idData.setText("...");

		final Button textRadio = new Button(this, SWT.RADIO);
		textRadio.setSelection(false);
		textRadio.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		textRadio.setText("Text");

		final Text textData = new Text(this, SWT.SINGLE | SWT.BORDER);
		textData.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		textData.setText("...");
		textData.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		textData.setText("...");
	}
}