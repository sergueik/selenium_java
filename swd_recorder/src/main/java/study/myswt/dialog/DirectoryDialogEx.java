package study.myswt.dialog;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

/**
 * ZetCode Java SWT tutorial
 *
 * This example shows a directory dialog.
 *
 * Author: Jan Bodnar Website: zetcode.com Last modified: June 2015
 */

public class DirectoryDialogEx {

	private Shell shell;
	private Label status;

	public DirectoryDialogEx(Display display) {

		initUI(display);
	}

	private void initUI(Display display) {

		shell = new Shell(display, SWT.SHELL_TRIM | SWT.CENTER);

		status = new Label(shell, SWT.BORDER);
		status.setText("Ready");

		FormLayout layout = new FormLayout();
		shell.setLayout(layout);

		FormData labelData = new FormData();
		labelData.left = new FormAttachment(0);
		labelData.right = new FormAttachment(100);
		labelData.bottom = new FormAttachment(100);
		status.setLayoutData(labelData);

		shell.addListener(SWT.MouseDown, event -> onMouseDown());

		shell.setText("DirectoryDialog");
		shell.setSize(350, 250);
		shell.open();

		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	private void onMouseDown() {

		DirectoryDialog dialog = new DirectoryDialog(shell);
		String path = dialog.open();

		if (path != null) {
			status.setText(path);
		}
	}

	@SuppressWarnings("unused")
	public static void main(String[] args) {

		Display display = new Display();
		DirectoryDialogEx ex = new DirectoryDialogEx(display);
		display.dispose();
	}
}