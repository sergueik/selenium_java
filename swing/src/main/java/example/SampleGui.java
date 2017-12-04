package example;

import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

// based on https://github.com/chabala/example-swing-gui, re-pusposed
public class SampleGui {

	private static JFrame frame;

	public static void main(String[] args) {
		new SampleGui();
	}

	public SampleGui() {
		SwingUtilities.invokeLater(this::showFrame);
	}

	private void showFrame() {
		frame = new JFrame("Basic");
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		frame.setLayout(new FlowLayout());
		frame.add(new JLabel("Hello World"));
		frame.add(new BasicButton("Click Me"));
		frame.pack();
		frame.setVisible(true);
	}

	private static class BasicButton extends JButton {
		BasicButton(final String text) {
			super(text);
			setPreferredSize(new Dimension(140, 60));
			addActionListener((e) -> {

				JPasswordModalDialog dialog2 = new JPasswordModalDialog(frame,
						"Modal Dialog", "Enter your credentials");
				System.err.println("Done");
			});
		}
	}
}
