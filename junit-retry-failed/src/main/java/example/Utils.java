package example;

import java.io.File;
import java.io.IOException;

import java.util.Random;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Utils {

	private static Utils instance = new Utils();
	private static final Logger logger = LoggerFactory.getLogger(Utils.class);

	protected Utils() {
	}

	public static Utils getInstance() {
		return instance;
	}

	private boolean debug = false;

	public void setDebug(boolean value) {
		debug = value;
	}

	public void takescreenshot(WebDriver driver) {
		int cnt = new Random().nextInt(1000);
		String filename = String.format("c:\\temp\\test%04d.jpg", cnt);
		takescreenshot(driver, filename);
	}

	public void takescreenshot(WebDriver driver, String filename) {
		if (debug)
			logger.info("driver url is " + driver.getCurrentUrl());

		TakesScreenshot screenshot = (TakesScreenshot) driver;
		File screenshotFile = screenshot.getScreenshotAs(OutputType.FILE);

		try {
			FileUtils.copyFile(screenshotFile, new File(filename));
			if (debug)
				logger.info("Screenshot saved in {}", filename);
		} catch (IOException e) {
			logger.error("Exception (ignored): " + e.toString());
		}
	}
}
