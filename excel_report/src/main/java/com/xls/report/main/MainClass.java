package com.xls.report.main;

import java.io.FileNotFoundException;
import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;
import com.xls.report.exception.InvalidNumberOfArgumentException;

//based on: https://github.com/rahulrathore44/ExcelReportGenerator

public class MainClass {

	public static void main(String[] args) throws SAXException, IOException,
			ParserConfigurationException, InterruptedException {
		if ((args.length < 1))
			throw new FileNotFoundException("File : testng-result.xml "
					+ "was not present at the " + "given location");
		switch (args.length) {
		case 1:
			ExcelReport.generateReport(args[0]);
			break;
		case 2:
			ExcelReport.createOrUpdateReport(args[0], args[1]);
			break;
		default:
			throw new InvalidNumberOfArgumentException(" Invalid Number of Argument ");
		}

		/*
		 * ExcelReport.generateReport("testngxmlfiles\\testng-results.xml");
		 * Thread.sleep(2000);
		 * ExcelReport.generateReport("testngxmlfiles\\dptestng-results.xml");
		 * Thread.sleep(2000);
		 * ExcelReport.generateReport("testngxmlfiles\\pktestng-results.xml");
		 * Thread.sleep(2000);
		 * ExcelReport.generateReport("testngxmlfiles\\AllAnotestng-results.xml");
		 */
		// ExcelReport.CreateOrUpdateReport("Excel_Report_2015-07-15_22-18-58.xlsx",
		// "testngxmlfiles\\testng-results.xml");

		/*
		 * HashMap<String, Map<String, ArrayList<String>>> data = (HashMap<String,
		 * Map<String, ArrayList<String>>>)
		 * ExcelReportUpdate.getTestMethodDetail("F:\\testng-results.xml");
		 * for(String outerKey : data.keySet()){ HashMap<String, ArrayList<String>>
		 * innerData = (HashMap<String, ArrayList<String>>) data.get(outerKey);
		 * for(String innerKey : innerData.keySet()){ ArrayList<String>
		 * nextInnerData = innerData.get(innerKey); for(int i = 0; i <
		 * nextInnerData.size(); i++){ if(i >= 1 &&
		 * "fail".equalsIgnoreCase(nextInnerData.get(1))) continue;
		 * System.out.println(nextInnerData.get(i)); } } }
		 */
		// ExcelReport.generateReport("G:\\AutomationWorkspace\\Automation-Frameworks\\Webdriver\\test-output\\testng-results.xml");
	}
}