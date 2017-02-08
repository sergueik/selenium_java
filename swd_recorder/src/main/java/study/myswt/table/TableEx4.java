package study.myswt.table;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

/**
 * ZetCode Java SWT tutorial
 *
 * In this program, we add new table items to the table.
 *
 * Author: Jan Bodnar Website: zetcode.com Last modified: June 2015
 */

public class TableEx4 {

	private Text text1;
	private Text text2;
	private Table table;

	private final String data[][] = { { "Ferarri", "33333" }, { "Skoda", "22000" }, { "Volvo", "18000" }, { "Mazda", "15000" }, { "Mercedes", "38000" } };

	public TableEx4(Display display) {

		initUI(display);
	}

	private void initUI(Display display) {

		Shell shell = new Shell(display, SWT.SHELL_TRIM | SWT.CENTER);
		shell.setLayout(new GridLayout(5, false));

		table = new Table(shell, SWT.BORDER | SWT.MULTI);
		table.setHeaderVisible(true);
		// table.setLinesVisible(true);

		String[] titles = { "Car", "Price" };

		for (int i = 0; i < titles.length; i++) {
			TableColumn column = new TableColumn(table, SWT.NULL);
			column.setText(titles[i]);
			column.setWidth(130);
		}

		for (int i = 0; i < data.length; i++) {

			TableItem item = new TableItem(table, SWT.NULL);
			item.setText(0, data[i][0]);
			item.setText(1, data[i][1]);
		}

		GridData gd = new GridData(SWT.FILL, SWT.FILL, true, true);
		gd.horizontalSpan = 5;
		gd.widthHint = 360;
		gd.heightHint = 300;
		table.setLayoutData(gd);

		Label carName = new Label(shell, SWT.NONE);
		carName.setText("Car:");
		text1 = new Text(shell, SWT.BORDER);

		Label priceOfCar = new Label(shell, SWT.NONE);
		priceOfCar.setText("Price:");
		text2 = new Text(shell, SWT.BORDER);

		text1.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		text2.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));

		Button addBtn = new Button(shell, SWT.PUSH);
		addBtn.setText("Insert");
		addBtn.addListener(SWT.Selection, event -> onInsertButtonSelected(event));

		shell.setText("Table widget");
		shell.pack();
		shell.open();

		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
	}

	private void onInsertButtonSelected(Event event) {

		String val1 = text1.getText();
		String val2 = text2.getText();

		if (val1.isEmpty() || val2.isEmpty()) {
			return;
		}

		TableItem item = new TableItem(table, SWT.NULL);
		item.setText(0, val1);
		item.setText(1, val2);

		text1.setText("");
		text2.setText("");
	}

	@SuppressWarnings("unused")
	public static void main(String[] args) {

		Display display = new Display();
		TableEx4 ex = new TableEx4(display);
		display.dispose();
	}
}