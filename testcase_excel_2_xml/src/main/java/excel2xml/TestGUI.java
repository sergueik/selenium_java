package excel2xml;

import java.awt.Color;
import java.awt.FileDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.junit.Test;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.junit.Test;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

public class TestGUI implements ActionListener {

	JTextField ja1 = new JTextField(20);
	FileDialog fd = null;
	JFrame jf = null;
	JButton jb1 = null;
	JButton jb2 = null;
	String excelTestCase;
	String ouputPath;

	public TestGUI() {
		jf = new JFrame("ExcelToXml 1.0");
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
		new TestGUI();
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

	private static class FileTransferTool extends Thread {
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
				TestGUI.ExcelToXml.run(ouputPath, excelTestCase);
			} catch (Exception e) {
				e.printStackTrace();
			}
			// http://www.java2s.com/Tutorial/Java/0240__Swing/UsingJOptionPanetoDisplayaMessage.htm
			JOptionPane.showMessageDialog(jb2, "Excel to xml convert end!");
			System.out.println("Excel to xml convert end!");
			ja1.setText("");
			jb1.setEnabled(true);

		}
	}

	private static class ExcelToXml {

		static String testSuite;
		static String testSuiteDetails;

		static String testCase;
		static String testCaseName;
		static String testCaseSummary;
		static String testCasePreconditions;
		static String testCaseType;
		static String testCaseStatus;
		static String testCaseImportance;

		static String testStep;
		static String testStepAction;
		static String testStepResult;
		static String testStepType;

		static int stepCount;

		@Test
		public static void run(String filePath, String xlsFile) throws Exception {

			InputStream is = new FileInputStream(xlsFile);
			Workbook book = Workbook.getWorkbook(is);
			Sheet sheet = book.getSheet(0);
			Element testsuite = DocumentHelper.createElement("testsuite");
			Document document = DocumentHelper.createDocument(testsuite);
			Element tsDetails = testsuite.addElement("details");

			for (int i = 0; i < sheet.getRows(); i++) {
				Cell finder = sheet.getCell(0, i);
				if (finder.getContents().compareTo("testSuiteName") == 0) {
					testSuite = sheet.getCell(1, i).getContents();
					testsuite.addAttribute("name", testSuite);
				}

				if (finder.getContents().compareTo("testSuiteDetails") == 0) {
					testSuiteDetails = sheet.getCell(1, i).getContents();
					tsDetails.addText(testSuiteDetails);
				}

				if (finder.getContents().compareTo("testStep") == 0) {

					Element testcase = testsuite.addElement("testcase");

					testCase = sheet.getCell(0, i - 1).getContents();
					testCaseName = sheet.getCell(1, i - 1).getContents();
					testCaseSummary = sheet.getCell(2, i - 1).getContents();
					testCasePreconditions = sheet.getCell(3, i - 1).getContents();
					testCaseType = sheet.getCell(4, i - 1).getContents();
					testCaseStatus = sheet.getCell(5, i - 1).getContents();
					testCaseImportance = sheet.getCell(6, i - 1).getContents();

					testcase.addAttribute("name", testCaseName);
					testcase.addElement("node_order").addText(testCase);
					testcase.addElement("summary").addText(testCaseSummary);
					testcase.addElement("preconditions").addText(testCasePreconditions);
					testcase.addElement("status").addText(testCaseStatus);
					testcase.addElement("importance").addText(testCaseImportance);
					testcase.addElement("execution_type").addText(testCaseType);

					Element steps = testcase.addElement("steps");

					for (int j = i; j < sheet.getRows(); j++) {
						Cell stepFinder = sheet.getCell(0, j);
						if (stepFinder.getContents().compareTo("testCaseEnd") == 0) {
							stepCount = j - i - 1;
							break;
						}
					}

					int stepReader = i;

					for (int k = 0; k < stepCount; k++) {

						Element step = steps.addElement("step");

						testStep = sheet.getCell(0, stepReader + 1).getContents();
						testStepAction = sheet.getCell(1, stepReader + 1).getContents();
						testStepResult = sheet.getCell(2, stepReader + 1).getContents();
						testStepType = sheet.getCell(3, stepReader + 1).getContents();

						step.addElement("step_number").addText(testStep);
						step.addElement("actions").addText(testStepAction);
						step.addElement("expectedresults").addText(testStepResult);
						step.addElement("execution_type").addText(testStepType);

						stepReader++;
					}

				}

			}

			book.close();

			OutputFormat format = new OutputFormat("	", true);
			format.setEncoding("UTF-8");
			XMLWriter xmlWriter = new XMLWriter(
					new FileOutputStream(filePath + testSuite + ".xml"), format);

			xmlWriter.write(document);
			xmlWriter.close();
		}
	}
}
