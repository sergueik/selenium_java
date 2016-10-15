package com.xls.report.main;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class AppTest extends TestCase {
	/**
	 * Create the test case
	 *
	 * @param testName
	 *            name of the test case
	 */
	public AppTest(String testName) {
		super(testName);
	}

	/**
	 * @return the suite of tests being tested
	 */
	public static Test suite() {
		return new TestSuite(AppTest.class);
	}

	/**
	 * Rigourous Test :-)
	 * 
	 * @throws ParserConfigurationException
	 * @throws IOException
	 * @throws SAXException
	 * @throws InterruptedException
	 */
	public void testCreateReport() throws SAXException, IOException,
			ParserConfigurationException, InterruptedException {
		assertTrue(ExcelReport.generateReport(
				"testngxmlfiles\\testng-results.xml").length() > 1);
		Thread.sleep(2000);
		/*
		 * assertTrue(ExcelReport.generateReport(
		 * "testngxmlfiles\\dptestng-results.xml").length() > 1) ;
		 * Thread.sleep(2000);
		 */
		assertTrue(ExcelReport.generateReport(
				"testngxmlfiles\\pktestng-results.xml").length() > 1);
		Thread.sleep(2000);
		assertTrue(ExcelReport.generateReport(
				"testngxmlfiles\\AllAnotestng-results.xml").length() > 1);
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
