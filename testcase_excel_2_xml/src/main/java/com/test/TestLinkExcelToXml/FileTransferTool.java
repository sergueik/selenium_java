
package com.test.TestLinkExcelToXml;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class FileTransferTool extends Thread {
	private JTextField ja1 = null;
	private JButton jb1 = null;
	private JButton jb2 = null;
	private String excelTestCase;
	private String ouputPath;

	public FileTransferTool(JTextField ja1, JButton jb1, JButton jb2,
			String excelTestCase, String ouputPath) {
		this.ja1 = ja1;
		this.jb1 = jb1;
		this.jb2 = jb2;
		this.excelTestCase = excelTestCase;
		this.ouputPath = ouputPath;
	}

	@Override
	public void run() {

		System.out.println("Excel to xml convert start!");
		System.out.println("excelTestCase:" + excelTestCase);
		try {
			TestLinkExcelToXml.excelToXML(ouputPath, excelTestCase);
		} catch (Exception e) {
			e.printStackTrace();
		}
		JOptionPane.showMessageDialog(jb2, "Excel to xml convert end!");
		System.out.println("Excel to xml convert end!");
		ja1.setText("");
		jb1.setEnabled(true);

	}
}
