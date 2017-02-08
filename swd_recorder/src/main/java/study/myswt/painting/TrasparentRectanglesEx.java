package study.myswt.painting;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Shell;

/**
 * ZetCode Java SWT tutorial
 *
 * This program draws ten rectangles with different levels of transparency.
 *
 * Author: Jan Bodnar Website: zetcode.com Last modified: June 2015
 */

public class TrasparentRectanglesEx {

	public TrasparentRectanglesEx(Display display) {

		initUI(display);
	}

	private void initUI(Display display) {

		Shell shell = new Shell(display, SWT.SHELL_TRIM | SWT.CENTER);

		shell.addListener(SWT.Paint, event -> drawRectangles(event));

		shell.setText("Transparent rectangles");
		shell.setSize(590, 120);
		shell.open();

		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	private void drawRectangles(Event e) {

		GC gc = e.gc;

		Color blueCol = new Color(e.display, 0, 0, 255);
		gc.setBackground(blueCol);

		for (int i = 1; i < 11; i++) {
			gc.setAlpha(i * 25);
			gc.fillRectangle(50 * i, 20, 40, 40);
		}

		blueCol.dispose();
	}

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		Display display = new Display();
		TrasparentRectanglesEx ex = new TrasparentRectanglesEx(display);
		display.dispose();
	}
}