package example;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import javax.swing.JTextField;

import java.awt.AWTException;
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.im.spi.InputMethodDescriptor;

// example used in https://qna.habr.com/q/723889?e=8710175#clarification_844719
public class LocaleManagementExample extends javax.swing.JFrame {

	private javax.swing.JPanel jPanel1;
	private javax.swing.JTextField jTextField;
	private javax.swing.JToggleButton jToggleButton;

	private Locale locale = new Locale("en", "US");

	public LocaleManagementExample() {
		initComponents();
	}

	private void initComponents() {

		jPanel1 = new javax.swing.JPanel();
		jTextField = new javax.swing.JTextField();

		jTextField.addFocusListener(new FocusListener() {
			public void focusGained(FocusEvent e) {
				System.err.println("Locale is: " + ((JTextField) e.getSource()).getLocale());
			}

			public void focusLost(FocusEvent e) {
			}

			@SuppressWarnings("unused")
			void displayMessage(String prefix, FocusEvent e) {
			}
		});

		jTextField.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				System.err.println("Value: " + ((JTextField) e.getSource()).getText());
				// textField.setText(text.toUpperCase());
			}

			public void keyTyped(KeyEvent e) {
			}

			public void keyPressed(KeyEvent e) {
			}
		});

		jToggleButton = new javax.swing.JToggleButton();

		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

		jToggleButton.setText("jToggleButton1");
		jToggleButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jToggleButtonActionPerformed(evt);
			}
		});

		javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
		jPanel1.setLayout(jPanel1Layout);
		jPanel1Layout.setHorizontalGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jPanel1Layout.createSequentialGroup().addContainerGap()
						.addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addComponent(jTextField).addGroup(jPanel1Layout.createSequentialGroup()
										.addComponent(jToggleButton).addGap(0, 588, Short.MAX_VALUE)))
						.addContainerGap()));
		jPanel1Layout.setVerticalGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jPanel1Layout.createSequentialGroup().addContainerGap()
						.addComponent(jTextField, javax.swing.GroupLayout.PREFERRED_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addGap(18, 18, 18).addComponent(jToggleButton).addContainerGap(504, Short.MAX_VALUE)));

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup().addContainerGap().addComponent(jPanel1,
						javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addContainerGap()));
		layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup().addContainerGap().addComponent(jPanel1,
						javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addContainerGap()));

		pack();
	}

	private void jToggleButtonActionPerformed(java.awt.event.ActionEvent evt) {
		if (jToggleButton.isSelected()) {
			System.err.println("Locale was: " + jPanel1.getInputContext().getLocale());
			locale = new Locale("ru", "RU");
			Locale.setDefault(locale);
			// jPanel1.getInputContext().selectInputMethod(locale);
			// jTextField.setLocale(locale);
			jToggleButton.setText(locale.getDisplayLanguage());
			jPanel1.getInputContext().selectInputMethod(locale);
			System.err.println("Locale changed to: " + jPanel1.getInputContext().getLocale());
			// the next line changes input locale on Windows but not on Linux
			jPanel1.getInputContext().selectInputMethod(new Locale("ru", "RU"));
		} else if (jToggleButton.isSelected() == false) {
			locale = new Locale("en", "US");
			jToggleButton.setText(locale.getDisplayLanguage());
			System.err.println("Locale was: " + jPanel1.getInputContext().getLocale());
			jPanel1.getInputContext().selectInputMethod(new Locale("en", "US"));
			// jTextField.setLocale(locale);
			System.err.println("Locale changed to: " + jPanel1.getInputContext().getLocale());
			// the next line changes input locale on Windows but not on Linux
		}
		jTextField.requestFocusInWindow();
	}

	/**
	 * @param args the command line arguments
	 */
	public static void main(String args[]) {
		try {
			for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					javax.swing.UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (ClassNotFoundException ex) {
			java.util.logging.Logger.getLogger(LocaleManagementExample.class.getName())
					.log(java.util.logging.Level.SEVERE, null, ex);
		} catch (InstantiationException ex) {
			java.util.logging.Logger.getLogger(LocaleManagementExample.class.getName())
					.log(java.util.logging.Level.SEVERE, null, ex);
		} catch (IllegalAccessException ex) {
			java.util.logging.Logger.getLogger(LocaleManagementExample.class.getName())
					.log(java.util.logging.Level.SEVERE, null, ex);
		} catch (javax.swing.UnsupportedLookAndFeelException ex) {
			java.util.logging.Logger.getLogger(LocaleManagementExample.class.getName())
					.log(java.util.logging.Level.SEVERE, null, ex);
		}
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				new LocaleManagementExample().setVisible(true);
			}
		});
	}

}
