package study.myswt.layouts;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

/**
 * ZetCode Java SWT tutorial
 *
 * In this program, we position two buttons in the bottom right corner of the
 * window.
 *
 * Author: Jan Bodnar Website: zetcode.com Last modified: May 2015
 */

public class ButtonsEx {

	public ButtonsEx(Display display) {

		initUI(display);
	}

	private void initUI(Display display) {

		Shell shell = new Shell(display, SWT.SHELL_TRIM | SWT.CENTER);

		FormLayout layout = new FormLayout();
		shell.setLayout(layout);

		Button okBtn = new Button(shell, SWT.PUSH);
		okBtn.setText("OK");

		Button cancBtn = new Button(shell, SWT.PUSH);
		cancBtn.setText("Cancel");

		FormData cancelData = new FormData(80, 30);
		cancelData.right = new FormAttachment(98);
		cancelData.bottom = new FormAttachment(95);
		cancBtn.setLayoutData(cancelData);

		FormData okData = new FormData(80, 30);
		okData.right = new FormAttachment(cancBtn, -5, SWT.LEFT);
		okData.bottom = new FormAttachment(cancBtn, 0, SWT.BOTTOM);
		okBtn.setLayoutData(okData);

		shell.setText("Buttons");
		shell.setSize(350, 200);
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
		ButtonsEx ex = new ButtonsEx(display);
		display.dispose();
	}
}