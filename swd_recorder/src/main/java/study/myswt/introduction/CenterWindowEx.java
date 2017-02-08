package study.myswt.introduction;

import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

/**
 * ZetCode Java SWT tutorial
 *
 * In this program, we show a window in the center of the screen
 *
 * Author: Jan Bodnar Website: zetcode.com Last modified: May 2015
 */

public class CenterWindowEx {

	public CenterWindowEx(Display display) {

		Shell shell = new Shell(display);
		shell.setText("Center");
		shell.setSize(250, 200);

		centerWindow(shell);

		shell.open();

		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	private void centerWindow(Shell shell) {

		Rectangle bds = shell.getDisplay().getBounds();

		Point p = shell.getSize();

		int nLeft = (bds.width - p.x) / 2;
		int nTop = (bds.height - p.y) / 2;

		shell.setBounds(nLeft, nTop, p.x, p.y);
	}

	@SuppressWarnings("unused")
	public static void main(String[] args) {

		Display display = new Display();
		CenterWindowEx ex = new CenterWindowEx(display);
		display.dispose();
	}
}
