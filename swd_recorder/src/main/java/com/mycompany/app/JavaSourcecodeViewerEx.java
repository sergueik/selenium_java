package com.mycompany.app;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.text.MessageFormat;
import java.util.Hashtable;
import java.util.ResourceBundle;
import java.util.Vector;

import org.eclipse.swt.SWT;

import org.eclipse.swt.custom.LineStyleEvent;
import org.eclipse.swt.custom.LineStyleListener;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

import com.mycompany.app.Utils;

// http://www.java2s.com/Code/Java/SWT-JFace-Eclipse/JavaSourcecodeViewer.htm

public class JavaSourcecodeViewerEx {

	Shell shell;
	StyledText text;
	JavaLineStyler lineStyler = new JavaLineStyler();
	FileDialog fileDialog;

	Menu createFileMenu() {
		Menu bar = shell.getMenuBar();
		Menu menu = new Menu(bar);
		MenuItem item;

		// Exit
		item = new MenuItem(menu, SWT.PUSH);
		item.setText("Exit");
		item.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				menuFileExit();
			}
		});
		return menu;
	}

	void createMenuBar() {
		Menu bar = new Menu(shell, SWT.BAR);
		shell.setMenuBar(bar);

		MenuItem fileItem = new MenuItem(bar, SWT.CASCADE);
		fileItem.setText("File");
		fileItem.setMenu(createFileMenu());

	}

	void createShell(Display display) {
		shell = new Shell(display);
		shell.setText("Window");
		GridLayout layout = new GridLayout();
		layout.numColumns = 1;
		shell.setLayout(layout);
		shell.addShellListener(new ShellAdapter() {
			public void shellClosed(ShellEvent e) {
				lineStyler.disposeColors();
				text.removeLineStyleListener(lineStyler);
			}
		});
	}

	void createStyledText() {
		text = new StyledText(shell,
				SWT.BORDER | SWT.MULTI | SWT.V_SCROLL | SWT.H_SCROLL);
		GridData spec = new GridData();
		Font font = new Font(shell.getDisplay(), "Courier New", 12, SWT.NORMAL);
		text.setFont(font);
		spec.horizontalAlignment = GridData.FILL;
		spec.grabExcessHorizontalSpace = true;
		spec.verticalAlignment = GridData.FILL;
		spec.grabExcessVerticalSpace = true;
		text.setLayoutData(spec);
		text.addLineStyleListener(lineStyler);
		text.setEditable(false);
		Color bg = Display.getDefault().getSystemColor(SWT.COLOR_GRAY);
		text.setBackground(bg);

		String textString = new Utils().getScriptContent("ElementSearch.js");
		// Guard against superfluous mouse move events -- defer action until
		// later
		Display display = text.getDisplay();
		display.asyncExec(new Runnable() {
			public void run() {
				text.setText(textString);
			}
		});

		// parse the block comments up front since block comments can go across
		// lines - inefficient way of doing this
		lineStyler.parseBlockComments(textString);

	}

	void displayError(String msg) {
		MessageBox box = new MessageBox(shell, SWT.ICON_ERROR);
		box.setMessage(msg);
		box.open();
	}

	public static void main(String[] args) {
		Display display = new Display();
		JavaSourcecodeViewerEx example = new JavaSourcecodeViewerEx();
		Shell shell = example.open(display);
		while (!shell.isDisposed())
			if (!display.readAndDispatch())
				display.sleep();
		display.dispose();
	}

	public Shell open(Display display) {
		createShell(display);
		createMenuBar();
		createStyledText();
		shell.setSize(500, 400);
		shell.open();
		return shell;
	}

	void menuFileExit() {
		shell.close();
	}
}
