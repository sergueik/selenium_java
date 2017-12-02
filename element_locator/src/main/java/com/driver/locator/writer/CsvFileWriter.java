package com.driver.locator.writer;

import java.io.FileWriter;
import java.util.List;

import com.driver.locator.model.LocatorModel;
import com.driver.locator.utility.ResourceHelper;
import com.opencsv.CSVWriter;

public class CsvFileWriter implements FileWrite {
	@Override
	public boolean writeToFile(String fileName, List<LocatorModel> dData) {
		try (CSVWriter writer = new CSVWriter(new FileWriter(
				ResourceHelper.getResourcePath("locator/") + fileName + ".csv", false),
				',')) {
			for (LocatorModel model : dData) {
				String[] str = model.toString().split(":");
				writer.writeNext(str, false);
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

}
