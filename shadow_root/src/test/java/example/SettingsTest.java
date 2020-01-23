package example;

import static java.lang.System.err;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;

import java.util.List;

import org.junit.Assume;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebElement;

// TODO: skip when run with firefox browser
public class SettingsTest extends BaseTest {

	private final static String baseUrl = "chrome://settings/";
	private static final String urlLocator = "#basicPage > settings-section[page-title=\"Search engine\"]";
	private static final String shadowLocator = "#card";

	private static final BrowserChecker browserChecker = new BrowserChecker(
			getBrowser());

	@Before
	public void beforMethod() {
	}

	@Test
	public void test1() {
		Assume.assumeTrue(super.getBrowser().equals("chrome"));
		driver.navigate().to(baseUrl);
		List<WebElement> elements = shadowDriver.findElements(urlLocator);
		assertThat(elements, notNullValue());
		assertThat(elements.size(), greaterThan(0));
		err.println(
				String.format("Located %d %s elements:", elements.size(), urlLocator));
		// NOTE: default toString() is not be particularly useful
		elements.stream().forEach(err::println);
		elements.stream().map(o -> o.getTagName()).forEach(err::println);
		elements.stream()
				.map(o -> String.format("innerHTML: %s", o.getAttribute("innerHTML")))
				.forEach(err::println);
		elements.stream()
				.map(o -> String.format("outerHTML: %s", o.getAttribute("outerHTML")))
				.forEach(err::println);
	}

	@Test
	public void test2() {
		Assume.assumeTrue(browserChecker.testingChrome());
		driver.navigate().to(baseUrl);
		WebElement element = shadowDriver.findElement(urlLocator);
		err.println(
				String.format("outerHTML: %s", element.getAttribute("outerHTML")));
		List<WebElement> elements = shadowDriver.findElements(element,
				shadowLocator);
		assertThat(elements, notNullValue());
		assertThat(elements.size(), greaterThan(0));
		err.println(String.format("Found %d elements: ", elements.size()));
		elements.stream()
				.map(o -> String.format("outerHTML: %s", o.getAttribute("outerHTML")))
				.forEach(err::println);
	}

	// origin: https://reflectoring.io/conditional-junit4-junit5-tests/
	// probably an overkill
	public static class BrowserChecker {
		private String browser;

		public BrowserChecker(String browser) {
			this.browser = browser;
		}

		public boolean testingChrome() {
			return (this.browser.equals("chrome"));
		}
	}
}
