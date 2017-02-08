package study.myswt.layouts;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

/**
 * ZetCode Java SWT tutorial
 *
 * In this program, we position two buttons using absolute coordinates.
 *
 * Author: Jan Bodnar Website: zetcode.com Last modified: June 2015
 */

public class AbsoluteLayoutEx {

	public AbsoluteLayoutEx(Display display) {

		initUI(display);
	}

	private void initUI(Display display) {

		Shell shell = new Shell(display, SWT.SHELL_TRIM | SWT.CENTER);
		shell.setText("Absolute layout");
		shell.setSize(300, 250);

		Button btn1 = new Button(shell, SWT.PUSH);
		btn1.setText("Button");
		btn1.setBounds(20, 50, 80, 30);

		Button btn2 = new Button(shell, SWT.PUSH);
		btn2.setText("Button");
		btn2.setSize(80, 30);
		btn2.setLocation(50, 100);

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
		AbsoluteLayoutEx ex = new AbsoluteLayoutEx(display);
		display.dispose();
	}
}
