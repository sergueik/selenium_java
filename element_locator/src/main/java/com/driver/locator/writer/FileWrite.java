package com.driver.locator.writer;

import java.util.List;

import com.driver.locator.model.LocatorModel;

public interface FileWrite {

	public boolean writeToFile(String fileName, List<LocatorModel> dData);

}
