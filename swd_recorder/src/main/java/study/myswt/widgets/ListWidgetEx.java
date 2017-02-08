package study.myswt.widgets;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Shell;

/**
 * ZetCode Java SWT tutorial
 *
 * This program shows the List widget.
 *
 * Author: Jan Bodnar Website: zetcode.com Last modified: June 2015
 */

public class ListWidgetEx {

	private Label status;

	public ListWidgetEx(Display display) {

		initUI(display);
	}

	private void initUI(Display display) {

		Shell shell = new Shell(display);

		status = new Label(shell, SWT.NONE);
		status.setText("Ready");

		FormLayout layout = new FormLayout();
		layout.marginHeight = 5;
		layout.marginWidth = 5;
		layout.spacing = 5;
		shell.setLayout(layout);

		FormData labelData = new FormData();
		labelData.left = new FormAttachment(0);
		labelData.right = new FormAttachment(100);
		labelData.bottom = new FormAttachment(100);
		status.setLayoutData(labelData);

		List list = new List(shell, SWT.BORDER);

		list.add("Aliens");
		list.add("Capote");
		list.add("Neverending story");
		list.add("Starship troopers");
		list.add("Exorcist");
		list.add("Omen");

		list.addListener(SWT.Selection, event -> onListItemSelect(list));

		FormData listData = new FormData();
		listData.width = 250;
		listData.height = 200;
		listData.left = new FormAttachment(shell, 0);
		listData.top = new FormAttachment(shell, 0);
		listData.right = new FormAttachment(100, 0);
		listData.bottom = new FormAttachment(status, 0);
		list.setLayoutData(listData);

		shell.setText("List");
		shell.pack();
		shell.open();

		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	private void onListItemSelect(List list) {

		String[] items = list.getSelection();
		status.setText(items[0]);
	}

	@SuppressWarnings("unused")
	public static void main(String[] args) {

		Display display = new Display();
		ListWidgetEx ex = new ListWidgetEx(display);
		display.dispose();
	}
}
