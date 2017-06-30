package org.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;

public class ExcelTestSuite {
	public String name;
	private List<ExcelTestCase> testCases;
	private Map<String, String> suiteParams;

	public ExcelTestSuite(String name) {
		this.name = name;
		testCases = new ArrayList<ExcelTestCase>();
		suiteParams = new HashMap<String, String>();
	}

	public ExcelTestSuite(String name, List<ExcelTestCase> testCases) {
		this(name);
		this.testCases = testCases;
	}

	public ExcelTestSuite(String name, List<ExcelTestCase> testCases,
			Map<String, String> params) {
		this(name, testCases);
		this.suiteParams = params;
	}

	/**
	 * @return the testCases
	 */
	public List<ExcelTestCase> getTestCases() {
		return testCases;
	}

	public void addTestCase(ExcelTestCase testCase) {
		this.testCases.add(testCase);
	}

	public void setTestCases(List<ExcelTestCase> testCases) {
		this.testCases = testCases;
	}

	public void setSuiteParams(Map<String, String> params) {
		this.suiteParams = params;
	}

	/**
	 * Returns the current suite as TestNG XmlSuite
	 * 
	 * @return the suite as TestNG XmlSuite
	 */
	public XmlSuite getSuiteAsXmlSuite(boolean loadClasses) {
		XmlSuite suite = new XmlSuite();
		suite.setName(this.name);
		suite.setParameters(this.suiteParams);
		List<XmlTest> xmltests = new ArrayList<XmlTest>();
		for (ExcelTestCase tc : this.testCases) {
			xmltests.add(tc.getTestAsXmlTest(suite, loadClasses));
		}
		suite.setTests(xmltests);
		return suite;

	}
}
