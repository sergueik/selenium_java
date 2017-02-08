package study.myswt.layouts;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

/**
 * ZetCode Java SWT tutorial
 *
 * This program demonstrates the RowLayout manager.
 *
 * Author: Jan Bodnar Website: zetcode.com Last modified: June 2015
 */

public class RowLayoutEx {

	public RowLayoutEx(Display display) {

		initUI(display);
	}

	private void initUI(Display display) {

		Shell shell = new Shell(display, SWT.SHELL_TRIM | SWT.CENTER);

		RowLayout rowLayout = new RowLayout(SWT.HORIZONTAL);
		rowLayout.marginTop = 10;
		rowLayout.marginBottom = 10;
		rowLayout.marginLeft = 5;
		rowLayout.marginRight = 5;
		rowLayout.spacing = 10;
		shell.setLayout(rowLayout);

		Button btn1 = new Button(shell, SWT.PUSH);
		btn1.setText("Button1");
		btn1.setLayoutData(new RowData(80, 30));

		Button btn2 = new Button(shell, SWT.PUSH);
		btn2.setText("Button2");
		btn2.setLayoutData(new RowData(80, 30));

		Button btn3 = new Button(shell, SWT.PUSH);
		btn3.setText("Button3");
		btn3.setLayoutData(new RowData(80, 30));

		shell.setText("RowLayout");
		shell.pack();
		shell.open();

		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	@SuppressWarnings("unused")
	public static void main(String[] args) {

		Display display = new Display();
		RowLayoutEx ex = new RowLayoutEx(display);
		display.dispose();
	}
}