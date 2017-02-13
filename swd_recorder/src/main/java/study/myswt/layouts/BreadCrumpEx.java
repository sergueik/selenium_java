package study.myswt.layouts;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.mihalis.opal.breadcrumb.*;

/**
 * based on: simple snippet for the breadcrumb Widget by (c)Laurent Caron
 */
 
public class BreadCrumpEx {

	private static Image[] images;
	private static String[] Labels = { "Open application",
			"Navigate to login page", "Something else" };

	private static Display display;

	public static void main(final String[] args) {
		display = new Display();
		final Shell shell = new Shell(display);
		shell.setText("BreakCrumb Snippet");
		shell.setLayout(new GridLayout(2, false));

		createImages();
		createToggleButtonsBreadCrumb(shell);

		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		display.dispose();
	}

	private static void createToggleButtonsBreadCrumb(final Shell shell) {
		final Label label = new Label(shell, SWT.NONE);
		label.setText("Toggle buttons breadcrumb:");
		label.setLayoutData(
				new GridData(GridData.END, GridData.CENTER, false, false));

		createBreadcrumb(shell, SWT.BORDER, SWT.CENTER | SWT.TOGGLE, true);
		// new Label(shell, SWT.NONE);

	}

	private static void createBreadcrumb(final Shell shell,
			final int breadCrumbArgument, final int itemArgument,
			final boolean showImages) {
		final Breadcrumb bc = new Breadcrumb(shell, breadCrumbArgument);
		bc.setLayoutData(
				new GridData(GridData.BEGINNING, GridData.CENTER, false, false));

		for (int cnt = 1; cnt < 5; cnt++) {
			final BreadcrumbItem item = new BreadcrumbItem(bc, itemArgument);

			int step_index = cnt - 1;
			item.setData("Item " + String.valueOf(cnt));
			item.setText((step_index < Labels.length)
					? String.format("Step %d: %s", (int) (step_index + 1), Labels[step_index])
					: String.format("Step %d", (int) (step_index + 1)));

			if (showImages) {
				item.setImage(images[cnt]);
				item.setSelectionImage(images[cnt]);
			}
			item.addSelectionListener(new SelectionAdapter() {

				@Override
				public void widgetSelected(final SelectionEvent e) {
					System.out
							.println(String.format("Clicked %s", e.item.getData().toString()));
				}

			});
		}
	}

	private static String getResourcePath(String resourceFileName) {
		String resourcePath = String.format("%s/src/main/resources/%s",
				System.getProperty("user.dir"), resourceFileName);
		System.err.println("Resource path : " + resourcePath);
		return (resourcePath);
	}

	private static void createImages() {
		images = new Image[5];
		final String[] fileNames = new String[] { "add.png", "bell.png", "feed.png",
				"house.png", "page-white-text-width-icon.png" };
		for (int i = 0; i < 5; i++) {
			// final Image image = new Image(Display.getCurrent(),
			// BreadCrumpEx.class.getClassLoader().getResourceAsStream("org/mihalis/opal/breadcrumb/"
			// + fileNames[i]));
			final Image image = new Image(Display.getCurrent(),
					getResourcePath(fileNames[i]));
			images[i] = image;
		}
	}

}
