package example;

import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
// https://www.geeksforgeeks.org/java-swing-jpanel-examples/
public class TwoJPanel extends JFrame {

	public TwoJPanel() {
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setExtendedState(MAXIMIZED_BOTH);
		setTitle("Rails");
		week();
		setVisible(true);

	}

	public void week() {
		Object[] headers = { "1", "Lesson" };
		Object[][] monday = { { "1", "English" }, { "2", "Algebra" } };
		Object[][] two = { { "1", "Physic" }, { "2", "Kazkah" } };

		JTable jtMonday = new JTable(monday, headers);
		JTable jtTwo = new JTable(two, headers);

		JPanel weekClassOne = new JPanel();
		weekClassOne.setBounds(0, 20, 400, 650);
		weekClassOne.setBackground(Color.DARK_GRAY);
		weekClassOne.setBorder(
				BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(),
						"1", TitledBorder.CENTER, TitledBorder.TOP));
		weekClassOne.add(new JScrollPane(jtMonday));
		weekClassOne.add(new JScrollPane(jtTwo));

		// this.getContentPane().add(weekClassOne);
		this.add(weekClassOne);

		JTable jtMondayCls2 = new JTable(monday, headers);
		JTable jtTwoCls2 = new JTable(two, headers);

		JPanel weekClassTwo = new JPanel();
		weekClassTwo.setBounds(0, 20, 400, 650);
		weekClassTwo.setBackground(Color.PINK);
		weekClassTwo.setBorder(
				BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(),
						"2", TitledBorder.CENTER, TitledBorder.TOP));
		weekClassTwo.add(new JScrollPane(jtMondayCls2));
		weekClassTwo.add(new JScrollPane(jtTwoCls2));

		// this.getContentPane().add(weekClassTwo);
		this.add(weekClassTwo);

	}

	public static void main(String[] args) {
		new TwoJPanel();
	}
}
