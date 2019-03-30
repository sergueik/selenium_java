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

	JTextField fileNameInput = new JTextField(20);
	FileDialog fileDialog = null;
	JFrame jf = null;
	JButton chooseButton = null;
	JButton commandButton = null;
	String excelTestCase;
	String ouputPath;
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

	public TestGUI() {
		jf = new JFrame("ExcelToXml 1.0");
		fileDialog = new FileDialog(jf);
		JPanel j1 = new JPanel();
		JPanel j2 = new JPanel();
		JLabel jl1 = new JLabel("Excel test case:");
		chooseButton = new JButton("Choose");
		commandButton = new JButton("Convert to xml");
		chooseButton.addActionListener(this);
		commandButton.addActionListener(this);
		j1.add(jl1);
		fileNameInput.setEditable(false);
		fileNameInput.setBackground(Color.white);
		j1.add(fileNameInput);
		j1.add(chooseButton);
		commandButton.setEnabled(false);
		j2.add(commandButton);
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
			fileDialog.setVisible(true);
			String filename = fileDialog.getFile();
			if (filename != null) {
				String fileDirectory = fileDialog.getDirectory();
				if (filename.endsWith(".xls") || filename.endsWith(".xlsx")) {
					excelTestCase = fileDirectory + filename;
					fileNameInput.setText(excelTestCase);
					ouputPath = fileDirectory;
					commandButton.setEnabled(true);
				} else {
					JOptionPane.showMessageDialog(fileNameInput,
							"Please choose an excel file！");
					fileNameInput.setText("");
					commandButton.setEnabled(false);
				}
			}
		} else {
			commandButton.setEnabled(false);
			chooseButton.setEnabled(false);
			new FileTransferTool(fileNameInput, chooseButton, commandButton,
					excelTestCase, ouputPath).start();
		}
	}

	static void convert(String filePath, String xlsFile) throws Exception {

		InputStream is = new FileInputStream(xlsFile);
		Workbook book = Workbook.getWorkbook(is);
		Sheet sheet = book.getSheet(0);
		Element testsuite = DocumentHelper.createElement("testsuite");
		Document document = DocumentHelper.createDocument(testsuite);
		Element tsDetails = testsuite.addElement("details");

		for (int rowNum = 0; rowNum < sheet.getRows(); rowNum++) {
			Cell finder = sheet.getCell(0, rowNum);
			if (finder.getContents().compareTo("testSuiteName") == 0) {
				testSuite = sheet.getCell(1, rowNum).getContents();
				testsuite.addAttribute("name", testSuite);
			}

			if (finder.getContents().compareTo("testSuiteDetails") == 0) {
				testSuiteDetails = sheet.getCell(1, rowNum).getContents();
				tsDetails.addText(testSuiteDetails);
			}

			if (finder.getContents().compareTo("testStep") == 0) {

				Element testcase = testsuite.addElement("testcase");

				testCase = sheet.getCell(0, rowNum - 1).getContents();
				testCaseName = sheet.getCell(1, rowNum - 1).getContents();
				testCaseSummary = sheet.getCell(2, rowNum - 1).getContents();
				testCasePreconditions = sheet.getCell(3, rowNum - 1).getContents();
				testCaseType = sheet.getCell(4, rowNum - 1).getContents();
				testCaseStatus = sheet.getCell(5, rowNum - 1).getContents();
				testCaseImportance = sheet.getCell(6, rowNum - 1).getContents();

				testcase.addAttribute("name", testCaseName);
				testcase.addElement("node_order").addText(testCase);
				testcase.addElement("summary").addText(testCaseSummary);
				testcase.addElement("preconditions").addText(testCasePreconditions);
				testcase.addElement("status").addText(testCaseStatus);
				testcase.addElement("importance").addText(testCaseImportance);
				testcase.addElement("execution_type").addText(testCaseType);

				Element steps = testcase.addElement("steps");

				for (int j = rowNum; j < sheet.getRows(); j++) {
					Cell stepFinder = sheet.getCell(0, j);
					if (stepFinder.getContents().compareTo("testCaseEnd") == 0) {
						stepCount = j - rowNum - 1;
						break;
					}
				}

				int stepReader = rowNum;

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

	private static class FileTransferTool extends Thread {
		private JTextField fileNameInput = null;
		private JButton chooseButton = null;
		private JButton commandButton = null;
		private String excelTestCase;
		private String ouputPath;

		public FileTransferTool(JTextField fileNameInput, JButton chooseButton,
				JButton commandButton, String excelTestCase, String ouputPath) {
			this.fileNameInput = fileNameInput;
			this.chooseButton = chooseButton;
			this.commandButton = commandButton;
			this.excelTestCase = excelTestCase;
			this.ouputPath = ouputPath;
		}

		@Override
		public void run() {

			System.out.println("Excel to xml convert start!");
			System.out.println("excelTestCase:" + excelTestCase);
			try {
				// https://stackoverflow.com/questions/1816458/getting-hold-of-the-outer-class-object-from-the-inner-class-object
				// TestGUI.this.convert(ouputPath, excelTestCase);
				TestGUI.convert(ouputPath, excelTestCase);
			} catch (Exception e) {
				e.printStackTrace();
			}
			// http://www.java2s.com/Tutorial/Java/0240__Swing/UsingJOptionPanetoDisplayaMessage.htm
			JOptionPane.showMessageDialog(commandButton, "Finished!");
			System.out.println("Finished!");
			fileNameInput.setText("");
			chooseButton.setEnabled(true);

		}
	}
}
