package study.myswt.layouts;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

/**
 * ZetCode Java SWT tutorial
 *
 * This program creates a layout using a FormLayout and a RowLayout.
 *
 * Author: Jan Bodnar Website: zetcode.com Last modified: June 2015
 */

public class NewFolderEx {

	public NewFolderEx(Display display) {

		initUI(display);
	}

	private void initUI(Display display) {

		Shell shell = new Shell(display, SWT.SHELL_TRIM | SWT.CENTER);
		shell.setLayout(new FormLayout());
		shell.setText("New folder");

		Label lbl = new Label(shell, SWT.LEFT);
		lbl.setText("Name:");

		FormData data1 = new FormData();
		data1.left = new FormAttachment(0, 5);
		data1.top = new FormAttachment(0, 10);
		lbl.setLayoutData(data1);

		Text text = new Text(shell, SWT.SINGLE);
		FormData data2 = new FormData();
		data2.left = new FormAttachment(lbl, 15);
		data2.top = new FormAttachment(0, 10);
		data2.right = new FormAttachment(100, -5);
		text.setLayoutData(data2);

		Composite com = new Composite(shell, SWT.NONE);
		RowLayout rowLayout = new RowLayout();
		com.setLayout(rowLayout);
		FormData data3 = new FormData();
		data3.bottom = new FormAttachment(100, -5);
		data3.right = new FormAttachment(100, 0);
		com.setLayoutData(data3);

		Button okBtn = new Button(com, SWT.PUSH);
		okBtn.setText("OK");
		okBtn.setLayoutData(new RowData(80, 30));
		Button closeBtn = new Button(com, SWT.PUSH);
		closeBtn.setText("Close");
		closeBtn.setLayoutData(new RowData(80, 30));

		Text mainText = new Text(shell, SWT.MULTI | SWT.BORDER);
		FormData data4 = new FormData();
		data4.width = 250;
		data4.height = 180;
		data4.top = new FormAttachment(text, 10);
		data4.left = new FormAttachment(0, 5);
		data4.right = new FormAttachment(100, -5);
		data4.bottom = new FormAttachment(com, -10);
		mainText.setLayoutData(data4);

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
		NewFolderEx ex = new NewFolderEx(display);
		display.dispose();
	}
}