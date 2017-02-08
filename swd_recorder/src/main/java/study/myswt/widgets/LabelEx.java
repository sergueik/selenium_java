package study.myswt.widgets;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

/**
 * ZetCode Java SWT tutorial
 *
 * This program uses the Label widget to show lyrics of a song.
 *
 * Author: Jan Bodnar Website: zetcode.com Last modified: June 2015
 */

public class LabelEx {

	final String lyrics = "And I know that he knows I'm unfaithful\n" + "And it kills him inside\n" + "To know that I am happy with some other guy\n" + "I can see him dyin'\n" + "\n" + "I don't wanna do this anymore\n"
			+ "I don't wanna be the reason why\n" + "Every time I walk out the door\n" + "I see him die a little more inside\n" + "I don't wanna hurt him anymore\n" + "I don't wanna take away his life\n" + "I don't wanna be... A murderer";

	public LabelEx(Display display) {

		initUI(display);
	}

	private void initUI(Display display) {

		Shell shell = new Shell(display, SWT.SHELL_TRIM | SWT.CENTER);

		Label label = new Label(shell, SWT.LEFT);
		label.setText(lyrics);

		Point p = label.computeSize(SWT.DEFAULT, SWT.DEFAULT);
		label.setBounds(5, 5, p.x + 5, p.y + 5);

		shell.setText("Unfaithful");
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
		LabelEx ex = new LabelEx(display);
		display.dispose();
	}
}