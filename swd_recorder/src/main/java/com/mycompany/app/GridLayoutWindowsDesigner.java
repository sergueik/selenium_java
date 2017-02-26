package com.mycompany.app;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Optional;

import org.json.JSONException;
// import org.json.*;
import org.json.JSONObject;

import org.eclipse.swt.SWT;

import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.layout.GridDataFactory;

import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.ExpandBar;
import org.eclipse.swt.widgets.ExpandItem;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

import org.mihalis.opal.breadcrumb.*;

// modified in   WindowDesigner
public class GridLayoutWindowsDesigner {

	private String commandId;
	private Display display;
	private String dataKey = "CurrentCommandId";
	private static HashMap<String, String> elementData = new HashMap<String, String>(); // empty
	private static Shell parentShell = null;
	private static Boolean updated = false;
	private static String result = null;
	private static Shell shell;
	private static int step_index = 0;
	private Breadcrumb bc;

	public Image launch_icon;
	public Image find_icon;
	public Image shutdown_icon;
	public Image preferences_icon;
	public Image demo_icon;
	public Image page_icon;
	public Image flowchart_icon;
	public Image open_icon;
	public Image save_icon;
	private static int width = 900;
	private static int height = 800;

	private Composite composite;
	private static String[] Labels = { "Open application",
			"Navigate to login page", "Next step", "Something else"  };

	GridLayoutWindowsDesigner(Display display) {

		initUI(display);
	}

	private void initUI(Display display) {

		Shell shell = new Shell(display, SWT.SHELL_TRIM | SWT.CENTER);
		Rectangle boundRect = new Rectangle(0, 0, 768, 324);
		shell.setBounds(boundRect);
		Device dev = shell.getDisplay();

		shell.open();
		try {

			launch_icon = new Image(dev, getResourcePath("launch.png"));
			find_icon = new Image(dev, getResourcePath("find.png"));
			preferences_icon = new Image(dev, getResourcePath("preferences.png"));
			shutdown_icon = new Image(dev, getResourcePath("quit.png"));
			demo_icon = new Image(dev, getResourcePath("demo.png"));
			page_icon = new Image(dev, getResourcePath("page.png"));
			flowchart_icon = new Image(dev, getResourcePath("flowchart.png"));
			open_icon = new Image(dev, getResourcePath("open.png"));
			save_icon = new Image(dev, getResourcePath("save.png"));

		} catch (Exception e) {

			System.err.println("Cannot load images: " + e.getMessage());
			System.exit(1);
		}

		ToolBar toolBar = new ToolBar(shell, SWT.FLAT | SWT.HORIZONTAL | SWT.RIGHT);

		ToolItem tltmNewItem1 = new ToolItem(toolBar, SWT.PUSH);
		tltmNewItem1.setImage(launch_icon);

		ToolItem tltmNewItem2 = new ToolItem(toolBar, SWT.PUSH);
		tltmNewItem2.setImage(find_icon);

		ToolItem tltmNewItem3 = new ToolItem(toolBar, SWT.PUSH);
		tltmNewItem3.setImage(flowchart_icon);

		ToolItem tltmNewItem4 = new ToolItem(toolBar, SWT.PUSH);
		tltmNewItem4.setImage(preferences_icon);

		toolBar.pack();

		Composite composite = new Composite(shell, SWT.BORDER);
		composite.setBounds(0, 50, 400, 200);
		// composite.setLayoutData(
		// GridDataFactory.fillDefaults().grab(true, false).create());

		/*
    
		RowLayout layout = new RowLayout();
		layout.wrap = true;
		composite.setLayout(layout);
		*/
		GridLayout gridLayout = new GridLayout();
		gridLayout.marginLeft = 5;
		gridLayout.marginTop = 5;
		gridLayout.marginRight = 5;
		gridLayout.marginBottom = 5;
		gridLayout.numColumns = 2;
		gridLayout.makeColumnsEqualWidth = false;
		composite.setLayout(gridLayout);

		bc = new Breadcrumb(composite, SWT.BORDER);

    /*
		final BreadcrumbItem item1 = new BreadcrumbItem(bc,
				SWT.CENTER | SWT.TOGGLE);
		item1.setText(String.format("Step: %d", (int) (step_index + 1)));
		item1.setImage(page_icon);
		item1.setSelectionImage(page_icon);
		final BreadcrumbItem item2 = new BreadcrumbItem(bc,
				SWT.CENTER | SWT.TOGGLE);
		item2.setText(String.format("Step: %d", (int) (step_index + 1)));
		item2.setImage(page_icon);
		item2.setSelectionImage(page_icon);
		bc.pack();
    */
		composite.pack();

		tltmNewItem1.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetDefaultSelected(SelectionEvent event) {
			}

			@Override
			public void widgetSelected(SelectionEvent event) {
				final BreadcrumbItem item = new BreadcrumbItem(bc,
						SWT.CENTER | SWT.PUSH);
				item.setText(String.format("Step: %d", (int) (step_index + 1)));
				item.setImage(page_icon);
				item.setSelectionImage(page_icon);
				step_index++;
				// bc.pack();
				composite.pack();
				shell.pack();
				tltmNewItem2.setEnabled(true);
			}
		});

		tltmNewItem2.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetDefaultSelected(SelectionEvent event) {
			}

			@Override
			public void widgetSelected(SelectionEvent event) {
				Button s = new Button(composite, SWT.PUSH);
				if (step_index < Labels.length) {
					s.setText(String.format("Step %d: %s", (int) (step_index + 1),
							Labels[step_index]));
				} else {
					s.setText(String.format("Step %d", (int) (step_index + 1)));
				}
				step_index++;
				Control[] children = composite.getChildren();
				if (children.length == 0) {
					s.setParent(composite);
				} else {
					s.moveBelow(children[children.length - 1]);
				}
				composite.layout(new Control[] { s });

				// shell.layout(true, true);
				final Point newSize = shell.computeSize(SWT.DEFAULT, SWT.DEFAULT, true);
				if (newSize.x > 500) {
					shell.setBounds(boundRect);
				}
				composite.pack();
				shell.pack();
			}
		});

		while (!shell.isDisposed())

		{
			if (!display.readAndDispatch())
				display.sleep();
		}
	}

	@SuppressWarnings("unused")
	public static void main(String[] args) {

		Display display = new Display();
		GridLayoutWindowsDesigner o = new GridLayoutWindowsDesigner(display);
		display.dispose();
	}

	public String getResourcePath(String resourceFileName) {
		return String.format("%s/src/main/resources/%s",
				System.getProperty("user.dir"), resourceFileName);
	}

}