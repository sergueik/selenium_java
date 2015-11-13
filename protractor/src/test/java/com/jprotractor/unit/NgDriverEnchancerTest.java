package com.jprotractor.unit;

import static com.jprotractor.NgConfiguration.DEFAULT_ANGULAR_TIMEOUT;
import static com.jprotractor.NgConfiguration.DEFAULT_PAGE_LOAD_TIMEOUT;
import static com.jprotractor.NgConfiguration.DEFAULT_WEBDRIVER_TIMEOUT;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import java.net.URL;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import com.jprotractor.mocks.WebDriverSpy;

@RunWith(Enclosed.class)
public class NgDriverEnchancerTest {

	public static class WithDefaultConfiguration {
		private WebDriverSpy driver;

		@Before
		public void beforeEach() {
			driver = new WebDriverSpy();
		}

		@Test
		public void testEnchanceNullDriver() throws Exception {
			NgDriverEnchancer.enchance(null);
		}

		@Test
		public void testEnchanceDriver() throws Exception {
			NgDriverEnchancer.enchance(driver);
			assertThat(driver.hasBeenEnchanced(), equalTo(true));
		}
	}

	public static class WithEnchancedDriverWithDefaultConfiguration {
		private static WebDriverSpy driver;

		@BeforeClass
		public static void init() throws Exception {
			driver = new WebDriverSpy();
			NgDriverEnchancer.enchance(driver);
		}

		@Test
		public void testPageLoadTimeout() throws Exception {
			assertThat(driver.getPageLoadTimeout(),
					equalTo(DEFAULT_PAGE_LOAD_TIMEOUT));
		}

		@Test
		public void testImplicitWait() throws Exception {
			assertThat(driver.getImplicitTimeout(),
					equalTo(DEFAULT_WEBDRIVER_TIMEOUT));
		}

		@Test
		public void testScriptTimeout() throws Exception {
			assertThat(driver.getScriptTimeout(),
					equalTo(DEFAULT_ANGULAR_TIMEOUT));
		}
	}

	public static class WithCustomConfiguration {
		private WebDriverSpy driver;

		@Before
		public void beforeEach() {
			driver = new WebDriverSpy();
		}

		@Test
		public void testEnchanceNullDriverAndNullConfiguration()
				throws Exception {
			NgDriverEnchancer.enchance(null, null);
		}

		@Test
		public void testEnchanceDriverWithNullConfiguration() throws Exception {
			NgDriverEnchancer.enchance(driver, null);
			assertThat(driver.hasBeenEnchanced(), equalTo(true));
		}

		@Test
		public void testEnchanceNullDriverWithConfiguration() throws Exception {
			URL config = NgDriverEnchancerTest.class.getClassLoader()
					.getResource("jprotractor.properties");
			NgDriverEnchancer.enchance(null, config);
			assertThat(driver.hasBeenEnchanced(), equalTo(false));
		}

		@Test
		public void testEnchanceWithDriverAndConfiguration() throws Exception {
			URL config = NgDriverEnchancerTest.class.getClassLoader()
					.getResource("jprotractor.properties");
			NgDriverEnchancer.enchance(driver, config);
			assertThat(driver.hasBeenEnchanced(), equalTo(true));
		}
	}

	public static class WithEnchancedDriverWithCustomConfiguration {
		private static WebDriverSpy driver;

		@BeforeClass
		public static void init() throws Exception {
			driver = new WebDriverSpy();
			URL config = NgDriverEnchancerTest.class.getClassLoader()
					.getResource("jprotractor.properties");
			NgDriverEnchancer.enchance(driver, config);
		}

		@Test
		public void testPageLoadTimeout() throws Exception {
			assertThat(driver.getPageLoadTimeout(), equalTo(100l));
		}

		@Test
		public void testImplicitWait() throws Exception {
			assertThat(driver.getImplicitTimeout(), equalTo(100l));
		}

		@Test
		public void testScriptTimeout() throws Exception {
			assertThat(driver.getScriptTimeout(), equalTo(100l));
		}
	}
}
