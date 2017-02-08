package study.myswt.dialog;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FontDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

/**
 * ZetCode Java SWT tutorial
 *
 * This example shows a font dialog.
 *
 * Author: Jan Bodnar Website: zetcode.com Last modified: June 2015
 */

public class FontDialogEx {

	private Shell shell;
	private Label label;

	public FontDialogEx(Display display) {

		initUI(display);
	}

	private void initUI(Display display) {

		shell = new Shell(display, SWT.SHELL_TRIM | SWT.CENTER);

		RowLayout layout = new RowLayout();
		layout.marginHeight = 100;
		layout.marginWidth = 100;
		shell.setLayout(layout);

		label = new Label(shell, SWT.NONE);
		label.setText("ZetCode Java SWT tutorial");

		shell.addListener(SWT.MouseDown, event -> onMouseDown());
		shell.setText("FontDialog");
		shell.pack();
		shell.open();

		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	private void onMouseDown() {

		FontDialog dialog = new FontDialog(shell);
		FontData fdata = dialog.open();

		if (fdata != null) {

			Font font = new Font(shell.getDisplay(), fdata);

			label.setFont(font);
			label.pack();
			shell.pack();
			font.dispose();
		}
	}

	@SuppressWarnings("unused")
	public static void main(String[] args) {

		Display display = new Display();
		FontDialogEx ex = new FontDialogEx(display);
		display.dispose();
	}
}