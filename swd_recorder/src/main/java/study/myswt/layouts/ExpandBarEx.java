package study.myswt.layouts;

import org.eclipse.swt.SWT;

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

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		Display display = new Display();
		Shell shell = new Shell(display);
		shell.setLayout(new FillLayout());
		shell.setText("ExpandBar Example");
		ExpandBar bar = new ExpandBar(shell, SWT.V_SCROLL);
		Image image = new Image(display, "arrow_expanded.png");
		GridLayout layout = new GridLayout();
		layout.marginLeft = layout.marginTop = layout.marginRight = layout.marginBottom = 10;
		layout.verticalSpacing = 10;

		// First item
		Composite composite1 = new Composite(bar, SWT.NONE);
		composite1.setLayout(layout);
				
    Text cssSelector1 = new Text(composite1, SWT.BORDER);
    cssSelector1.setText("html body div.container-fluid div.row.row-offcanvas.row-offcanvas-left div.col-sm-9.col-md-9 img");

    Text Id1 = new Text(composite1, SWT.READ_ONLY | SWT.BORDER);
    Id1.setText("ID (read-only)");

    /*
      Button button = new Button(composite, SWT.PUSH);
      button.setText("SWT.PUSH");
      button = new Button(composite, SWT.RADIO);
      button.setText("SWT.RADIO");
      button = new Button(composite, SWT.CHECK);
      button.setText("SWT.CHECK");
      button = new Button(composite, SWT.TOGGLE);
      button.setText("SWT.TOGGLE");
    */
		ExpandItem item1 = new ExpandItem(bar, SWT.NONE, 0);
		item1.setText("Step 1");
		item1.setHeight(composite1.computeSize(SWT.DEFAULT, SWT.DEFAULT).y);
		item1.setControl(composite1);
		// item1.setImage(image);

		item1.setExpanded(true);

    // Second item
		Composite composite2 = new Composite(bar, SWT.NONE);
		composite2.setLayout(layout);
				
    Text cssSelector2 = new Text(composite2, SWT.BORDER);
    cssSelector2.setText("html body div.container-fluid div.row.row-offcanvas.row-offcanvas-left div.col-sm-9.col-md-9 img");

    Text Id2 = new Text(composite2, SWT.READ_ONLY | SWT.BORDER);
    Id2.setText("ID (read-only)");

		ExpandItem item2 = new ExpandItem(bar, SWT.NONE, 0);
		item2.setText("Step 2");
		item2.setHeight(composite2.computeSize(SWT.DEFAULT, SWT.DEFAULT).y);
		item2.setControl(composite2);
		item2.setExpanded(false);

		bar.setSpacing(8);
		shell.setSize(400, 350);
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		image.dispose();
		display.dispose();
	}
}
