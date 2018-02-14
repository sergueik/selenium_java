package com.mycompany.app;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import org.junit.Test;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.UseDataProvider;

@RunWith(Parameterized.class)

// based on:
// https://github.com/junit-team/junit4/wiki/Parameterized-tests

public class MultiBrowserTest {

	private static String browser = "default browser";
	private String baseURL = "default URL";

	// No public static parameters method on class
	// com.mycompany.app.MultiBrowserTest

	public MultiBrowserTest(String browser) {
		MultiBrowserTest.browser = browser;
	}

	@Before
	public void before() {
		System.err.println(
				String.format("Before test: browser = %s", MultiBrowserTest.browser));
	}

	/* 	@BeforeClass
		public static void beforeClass() {
			// MultiBrowserTest.browser will be null
			System.err.println(
					String.format("Before class: browser = %s", MultiBrowserTest.browser));
		}
	*/

	@DataProvider
	public static Object[][] testData() {
		// @formatter:off
		return new Object[][] { { "https://www.google.com", "" },
				{ "https://www.linkedin.com", "" },
				{ "https://stackoverflow.com", "" }, };
		// @formatter:on
	}

	@Test
	@UseDataProvider("testData")
	//  Method testURL should have no parameters
	public void testURL(String input) {
		// Given:
		System.err.println(String.format("Test1: baseURL = %s, browser = %s",
				baseURL, MultiBrowserTest.browser));
		// When:
		// Then:
	}

	// inline static disconnected data provider
	@Parameters(name = "{index}: multibrowser test: browser: {0}")
	public static Iterable<Object[]> data() {
		return Arrays
				.asList(new Object[][] { { "chrome" }, { "firefox" }, { "ie" }, });
	}
}
