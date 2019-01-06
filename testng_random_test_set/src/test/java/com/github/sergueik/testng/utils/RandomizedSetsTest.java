package com.github.sergueik.testng.utils;

import static org.testng.Assert.assertTrue;

import java.lang.reflect.Method;

import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import com.github.sergueik.testng.utils.TestRandomizer;

public class RandomizedSetsTest {

	private static TestRandomizer testRandomizer = TestRandomizer.getInstance();
	private static boolean runAll = false;

	@BeforeSuite
	static void beforeSuite() {
		testRandomizer.setRunAll(runAll);
		testRandomizer.setInventoryFilePath("src/main/resources/tests.yaml");
		testRandomizer.loadInventory();
	}

	// https://stackoverflow.com/questions/21591712/how-do-i-use-testng-skipexception
	// see also: https://github.com/djangofan/testng-custom-report-example
	@BeforeMethod
	void beforeMethod(Method method) {
		String methodName = method.getName();
		// method.toString() returns fully-qualified name
		if (!testRandomizer.decide(methodName)) {
			{
				System.err
						.println(String.format("Test Ramdomizer decides to skip %s [%s]",
								methodName, method.toString()));

				// throw new SkipException("skipping " + methodName);
				// not really, not skip
				System.err.println("Not really skiping now.");
			}
		}
	}

	public void doTest(String testName) {
		if (!testRandomizer.decide(testName)) {
			System.err.println(
					String.format("Test Ramdomizer decides to skip %s", testName));
			throw new SkipException("skipping " + testName);
		}
		assertTrue(true);
	}

	@Test(enabled = true)
	public void testOne(String testName) {
		doTest(testName);
	}

	@Test(enabled = true)
	// org.testng.TestNGException:
	// Cannot inject @Test annotated Method [testTwo] with [class
	// java.lang.String].
	public void testTwo(String testName) {
		doTest(testName);
	}

	@Test(enabled = true)
	public void testThree(String testName) {
		doTest(testName);
	}

	@Test(enabled = true)
	public void testFour(String testName) {
		doTest(testName);
	}

	@Test(enabled = true)
	public void testFive(String testName) {
		doTest(testName);
	}

	@Test(enabled = true)
	public void testSix(String testName) {
		doTest(testName);
	}

	@Test(enabled = true)
	public void testSeven(String testName) {
		doTest(testName);
	}

	@Test(enabled = true)
	public void testEight(String testName) {
		doTest(testName);
	}

	@Test(enabled = true)
	public void testNine(String testName) {
		doTest(testName);
	}

	@Test(enabled = true)
	public void testTen(String testName) {
		doTest(testName);
	}

	@AfterSuite
	static void afterSuite() {
		testRandomizer.printInventory();
	}

}
