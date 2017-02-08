package study.myswt.painting;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Shell;

/**
 * ZetCode Java SWT tutorial
 *
 * This program draws text on the window.
 *
 * Author: Jan Bodnar Website: zetcode.com Last modified: June 2015
 */

public class LineStylesEx {

	public LineStylesEx(Display display) {

		initUI(display);
	}

	private void initUI(Display display) {

		Shell shell = new Shell(display);

		shell.addListener(SWT.Paint, event -> drawLyrics(event));

		shell.setText("Line styles");
		shell.setSize(300, 330);
		shell.open();

		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	private void drawLyrics(Event e) {

		GC gc = e.gc;

		gc.setLineWidth(2);

		gc.setLineStyle(SWT.LINE_DASHDOT);
		gc.drawLine(20, 40, 250, 40);

		gc.setLineStyle(SWT.LINE_DASH);
		gc.drawLine(20, 80, 250, 80);

		gc.setLineStyle(SWT.LINE_DASHDOTDOT);
		gc.drawLine(20, 120, 250, 120);

		gc.setLineStyle(SWT.LINE_SOLID);
		gc.drawLine(20, 160, 250, 160);

		gc.setLineStyle(SWT.LINE_DOT);
		gc.drawLine(20, 200, 250, 200);

		gc.setLineStyle(SWT.LINE_CUSTOM);
		gc.setLineDash(new int[] { 1, 4, 5, 4 });
		gc.drawLine(20, 240, 250, 240);
	}

	@SuppressWarnings("unused")
	public static void main(String[] args) {

		Display display = new Display();
		LineStylesEx ex = new LineStylesEx(display);
		display.dispose();
	}
}