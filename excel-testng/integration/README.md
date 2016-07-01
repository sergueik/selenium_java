
##excel-testng-demo

###Introduction

This project demonstrates the use of [excel-testng](https://github.com/randomsync/excel-testng),  [Selenium WebDriver](http://code.google.com/p/selenium/) and [TestNG](http://testng.org) to test basic Google Search functionality. Specifications for test methods are provided as input in MS Excel file(s), which are parsed by excel-testng and executed by TestNG. The advantages of using excel-testng is that you can specify your test cases in Excel format, which can be easily distributed to other team members for review etc.

To execute the tests, you can either use the provided jar (excel-testng-demo.jar) or compile the project yourself. The class TestRunner is the entry point which has a main method that can be launched from command-line like this:

Windows:
>java -cp .\excel-testng-demo.jar;.\lib\* net.randomsync.googlesearch.TestRunner test-input 2

The 1st argument is the file(s)/dir which contains test specifications in Excel format and 2nd is suite threads, which parallelizes the test execution of multiple suites. You'll also need to setup a Selenium Hub and configure Test nodes as specified [here](http://code.google.com/p/selenium/wiki/Grid2). You'll need to modify the hubUrl suite parameter accordingly, or leave it blank to run the tests on your local machine. See Excel Test Specification below.

If you have everything setup correctly (including all the required libraries), you should see TestNG output (and any test output) like this:

	[TestNG] Starting executor for all suites
	query=testng
	PASSED: testGoogleSearch("testng")
	
	===============================================
	    2.1.GoogleSearch
	    Tests run: 1, Failures: 0, Skips: 0
	===============================================

Once all the tests finish executing, the TestNG reports are saved in test-output directory.

###Excel Test Specification

As mentioned above, the advantage of using excel-testng is that the tests can be specified in Excel format. Each worksheet in each Excel file is parsed as a separate test suite. This way, you can maintain test suites that validate different parts of application as separate worksheets or Excel files and then use them accordingly, depending on which part(s) of application(s) you want to test.

![Google Search Tests in Excel](https://lh6.googleusercontent.com/-aQJizh9H60w/T1E5AWBTUkI/AAAAAAAAABk/CcZIhp4itMw/s800/GoogleSearchTests-Excel.jpg "Google Search Tests in Excel")

The 1st few rows provide the suite information. The default parser included with excel-testng looks for the cell containing "Suite Name" string and gets the suite name from the next cell in same row. Same for "Suite Parameters".

For test specifications, the parser looks for the 1st row containing "Id" in its 1st cell and considers that as the header row. Each row under the header row  is a separate test case. The header row and tests must contains columns specifying "Id", "Test Name", "Test Parameters" and "Test Configuration" for a test to be run. If Id is left blank, the test is skipped. The test name is created by concatenating Id and Test Name.

If you already have your automated test cases specified in Excel but in a different format, you can specify your own parser map to the built-in parser, or even your own parser for more flexibility (see excel-testng documentation for more details).

###Other features demonstrated

1. Use of a BaseTest as a super class for all WebDriver tests. The Base Test contains the common code that is needed for all tests.
2. Using [RemoteWebDriver](http://code.google.com/p/selenium/wiki/RemoteWebDriver) to execute the tests remotely.
3. Use of [Page Object](http://code.google.com/p/selenium/wiki/PageObjects) design pattern.  
4. Adding RetryListerner to TestNG so that if a test fails, it is retried 1 more time.
5. Adding Screenshot capabilities: whenever a test fails (throwing WebDriverException or AssertionException), a screenshot is taken and added to TestNG results.
6. Running multiple suites concurrently by setting the TestNG's [suite thread pool size](http://testng.org/doc/documentation-main.html#parallel-running).
