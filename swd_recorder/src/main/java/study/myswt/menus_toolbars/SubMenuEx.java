package study.myswt.menus_toolbars;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;

/**
 * ZetCode Java SWT tutorial
 *
 * This program creates a submenu.
 *
 * Author: Jan Bodnar Website: zetcode.com Last modified: June 2015
 */

public class SubMenuEx {

	public SubMenuEx(Display display) {

		initUI(display);
	}

	private void initUI(Display display) {

		Shell shell = new Shell(display, SWT.SHELL_TRIM | SWT.CENTER);

		Menu menuBar = new Menu(shell, SWT.BAR);
		MenuItem cascadeFileMenu = new MenuItem(menuBar, SWT.CASCADE);
		cascadeFileMenu.setText("&File");

		Menu fileMenu = new Menu(shell, SWT.DROP_DOWN);
		cascadeFileMenu.setMenu(fileMenu);

		MenuItem cascadeEditMenu = new MenuItem(menuBar, SWT.CASCADE);
		cascadeEditMenu.setText("&Edit");

		MenuItem subMenuItem = new MenuItem(fileMenu, SWT.CASCADE);
		subMenuItem.setText("Import");

		Menu submenu = new Menu(shell, SWT.DROP_DOWN);
		subMenuItem.setMenu(submenu);

		MenuItem feedItem = new MenuItem(submenu, SWT.PUSH);
		feedItem.setText("&Import news feed...");

		MenuItem bmarks = new MenuItem(submenu, SWT.PUSH);
		bmarks.setText("&Import bookmarks...");

		MenuItem mailItem = new MenuItem(submenu, SWT.PUSH);
		mailItem.setText("&Import mail...");

		MenuItem exitItem = new MenuItem(fileMenu, SWT.PUSH);
		exitItem.setText("&Exit");
		shell.setMenuBar(menuBar);

		exitItem.addListener(SWT.Selection, event -> {
			shell.getDisplay().dispose();
			System.exit(0);
		});

		shell.setText("Submenu");
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
		SubMenuEx ex = new SubMenuEx(display);
		display.dispose();
	}
}