package com.github.mike10004.seleniumtrafficeg;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.google.common.collect.*;
import com.google.common.net.HttpHeaders;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.lightbody.bmp.core.har.HarEntry;
import net.lightbody.bmp.core.har.HarNameValuePair;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOCase;
import org.apache.commons.lang3.StringEscapeUtils;
import org.jsoup.Jsoup;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.io.TemporaryFilesystem;

import javax.annotation.Nullable;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.google.common.base.Preconditions.checkArgument;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class TrafficEaterTest {

	public static final String SYSPROP_EXPECTED_ORIGIN = "TrafficEaterTest.expectedOrigin";
	public static final String SYSPROP_DISABLE_HEADER_CHECK = "TrafficEaterTest.disableHeaderCheck";

	@Rule
	public TemporaryFolder tmp = new TemporaryFolder();

	@Before
	public void setUp() {
		TemporaryFilesystem.setTemporaryDirectory(tmp.getRoot());
	}

	@Test
	public void consume_chrome_https() throws Exception {
		testHttpBinResponse(new ChromeTrafficEater(), "https");
	}

	@Test
	public void consume_chrome_http() throws Exception {
		testHttpBinResponse(new ChromeTrafficEater(), "http");
	}

	@Test
	public void consume_firefox_http() throws Exception {
		testHttpBinResponse(new FirefoxTrafficEater(), "http");
	}

	@Test
	public void consume_firefox_https() throws Exception {
		testHttpBinResponse(new FirefoxTrafficEater(), "https");
	}

	private void testHttpBinResponse(TrafficEater eater, String scheme)
			throws IOException {
		checkArgument("http".equals(scheme) || "https".equals(scheme),
				"scheme must be http or https, not %s", scheme);
		System.out.format("testHttpBinResponse: %s with %s%n", scheme, eater);
		dumpSystemProperties("*.proxy*");
		ExampleVisitor generator = new ExampleVisitor(
				new URL(scheme + "://httpbin.org/get?foo=bar&foo=baz"), true);
		testConsume(eater, generator);
		String httpbinHtml = generator.getPageSource();
		String httpbinJson = Jsoup.parse(httpbinHtml).select("pre").text();
		JsonObject httpbinResponseData = new JsonParser().parse(httpbinJson)
				.getAsJsonObject();
		List<String> origins = Splitter.onPattern(",\\s*")
				.splitToList(httpbinResponseData.get("origin").getAsString());
		assertEquals("origins.size", 1, origins.size());
		System.out.format("origins: %s%n", origins);
		String expectedOrigin = System.getProperty(SYSPROP_EXPECTED_ORIGIN);
		if (expectedOrigin != null) {
			assertEquals("origin", expectedOrigin, origins.get(0));
		}
		if (!isHeaderCheckDisabled()) {
			Set<String> headersInRequest = ImmutableSet.copyOf(Iterables.transform(
					httpbinResponseData.get("headers").getAsJsonObject().entrySet(),
					new Function<Map.Entry<String, JsonElement>, String>() {
						@Override
						public String apply(
								@Nullable Map.Entry<String, JsonElement> input) {
							return input.getKey().toLowerCase();
						}
					}));
			failIfContainsProhibitedHeaders(headersInRequest);
		}
	}

	private void testConsume(TrafficEater eater,
			TrafficEater.TrafficGenerator generator) throws IOException {
		System.out.println();
		net.lightbody.bmp.core.har.Har harInMemory = eater.consume(generator);
		BmpHars.dumpInfo(harInMemory, System.out);
		String timestamp = new SimpleDateFormat("yyyy-MM-dd'T'HHmmss")
				.format(Calendar.getInstance().getTime());
		File buildDir = new File(System.getProperty("user.dir"), "target");
		File outputHarFile = new File(buildDir, "traffic-" + timestamp + ".har");
		harInMemory.writeTo(outputHarFile);
		System.out.format("traffic archived in  %s%n", outputHarFile);
		assertFalse("har entries empty",
				harInMemory.getLog().getEntries().isEmpty());
		if (!isHeaderCheckDisabled()) {
			checkRequestHeaders(harInMemory);
		}
	}

	private static class ExampleVisitor implements TrafficEater.TrafficGenerator {

		private final URL url;
		private final boolean retainPageSource;
		private String pageSource;

		public ExampleVisitor(URL url, boolean retainPageSource) {
			this.url = url;
			this.retainPageSource = retainPageSource;
		}

		public void generate(WebDriver driver) throws IOException {
			driver.get(url.toString());
			if (retainPageSource) {
				pageSource = driver.getPageSource();
				System.out.format("%n%s%n%s%n%s%n%n", Strings.repeat("=", 80),
						pageSource, Strings.repeat("=", 80));
			}
		}

		public String getPageSource() {
			return pageSource;
		}
	}

	private void dumpSystemProperties(final String wildcard) {
		Map<String, String> sysprops = Maps.filterKeys(
				Maps.fromProperties(System.getProperties()), new Predicate<String>() {
					public boolean apply(@Nullable String propertyName) {
						return wildcard == null || (propertyName != null && FilenameUtils
								.wildcardMatch(propertyName, wildcard, IOCase.INSENSITIVE));
					}
				});
		for (String key : Ordering.<String> natural()
				.immutableSortedCopy(sysprops.keySet())) {
			System.out.format("%s=%s%n", key,
					StringEscapeUtils.escapeJava(sysprops.get(key)));
		}
	}

	private Set<String> findProhibitedHeaders(Set<String> requestHeaderNames) {
		return Sets.intersection(requestHeaderNames, prohibitedHeadersLowerCase);
	}

	private void checkRequestHeaders(net.lightbody.bmp.core.har.Har har) {
		for (HarEntry harEntry : har.getLog().getEntries()) {
			List<HarNameValuePair> headers = harEntry.getRequest().getHeaders();
			Set<String> requestHeaderNamesLowerCase = ImmutableSet.copyOf(Iterables
					.transform(headers, new Function<HarNameValuePair, String>() {
						@Override
						public String apply(HarNameValuePair input) {
							return input.getName().toLowerCase();
						}
					}));
			failIfContainsProhibitedHeaders(requestHeaderNamesLowerCase);
		}
	}

	private void failIfContainsProhibitedHeaders(
			Set<String> requestHeaderNamesLowerCase) {
		Set<String> violations = findProhibitedHeaders(requestHeaderNamesLowerCase);
		if (!violations.isEmpty()) {
			Assert.fail("some prohibited headers found in request : " + violations);
		}
	}

	private static boolean isHeaderCheckDisabled() {
		return Boolean.parseBoolean(
				System.getProperty(SYSPROP_DISABLE_HEADER_CHECK, "false"));
	}

	private static final ImmutableSet<String> prohibitedHeadersLowerCase = ImmutableSet
			.copyOf(Iterables.transform(
					Arrays.asList(HttpHeaders.X_FORWARDED_FOR,
							HttpHeaders.X_FORWARDED_PROTO, HttpHeaders.VIA,
							HttpHeaders.PROXY_AUTHENTICATE, HttpHeaders.PROXY_AUTHORIZATION),
					new Function<String, String>() {
						@Override
						public String apply(String input) {
							return input.toLowerCase();
						}
					}));

}