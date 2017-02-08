package study.myswt.menus_toolbars;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;

/**
 * ZetCode Java SWT tutorial
 *
 * This program creates a popup menu.
 *
 * Author: Jan Bodnar Website: zetcode.com Last modified: June 2015
 */

public class PopupMenuEx {

	public PopupMenuEx(Display display) {

		initUI(display);
	}

	private void initUI(Display display) {

		Shell shell = new Shell(display, SWT.SHELL_TRIM | SWT.CENTER);

		Menu menu = new Menu(shell, SWT.POP_UP);
		MenuItem minItem = new MenuItem(menu, SWT.PUSH);
		minItem.setText("Minimize");

		minItem.addListener(SWT.Selection, event -> {
			shell.setMinimized(true);
		});

		MenuItem exitItem = new MenuItem(menu, SWT.PUSH);
		exitItem.setText("Exit");

		exitItem.addListener(SWT.Selection, event -> {
			shell.getDisplay().dispose();
			System.exit(0);
		});

		shell.setText("Popup menu");
		shell.setMenu(menu);
		shell.setSize(300, 250);
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
		PopupMenuEx ex = new PopupMenuEx(display);
		display.dispose();
	}
}