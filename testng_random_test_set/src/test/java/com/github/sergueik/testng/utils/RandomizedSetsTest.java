package com.github.sergueik.testng.utils;
/**
 * Copyright 2019 Serguei Kouzmine
 */

import static org.testng.Assert.assertTrue;

import java.lang.reflect.Method;

import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import com.github.sergueik.testng.utils.TestRandomizer;
import com.github.sergueik.testng.utils.BaseTest;

/**
 * Class for skipping random testNg tests from execution by invoking the appropriate 
 * TestRandomizer class from individual @Test method  
 * @author: Serguei Kouzmine (kouzmine_serguei@yahoo.com)
 */

public class RandomizedSetsTest extends BaseTest {

	private static final TestRandomizer testRandomizer = TestRandomizer
			.getInstance();
	private static final boolean runAll = false;
	private static final boolean debug = false;
	private static final boolean verbose = true;

	@BeforeClass
	static void beforeClass() {
		testRandomizer.setRunAll(RandomizedSetsTest.runAll);
		testRandomizer.setVerbose(RandomizedSetsTest.verbose);
		testRandomizer.setDebug(RandomizedSetsTest.debug);
		testRandomizer.setInventoryFilePath("src/main/resources/tests.yaml");
		testRandomizer.loadInventory();
	}

	@BeforeMethod
	private void setName(Method m) {
		setTestName(m.getName());
	}

	public void doTest(String testName) {
		if (debug) {
			System.err.println("Called Test Ramdomizer from method.");
		}
		if (!testRandomizer.decide(testName)) {
			if (debug) {
				System.err.println(String.format("will skip %s", testName));
			}
			throw new SkipException("skipping " + testName);
		}
		assertTrue(true);
	}

	// NOTE: cannot modify test method signature this way
	// org.testng.TestNGException: Cannot inject @Test annotated Method [testOne]
	// with [class java.lang.String].
	@Test(enabled = true)
	public void testOne() {
		doTest(getTestName());
	}

	@Test(enabled = true)
	public void testTwo() {
		doTest(getTestName());
	}

	@Test(enabled = true)
	public void testThree() {
		doTest(getTestName());
	}

	@Test(enabled = true)
	public void testFour() {
		doTest(getTestName());
	}

	@Test(enabled = true)
	public void testFive() {
		doTest(getTestName());
	}

	@Test(enabled = true)
	public void testSix() {
		doTest(getTestName());
	}

	@Test(enabled = true)
	public void testSeven() {
		doTest(getTestName());
	}

	@Test(enabled = true)
	public void testEight() {
		doTest(getTestName());
	}

	@Test(enabled = true)
	public void testNine() {
		doTest(getTestName());
	}

	@Test(enabled = true)
	public void testTen() {
		doTest(getTestName());
	}

	@AfterClass
	static void afterClass() {
		testRandomizer.printInventory();
	}

}
