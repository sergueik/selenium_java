package study.myswt.introduction;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

/**
 * ZetCode Java SWT tutorial
 *
 * This example shows a button on a window. Clicking on the button, we terminate
 * the application.
 *
 * Author: Jan Bodnar Website: zetcode.com Last modified: May 2015
 */

public class QuitButtonEx {

	public QuitButtonEx(Display display) {

		initUI(display);
	}

	private void initUI(Display display) {

		final Shell shell = new Shell(display, SWT.SHELL_TRIM | SWT.CENTER);

		RowLayout layout = new RowLayout();
		layout.marginLeft = 50;
		layout.marginTop = 50;
		shell.setLayout(layout);

		shell.setText("Quit button");
		shell.setSize(250, 200);

		Button quitBtn = new Button(shell, SWT.PUSH);
		quitBtn.setText("Quit");
		quitBtn.setLayoutData(new RowData(80, 30));

		quitBtn.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				shell.getDisplay().dispose();
				System.exit(0);
			}
		});

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
		QuitButtonEx ex = new QuitButtonEx(display);
		display.dispose();
	}
}