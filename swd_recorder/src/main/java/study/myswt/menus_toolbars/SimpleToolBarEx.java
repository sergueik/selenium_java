package study.myswt.menus_toolbars;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;

/**
 * ZetCode Java SWT tutorial
 *
 * This program creates a simple toolbar.
 *
 * Author: Jan Bodnar Website: zetcode.com Last modified: June 2015
 */

public class SimpleToolBarEx {

	private Image newi;
	private Image opei;
	private Image quii;

	public SimpleToolBarEx(Display display) {

		initUI(display);
	}

	@SuppressWarnings("unused")
	public void initUI(Display display) {

		Shell shell = new Shell(display, SWT.SHELL_TRIM | SWT.CENTER);

		Device dev = shell.getDisplay();

		try {
			newi = new Image(dev, "toolbar_new.png");
			opei = new Image(dev, "toolbar_open.png");
			quii = new Image(dev, "toolbar_quit.png");

		} catch (Exception e) {

			System.out.println("Cannot load images");
			System.out.println(e.getMessage());
			System.exit(1);
		}

		ToolBar toolBar = new ToolBar(shell, SWT.BORDER);

		ToolItem item1 = new ToolItem(toolBar, SWT.PUSH);
		item1.setImage(newi);

		ToolItem item2 = new ToolItem(toolBar, SWT.PUSH);
		item2.setImage(opei);

		ToolItem separator = new ToolItem(toolBar, SWT.SEPARATOR);

		ToolItem item3 = new ToolItem(toolBar, SWT.PUSH);
		item3.setImage(quii);

		toolBar.pack();

		item3.addListener(SWT.Selection, event -> {
			shell.getDisplay().dispose();
			System.exit(0);
		});

		shell.setText("Simple toolbar");
		shell.setSize(300, 250);
		shell.open();

		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	@Override
	public void finalize() {

		newi.dispose();
		opei.dispose();
		quii.dispose();
	}

	public static void main(String[] args) {

		Display display = new Display();
		SimpleToolBarEx ex = new SimpleToolBarEx(display);
		ex.finalize();
		display.dispose();
	}
}