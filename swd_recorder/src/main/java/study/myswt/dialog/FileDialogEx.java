package study.myswt.dialog;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

/**
 * ZetCode Java SWT tutorial
 *
 * This example shows a file dialog.
 *
 * Author: Jan Bodnar Website: zetcode.com Last modified: June 2015
 */

public class FileDialogEx {

	private Shell shell;
	private Label label;

	public FileDialogEx(Display display) {

		initUI(display);
	}

	private void initUI(Display display) {

		shell = new Shell(display, SWT.SHELL_TRIM | SWT.CENTER);

		RowLayout layout = new RowLayout();
		layout.marginHeight = 50;
		layout.marginWidth = 50;
		shell.setLayout(layout);

		label = new Label(shell, SWT.NONE);
		String homeDir = System.getProperty("user.home");
		label.setText(homeDir);
		label.pack();

		shell.addListener(SWT.MouseDown, event -> onMouseDown());

		shell.setText("FileDialog");
		shell.pack();
		shell.open();

		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	private void onMouseDown() {

		FileDialog dialog = new FileDialog(shell, SWT.OPEN);

		String[] filterNames = new String[] { "Java sources", "All Files (*)" };

		String[] filterExtensions = new String[] { "*.java", "*" };

		dialog.setFilterNames(filterNames);
		dialog.setFilterExtensions(filterExtensions);

		String path = dialog.open();

		if (path != null) {

			label.setText(path);
			label.pack();
			shell.pack();
		}
	}

	@SuppressWarnings("unused")
	public static void main(String[] args) {

		Display display = new Display();
		FileDialogEx ex = new FileDialogEx(display);
		display.dispose();
	}
}