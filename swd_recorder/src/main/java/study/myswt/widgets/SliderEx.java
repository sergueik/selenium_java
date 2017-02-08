package study.myswt.widgets;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Slider;

/**
 * ZetCode Java SWT tutorial
 *
 * In this program, we use the slider widget to create a volume control
 *
 * Author: Jan Bodnar Website: zetcode.com Last modified: June 2015
 */

public class SliderEx {

	private Shell shell;
	private Label label;
	private Image mute;
	private Image min;
	private Image med;
	private Image max;

	public SliderEx(Display display) {

		initUI(display);
	}

	private void loadImages() {

		Device dev = shell.getDisplay();

		try {

			mute = new Image(dev, "slider_mute.png");
			min = new Image(dev, "slider_min.png");
			med = new Image(dev, "slider_med.png");
			max = new Image(dev, "slider_max.png");

		} catch (Exception e) {

			System.out.println("Cannot load images");
			System.out.println(e.getMessage());
			System.exit(1);
		}
	}

	private void initUI(Display display) {

		shell = new Shell(display, SWT.SHELL_TRIM | SWT.CENTER);

		loadImages();

		RowLayout layout = new RowLayout();
		layout.marginLeft = 30;
		layout.marginTop = 30;
		layout.spacing = 30;
		layout.fill = true;
		shell.setLayout(layout);

		Slider slider = new Slider(shell, SWT.HORIZONTAL);
		slider.setMaximum(100);
		slider.setLayoutData(new RowData(180, -1));

		label = new Label(shell, SWT.IMAGE_PNG);
		label.setImage(mute);

		slider.addListener(SWT.Selection, event -> onSelection(slider));

		shell.setText("Slider");
		shell.setSize(350, 200);
		shell.open();

		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	private void onSelection(Slider slider) {

		int value = slider.getSelection();

		if (value == 0) {
			label.setImage(mute);
			label.pack();
		} else if (value > 0 && value <= 30) {
			label.setImage(min);
		} else if (value > 30 && value < 80) {
			label.setImage(med);
		} else {
			label.setImage(max);
		}
	}

	@Override
	public void finalize() {

		mute.dispose();
		med.dispose();
		min.dispose();
		max.dispose();
	}

	public static void main(String[] args) {

		Display display = new Display();
		SliderEx ex = new SliderEx(display);
		ex.finalize();
		display.dispose();
	}
}