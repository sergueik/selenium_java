package study.myswt.widgets;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Spinner;

public class SpinnerEx {

	private Label label;

	public SpinnerEx(Display display) {

		initUI(display);
	}

	private void initUI(Display display) {

		Shell shell = new Shell(display, SWT.SHELL_TRIM | SWT.CENTER);

		RowLayout layout = new RowLayout();
		layout.marginLeft = 10;
		layout.marginTop = 10;
		layout.spacing = 30;
		layout.center = true;
		shell.setLayout(layout);

		Spinner spinner = new Spinner(shell, SWT.BORDER);
		spinner.setMinimum(0);
		spinner.setMaximum(100);
		spinner.setSelection(0);
		spinner.setIncrement(1);
		spinner.setPageIncrement(10);
		spinner.setLayoutData(new RowData(30, -1));

		spinner.addListener(SWT.Selection, event -> onSelected(spinner));

		label = new Label(shell, SWT.NONE);
		label.setText("0");

		shell.setText("Spinner");
		shell.setSize(200, 150);
		shell.open();

		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	private void onSelected(Spinner spinner) {

		String val = spinner.getText();
		label.setText(val);
		label.pack();
	}

	@SuppressWarnings("unused")
	public static void main(String[] args) {

		Display display = new Display();
		SpinnerEx ex = new SpinnerEx(display);
		display.dispose();
	}
}