package com.test.TestLinkExcelToXml;

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

public class TestLinkExcelToXml {

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
	public void run() throws Exception {

		String filePath = "workbook/";
		String xlsFile = "workbook/TestCaseSample.xls";

		excelToXML(filePath, xlsFile);

	}

	public static void excelToXML(String filePath, String xlsFile)
			throws Exception {

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

		// 把生成的xml文档存放在硬盘上 true代表是否换行
		OutputFormat format = new OutputFormat("	", true);
		format.setEncoding("UTF-8");// 设置编码格式
		XMLWriter xmlWriter = new XMLWriter(
				new FileOutputStream(filePath + testSuite + ".xml"), format);

		xmlWriter.write(document);
		xmlWriter.close();
	}
}