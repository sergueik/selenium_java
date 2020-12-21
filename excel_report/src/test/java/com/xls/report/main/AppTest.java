package com.xls.report.main;

import static org.junit.Assert.assertTrue;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

// NOTE: originally class did not use any Junit annotation
public class AppTest {

	@Test
	public void testCreateReport() throws SAXException, IOException,
			ParserConfigurationException, InterruptedException {
		assertTrue(ExcelReport.generateReport("testngxmlfiles\\testng-results.xml")
				.length() > 1);
		Thread.sleep(2000);
		/*
		 * assertTrue(ExcelReport.generateReport(
		 * "testngxmlfiles\\dptestng-results.xml").length() > 1) ;
		 * Thread.sleep(2000);
		 */
		assertTrue(ExcelReport
				.generateReport("testngxmlfiles\\pktestng-results.xml").length() > 1);
		Thread.sleep(2000);
		assertTrue(
				ExcelReport.generateReport("testngxmlfiles\\AllAnotestng-results.xml")
						.length() > 1);
		Thread.sleep(2000);
	}

	@Test
	public void testUpdateReport() throws SAXException, IOException,
			ParserConfigurationException, InterruptedException {
		System.out.println("=== Update ===");
		String name = "";
		assertTrue((name = ExcelReport.createOrUpdateReport("",
				"testngxmlfiles\\dptestng-results.xml")).length() > 1);
		Thread.sleep(2000);
		assertTrue((ExcelReport.createOrUpdateReport(name,
				"testngxmlfiles\\newdptestng-results.xml")).length() > 1);
		Thread.sleep(2000);
	}
}
