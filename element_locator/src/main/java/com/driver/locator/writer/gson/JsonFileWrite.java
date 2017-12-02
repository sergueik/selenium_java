package com.driver.locator.writer.gson;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import com.driver.locator.model.LocatorModel;
import com.driver.locator.utility.ResourceHelper;
import com.driver.locator.writer.FileWrite;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JsonFileWrite implements FileWrite {

	@Override
	public boolean writeToFile(String fileName, List<LocatorModel> dData) {
		GsonBuilder builder = new GsonBuilder();
		builder.setPrettyPrinting();
		Gson gson = builder.create();
		try (PrintWriter out = new PrintWriter(new File(
				ResourceHelper.getResourcePath("json/") + fileName + ".json"))) {
			String str = gson.toJson(dData, ArrayList.class).replace("\\u0027", "'")
					.replace("\\u003d", "=");
			out.write(str);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

}
