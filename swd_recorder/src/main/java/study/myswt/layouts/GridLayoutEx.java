package study.myswt.layouts;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

/**
 * ZetCode Java SWT tutorial
 *
 * This example presents the GridLayout.
 *
 * Author: Jan Bodnar Website: zetcode.com Last modified: June 2015
 */

public class GridLayoutEx {

	public GridLayoutEx(Display display) {

		initUI(display);
	}

	private void initUI(Display display) {

		Shell shell = new Shell(display, SWT.SHELL_TRIM | SWT.CENTER);

		Color col = new Color(display, 100, 200, 100);
		shell.setBackground(col);
		col.dispose();

		GridLayout layout = new GridLayout(2, false);
		shell.setLayout(layout);

		Label lbl1 = new Label(shell, SWT.NONE);
		GridData gd1 = new GridData(SWT.FILL, SWT.FILL, true, true);
		lbl1.setLayoutData(gd1);

		Color col1 = new Color(display, 250, 155, 100);
		lbl1.setBackground(col1);
		col1.dispose();

		Label lbl2 = new Label(shell, SWT.NONE);
		GridData gd2 = new GridData(SWT.FILL, SWT.FILL, true, true);
		gd2.heightHint = 100;
		lbl2.setLayoutData(gd2);

		Color col2 = new Color(display, 10, 155, 100);
		lbl2.setBackground(col2);
		col2.dispose();

		Label lbl3 = new Label(shell, SWT.NONE);
		GridData gd3 = new GridData(SWT.FILL, SWT.FILL, true, true);
		gd3.widthHint = 300;
		gd3.heightHint = 100;
		gd3.horizontalSpan = 2;
		lbl3.setLayoutData(gd3);

		Color col3 = new Color(display, 100, 205, 200);
		lbl3.setBackground(col3);
		col3.dispose();

		shell.setText("Grid");
		shell.pack();
		shell.open();

		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
	}

	@SuppressWarnings("unused")
	public static void main(String[] args) {

		Display display = new Display();
		GridLayoutEx ex = new GridLayoutEx(display);
		display.dispose();
	}
}