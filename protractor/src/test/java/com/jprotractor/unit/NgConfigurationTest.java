package com.jprotractor.unit;

import static com.jprotractor.NgConfiguration.DEFAULT_ANGULAR_TIMEOUT;
import static com.jprotractor.NgConfiguration.DEFAULT_PAGE_LOAD_TIMEOUT;
import static com.jprotractor.NgConfiguration.DEFAULT_PAGE_SYNC_TIMEOUT;
import static com.jprotractor.NgConfiguration.DEFAULT_WEBDRIVER_TIMEOUT;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import java.net.URL;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import com.jprotractor.NgConfiguration;

@RunWith(Enclosed.class)
public class NgConfigurationTest {
	public static class WithCustomConfiguration {
		public static NgConfiguration config;

		@BeforeClass
		public static void setup() throws Exception {
			URL properties = NgConfigurationTest.class.getClassLoader()
					.getResource("jprotractor.properties");
			config = new NgConfiguration(properties);
		}

		@Test
		public void testPageLoadTimeout() throws Exception {
			assertThat(config.getPageLoadTimeout(), equalTo(100l));
		}

		@Test
		public void testSyncPageTimeout() throws Exception {
			assertThat(config.getPageSyncTimeout(), equalTo(100l));
		}

		@Test
		public void testAngularTimeout() throws Exception {
			assertThat(config.getAngularTimeout(), equalTo(100l));
		}

		@Test
		public void testWebDriverTimeout() throws Exception {
			assertThat(config.getWebDriverTimeout(), equalTo(100l));
		}
	}

	public static class WithDefaultConfiguration {
		public static NgConfiguration config;

		@BeforeClass
		public static void setup() throws Exception {
			config = new NgConfiguration(null);
		}

		@Test
		public void testPageLoadTimeout() throws Exception {
			assertThat(config.getPageLoadTimeout(),
					equalTo(DEFAULT_PAGE_LOAD_TIMEOUT));
		}

		@Test
		public void testSyncPageTimeout() throws Exception {
			assertThat(config.getPageSyncTimeout(),
					equalTo(DEFAULT_PAGE_SYNC_TIMEOUT));
		}

		@Test
		public void testAngularTimeout() throws Exception {
			assertThat(config.getAngularTimeout(),
					equalTo(DEFAULT_ANGULAR_TIMEOUT));
		}

		@Test
		public void testWebDriverTimeout() throws Exception {
			assertThat(config.getWebDriverTimeout(),
					equalTo(DEFAULT_WEBDRIVER_TIMEOUT));
		}
	}
}
