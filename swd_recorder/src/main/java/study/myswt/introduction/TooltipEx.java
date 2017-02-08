package study.myswt.introduction;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

/**
 * ZetCode Java SWT tutorial
 *
 * In this program, we show a tooltip.
 * 
 * Author: Jan Bodnar Website: zetcode.com Last modified: May 2015
 */

public class TooltipEx {

	public TooltipEx(Display display) {

		initUI(display);
	}

	private void initUI(Display display) {

		Shell shell = new Shell(display, SWT.SHELL_TRIM | SWT.CENTER);

		shell.setText("Tooltip");
		shell.setToolTipText("This is a window");
		shell.setSize(250, 200);

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
		TooltipEx ex = new TooltipEx(display);
		display.dispose();
	}
}