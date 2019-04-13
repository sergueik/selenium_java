package example;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

// based on: https://www.roseindia.net/java/example/java/swing/frame-close-on-button-click.shtml
// absorbing the virtual event handler class into self

public class CountdownButton3Ex implements ActionListener {

	private final Timer timer;
	private JPanel jPanel;
	private Container contentPane;
	private JFrame frame;
	private JButton button;

	private int interval = 1000;
	private int totalSteps = 10;
	int remainSteps = totalSteps;

	public void createUI() {
		frame = new JFrame("Countdown Example");
		frame.setLayout(null);
		frame.setSize(400, 300);

		contentPane = frame.getContentPane();
		contentPane.setLayout(null);
		Insets insets = contentPane.getInsets();
		Dimension size;

		jPanel = new JPanel();

		contentPane.add(jPanel);
		button = new JButton("Close Frame");
		button.addActionListener(this);
		jPanel.add(button);
		size = jPanel.getPreferredSize();
		jPanel.setBounds(150 + insets.left, 100 + insets.top, size.width,
				size.height);

		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		frame.setLayout(new BorderLayout());
		frame.add(button, BorderLayout.PAGE_END);
		frame.pack();
		frame.setVisible(true);

	}


	public CountdownButton3Ex() {
		// create timer in the constructor
		timer = new Timer(interval, e -> {
			if (remainSteps > 0) {
				button.setVisible(false);
				String buttonText = String.format("Closing in %s sec",
						String.valueOf(remainSteps--));
				button.setText(buttonText);
				button.doLayout();
				button.setVisible(true);
			} else {
				((Timer) (e.getSource())).stop();
				frame.dispose();
				System.out.println("Frame Closed.");
			}
		});
		timer.setInitialDelay(0);

	}

	// normally anonymous
	public void actionPerformed(ActionEvent e) {

		timer.start();
		button = (JButton) e.getSource();
		button.setEnabled(false);
	}

	public static void main(String args[]) {
		CountdownButton3Ex o = new CountdownButton3Ex();
		o.createUI();
	}
}
