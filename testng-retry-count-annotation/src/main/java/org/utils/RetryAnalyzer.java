package org.utils;

import org.testng.IRetryAnalyzer;
import org.testng.ITestContext;
import org.testng.ITestResult;

public class RetryAnalyzer implements IRetryAnalyzer {

	int counter = 0;
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.testng.IRetryAnalyzer#retry(org.testng.ITestResult)
	 * 
	 * This method decides how many times a test needs to be rerun. TestNg will
	 * call this method every time a test fails. So we can put some code in here
	 * to decide when to rerun the test.
	 * 
	 * Note: This method will return true if a tests needs to be retried and
	 * false it not.
	 *
	 */

	@Override
	public boolean retry(ITestResult result) {

		// check if the test method had RetryCountIfFailed annotation
		FailedRetryCount annotation = result.getMethod().getConstructorOrMethod()
				.getMethod().getAnnotation(FailedRetryCount.class);

		// All tests that are retried after failures will appear in the skipped
		// tests
		// list. Which causes TestNg to report those retry attempts as skipped
		// tests.
		// Here we will explicitly remove the retry test from the skipped tests list
		// so that
		// TestNg doesn't report retry attempts as skipped attempts.
		// The line below simply does that.
		result.getTestContext().getSkippedTests().removeResult(result.getMethod());

		// based on the value of annotation see if test needs to be rerun
		if ((annotation != null) && (counter < annotation.value())) {
			System.err.println(String.format("Retry '%s'[%d] %d time",
					result.getName(), result.FAILURE, counter));
			counter++;
			return true;
		}
		return false;
	}
}
