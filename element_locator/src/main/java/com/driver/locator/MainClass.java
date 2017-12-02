package com.driver.locator;

public class MainClass {

	public static void main(String[] args) {
		ElementLocator locator = new ElementLocator();
		PropertyFileReader reader = new PropertyFileReader();
		try {
			locator.writeToFile(reader.getFileType());
			System.out.println("DONE");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
