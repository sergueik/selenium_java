package example;

import static java.util.logging.Logger.getLogger;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Locale;
import javax.swing.JFrame;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.GroupLayout.Alignment;
import javax.swing.GroupLayout.ParallelGroup;
import javax.swing.GroupLayout.SequentialGroup;
import javax.swing.GroupLayout;

public class LocaleSwitcher extends JFrame {

	private javax.swing.JPanel jPanel;
	private javax.swing.JTextField jTextField;
	private javax.swing.JToggleButton jToggleButton;

	private Locale locale = new Locale("en", "US");

	public LocaleSwitcher() {
		initComponents();
	}

	@SuppressWarnings("unchecked")
	private void initComponents() {

		jPanel = new javax.swing.JPanel();
		jPanel.setSize(400, 100);
		jPanel.setLocation(10, 10);

		jTextField = new javax.swing.JTextField();
		jToggleButton = new javax.swing.JToggleButton();

		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

		jToggleButton.setText("jToggleButton1");
		jToggleButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				jToggleButtonActionPerformed(evt);
			}
		});

		GroupLayout jPanelLayout = new GroupLayout(jPanel);
		SequentialGroup sequentialGroup = jPanelLayout.createSequentialGroup();
		ParallelGroup parallelGroup = jPanelLayout
				.createParallelGroup(Alignment.LEADING);
		jPanel.setLayout(jPanelLayout);

		jPanelLayout
				.setHorizontalGroup(jPanelLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(jPanelLayout.createSequentialGroup().addContainerGap()
								.addGroup(jPanelLayout.createParallelGroup(Alignment.LEADING)
										.addComponent(jTextField)
										.addGroup(jPanelLayout.createSequentialGroup()
												.addComponent(jToggleButton).addGap(0, 200,
														Short.MAX_VALUE)))
								.addContainerGap()));
		jPanelLayout
				.setVerticalGroup(jPanelLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(jPanelLayout.createSequentialGroup().addContainerGap()
								.addComponent(jTextField, GroupLayout.PREFERRED_SIZE,
										GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addGap(18, 18, 18).addComponent(jToggleButton)
								.addContainerGap(100, Short.MAX_VALUE)));

		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout
				.setHorizontalGroup(layout.createParallelGroup(Alignment.LEADING)
						.addGroup(layout.createSequentialGroup().addContainerGap()
								.addComponent(jPanel, GroupLayout.DEFAULT_SIZE,
										GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addContainerGap()));
		layout
				.setVerticalGroup(layout.createParallelGroup(Alignment.LEADING)
						.addGroup(layout.createSequentialGroup().addContainerGap()
								.addComponent(jPanel, GroupLayout.DEFAULT_SIZE,
										GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addContainerGap()));

		pack();
	}

	private void jToggleButtonActionPerformed(ActionEvent evt) {
		if (jToggleButton.isSelected()) {
			System.err.println("Locale was: " + jPanel.getInputContext().getLocale());
			locale = new Locale("ru", "RU");
			Locale.setDefault(locale);
			// jPanel1.getInputContext().selectInputMethod(locale);
			// jTextField.setLocale(locale);
			jToggleButton.setText(locale.getDisplayLanguage());
			jPanel.getInputContext().selectInputMethod(locale);
			System.err.println(
					"Locale changed to: " + jPanel.getInputContext().getLocale());
			// the next line changes input locale on Windows but not on Linux
			jPanel.getInputContext().selectInputMethod(new Locale("ru", "RU"));
		} else if (jToggleButton.isSelected() == false) {
			locale = new Locale("en", "US");
			jToggleButton.setText(locale.getDisplayLanguage());
			System.err.println("Locale was: " + jPanel.getInputContext().getLocale());
			jPanel.getInputContext().selectInputMethod(new Locale("en", "US"));
			// jTextField.setLocale(locale);
			System.err.println(
					"Locale changed to: " + jPanel.getInputContext().getLocale());
			// the next line changes input locale on Windows but not on Linux
		}
		jTextField.requestFocusInWindow();
	}

	private void jToggleButtonActionPerformedOrig(ActionEvent evt) {
		if (jToggleButton.isSelected()) {
			jToggleButton.setText("RU");
			jPanel.getInputContext().selectInputMethod(new Locale("ru", "RU"));
		} else if (jToggleButton.isSelected() == false) {
			jToggleButton.setText("EN");
			jPanel.getInputContext().selectInputMethod(new Locale("en", "US"));
		}
		jTextField.requestFocusInWindow();
	}

	public static void main(String args[]) {
		Logger logger = getLogger(LocaleSwitcher.class.getName());
		/*
		try {
			
			for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager
					.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					javax.swing.UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
			
		} catch (InstantiationException | ClassNotFoundException
				| IllegalAccessException e) {
			logger.log(Level.SEVERE, null, e);
		} catch (javax.swing.UnsupportedLookAndFeelException e) {
			logger.log(Level.WARNING, null, e);
		}
		*/
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				new LocaleSwitcher().setVisible(true);
			}
		});
	}
}
