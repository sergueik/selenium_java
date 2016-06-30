package net.randomsync.testng.excel;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;

import org.testng.xml.XmlClass;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;

public class ExcelTestCase {
	public String id;
	public String name;
	public String description;
	private String parameters;
	private String configuration;
	private List<String> classNames; // class names that are a part of this test

	// private Map<XmlClass, List<XmlInclude>> methods;

	public ExcelTestCase(String id, String name, String desc) {
		this.id = id;
		this.name = name;
		this.description = desc;
	}

	public ExcelTestCase(String id, String name, String desc, String params, String config) {
		this(id, name, desc);
		this.parameters = params;
		this.configuration = config;
	}

	public void setClassNames(List<String> classNames) {
		this.classNames = classNames;
	}

	public void setParameters(String params) {
		this.parameters = params;
	}

	/**
	 * Return the test classes that are a part of this Test. If classes are not
	 * set, this method will parse the classes property from test configuration
	 * 
	 * @return the List of class names that are a part of this Test
	 */
	public List<String> getClassNames() {
		if (classNames == null) {
			Properties cfg = this.getConfigurationAsProperties();
			if (!cfg.containsKey("classes")
					|| cfg.getProperty("classes").trim().isEmpty()) {
				return new ArrayList<String>(); // return an empty list
			} else
				return Arrays.asList(cfg.getProperty("classes").split(","));

		}
		return classNames;

	}

	/**
	 * Returns the test parameteres as Properties object. When ExcelTestCase is
	 * initialized, the parameters are in String format. To get the testData as
	 * a Properties object, use this method
	 * 
	 * @return the test parameters as Properties object.
	 */
	public Properties getParametersAsProperties() {
		if (this.parameters == null)
			return new Properties();
		Properties params = new Properties();
		try {
			params.load(new StringReader(parameters));
		} catch (IOException e) {
			// do nothing in this case, return empty Properties object
		}
		return params;
	}

	public Properties getConfigurationAsProperties() {
		if (this.configuration == null)
			return new Properties();
		Properties cfg = new Properties();
		try {
			cfg.load(new StringReader(configuration));
		} catch (IOException e) {
			// do nothing in this case, return empty Properties object
		}
		return cfg;
	}

	/**
	 * Return the test classes as a list of TestNG XmlClass that are a part of
	 * this Test. If classes are not set, this method will parse the classes
	 * property from test configuration
	 * 
	 * @return the List of XmlClass that are a part of this Test
	 */
	public List<XmlClass> getXmlClasses(boolean loadClasses) {
		List<XmlClass> xmlClasses = null;
		List<String> classes = this.getClassNames();
		if (classes != null) {
			xmlClasses = new ArrayList<XmlClass>();
			for (int i = 0; i < classes.size(); i++) {
				// new XmlClass, loadClasses determines if classes are loaded.
				// If not loaded and classes are not found, testng will throw
				// excpetion during execution
				XmlClass cls = new XmlClass(classes.get(i), i, loadClasses);
				xmlClasses.add(i, cls);

			}
		}
		return xmlClasses;
	}

	/**
	 * Returns this test as a TestNG XmlTest
	 * 
	 * @param suite
	 * @param loadClasses whether to load classes when creating the XmlTest
	 * @return this test as TestNG XmlTest
	 */
	public XmlTest getTestAsXmlTest(XmlSuite suite, boolean loadClasses) {
		XmlTest xmltest = new XmlTest(suite);
		xmltest.setName(this.id + "." + this.name); // set name like
													// "<id>.<name>"
		// add parameters to this test case
		Properties params = this.getParametersAsProperties();
		for (Enumeration<?> e = params.keys(); e.hasMoreElements();) {
			String key = (String) e.nextElement();
			xmltest.addParameter(key, params.getProperty(key));
		}
		// add test classes
		xmltest.setXmlClasses(this.getXmlClasses(loadClasses));
		return xmltest;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ExcelTestCase [Id: " + id + ", Name: " + name
				+ ", Description: " + description + ", Configuration: "
				+ configuration + ", Parameters: " + parameters + "]";
	}

}
