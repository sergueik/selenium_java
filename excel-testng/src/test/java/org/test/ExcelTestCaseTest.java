package org.test;

import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import org.test.ExcelTestCase;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.xml.XmlClass;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;

public class ExcelTestCaseTest {

	@Test
	public void testGetParametersValid() {
		String params = "query=query 1\nquery2=query 2";
		ExcelTestCase tc = new ExcelTestCase("1.1", "Search",
				"Perform a search and validate results", params, null);
		Properties p = tc.getParametersAsProperties();
		Assert.assertEquals(p.size(), 2);
		Assert.assertEquals(p.getProperty("query"), "query 1");
		Assert.assertEquals(p.getProperty("query2"), "query 2");

	}

	@Test
	public void testGetParametersNull() {
		ExcelTestCase tc = new ExcelTestCase("1.1", "Search",
				"Perform a search and validate results", null, null);
		Properties p = tc.getParametersAsProperties();
		Assert.assertEquals(p.size(), 0);

	}

	@Test
	public void testGetParametersBlank() {
		ExcelTestCase tc = new ExcelTestCase("1.1", "Search",
				"Perform a search and validate results", "", null);
		Properties p = tc.getParametersAsProperties();
		Assert.assertEquals(p.size(), 0);

	}

	@Test
	public void testSetParameters() {
		ExcelTestCase tc = new ExcelTestCase("1.1", "Search",
				"Perform a search and validate results", null, null);
		tc.setParameters("query1=query abc");
		Properties p = tc.getParametersAsProperties();
		Assert.assertEquals(p.getProperty("query1"), "query abc");

	}

	@Test
	public void testGetXmlClasses() {
		ExcelTestCase tc = new ExcelTestCase("1.1", "Search",
				"Perform a search and validate results", null, null);
		Assert.assertEquals(tc.getXmlClasses(false).size(), 0);
		tc.setClassNames(Arrays.asList("net.randomsync.testng.excel.DummyTest"));
		Assert.assertEquals(tc.getXmlClasses(false).size(), 1);
	}

	@Test
	public void testGetXmlClassesBlank() {
		ExcelTestCase tc = new ExcelTestCase("1.1", "Search",
				"Perform a search and validate results", null, "classes=");
		Assert.assertEquals(tc.getXmlClasses(false).size(), 0);
	}

	@Test
	public void testGetXmlClassesMultiple() {
		ExcelTestCase tc = new ExcelTestCase("1.1", "Search",
				"Perform a search and validate results", null,
				"classes=abc,def,xyz\nsome=other");
		List<XmlClass> classes = tc.getXmlClasses(false);
		Assert.assertEquals(classes.size(), 3);
		Assert.assertEquals(classes.get(0).getName(), "abc");
		Assert.assertEquals(classes.get(1).getName(), "def");
		Assert.assertEquals(classes.get(2).getName(), "xyz");
		Properties cfg = tc.getConfigurationAsProperties();
		Assert.assertEquals(cfg.size(), 2);
		Assert.assertEquals(cfg.getProperty("classes"), "abc,def,xyz");
	}

	@Test
	public void testGetTestAsXmlTest() {
		ExcelTestCase tc = new ExcelTestCase("1.1", "Search",
				"Perform a search and validate results", "param1=value1\np2=v2",
				"classes=net.DummyTest\nmethods=m2");
		Assert.assertEquals(tc.getXmlClasses(false).size(), 1);
		XmlTest test = tc.getTestAsXmlTest(new XmlSuite(), false);
		Assert.assertEquals(test.getName(), "1.1.Search");
		Assert.assertEquals(test.getParameter("param1"), "value1");
		Assert.assertEquals(test.getClasses().get(0).getName(), "net.DummyTest");

	}
}
