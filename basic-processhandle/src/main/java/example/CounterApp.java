package example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CounterApp extends JFrame {

	private static final long serialVersionUID = 1L;
	private int value;
	private JLabel counterValueView;

	public CounterApp(int initialValue) {
		setBounds(500, 500, 300, 120);
		setTitle("Простой счетчик");
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		Font font = new Font("Arial", Font.BOLD, 30);
		counterValueView = new JLabel();
		counterValueView.setFont(font);
		counterValueView.setHorizontalAlignment(SwingConstants.CENTER);
		add(counterValueView, BorderLayout.CENTER);

		value = initialValue;
		counterValueView.setText(String.valueOf(value));

		JButton decrementButton = new JButton("<");
		decrementButton.setFont(font);
		add(decrementButton, BorderLayout.WEST);

		JButton incrementButton = new JButton(">");
		incrementButton.setFont(font);
		add(incrementButton, BorderLayout.EAST);

		JButton resetButton = new JButton("сброс");
		resetButton.setFont(font);
		add(resetButton, BorderLayout.SOUTH);

		JButton saveButton = new JButton("сохранить");
		saveButton.setFont(font);
		add(saveButton, BorderLayout.NORTH);

		decrementButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				value--;
				counterValueView.setText(String.valueOf(value));
			}
		});

		incrementButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				value++;
				counterValueView.setText(String.valueOf(value));
			}
		});

		resetButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				value = 0;
				counterValueView.setText(String.valueOf(value));
			}
		});

		saveButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				counterValueView.setText(String.valueOf(value));
			}
		});

		setVisible(true);
	}

	public static void main(String[] args) {
		new CounterApp(0);
	}

}
