package example;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

// based on https://github.com/chabala/example-swing-gui, repusposed

public class Gui {

	private static int count = 1;
	private static JFrame frame;

	public static void main(String[] args) {
		new Gui();
	}

	public Gui() {
		SwingUtilities.invokeLater(this::showFrame);
	}

	private void showFrame() {
		frame = new JFrame("Beeper " + count++);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		frame.setLayout(new FlowLayout());
		frame.add(new JLabel("Hello World"));
		frame.add(new BeepingButton("Click Me"));

		frame.pack();

		frame.setVisible(true);
	}

	private static class BeepingButton extends JButton {
		BeepingButton(final String text) {
			super(text);
			setPreferredSize(new Dimension(140, 60));
			addActionListener((e) -> {

				JPasswordModalDialog dialog2 = new JPasswordModalDialog(frame,
						"Modal Dialog", "Enter your credentials");
				System.err.println("Done");
			});
		}
	}

	public static class JPasswordModalDialog extends JDialog
			implements ActionListener {

		private JTextField userNameField;
		private JPasswordField passwordField;

		public JPasswordModalDialog(JFrame parent, String title, String message) {

			super(parent, title, Dialog.ModalityType.DOCUMENT_MODAL);
			if (parent != null) {
				Dimension parentSize = parent.getSize();
				Point p = parent.getLocation();
				setLocation(p.x + parentSize.width / 4, p.y + parentSize.height / 4);
			}
			setDefaultCloseOperation(DISPOSE_ON_CLOSE);

			JPanel messagePanel = new JPanel();
			messagePanel.add(new JLabel(message));
			getContentPane().add(messagePanel);

			JPanel inputPanel = new JPanel();
			getContentPane().add(inputPanel);

			// http://www.dreamincode.net/forums/topic/243512-making-buttons-span-multiple-columns-with-gridlayout/
			inputPanel.setLayout(new GridLayout(2, 2));
			JLabel label = new JLabel("User Name:", SwingConstants.RIGHT);
			JLabel label2 = new JLabel("Password:", SwingConstants.RIGHT);
			userNameField = new JTextField(20);
			passwordField = new JPasswordField();

			inputPanel.add(label);
			inputPanel.add(userNameField);
			inputPanel.add(label2);
			inputPanel.add(passwordField);

			JPanel buttonPanel = new JPanel();
			getContentPane().add(buttonPanel, BorderLayout.SOUTH);
			JButton button = new JButton("OK");
			button.addActionListener(this);
			buttonPanel.add(button);
			pack();
			setVisible(true);
		}

		@SuppressWarnings("deprecation")
		@Override
		public void actionPerformed(ActionEvent e) {
			String password = passwordField.getText();

			if (password.matches("password")) {
				dispose();
				setVisible(false);
			} else {
				System.err.println(" Wrong password: " + password);
				// beep the password is not valid
				Tone.sound(2000, 150);
			}
		}

	}
}
