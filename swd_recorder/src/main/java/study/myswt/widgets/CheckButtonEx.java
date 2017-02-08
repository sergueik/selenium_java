package study.myswt.widgets;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

/**
 * ZetCode Java SWT tutorial
 *
 * This program uses a check button widget to show/hide the title of the window.
 *
 * Author: Jan Bodnar Website: zetcode.com Last modified: June 2015
 */

public class CheckButtonEx {

	private Shell shell;

	public CheckButtonEx(Display display) {

		initUI(display);
	}

	private void initUI(Display display) {

		shell = new Shell(display);

		RowLayout layout = new RowLayout();
		layout.marginLeft = 30;
		layout.marginTop = 30;
		shell.setLayout(layout);

		Button cb = new Button(shell, SWT.CHECK);
		cb.setText("Show title");
		cb.setSelection(true);

		cb.addListener(SWT.Selection, event -> onButtonSelect(cb));

		shell.setText("Check button");
		shell.setSize(250, 200);
		shell.open();

		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	private void onButtonSelect(Button cb) {

		if (cb.getSelection()) {
			shell.setText("Check button");
		} else {
			shell.setText("");
		}
	}

	@SuppressWarnings("unused")
	public static void main(String[] args) {

		Display display = new Display();
		CheckButtonEx ex = new CheckButtonEx(display);
		display.dispose();
	}
}
