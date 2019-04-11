package example;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

// based on: 
// https://toster.ru/q/620170?e=7470646#comment_1874748 
// При создании окна создаётся Event Dispatch Thread, внутри которого крутится бесконечный цикл, на каждой итерации достающий событие из очереди и запускающий обработчик для него. В обработчике бесполезно использовать циклы для изменения интерфейса, так как все изменения просто встанут в очередь и будут выполнены только на одной из следующих итераций цикла событий. И тем более нельзя останавливать Поток Обработки Событий (что вы делаете вызовом Thread.sleep(1000)), это заморозит всё приложение. Поэтому необходимо использовать предлагаемые библиотекой механизмы запуска фоновых задач и взаимодействия с ними. Например такие, как SwingUtilities.invokeLater(), Timer и SwingWorker.

@SuppressWarnings("serial")
public class SwingButtonCountdownEx extends JPanel {

	private final JButton button = new JButton("Click me");
	private final Timer timer;
	private int interval = 1000;
	private int count = 3;

	// create timer in the constructor
	public SwingButtonCountdownEx() {
		timer = new Timer(1000, e -> {
			if (count > 0) {
				button.setVisible(false);
				button.setText(String.format("Counting %s", String.valueOf(count--)));
				button.doLayout();
				button.setVisible(true);
			} else {
				((Timer) (e.getSource())).stop();
				count = 3;
				button.setVisible(false);
				button.setText("Click me");
				button.doLayout();
				button.setVisible(true);
				button.setEnabled(true);
			}
		});
		timer.setInitialDelay(0);

		button.addActionListener(e -> {
			timer.start();
			button.setEnabled(false);
		});

		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		frame.add(button, BorderLayout.PAGE_END);
		frame.pack();
		frame.setVisible(true);
	}

	public static void main(String[] args) {
		new SwingButtonCountdownEx();
	}
}
