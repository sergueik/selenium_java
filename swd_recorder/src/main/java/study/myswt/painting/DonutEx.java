package study.myswt.painting;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Transform;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Shell;

/**
 * ZetCode Java SWT tutorial
 *
 * This program creates a donut shape.
 *
 * Author: Jan Bodnar Website: zetcode.com Last modified: June 2015
 */

public class DonutEx {

	public DonutEx(Display display) {

		initUI(display);
	}

	private void initUI(Display display) {

		Shell shell = new Shell(display, SWT.SHELL_TRIM | SWT.CENTER);

		shell.addListener(SWT.Paint, event -> drawDonut(event));

		shell.setText("Donut");
		shell.setSize(430, 300);
		shell.open();

		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	private void drawDonut(Event e) {

		GC gc = e.gc;

		int w = e.width;
		int h = e.height;

		gc.setAntialias(SWT.ON);

		Transform tr = new Transform(e.display);
		tr.translate(w / 2, h / 2);
		gc.setTransform(tr);

		for (int rot = 0; rot < 36; rot++) {

			tr.rotate(5f);
			gc.setTransform(tr);
			gc.drawOval(-125, -40, 250, 80);
		}

		tr.dispose();
	}

	@SuppressWarnings("unused")
	public static void main(String[] args) {

		Display display = new Display();
		DonutEx ex = new DonutEx(display);
		display.dispose();
	}
}