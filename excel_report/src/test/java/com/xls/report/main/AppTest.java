package com.xls.report.main;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class AppTest extends TestCase {
	public AppTest(String testName) {
		super(testName);
	}

	public static Test suite() {
		return new TestSuite(AppTest.class);
	}

	public void testCreateReport() throws SAXException, IOException,
			ParserConfigurationException, InterruptedException {
		assertTrue(ExcelReport.generateReport("testngxmlfiles\\testng-results.xml")
				.length() > 1);
		Thread.sleep(2000);
		assertTrue(ExcelReport
				.generateReport("testngxmlfiles\\pktestng-results.xml").length() > 1);
		Thread.sleep(2000);
		assertTrue(
				ExcelReport.generateReport("testngxmlfiles\\AllAnotestng-results.xml")
						.length() > 1);
		Thread.sleep(2000);
	}

	public void testUpdateReport() throws SAXException, IOException,
			ParserConfigurationException, InterruptedException {
		Thread.sleep(2000);
		System.out.println("=== Update ===");
		String name = "";
		assertTrue((name = ExcelReport.createOrUpdateReport("",
				"testngxmlfiles\\dptestng-results.xml")).length() > 1);
		Thread.sleep(2000);
		assertTrue((ExcelReport.createOrUpdateReport(name,
				"testngxmlfiles\\newdptestng-results.xml")).length() > 1);
	}
}
