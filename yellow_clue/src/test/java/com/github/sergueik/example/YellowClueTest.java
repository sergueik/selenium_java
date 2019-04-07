package com.github.sergueik.example;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.junit.Test;
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

	// opens the url, with a specific User-Agent and returns the pagehtml
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
