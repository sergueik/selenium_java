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
 * This program draws a star.
 *
 * Author: Jan Bodnar Website: zetcode.com Last modified: June 2015
 */

public class PolygonEx {

	private final int points[] = { 0, 85, 75, 75, 100, 10, 125, 75, 200, 85, 150, 125, 160, 190, 100, 150, 40, 190, 50, 125, 0, 85 };

	public PolygonEx(Display display) {

		initUI(display);
	}

	private void initUI(Display display) {

		Shell shell = new Shell(display);

		shell.addListener(SWT.Paint, event -> drawPolygon(event));

		shell.setText("Polygon");
		shell.setSize(280, 280);
		shell.open();

		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	private void drawPolygon(Event e) {

		GC gc = e.gc;

		Color grayCol = new Color(e.display, 120, 120, 120);

		gc.setBackground(grayCol);
		gc.fillPolygon(points);

		grayCol.dispose();
	}

	@SuppressWarnings("unused")
	public static void main(String[] args) {

		Display display = new Display();
		PolygonEx ex = new PolygonEx(display);
		display.dispose();
	}
}