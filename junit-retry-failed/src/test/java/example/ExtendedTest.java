package example;

import static org.junit.jupiter.api.Assertions.fail;

import java.io.File;
import java.nio.file.Paths;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.CoreMatchers.is;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ExtensionContext.Store;
import org.junit.jupiter.api.extension.ParameterResolutionException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@ExtendWith(TestResultLoggerExtension.class)
class ExtendedTest {

	private static DesiredCapabilities capabilities;
	private static String osName = getOSName();
	private WebDriver driver;

	public WebDriver getDriver() {
		return driver;
	}

	private Integer value = 42;

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

	private static final Logger LOG = LoggerFactory.getLogger(ExtendedTest.class);

	// https://junit.org/junit5/docs/5.3.0/api/org/junit/jupiter/api/extension/ExtensionContext.Namespace.html
	// org.junit.platform.commons.PreconditionViolationException: Namespace must
	// not be null
	private final ExtensionContext.Namespace namespace = ExtensionContext.Namespace
			.create(ExtendedTest.class);

	@BeforeAll
	public static void init() {
		System.setProperty("webdriver.gecko.driver", osName.equals("windows")
				? new File("c:/java/selenium/geckodriver.exe").getAbsolutePath()
				: /* String.format("%s/Downloads/geckodriver", System.getenv("HOME"))*/
				Paths.get(System.getProperty("user.home")).resolve("Downloads")
						.resolve("geckodriver").toAbsolutePath().toString());
		System.setProperty("webdriver.firefox.bin",
				osName.equals("windows")
						? new File("c:/Program Files (x86)/Mozilla Firefox/firefox.exe")
								.getAbsolutePath()
						: "/usr/bin/firefox");
		// https://github.com/SeleniumHQ/selenium/wiki/DesiredCapabilities
		capabilities = DesiredCapabilities.firefox();
		capabilities.setCapability("marionette", false);

	}

	// see also:
	// https://www.programcreek.com/java-api-examples/?api=org.junit.jupiter.api.extension.ExtensionContext.Store
	// https://stackoverflow.com/questions/49532885/with-junit-5-how-to-share-information-in-extensioncontext-store-between-test
	// https://github.com/manovotn/YetAnotherMinimalReproducer
	@BeforeEach
	public void beforeEach(/* ExtensionContext context */ ) {
		// uncertain what can be done here
		driver = new FirefoxDriver(capabilities);

		driver.get("http://www.last.fm/ru/");

	}

	@AfterEach
	public void close() {
		driver.close();
	}

	@Test
	void test4() {
		driver.findElement(By.cssSelector("[href='/ru/dashboard']")).click();
	}

	@Disabled
	@Test
	void test1() {

		assertThat(true, is(false));
	}

	@Disabled
	@Test
	void test2() {
		assertThat(true, is(true));
	}

	@Disabled
	@Test
	void test3() {
		assertThat(true, is(true));
	}

	@Disabled("This test is disabled")
	@Test
	void givenFailure_whenTestDisabledWithReason_ThenCaptureResult() {
		fail("Not yet implemented");
	}

	public static String getOSName() {
		if (osName == null) {
			osName = System.getProperty("os.name").toLowerCase();
			if (osName.startsWith("windows")) {
				osName = "windows";
			}
		}
		return osName;
	}
}
