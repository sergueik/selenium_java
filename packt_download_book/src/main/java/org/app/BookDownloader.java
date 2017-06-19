package org.app;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import java.io.File;

class BookDownloader {
	private static WebDriver driver;
	private static BookDownloader instance = null;
	private static final Logger LOG = LogManager.getLogger(BookDownloader.class);
	private static String filePath;
	private static SiteNavigator siteNavigator;
	private Settings settings = Settings.getInstance();
	private boolean flag = true;

	private BookDownloader() {
	}

	static BookDownloader getInstance() {
		if (instance == null) {
			instance = new BookDownloader();
		}
		return instance;
	}

	void startDownload() {
		while (flag) {
			filePath = settings.getDownloadFolder();
			settings.unpackExeFromJar();
			settings.setUp();
			driver = settings.getDriver();
			siteNavigator = SiteNavigator.getInstance();
			if (!checkFileExists(siteNavigator.getBookTitle(), "pdf")) {
				downloadPDF();
				waitFor(20000);
			} else {
				LOG.warn(
						"PDF file already exists. It means that second file should also exists.");
				setEnd();
				break;
			}
			File lastModified = getLastModifiedFile(filePath);
			LOG.info("PDF file name before: " + lastModified);
			renameDownloadedFile(lastModified, siteNavigator.getBookTitle(), "pdf");
			lastModified = getLastModifiedFile(filePath);
			LOG.info("PDF file name after: " + lastModified);
			downloadEPUB();
			waitFor(20000);
			lastModified = getLastModifiedFile(filePath);
			LOG.info("EPUB file name before: " + lastModified);
			renameDownloadedFile(lastModified, siteNavigator.getBookTitle(), "epub");
			lastModified = getLastModifiedFile(filePath);
			LOG.info("EPUB file name after: " + lastModified);
			setEnd();
		}
	}

	private void setEnd() {
		driver.close();
		LOG.info("Tasks are completed.");
		LOG.warn("Shutdown app after 5 seconds.");
		settings.deleteTempExe();
		waitFor(5000);
		System.exit(0);
	}

	private void downloadPDF() {
		driver
				.findElement(By.xpath(
						"//a[contains(@href, '" + siteNavigator.getBookId() + "/pdf' )]"))
				.click();
	}

	private void downloadEPUB() {
		driver
				.findElement(By.xpath(
						"//a[contains(@href, '" + siteNavigator.getBookId() + "/epub' )]"))
				.click();
	}

	private void waitFor(long ms) {
		try {
			LOG.info("Wait for " + ms / 1000 + "s");
			Thread.sleep(ms);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private File getLastModifiedFile(String dir) {
		File fl = new File(dir);
		File[] files = fl.listFiles(File::isFile);
		long lastMod = Long.MIN_VALUE;
		File choice = null;
		for (File file : files) {
			if (file.lastModified() > lastMod) {
				choice = file;
				lastMod = file.lastModified();
			}
		}
		return choice;
	}

	private void renameDownloadedFile(File old, String newName,
			String extension) {
		File file = new File(filePath + "\\" + newName + "." + extension);
		if (!file.exists())
			old.renameTo(file);
		else {
			System.out
					.println("Sorry this file already exist. There is nothing to do.");
		}
	}

	private boolean checkFileExists(String newName, String extension) {
		File file = new File(filePath + "\\" + newName + "." + extension);
		return file.exists();
	}
}
