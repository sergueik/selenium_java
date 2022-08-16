package com.github.sergueik.tests.retry;

import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

public class TestListener extends TestListenerAdapter {

	@Override
	public void onTestSuccess(ITestResult testResult) {
		WebDriver driver = (WebDriver) testResult.getTestContext()
				.getAttribute("driver");
		System.err.println(driver.getCurrentUrl());
		ReportCreator.addTestInfo(testResult.getName(),
				testResult.getTestClass().toString(), resultOfTest(testResult), "");
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