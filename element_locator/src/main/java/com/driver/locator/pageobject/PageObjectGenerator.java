package com.driver.locator.pageobject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.openqa.selenium.support.How;

import com.driver.locator.model.LocatorModel;
import com.driver.locator.utility.ResourceHelper;
import com.driver.locator.writer.FileWrite;

public class PageObjectGenerator implements FileWrite {

	private static int count = 1;

	protected String formatWebElement(String how, String using) {
		StringBuilder build = new StringBuilder();

		build.append("\n").append("\t").append("\t")
				.append("@FindBy(how=" + how + ",using=\"" + using + "\")").append("\n")
				.append("\t").append("\t");

		if (!"How.XPATH".equalsIgnoreCase(how)) {
			build
					.append(
							"public WebElement " + using.replaceAll("[^\\w\\s]", "") + ";")
					.append("\n");
			return new String(build);
		}
		build.append("public WebElement xpath_" + count++ + ";").append("\n");
		return new String(build);
	}

	private String getWebElementString(String how, String using) {

		if (!"class".equalsIgnoreCase(how))
			switch (How.valueOf(how.toUpperCase())) {
			case ID:
				return formatWebElement("How.ID", using);
			case NAME:
				return formatWebElement("How.NAME", using);
			case XPATH:
				return formatWebElement("How.XPATH", using);
			default:
				break;
			}

		return formatWebElement("How.CLASS_NAME", using);
	}

	@Override
	public boolean writeToFile(String fileName, List<LocatorModel> dData) {

		count = 1;
		File file = new File(
				ResourceHelper.getResourcePath("pageobject/") + fileName + ".java");
		if (!file.exists())
			try {
				file.createNewFile();
			} catch (IOException e1) {
				e1.printStackTrace();
			}

		try (FileWriter fWrite = new FileWriter(file, false)) {

			fWrite.append("\n").append("public class " + fileName + " {");
			for (LocatorModel model : dData) {
				fWrite.append(getWebElementString(model.getLocatorType(),
						model.getLocatorValue()));
			}
			fWrite.append("\n").append("}");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

}
