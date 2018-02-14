### Info

This directory contains a refactored replica of the
[aaschmid/junit-dataprovider-and-selenium](https://github.com/aaschmid/junit-dataprovider-and-selenium) project.
 
It explores `com.tngtech.java.junit.dataprovider` package - a TestNG-like dataprovider runner for JUnit - and Allure.
Note: The parent  project seems to be deleted by the author.

This runner can be used as alternative to allure runner.
Note: Two concurrent runners cannot be combined and having both active would not make much sense anyway.

Regular Junit class does not allow anntating he `@Before` method with `Params` of any kind: Test class can only have one constructor and it has
to be  public zero-argument constructor.

This junit extension can be used to run Selenium tests over a list of browsers:
```java

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
```
The parameters will be visible in
the `@Before` method which will run in the correct order , though the test order (by browser) appears to not be guaranteed, and the `@BeforeClass` method will not get the `browser` parameter

```sh
mvn test
Before class: browser = null
Running [5: multibrowser test: url: https://stackoverflow.com, browser: firefox]

Before test: browser = chrome
Test2: baseUrl = https://www.google.com, browser = chrome
Before test: browser = chrome
Test1: baseUrl = https://www.google.com, browser = chrome

Before test: browser = firefox
Test2: baseUrl = https://www.google.com, browser = firefox
Before test: browser = firefox
Test1: baseUrl = https://www.google.com, browser = firefox

Before test: browser = chrome
Test1: baseUrl = https://www.linkedin.com, browser = chrome
Before test: browser = chrome
Test2: baseUrl = https://www.linkedin.com, browser = chrome


Before test: browser = firefox
Test2: baseUrl = https://www.linkedin.com, browser = firefox
Before test: browser = firefox
Test1: baseUrl = https://www.linkedin.com, browser = firefox

Before test: browser = chrome
Test2: baseUrl = https://stackoverflow.com, browser = chrome
Before test: browser = chrome
Test1: baseUrl = https://stackoverflow.com, browser = chrome

Before test: browser = firefox
Test1: baseUrl = https://stackoverflow.com, browser = firefox
Before test: browser = firefox
Test2: baseUrl = https://stackoverflow.com, browser = firefox

Tests run: 12, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.604 sec - in
[5: multibrowser test: url: https://stackoverflow.com, browser: firefox]
```

### References
 * [TNG/junit-dataprovider](https://github.com/TNG/junit-dataprovider)
 * [Pragmatists/JUnitParams](https://github.com/Pragmatists/JUnitParam://github.com/Pragmatists/JUnitParams)
 * [allure-examples/allure-cucumber-jvm-example](https://github.com/allure-examples/allure-cucumber-jvm-example)
 * core junit4 parameterized runner tutorials
   * [1](https://www.tutorialspoint.com/junit/junit_parameterized_test.htm)
   * [2](https://www.mkyong.com/unittest/junit-4-tutorial-6-parameterized-test/)
   * [3](https://github.com/junit-team/junit4/wiki/Parameterized-tests)

### Author
[Serguei Kouzmine](kouzmine_serguei@yahoo.com)
