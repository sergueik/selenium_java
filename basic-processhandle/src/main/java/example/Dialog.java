package example;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

// origin: http://www.java2s.com/Tutorial/Java/0240__Swing/ASimpleModalDialog.htm
public class Dialog extends JDialog implements ActionListener {
	public Dialog(JFrame parent, String title, String message) {
		super(parent, title, true);
		JPanel messagePane = new JPanel();
		messagePane.add(new JLabel(message));
		getContentPane().add(messagePane);
		JPanel buttonPane = new JPanel();
		JButton button = new JButton("OK");
		buttonPane.add(button);
		button.addActionListener(this::actionPerformed);
		getContentPane().add(buttonPane, BorderLayout.SOUTH);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		pack();
		setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {
		setVisible(false);
		dispose();
	}

	public static void main(String[] a) {
		new Dialog(new JFrame(), "title", "message");
		System.exit(0);
	}
}
