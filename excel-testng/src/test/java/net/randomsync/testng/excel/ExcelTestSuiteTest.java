package net.randomsync.testng.excel;

import java.util.Arrays;
import java.util.HashMap;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.xml.XmlSuite;

public class ExcelTestSuiteTest {

	@Test
	public void testGetSuiteAsXmlSuite() {
		ExcelTestCase tc = new ExcelTestCase("1.1", "Search",
				"Perform a search and validate results",
				"param1=value1\np2=v2", "classes=net.DummyTest\nmethods=m2");
		HashMap<String, String> suiteparams = new HashMap<String, String>();
		suiteparams.put("suitep1", "svalue1");
		ExcelTestSuite suite = new ExcelTestSuite("Test Suite",
				Arrays.asList(tc), suiteparams);
		XmlSuite xmlSuite = suite.getSuiteAsXmlSuite(false);
		Assert.assertEquals(xmlSuite.getName(), "Test Suite");
		Assert.assertEquals(xmlSuite.getParameter("suitep1"), "svalue1");
		// 1 suite level param, 2 test params
		Assert.assertEquals(xmlSuite.getAllParameters().size(), 3);
		// verify that test params are available too
		Assert.assertEquals(xmlSuite.getAllParameters().get("param1"), "value1");
		Assert.assertEquals(xmlSuite.getTests().get(0).getName(), "1.1.Search");
		Assert.assertEquals(xmlSuite.getTests().get(0).getClasses().get(0)
				.getName(), "net.DummyTest");

	}

}
