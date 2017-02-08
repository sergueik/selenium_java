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
 * In this program, we draw some basic shapes.
 *
 * Author: Jan Bodnar Website: zetcode.com Last modified: June 2015
 */

public class BasicShapesEx {

	private Shell shell;

	public BasicShapesEx(Display display) {

		initUI(display);
	}

	private void initUI(Display display) {

		shell = new Shell(display, SWT.SHELL_TRIM | SWT.CENTER);

		shell.addListener(SWT.Paint, event -> drawShapes(event));

		shell.setText("Basic shapes");
		shell.setSize(430, 300);
		shell.open();

		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	private void drawShapes(Event e) {

		GC gc = e.gc;

		gc.setAntialias(SWT.ON);

		Color col = new Color(e.display, 150, 150, 150);
		gc.setBackground(col);

		gc.fillRectangle(20, 20, 120, 80);
		gc.fillRectangle(180, 20, 80, 80);
		gc.fillOval(290, 20, 120, 70);

		gc.fillOval(20, 150, 80, 80);
		gc.fillRoundRectangle(150, 150, 100, 80, 25, 25);
		gc.fillArc(280, 150, 100, 100, 0, 115);

		col.dispose();
	}

	@SuppressWarnings("unused")
	public static void main(String[] args) {

		Display display = new Display();
		BasicShapesEx ex = new BasicShapesEx(display);
		display.dispose();
	}
}