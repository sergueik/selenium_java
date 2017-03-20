package com.mycompany.app;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.MessageDialog;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import com.mycompany.app.Utils;

/**
 * Exception dialog for Selenium Webdriver Elementor Tool
 * 
 * @author Serguei Kouzmine (kouzmine_serguei@yahoo.com)
 */
// based on http://www.vogella.com/tutorials/EclipseDialogs/article.html
// http://www.programcreek.com/java-api-examples/org.eclipse.jface.dialogs.ErrorDialog
// http://stackoverflow.com/questions/2826959/jface-errordialog-how-do-i-show-something-in-the-details-portion

public class ExceptionDialogEx {

	private MultiStatus status;
	private Shell shell = null;
	private Display display;

	public static void main(String[] arg) {
		try {
			String s = null;
			System.out.println(s.length());
		} catch (NullPointerException e) {
			// create exception on purpose to demonstrate ErrorDialog
			ExceptionDialogEx o = new ExceptionDialogEx(null, null, e);
			// show the error dialog with exception trace
			o.execute();
		}
	}

	public void execute() {

		ErrorDialog.openError(shell, "Error", "Exception thrown", status);

		// TODO: next does not show the dialog
		/*
		  import org.eclipse.jdt.internal.ui.dialogs.ProblemDialog;
		  ProblemDialog dialog = new ProblemDialog(shell, "Error", "Exception thrown", new Image(dev, new Utils().getResourcePath("launch.png"));
		      status, 0);
		  // dialog.setDefaultImages();
		  dialog.create();
		  dialog.open();
		*/
	}

	public ExceptionDialogEx(Display parentDisplay, Shell parentShell,
			Throwable e) {
		display = (parentDisplay != null) ? parentDisplay : new Display();
		shell = new Shell(display);
		if (parentShell != null) {
			shell = parentShell;
			// commandId = parent.getData(dataKey).toString();
		} else {
			shell = Display.getCurrent().getActiveShell();
		}
		// Collect the exception stack trace
		status = createMultiStatus(e.getLocalizedMessage(), e);
	}

	private static MultiStatus createMultiStatus(String description,
			Throwable t) {
		List<Status> childStatuses = new ArrayList<>();

		for (StackTraceElement stackTrace : Thread.currentThread()
				.getStackTrace()) {
			Status status = new Status(IStatus.ERROR, "com.mycompany.app",
					stackTrace.toString());
			childStatuses.add(status);
		}
		String summary = (description != null) ? description : t.toString();
		MultiStatus status = new MultiStatus("com.mycompany.app",
				IStatus.ERROR, childStatuses.toArray(new Status[] {}),
				(summary.length() > 120) ? summary.substring(0, 120) : summary,
				t);
		return status;
	}
}