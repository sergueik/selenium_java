package org.test;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.testng.ITestNGListener;
import org.testng.TestNG;
import org.testng.xml.XmlSuite;

public class ExcelTestNGRunner {
	private String source;
	private TestNG testng = new TestNG();
	private IExcelFileParser parser;
	private Map<ParserMapConstants, int[]> parserMap;

	public ExcelTestNGRunner(String source) {
		this.source = source;
	}

	public ExcelTestNGRunner(String source, IExcelFileParser parser) {
		this.source = source;
		this.parser = parser;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public void setTestng(TestNG testng) {
		this.testng = testng;
	}

	public void setParserMap(Map<ParserMapConstants, int[]> parserMap) {
		this.parserMap = parserMap;
	}

	public void setParser(IExcelFileParser parser) {
		this.parser = parser;
	}

	/**
	 * this is the main method that parses the input file(s) into TestNG
	 * XmlSuite(s) and then runs them using TestNG.run()
	 * 
	 */
	public void run() {
		File srcFile = new File(source);
		File[] filesList = null; // if source is a directory, this will hold the
									// list of excel files
		// make sure the file source exists
		if (!srcFile.exists()) {
			throw new IllegalArgumentException("The source for the Excel "
					+ "file(s) cannot be found.");
		}
		// if source is a folder, get all excel files within it
		if (srcFile.isDirectory()) {
			filesList = srcFile.listFiles(new ExcelFileNameFilter());
		} else {
			filesList = new File[] { srcFile };
		}
		if (this.testng == null) {
			this.testng = new TestNG();
		}
		//if no custom parser is specified, use default
		if (this.parser == null) {
			if (this.parserMap == null)
				this.parser = new ExcelSuiteParser();
			else
				this.parser = new ExcelSuiteParser(parserMap);
		}

		// this will keep a list of all XmlSuites to be run
		List<XmlSuite> allSuites = new ArrayList<XmlSuite>();
		// parse each file and each worksheet into an XmlSuite
		for (File file : filesList) {
			List<XmlSuite> suites = new ArrayList<XmlSuite>();
			try {
				suites = parser.getXmlSuites(file, false);
			} catch (InvalidFormatException e) {
				// e.printStackTrace();
				// any issues with parsing, skip this suite and continue
				continue;
			} catch (IOException e) {
				// e.printStackTrace();
				// any issues with parsing, skip this suite and continue
				continue;
			}
			for (XmlSuite suite : suites) {
				allSuites.add(suite);
			}
		}
		testng.setXmlSuites(allSuites);
		testng.run();
	}

	// helper methods to specify testng configuration
	public void setSuiteThreadPoolSize(int suiteThreadPoolSize) {
		if (testng != null) {
			testng.setSuiteThreadPoolSize(suiteThreadPoolSize);
		}
	}

	public void addListener(ITestNGListener listener) {
		if (testng != null) {
			testng.addListener(listener);
		}
	}

	public void setVerbose(int verbose) {
		if (testng != null) {
			testng.setVerbose(verbose);
		}
	}

	public void setPreserveOrder(boolean order) {
		if (testng != null) {
			testng.setPreserveOrder(order);
		}
	}

	public void setThreadCount(int threadCount) {
		if (testng != null) {
			testng.setThreadCount(threadCount);
		}
	}

	class ExcelFileNameFilter implements FilenameFilter {

		@Override
		public boolean accept(File file, String name) {
			if (name.endsWith(".xls") || name.endsWith(".xlsx")) {
				return true;
			}
			return false;
		}

	}

}
