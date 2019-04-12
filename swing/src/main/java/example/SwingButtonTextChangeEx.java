package example;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

// based on:
// see also http://www.kdgregory.com/index.php?page=swing.async
// http://www.java2s.com/Code/Java/Swing-JFC/ButtonActionSample.htm
// http://www.java2s.com/Tutorials/Java/Swing_How_to/Thread/Shake_Button_text_in_animation.htm

// based on: http://www.java2s.com/Code/Java/Swing-JFC/ButtonActionSample.htm
// http://www.java2s.com/Tutorials/Java/Swing_How_to/Thread/Shake_Button_text_in_animation.htm

@SuppressWarnings({ "serial" })
public class SwingButtonTextChangeEx extends JPanel {

	private static JButton button;
	private static int total = 10;

	public static void main(String args[]) {
		JFrame frame = new JFrame("Button text change example");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		button = new JButton("countdown test");

		ActionListener actionListener = new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				System.err.println("Counting down from: " + total);

				// NOTE: when loop is moved outside of the runnable, the
				// SwingUtilities.invokeLater displays only the last update of
				// the button text:
				// invokeLater(new Runnable() { button.setText(buttonText); });
				Runnable buttonAnimationRunnable = new Runnable() {
					@Override
					public void run() {
						final int progress = total;
						for (int current = 0; current != progress; current++) {
							String buttonText = String.format("Close in %d sec",
									(progress - current));

							SwingUtilities.invokeLater(new Runnable() {
								public void run() {
									button.setVisible(false);
									button.setText(buttonText);
									button.doLayout();
									button.setVisible(true);
								}
							});
							System.err.println("Set button text to: " + buttonText);
							try {
								Thread.sleep(1000);
							} catch (InterruptedException e) {
								System.err.println("Exception (ignored): " + e.toString());
							}
						}
						System.err.println("Closing.");
						frame.dispose();
					}
				};
				Thread buttonAnimationThread = new Thread(buttonAnimationRunnable);
				buttonAnimationThread.start();
			}
		};

		button.addActionListener(actionListener);

		Container contentPane = frame.getContentPane();
		contentPane.add(button, BorderLayout.CENTER);
		frame.setSize(300, 100);
		frame.setVisible(true);
	}
}
