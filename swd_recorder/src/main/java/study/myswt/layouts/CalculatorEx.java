package study.myswt.layouts;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

/**
 * ZetCode Java SWT tutorial
 *
 * In this program, we use the GridLayout to create a calculator skeleton.
 *
 * Author: Jan Bodnar Website: zetcode.com Last modified: June 2015
 */

public class CalculatorEx {

	public CalculatorEx(Display display) {

		initUI(display);
	}

	private void initUI(Display display) {

		Shell shell = new Shell(display, SWT.DIALOG_TRIM | SWT.CENTER);

		GridLayout gl = new GridLayout(4, true);

		gl.marginHeight = 5;
		shell.setLayout(gl);

		String[] buttons = { "Cls", "Bck", "", "Close", "7", "8", "9", "/", "4", "5", "6", "*", "1", "2", "3", "-", "0", ".", "=", "+" };

		Text text = new Text(shell, SWT.SINGLE);
		GridData gridData = new GridData();
		gridData.horizontalSpan = 4;
		gridData.horizontalAlignment = GridData.FILL;
		text.setLayoutData(gridData);

		for (int i = 0; i < buttons.length; i++) {

			if (i == 2) {

				Label lbl = new Label(shell, SWT.CENTER);
				GridData gd = new GridData(SWT.FILL, SWT.FILL, false, false);
				lbl.setLayoutData(gd);
			} else {

				Button btn = new Button(shell, SWT.PUSH);
				btn.setText(buttons[i]);
				GridData gd = new GridData(SWT.FILL, SWT.FILL, false, false);
				gd.widthHint = 50;
				gd.heightHint = 30;
				btn.setLayoutData(gd);
			}
		}

		shell.setText("Calculator");
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
		CalculatorEx ex = new CalculatorEx(display);
		display.dispose();
	}
}