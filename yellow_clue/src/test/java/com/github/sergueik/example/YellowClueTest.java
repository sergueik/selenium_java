package com.github.sergueik.example;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.junit.Test;

// based on:
// http://www.java2s.com/Tutorial/Java/0320__Network/DownloadingawebpageusingURLandURLConnectionclasses.htm
// http://www.java2s.com/Tutorials/Java/Network/HTTP_Read/Set_request_Property_for_URL_Connection_in_Java.htm
// http://www.java2s.com/Tutorial/Java/0320__Network/SendingaPOSTRequestwithParametersFromaJavaClass.htm
// http://www.java2s.com/Tutorial/Java/0320__Network/GettingtheCookiesfromanHTTPConnection.htm
// http://www.java2s.com/Tutorial/Java/0320__Network/PreventingAutomaticRedirectsinaHTTPConnection.htm

public class YellowClueTest {

	private static String url = "https://www.yellowpages.com.au/search/listings?clue=restaurant&locationClue=melbourne&lat=&lon=";
	// update to trigger assertion
	private final String city = "GREATER MELBOURNE";
	private static String pageSource = null;
	private static int maxcount = 2;
	private final static boolean debug = false;
	private final static String defaultBrowserUserAgent = "Mozilla 5.0 (Windows; U; "
			+ "Windows NT 5.1; en-US; rv:1.8.0.11) ";
	// NOTE: the below "User-Agent" header apparently causes the test to hang
	// private static String defaultBrowserUserAgent =
	// "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:33.0) " +
	// "Gecko/20120101 Firefox/33.0";

	@Test
	public void test7() throws Exception {
		String htmlSource = null;
		for (int cnt = 0; cnt != maxcount; cnt++) {
			htmlSource = getPageHTMLSource(url);
			assertTrue(htmlSource != null);
			assertTrue(htmlSource.length() > 10000);
			assertTrue(
					htmlSource.indexOf("we would like to ensure real humans") == -1);
			assertFalse(
					htmlSource.indexOf(String.format("Restaurant in %s", city)) == -1);
		}
	}

	// opens the url, with a specific User-Agent and returns the pagehtml
	private String getPageHTMLSource(String url) {
		return getPageHTMLSource(url, defaultBrowserUserAgent);
	}

	private String getPageHTMLSource(String url, String userAgent) {
		try {
			URLConnection urlConnection = (new URL(url)).openConnection();
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
		System.err.println(
				String.format("url %s response: %d char", url, pageSource.length()));

		if (debug) {
			System.err.println("Page source:\n%s" + pageSource);
		}
		return pageSource;
	}

}
