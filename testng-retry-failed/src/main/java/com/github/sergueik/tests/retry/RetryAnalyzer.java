package com.github.sergueik.tests.retry;

import org.openqa.selenium.WebDriver;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

import java.io.File;
import java.io.IOException;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.TimeoutException;
import org.apache.commons.io.FileUtils;

/**
 * implements IRetryAnalyzer Interface to rank a failed test.
 */
public class RetryAnalyzer implements IRetryAnalyzer {
	private int retryCount = 0;
	private int retryMaxCount = 3;

	/**
	 * Returns true if the test method has to be retried, false otherwise.
	 *
	 * @param result The result of the test method that just ran.
	 * @return true if the test method has to be retried, false otherwise.
	 */
	// unused
	/*
	@Override
	public boolean retry_unused(ITestResult testResult) {
	  if (!testResult.isSuccess()) {
	    if (retryCount < maxRetryCount) {
	      retryCount++;
	      // override status
	      testResult.setStatus(ITestResult.SUCCESS);
	      String message = Thread.currentThread().getName() + ": Error in " + testResult.getName() + 
	      " Retrying " + (maxRetryCount + 1 - retryCount) + " more times";
	      System.out.println(message);
	      Reporter.log("message");
	      return true;
	    } else {
	      testResult.setStatus(ITestResult.FAILURE);
	    }
	  }
	  return false;
	}
	*/

	@Override
	public boolean retry(ITestResult testResult) {
		if (testResult.getAttributeNames().contains("retry") == false) {
			System.out.println("retry count = " + retryCount + "\n"
					+ "max retry count = " + retryMaxCount);
			WebDriver driver = (WebDriver) testResult.getTestContext()
					.getAttribute("driver");
			System.err.println(driver.getCurrentUrl());

			TakesScreenshot screenshot = ((TakesScreenshot) driver);

			File screenshotFile = screenshot.getScreenshotAs(OutputType.FILE);
			// Move image file to new destination
			try {
				String filename = String.format("c:\\temp\\UserID%d.jpg", retryCount);
				FileUtils.copyFile(screenshotFile, new File(filename));
				System.err.println(String.format("Screenshot saved in %s.", filename));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.err.println("Exception (ignored): " + e.toString());
			}

			if (retryCount < retryMaxCount) {
				// override status
				testResult.setStatus(ITestResult.SUCCESS);
				System.out.println("Retrying " + testResult.getName() + " with status "
						+ testResult.getStatus() + " for the try " + (retryCount + 1)
						+ " of " + retryMaxCount + " max times.");
				retryCount++;
				return true;
			} else if (retryCount == retryMaxCount) {
				// last retry to fail loud
				String stackTrace = testResult.getThrowable().fillInStackTrace()
						.toString();
				System.out.println(stackTrace);
				ReportCreator.addTestInfo(testResult.getName(),
						testResult.getTestClass().toString(), resultOfTest(testResult),
						stackTrace);
				testResult.setStatus(ITestResult.FAILURE);
				return false;
			}
		}
		return false;
	}

	public String resultOfTest(ITestResult testResult) {
		int status = testResult.getStatus();
		if (status == 1) {
			return "Success";
		}
		if (status == 2) {
			return "Failure";
		} else {
			return "Unknown";
		}
	}
}
