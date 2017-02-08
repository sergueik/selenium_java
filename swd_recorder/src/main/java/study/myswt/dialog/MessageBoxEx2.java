package study.myswt.dialog;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

/**
 * ZetCode Java SWT tutorial
 *
 * This example shows a confirmation dialog when its closing is initiated.
 *
 * Author: Jan Bodnar Website: zetcode.com Last modified: June 2015
 */

public class MessageBoxEx2 {

	private Shell shell;

	public MessageBoxEx2(Display display) {

		initUI(display);
	}

	private void initUI(Display display) {

		shell = new Shell(display, SWT.SHELL_TRIM | SWT.CENTER);
		shell.addListener(SWT.Close, event -> doShowMessageBox(event));
		shell.setText("Message box");
		shell.setSize(350, 300);
		shell.open();

		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	private void doShowMessageBox(Event event) {

		int style = SWT.APPLICATION_MODAL | SWT.ICON_QUESTION | SWT.YES | SWT.NO;
		MessageBox messageBox = new MessageBox(shell, style);
		messageBox.setText("Information");
		messageBox.setMessage("Really close application?");
		event.doit = messageBox.open() == SWT.YES;
	}

	@SuppressWarnings("unused")
	public static void main(String[] args) {

		Display display = new Display();
		MessageBoxEx2 ex = new MessageBoxEx2(display);
		display.dispose();
	}
}