package com.driver.locator.writer;

import com.driver.locator.pageobject.PageObjectGenerator;
import com.driver.locator.writer.gson.JsonFileWrite;

public class ObjectFactory {

	public static FileWrite getObject(FileType type) {
		switch (type) {
		case Excel:
			return new ExcelFileWriter();

		case Csv:
			return new CsvFileWriter();

		case Json:
			return new JsonFileWrite();

		case POM:
			return new PageObjectGenerator();
		}
		throw new RuntimeException("Cannot create Object");
	}

}
