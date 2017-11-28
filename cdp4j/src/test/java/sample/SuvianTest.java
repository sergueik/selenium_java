package sample;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import java.io.File;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.io.IOException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import org.hamcrest.CoreMatchers;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.endsWith;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.CoreMatchers.startsWith;
import static org.hamcrest.MatcherAssert.assertThat;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotEquals;
import static org.testng.Assert.assertTrue;
import static org.testng.AssertJUnit.fail;

public class SuvianTest extends BaseTest {

	private static final StringBuffer verificationErrors = new StringBuffer();

	@AfterMethod
	public void AfterMethod(ITestResult result) {
		if (verificationErrors.length() != 0) {
			System.err.println(String.format("Error in the method %s : %s",
					result.getMethod().getMethodName(), verificationErrors.toString()));
		}
		session.navigate("about:blank");
		super.afterMethod();
	}

	@Test(enabled = true)
	public void test1() {
		// Arrange
		session.navigate("http://suvian.in/selenium/1.1link.html");

		// Wait for intro message to show
		session.waitUntil(o -> o.matches(".container .row .intro-message h3 a"),
				1000, 100);
		String xpath = "//div[1]/div/div/div/div/h3[2]/a";
		List<String> elementIds = session.getObjectIds(xpath);
		// Act
		assertTrue(elementIds.size() > 0);
		// Assert
		assertTrue(
				((String) session.getPropertyByObjectId(elementIds.get(0), "innerHTML"))
						.equalsIgnoreCase("Click Here"));
		// Act
		highlight(xpath);
		assertTrue(session.getText(xpath).equalsIgnoreCase("Click Here"));
		session.click(xpath);
		// Assert
		try {
			// Wait for validation page to load
			assertTrue(session.waitUntil(
					o -> o.getLocation().matches(".*/1.1link_validate.html$"), 1000,
					100));
		} catch (AssertionError e) {
			System.err.println("Exception: " + e.toString());
			verificationErrors.append(e.toString());
		}
		// NOTE: xpath change is not required when searching for the the first
		// element
		// Act
		String xpath1 = String.format("%s[%d]", xpath, 1);
		session.click(xpath1);
		// Assert
		try {
			// Wait for validation page to load
			assertTrue(session.waitUntil(
					o -> o.getLocation().matches(".*/1.1link_validate.html$"), 1000,
					100));
		} catch (AssertionError e) {
			System.err.println("Exception: " + e.toString());
			verificationErrors.append(e.toString());
		}
		// Act
		executeScript("function() { this.click(); }", xpath);
		// Assert
		// Wait for validation page to load
		assertTrue(session.waitUntil(
				o -> o.getLocation().matches(".*/1.1link_validate.html$"), 1000, 100));
		// Confirm the message is shown
		assertTrue(session.waitUntil(o -> o.getText(".intro-message")
				.contains("Link Successfully clicked")));
	}
}
