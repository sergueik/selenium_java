package study.myswt.introduction;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

/**
 * ZetCode SWT tutorial
 *
 * This program creates a mnemonic for a button widget.
 *
 * Author: Jan Bodnar Website: zetcode.com Last modified: June 2015
 */

public class MnemonicEx {

	public MnemonicEx(Display display) {

		initUI(display);
	}

	private void initUI(Display display) {

		Shell shell = new Shell(display, SWT.SHELL_TRIM | SWT.CENTER);
		RowLayout layout = new RowLayout();
		layout.marginLeft = 30;
		layout.marginTop = 30;
		layout.marginBottom = 150;
		layout.marginRight = 150;

		shell.setLayout(layout);

		shell.setText("Mnemonic");

		Button btn = new Button(shell, SWT.PUSH);
		btn.setText("&Button");
		btn.setLayoutData(new RowData(80, 30));
		btn.addListener(SWT.Selection, event -> System.out.println("Button clicked"));

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
		MnemonicEx ex = new MnemonicEx(display);
		display.dispose();
	}
}