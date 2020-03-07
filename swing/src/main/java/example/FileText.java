package example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JFrame;

public class FileText extends JFrame {
	public void FileText() {
		JFrame frame = new JFrame("Laboratory work");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JFrame.setDefaultLookAndFeelDecorated(true);
		setLayout(new FlowLayout());

		frame.setSize(400, 200);
		frame.setLocation(0, 0);
		/*
		frame.setIconImage(
				new ImageIcon("D:\\Projects\\guilaba\\src\\main\\resources\\icon3.jpg")
						.getImage());
						*/
		// exemplari
		JPanel panel01 = new JPanel();
		JPanel panel02 = new JPanel();
		panel01.setBounds(0, 0, 1440, 50);
		panel01.setBackground(Color.lightGray);
		panel02.setBounds(0, 50, 1440, 850);
		panel02.setBackground(Color.yellow);
		JLabel label01 = new JLabel("Server Idle Time: ");
		JLabel label02 = new JLabel("Average residence time in queue: ");
		JTextField text01 = new JTextField(15);
		text01.setText("Not counted yet");
		JTextField text02 = new JTextField(15);
		text02.setText("Not counted yet");
		JButton calculate = new JButton("Calculate");
		// LISTENER -ButtonActionListener
		calculate.addActionListener(new Listener());
		// JPANE
		JTextArea textp = new JTextArea();
		JScrollPane pane = new JScrollPane(textp);
		pane.setPreferredSize(new Dimension(1440, 860));
		// add
		panel01.add(label01);
		panel01.add(text01);
		panel01.add(label02);
		panel01.add(text02);
		panel01.add(calculate);
		panel02.add(pane);
		frame.add(panel01);
		frame.add(panel02);
		// setvisible
		frame.setVisible(true);
	}

	public static void main(String args[]) {
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				new FileText().setVisible(true);
			}
		});
	}

	private static class Listener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				FileReader reader = new FileReader("table.txt");
				BufferedReader br = new BufferedReader(reader);
				JTextArea textp = new JTextArea();
				char buffer[] = new char[4096];
				int len;
				while ((len = reader.read(buffer)) != -1) {
					String s = new String(buffer, 0, len);
					textp.append(s);
				}
				br.close();
				textp.requestFocus();
			} catch (IOException ex) {
				System.out.println("Error");
			}
		}
	}
}
