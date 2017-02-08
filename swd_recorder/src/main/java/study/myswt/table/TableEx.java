package study.myswt.table;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

/**
 * ZetCode Java SWT tutorial
 *
 * In this program, we create a simple, empty table widget.
 *
 * Author: Jan Bodnar Website: zetcode.com Last modified: June 2015
 */

public class TableEx {

	public TableEx(Display display) {

		initUI(display);
	}

	@SuppressWarnings("unused")
	private void initUI(Display display) {

		Shell shell = new Shell(display);
		shell.setLayout(new GridLayout());

		Table table = new Table(shell, SWT.BORDER);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);

		GridData data = new GridData(SWT.FILL, SWT.FILL, true, true);
		data.heightHint = 300;
		data.widthHint = 350;
		table.setLayoutData(data);

		String[] titles = { "A", "B", "C" };

		for (int i = 0; i < titles.length; i++) {
			TableColumn column = new TableColumn(table, SWT.CENTER);
			column.setWidth(120);
			column.setText(titles[i]);
		}

		for (int i = 0; i < 15; i++) {
			TableItem item = new TableItem(table, SWT.NONE);
		}

		shell.setText("Empty table");

		shell.pack();
		shell.open();

		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
	}

	@SuppressWarnings("unused")
	public static void main(String[] args) {

		Display display = new Display();
		TableEx ex = new TableEx(display);
		display.dispose();
	}
}