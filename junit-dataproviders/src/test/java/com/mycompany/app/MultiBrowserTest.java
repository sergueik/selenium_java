package com.mycompany.app;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import org.junit.Test;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)

// based on:
// https://github.com/junit-team/junit4/wiki/Parameterized-tests

public class MultiBrowserTest {

	private String baseUrl;
	private static String browser;

	public MultiBrowserTest(String baseUrl, String browser) {
		this.baseUrl = baseUrl;
		MultiBrowserTest.browser = browser;
	}

	@Before
	public void before() {
		System.err.println(
				String.format("Before test: browser = %s", MultiBrowserTest.browser));
	}

	@BeforeClass
	public static void beforeClass() {
		// MultiBrowserTest.browser will be null
		System.err.println(
				String.format("Before class: browser = %s", MultiBrowserTest.browser));
	}

	@Test
	public void test1() {
		System.err.println(String.format("Test1: baseUrl = %s, browser = %s",
				baseUrl, MultiBrowserTest.browser));
	}

	@Test
	public void test2() {
		System.err.println(String.format("Test2: baseUrl = %s, browser = %s",
				baseUrl, MultiBrowserTest.browser));
	}

	// inline static disconnected data provider
	@Parameters(name = "{index}: multibrowser test: url: {0}, browser: {1}")
	public static Iterable<Object[]> data() {
		return Arrays
				.asList(new Object[][] { { "https://www.google.com", "chrome" },
						{ "https://www.google.com", "firefox" },
						{ "https://www.linkedin.com", "chrome" },
						{ "https://www.linkedin.com", "firefox" },
						{ "https://stackoverflow.com", "chrome" },
						{ "https://stackoverflow.com", "firefox" } });
	}

}
