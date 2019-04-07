package com.github.sergueik.example;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.core.AnyOf.anyOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
// based on:
// http://www.java2s.com/Tutorial/Java/0320__Network/DownloadingawebpageusingURLandURLConnectionclasses.htm
// http://www.java2s.com/Tutorials/Java/Network/HTTP_Read/Set_request_Property_for_URL_Connection_in_Java.htm
// http://www.java2s.com/Tutorial/Java/0320__Network/SendingaPOSTRequestwithParametersFromaJavaClass.htm
// http://www.java2s.com/Tutorial/Java/0320__Network/GettingtheCookiesfromanHTTPConnection.htm
// http://www.java2s.com/Tutorial/Java/0320__Network/PreventingAutomaticRedirectsinaHTTPConnection.htm

public class YellowClueTest {

	private static String mainUrl = "https://www.yellowpages.com.au/search/listings?clue=restaurant&locationClue=melbourne&lat=&lon=";
	private static String baseUrl = "https://www.yellowpages.com.au/search/listings";
	private static String queryString = "clue=restaurant&locationClue=melbourne&lat=&lon=";
	private static String hostName = "www.yellowpages.com.au";
	private static String pageSource = null;

	private /* final */ static String defaultBrowserUserAgent = "Mozilla 5.0 (Windows; U; "
			+ "Windows NT 5.1; en-US; rv:1.8.0.11) ";
	// NOTE: the below "User-Agent" header apparently causes the test to hang
	// private static String defaultBrowserUserAgent =
	// "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:33.0) " +
	// "Gecko/20120101 Firefox/33.0";

	@BeforeClass
	public static void setup() throws IOException {
	}

	@Before
	public void beforeEach() {
	}

	@Ignore
	@Test
	public void test1() throws Exception {
		URLConnection urlConnection = new URL(mainUrl).openConnection();
		BufferedInputStream buffer = new BufferedInputStream(
				urlConnection.getInputStream());
		int byteRead;
		StringBuffer processOutput = new StringBuffer();
		while ((byteRead = buffer.read()) != -1) {
			processOutput.append((char) byteRead);
		}
		System.err.println("test1 response: " + processOutput.toString());
	}

	@Ignore
	@Test
	public void test2() throws Exception {
		URL pageURL = new URL(mainUrl);

		HttpURLConnection urlConnection = (HttpURLConnection) pageURL
				.openConnection();
		urlConnection.setRequestProperty("pageref", "www.yellowpages.com.au");

		BufferedInputStream buffer = new BufferedInputStream(
				urlConnection.getInputStream());
		int byteRead;
		StringBuffer processOutput = new StringBuffer();
		while ((byteRead = buffer.read()) != -1) {
			// System.err.println((char) byteRead);
			processOutput.append((char) byteRead);
		}
		System.err.println("test2 response: " + processOutput.toString());
	}

	@Ignore
	@Test
	// vanilla get request with the User-Agent
	public void test4() throws Exception {
		URL url = new URL("http://www.x.com");
		URLConnection urlConnection = url.openConnection();
		urlConnection.setRequestProperty("User-Agent", defaultBrowserUserAgent);

		InputStream inputStream = urlConnection.getInputStream();
		int byteRead;
		StringBuffer processOutput = new StringBuffer();
		while ((byteRead = inputStream.read()) != -1) {
			processOutput.append((char) byteRead);
		}
		System.err.println("test4 response: " + processOutput.toString());
	}

	@Test
	public void test5() throws Exception {

		URL url = new URL(mainUrl);
		URLConnection urlConnection = url.openConnection();
		urlConnection.setRequestProperty("User-Agent", defaultBrowserUserAgent);

		InputStream inputStream = urlConnection.getInputStream();
		int byteRead;
		StringBuffer processOutput = new StringBuffer();
		while ((byteRead = inputStream.read()) != -1) {
			processOutput.append((char) byteRead);
		}
		pageSource = processOutput.toString();
		System.err
				.println(String.format("test5 response: %d char", pageSource.length()));
	}

	@Ignore
	@Test
	public void test6() throws Exception {

		URL pageURL = new URL(baseUrl);
		HttpURLConnection urlConnection = (HttpURLConnection) pageURL
				.openConnection();
		urlConnection.setRequestProperty("pageref", baseUrl);
		urlConnection.setRequestProperty("User-Agent", defaultBrowserUserAgent);

		BufferedInputStream buffer = new BufferedInputStream(
				urlConnection.getInputStream());
		int byteRead;
		StringBuffer processOutput = new StringBuffer();
		while ((byteRead = buffer.read()) != -1) {
			processOutput.append((char) byteRead);
		}
		System.err.println("test6 response: " + processOutput.toString());
	}

