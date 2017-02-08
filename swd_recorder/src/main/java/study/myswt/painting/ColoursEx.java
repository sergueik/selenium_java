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
 * This program draws three rectangles. The interiors are filled with different
 * colors.
 * 
 * Author: Jan Bodnar Website: zetcode.com Last modified: June 2015
 */

public class ColoursEx {

	private Shell shell;

	public ColoursEx(Display display) {

		initUI(display);
	}

	private void initUI(Display display) {

		shell = new Shell(display, SWT.SHELL_TRIM | SWT.CENTER);

		shell.addListener(SWT.Paint, event -> drawRectangles(event));

		shell.setText("Colours");
		shell.setSize(360, 120);
		shell.open();

		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	private void drawRectangles(Event e) {

		GC gc = e.gc;

		Color c1 = new Color(e.display, 50, 50, 200);
		gc.setBackground(c1);
		gc.fillRectangle(10, 15, 90, 60);

		Color c2 = new Color(e.display, 105, 90, 60);
		gc.setBackground(c2);
		gc.fillRectangle(130, 15, 90, 60);

		Color c3 = new Color(e.display, 33, 200, 100);
		gc.setBackground(c3);
		gc.fillRectangle(250, 15, 90, 60);

		c1.dispose();
		c2.dispose();
		c3.dispose();
	}

	@SuppressWarnings("unused")
	public static void main(String[] args) {

		Display display = new Display();
		ColoursEx ex = new ColoursEx(display);
		display.dispose();
	}
}