package net.randomsync.googlesearch;

import org.testng.ITestResult;
import org.testng.util.RetryAnalyzerCount;

public class RetryAnalyzer extends RetryAnalyzerCount {

	@Override
	public boolean retryMethod(ITestResult result) {
		// using parent class default retry of 1
		result.setStatus(ITestResult.SKIP);
		return true;

	}

}