	// @Ignore
	// POST request
	@Test
	public void test7() throws Exception {
		String htmlSource = null;
		for (int cnt = 0; cnt != 100; cnt++) {
			htmlSource = getPageHTMLSource(mainUrl);
			assertThat(htmlSource, notNullValue());
			assertThat(htmlSource.length(), greaterThan(100000));
			assertThat(htmlSource,
					not(containsString("we would like to ensure real humans")));

		}
	}

	@Ignore
	// POST request
	@Test
	public void test3() throws Exception {

		URL url = new URL(baseUrl);
		URLConnection conn = url.openConnection();
		conn.setDoOutput(true);
		OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream());

		writer.write(queryString);
		writer.flush();
		String line;
		BufferedReader reader = new BufferedReader(
				new InputStreamReader(conn.getInputStream()));
		StringBuffer processOutput = new StringBuffer();
		while ((line = reader.readLine()) != null) {
			processOutput.append(line);
		}
		// 302 Moved Temporarily
		if (processOutput.toString().matches(".*302 Found.*")) {
			System.err.println("test3 Matched redirect in the response body");
			System.err.println(
					"Response headers: " + conn.getHeaderFields().keySet().toString());

			conn.getHeaderFields().keySet().stream().forEach(h -> System.err
					.println(String.format("%s: %s", h, conn.getHeaderField(h))));

			System.err.println("Response: " + processOutput.toString());

			// open new post request
			URL url2 = new URL("http://www.yellowpages.com.au/dataprotection");
			URLConnection conn2 = url2.openConnection();
			conn2.setDoOutput(true);
			// cannot reuse writer:
			// java.net.ProtocolException: Cannot write output after reading input.
			OutputStreamWriter writer2 = new OutputStreamWriter(
					conn2.getOutputStream());

			writer2.write(
					"path=/search/listings&clue=restaurant&locationClue=melbourne&lat=&lon=");
			writer2.flush();
			BufferedReader reader2 = new BufferedReader(
					new InputStreamReader(conn2.getInputStream()));
			processOutput.setLength(0);
			// 301 Moved Permanently
			while ((line = reader2.readLine()) != null) {
				processOutput.append(line);
			}
			System.err.println("test3 Response (2): " + processOutput.toString());
			System.err.println("Response(2) headers: "
					+ conn2.getHeaderFields().keySet().toString());

			conn2.getHeaderFields().keySet().stream().forEach(h -> System.err
					.println(String.format("%s: %s", h, conn2.getHeaderField(h))));

			// We value the quality of content provided to our customers, and to
			// maintain this, we would like to ensure real humans are accessing our
			// information.
		}
		// collect cookie
		for (int i = 0;; i++) {
			String headerName = conn.getHeaderFieldKey(i);
			String headerValue = conn.getHeaderField(i);

			if (headerName == null && headerValue == null) {
				break;
			}
			if ("Set-Cookie".equalsIgnoreCase(headerName)) {
				String[] fields = headerValue.split(";\\s*");
				for (int j = 1; j < fields.length; j++) {
					if ("secure".equalsIgnoreCase(fields[j])) {
						System.out.println("secure=true");
					} else if (fields[j].indexOf('=') > 0) {
						String[] f = fields[j].split("=");
						if ("expires".equalsIgnoreCase(f[0])) {
							System.out.println("expires" + f[1]);
						} else if ("domain".equalsIgnoreCase(f[0])) {
							System.out.println("domain" + f[1]);
						} else if ("path".equalsIgnoreCase(f[0])) {
							System.out.println("path" + f[1]);
						}
					}
				}
			}
		}
		writer.close();
		reader.close();
	}

	@AfterClass
	public static void teardown() {
	}

	private String getPageHTMLSource(String mainUrl) {
		return getPageHTMLSource(mainUrl, defaultBrowserUserAgent);
	}

	private String getPageHTMLSource(String mainUrl, String userAgent) {
		try {
			URL url = new URL(mainUrl);
			URLConnection urlConnection = url.openConnection();
			urlConnection.setRequestProperty("User-Agent", defaultBrowserUserAgent);

			InputStream inputStream = urlConnection.getInputStream();
			int byteRead;
			StringBuffer processOutput = new StringBuffer();
			while ((byteRead = inputStream.read()) != -1) {
				processOutput.append((char) byteRead);
			}
			pageSource = processOutput.toString();
		} catch (MalformedURLException e) {

		} catch (IOException e) {
		}
		System.err
				.println(String.format("test5 response: %d char", pageSource.length()));
		return pageSource;
	}

}
