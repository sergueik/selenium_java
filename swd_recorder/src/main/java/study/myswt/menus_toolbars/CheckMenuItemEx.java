package study.myswt.menus_toolbars;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;

/**
 * ZetCode Java SWT tutorial
 *
 * This program creates a check menu item. It will show or hide a statusbar.
 *
 * Author: Jan Bodnar Website: zetcode.com Last modified: June 2015
 */

public class CheckMenuItemEx {

	private Shell shell;
	private Label status;
	private MenuItem statItem;

	public CheckMenuItemEx(Display display) {

		initUI(display);
	}

	public void initUI(Display display) {

		shell = new Shell(display, SWT.SHELL_TRIM | SWT.CENTER);

		Menu menuBar = new Menu(shell, SWT.BAR);
		shell.setMenuBar(menuBar);

		MenuItem cascadeFileMenu = new MenuItem(menuBar, SWT.CASCADE);
		cascadeFileMenu.setText("&File");

		Menu fileMenu = new Menu(shell, SWT.DROP_DOWN);
		cascadeFileMenu.setMenu(fileMenu);

		MenuItem exitItem = new MenuItem(fileMenu, SWT.PUSH);
		exitItem.setText("&Exit");

		MenuItem cascadeViewMenu = new MenuItem(menuBar, SWT.CASCADE);
		cascadeViewMenu.setText("&View");

		Menu viewMenu = new Menu(shell, SWT.DROP_DOWN);
		cascadeViewMenu.setMenu(viewMenu);

		statItem = new MenuItem(viewMenu, SWT.CHECK);
		statItem.setSelection(true);
		statItem.setText("&View Statusbar");

		statItem.addListener(SWT.Selection, new MyStatusListener());

		exitItem.addSelectionListener(new MySelectionAdapter());

		status = new Label(shell, SWT.BORDER);
		status.setText("Ready");
		FormLayout layout = new FormLayout();
		shell.setLayout(layout);

		FormData labelData = new FormData();
		labelData.left = new FormAttachment(0);
		labelData.right = new FormAttachment(100);
		labelData.bottom = new FormAttachment(100);
		status.setLayoutData(labelData);

		shell.setText("Check menu item");
		shell.setSize(300, 250);
		shell.open();

		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	private class MyStatusListener implements Listener {

		@Override
		public void handleEvent(Event event) {

			if (statItem.getSelection()) {
				status.setVisible(true);
			} else {
				status.setVisible(false);
			}
		}
	}

	private class MySelectionAdapter extends SelectionAdapter {

		@Override
		public void widgetSelected(SelectionEvent e) {
			shell.getDisplay().dispose();
			System.exit(0);
		}
	}

	@SuppressWarnings("unused")
	public static void main(String[] args) {

		Display display = new Display();
		CheckMenuItemEx ex = new CheckMenuItemEx(display);
		display.dispose();
	}
}