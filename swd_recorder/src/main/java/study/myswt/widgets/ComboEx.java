package study.myswt.widgets;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

/**
 * ZetCode Java SWT tutorial
 *
 * In this program, we use the Combo widget to select an option. The selected
 * option is shown in the Label widget.
 *
 * Author: Jan Bodnar Website: zetcode.com Last modified: June 2015
 */

public class ComboEx {

	private Label label;

	public ComboEx(Display display) {

		initUI(display);
	}

	private void initUI(Display display) {

		Shell shell = new Shell(display, SWT.SHELL_TRIM | SWT.CENTER);

		RowLayout layout = new RowLayout(SWT.VERTICAL);
		layout.marginLeft = 50;
		layout.marginTop = 30;
		layout.spacing = 30;
		shell.setLayout(layout);

		Combo combo = new Combo(shell, SWT.DROP_DOWN);
		combo.add("Ubuntu");
		combo.add("Fedora");
		combo.add("Arch");
		combo.add("Red Hat");
		combo.add("Mint");
		combo.setLayoutData(new RowData(150, -1));

		label = new Label(shell, SWT.LEFT);
		label.setText("...");

		combo.addListener(SWT.Selection, event -> onSelected(combo));

		shell.setText("Combo");
		shell.setSize(300, 250);
		shell.open();

		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	private void onSelected(Combo combo) {

		label.setText(combo.getText());
		label.pack();
	}

	@SuppressWarnings("unused")
	public static void main(String[] args) {

		Display display = new Display();
		ComboEx ex = new ComboEx(display);
		display.dispose();
	}
}