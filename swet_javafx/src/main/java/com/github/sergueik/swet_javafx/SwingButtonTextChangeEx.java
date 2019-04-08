package com.github.sergueik.swet_javafx;

import java.awt.BorderLayout;
import java.awt.Container;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

// import javax.swing.SwingUtilities;
import static javax.swing.SwingUtilities.isMiddleMouseButton;
import static javax.swing.SwingUtilities.isLeftMouseButton;
import static javax.swing.SwingUtilities.isRightMouseButton;

import static javax.swing.SwingUtilities.invokeLater;

// based on: http://www.java2s.com/Code/Java/Swing-JFC/ButtonActionSample.htm
// http://www.java2s.com/Tutorials/Java/Swing_How_to/Thread/Shake_Button_text_in_animation.htm

@SuppressWarnings({ "unused", "serial" })
public class SwingButtonTextChangeEx extends JPanel {

	private static JButton button;
	private static int total = 10;

	public static void main(String args[]) {
		JFrame frame = new JFrame("Button text change example");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		button = new JButton("countdown test");

		ActionListener actionListener = new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				// System.err.println("I was selected.");
			}
		};

		MouseListener mouseListener = new MouseAdapter() {
			public void mousePressed(MouseEvent mouseEvent) {
				int modifiers = mouseEvent.getModifiers();
				if ((modifiers & InputEvent.BUTTON1_MASK) == InputEvent.BUTTON1_MASK) {
					System.err.println("Left button pressed.");
				}

				if ((modifiers & InputEvent.BUTTON2_MASK) == InputEvent.BUTTON2_MASK) {
					System.err.println("Middle button pressed.");
				}
				if ((modifiers & InputEvent.BUTTON3_MASK) == InputEvent.BUTTON3_MASK) {
					System.err.println("Right button pressed.");
				}

			}

			public void mouseReleased(MouseEvent mouseEvent) {
				if (isLeftMouseButton(mouseEvent)) {
					System.err.println("Left button released.");
					System.err.println("Counting down from: " + total);

					// NOTE: SwingUtilities.invokeLater displays only the last update of
					// the button text:
					// invokeLater(new Runnable() { button.setText(buttonText); });
					Runnable buttonAnimationRunnable = new Runnable() {
						@Override
						public void run() {
							final int progress = total;
							for (int current = progress; current > 0; current--) {
								String buttonText = String.format("Calculated %d of %d",
										current, progress);
								button.setVisible(false);
								button.setText(buttonText);
								button.doLayout();
								button.setVisible(true);
								System.err.println("Set button text to: " + buttonText);
								try {
									Thread.sleep(300);
								} catch (InterruptedException e) {
									System.err.println("Exception (ignored): " + e.toString());
								}
							}
						}
					};
					Thread buttonAnimationThread = new Thread(buttonAnimationRunnable);
					buttonAnimationThread.start();
				}
				if (isMiddleMouseButton(mouseEvent)) {
					System.err.println("Middle button released.");
				}
				if (isRightMouseButton(mouseEvent)) {
					System.err.println("Right button released.");
				}

				// indicate unmet progress
				// System.err.println();
			}
		};

		button.addActionListener(actionListener);
		button.addMouseListener(mouseListener);

		Container contentPane = frame.getContentPane();
		contentPane.add(button, BorderLayout.SOUTH);
		frame.setSize(300, 100);
		frame.setVisible(true);
	}
}
