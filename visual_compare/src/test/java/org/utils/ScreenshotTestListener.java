package org.utils;

import static java.nio.file.StandardOpenOption.CREATE;
import static org.openqa.selenium.OutputType.BYTES;
import static org.testng.ITestResult.SUCCESS;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * origin: https://github.com/boonyasukd/headless-chrome/blob/master/src/test/java/com/telenordigital/wowbox/admin/test/utils/listener/ScreenshotTestListener.java *
 */
public class ScreenshotTestListener implements ITestListener {
	private static final Logger log = LoggerFactory
			.getLogger(ScreenshotTestListener.class);

	@Override
	public void onTestStart(ITestResult result) {
		log.debug("Test {} starts at {}", result.getMethod().getMethodName(),
				toLocalDateTime(result.getStartMillis()));
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		log.debug("Test succeeded: Capture screenshot");
		this.captureScreenShot(result);
	}

	@Override
	public void onTestFailure(ITestResult result) {
		log.debug("Test failed: Capture screenshot");
		this.captureScreenShot(result);
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public void onStart(ITestContext context) {
		log.debug("Start test: " + context.getName());
	}

	@Override
	public void onFinish(ITestContext context) {
		log.debug("Passed: {}", context.getPassedTests().getAllResults().size());
		log.debug("Failed: {}", context.getFailedTests().getAllResults().size());
	}

	private void captureScreenShot(ITestResult result) {
		String timestamp = DateTimeFormatter
			.ofPattern("yyyy-MM-dd - HH-mm-ss")
				.format(toLocalDateTime(result.getEndMillis()));
		String methodName = result.getMethod().getRealClass().getSimpleName() + "."
				+ result.getMethod().getMethodName();
		Path path = Paths.get(getDir(result),
				methodName + " - " + timestamp + ".png");

		try {
			TakesScreenshot screenshot = (TakesScreenshot) ((WebDriver) result
					.getTestContext().getAttribute("driver"));
			byte[] bytes = screenshot.getScreenshotAs(BYTES);

			log.debug("Writing screenshot to path {}", path);
			Files.createDirectories(path.getParent());
			Files.write(path, bytes, CREATE);
		} catch (Exception ex) {
			throw new RuntimeException("Unable to take screenshot", ex);
		}
	}

	private String getDir(ITestResult result) {
		return (result.getStatus() == SUCCESS) ? "successes" : "failure";
	}

	private LocalDateTime toLocalDateTime(long millisecs) {
		return LocalDateTime.ofInstant(Instant.ofEpochMilli(millisecs),
				ZoneId.systemDefault());
	}

}
