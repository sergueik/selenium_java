package com.mycompany.app;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.ExpandBar;
import org.eclipse.swt.widgets.ExpandItem;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

/**
 * http://www.java2s.com/Tutorial/Java/0280__SWT/AddControlstoExpandBar.htm
 */

public class ExpandBarEx {

	private static int expandItemNumber = 0;

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		Display display = new Display();
		Shell shell = new Shell(display);
		shell.setLayout(new FillLayout());
		shell.setText("ExpandBar Example");
		ExpandBar bar = new ExpandBar(shell, SWT.V_SCROLL);
		// Image image = new Image(display, "arrow_expanded.png");
		GridLayout layout = new GridLayout();
		layout.marginLeft = layout.marginTop = layout.marginRight = layout.marginBottom = 10;
		layout.verticalSpacing = 10;

		// First item
		Composite composite1 = new Composite(bar, SWT.NONE);
		composite1.setLayout(layout);

		Text cssSelector1 = new Text(composite1, SWT.BORDER);
		cssSelector1.setText(
				"html body div.container-fluid div.row.row-offcanvas.row-offcanvas-left div.col-sm-9.col-md-9 img");

		Text Id1 = new Text(composite1, SWT.READ_ONLY | SWT.BORDER);
		Id1.setText("ID (read-only)");

		ExpandItem item1 = createNextExpandItem(bar, composite1, "Step 1");

		// Second item
		Composite composite2 = new Composite(bar, SWT.NONE);
		composite2.setLayout(layout);

		final StyledText cssSelector2 = new StyledText(composite2,
				SWT.MULTI | SWT.WRAP);
		cssSelector2.setText(
				"html body div.container-fluid div.row.row-offcanvas.row-offcanvas-left div.col-sm-9.col-md-9 img");

		Text Id2 = new Text(composite2, SWT.READ_ONLY | SWT.BORDER);
		Id2.setText("ID (read-only)");

		ExpandItem item2 = createNextExpandItem(bar, composite2, "Step 2");
		bar.setSpacing(8);
		shell.setSize(400, 350);
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		// image.dispose();
		display.dispose();
	}

	// http://www.programcreek.com/java-api-examples/index.php?api=org.eclipse.swt.widgets.ExpandItem
	private static ExpandItem createNextExpandItem(ExpandBar bar,
			Composite composite, String text) {
		ExpandItem item = new ExpandItem(bar, SWT.NONE, expandItemNumber);
		item.setText(text);
		item.setHeight(composite.computeSize(SWT.DEFAULT, SWT.DEFAULT).y);
		item.setControl(composite);
		if (expandItemNumber == 0) {
			item.setExpanded(true);
		}
		expandItemNumber++;
		return item;
	}

	private static void createItemContent(ExpandItem item, Composite composite) {
		if (item.getControl() == null) {
			ExpandBar bar = item.getParent();
			Text content = new Text(composite, SWT.WRAP | SWT.MULTI | SWT.READ_ONLY);
			String text = "This is the item's content";
			content.setText(text);
			item.setHeight(content.computeSize(SWT.DEFAULT, SWT.DEFAULT).y);
			item.setControl(content);
		}
	}
}
