package study.myswt.table;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

/**
 * ZetCode Java SWT tutorial
 *
 * In this program, we fill table cells with data.
 *
 * Author: Jan Bodnar Website: zetcode.com Last modified: June 2015
 */

public class TableEx2 {

	public TableEx2(Display display) {

		initUI(display);
	}

	private void initUI(Display display) {

		Shell shell = new Shell(display, SWT.SHELL_TRIM | SWT.CENTER);
		shell.setLayout(new FillLayout());

		Table table = new Table(shell, SWT.BORDER);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);

		TableColumn tc1 = new TableColumn(table, SWT.CENTER);
		TableColumn tc2 = new TableColumn(table, SWT.CENTER);
		TableColumn tc3 = new TableColumn(table, SWT.CENTER);

		tc1.setText("First Name");
		tc2.setText("Last Name");
		tc3.setText("Profession");

		tc1.setWidth(70);
		tc2.setWidth(70);
		tc3.setWidth(80);

		TableItem item1 = new TableItem(table, SWT.NONE);
		item1.setText(new String[] { "Jane", "Brown", "Accountant" });
		TableItem item2 = new TableItem(table, SWT.NONE);
		item2.setText(new String[] { "Tim", "Warner", "Lawyer" });
		TableItem item3 = new TableItem(table, SWT.NONE);
		item3.setText(new String[] { "Bob", "Milton", "Police officer" });

		shell.setText("Table widget");
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
		TableEx2 ex = new TableEx2(display);
		display.dispose();
	}
}
