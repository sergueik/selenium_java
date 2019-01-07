package com.github.sergueik.testng.utils;

/**
 * Copyright 2019 Serguei Kouzmine
 */
import static org.testng.Assert.assertTrue;

import java.lang.reflect.Method;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Class intending to skip specific testNg test(s) from execution but doing it incorrectly  
 * The appropriate TestRandomizer class is invoked and
 * throwing org.testng.SkipException from @BeforeMethod method
 * This has the following side effect:  
 * neither the intended specific test not any subsequent tests nor
 * @After annotated methods are ever run  
 * @author: Serguei Kouzmine (kouzmine_serguei@yahoo.com)
 */

public class SkipRestOfTestSetTest {

	private static final TestRandomizer testRandomizer = TestRandomizer
			.getInstance();
	private static final boolean runAll = false;
	private static final boolean debug = true;
	private static final boolean verbose = true;

	@BeforeClass
	static void beforeClass() {
		testRandomizer.setRunAll(SkipRestOfTestSetTest.runAll);
		testRandomizer.setVerbose(SkipRestOfTestSetTest.verbose);
		testRandomizer.setDebug(SkipRestOfTestSetTest.debug);
		testRandomizer.setInventoryFilePath("src/main/resources/tests.yaml");
		testRandomizer.loadInventory();
	}

	// NOTE: would skip everything
	@BeforeMethod
	void beforeMethod(Method method) {
		// NOTE: method.toString() returns fully-qualified name
		String methodName = method.getName();
		if (debug) {
			System.err.println(
					"BeforeMethod calling Test Ramdomizer to decide on " + methodName);
		}
		testRandomizer.skipTestFour(methodName);

	}

	@Test(enabled = true)
	public void testTwentyOne() {
		assertTrue(true);
	}

	@Test(enabled = true)
	public void testTwentyTwo() {
		assertTrue(true);
	}

	@Test(enabled = true)
	public void testTwentyThree() {
		assertTrue(true);
	}

	@Test(enabled = true)
	public void testTwentyFour() {
		assertTrue(true);
	}

	@Test(enabled = true)
	public void testTwentyFive() {
		assertTrue(true);
	}

	@Test(enabled = true)
	public void testTwentySix() {
		assertTrue(true);
	}

	@Test(enabled = true)
	public void testTwentySeven() {
		assertTrue(true);
	}

	@Test(enabled = true)
	public void testTwentyEight() {
		assertTrue(true);
	}

	@Test(enabled = true)
	public void testTwentyNine() {
		assertTrue(true);
	}

	@AfterClass
	// likely won't ever be called in this example
	static void afterClass() {
		testRandomizer.printInventory();
	}

}
