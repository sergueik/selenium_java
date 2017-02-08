package study.myswt.painting;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
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

public class LyricsEx {

	public LyricsEx(Display display) {

		initUI(display);
	}

	private void initUI(Display display) {

		Shell shell = new Shell(display);

		shell.addListener(SWT.Paint, event -> drawLyrics(event));

		shell.setText("Soulmate");
		shell.setSize(380, 300);
		shell.open();

		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	private void drawLyrics(Event e) {

		GC gc = e.gc;

		gc.setAntialias(SWT.ON);

		Font font = new Font(e.display, "Purisa", 10, SWT.NORMAL);
		Color col = new Color(e.display, 25, 25, 25);

		gc.setForeground(col);
		gc.setFont(font);

		gc.drawText("Most relationships seem so transitory", 20, 30);
		gc.drawText("They're good but not the permanent one", 20, 60);
		gc.drawText("Who doesn't long for someone to hold", 20, 120);
		gc.drawText("Who knows how to love without being told", 20, 150);
		gc.drawText("Somebody tell me why I'm on my own", 20, 180);
		gc.drawText("If there's a soulmate for everyone", 20, 210);

		col.dispose();
		font.dispose();
	}

	@SuppressWarnings("unused")
	public static void main(String[] args) {

		Display display = new Display();
		LyricsEx ex = new LyricsEx(display);
		display.dispose();
	}
}