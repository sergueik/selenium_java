package com.test.TestLinkExcelToXml;

import java.awt.Color;
import java.awt.FileDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class TestLinkExcelToXmlGUI implements ActionListener {

	JTextField ja1 = new JTextField(20);
	FileDialog fd = null;
	JFrame jf = null;
	JButton jb1 = null;
	JButton jb2 = null;
	String excelTestCase;
	String ouputPath;

	public TestLinkExcelToXmlGUI() {
		jf = new JFrame("TestLinkExcelToXml 1.0");
		fd = new FileDialog(jf);
		JPanel j1 = new JPanel();
		JPanel j2 = new JPanel();
		JLabel jl1 = new JLabel("ExcelTestCase:");
		jb1 = new JButton("Choose");
		jb2 = new JButton("ExcelToXml");
		jb1.addActionListener(this);
		jb2.addActionListener(this);
		j1.add(jl1);
		ja1.setEditable(false);
		ja1.setBackground(Color.white);
		j1.add(ja1);
		j1.add(jb1);
		jb2.setEnabled(false);
		j2.add(jb2);
		jf.add(j1, "North");
		jf.add(j2);
		jf.setLocation(300, 200);
		jf.setVisible(true);
		jf.pack();
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public static void main(String[] args) {
		new TestLinkExcelToXmlGUI();
	}

	public void actionPerformed(ActionEvent e) {
		String comm = e.getActionCommand();
		if (comm.equals("Choose")) {
			fd.setVisible(true);
			if (fd.getFile() != null) {
				if (fd.getFile().endsWith(".xls") || fd.getFile().endsWith(".xlsx")) {
					ja1.setText(fd.getDirectory() + fd.getFile());
					excelTestCase = fd.getDirectory() + fd.getFile();
					ouputPath = fd.getDirectory();
					jb2.setEnabled(true);
				} else {
					JOptionPane.showMessageDialog(ja1, "Please choose an excel file！");
					ja1.setText("");
					jb2.setEnabled(false);
				}
			}
		} else {
			jb2.setEnabled(false);
			jb1.setEnabled(false);
			new FileTransferTool(ja1, jb1, jb2, excelTestCase, ouputPath).start();
		}
	}
}
