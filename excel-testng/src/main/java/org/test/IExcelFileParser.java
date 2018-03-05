package org.test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.testng.xml.XmlSuite;

public interface IExcelFileParser {
	List<XmlSuite> getXmlSuites(File file, boolean loadClasses)
			throws FileNotFoundException, InvalidFormatException, IOException;
}
