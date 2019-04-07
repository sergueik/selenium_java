package com.github.sergueik.example;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class PageHTMLSourceGetter {

	private final static boolean debug = false;
	private final static String defaultBrowserUserAgent = "Mozilla 5.0 (Windows; U; "
			+ "Windows NT 5.1; en-US; rv:1.8.0.11) ";
	// NOTE: the below "User-Agent" header apparently causes the test to hang
	// private static String defaultBrowserUserAgent =
	// "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:33.0) " +
	// "Gecko/20120101 Firefox/33.0";

	// opens the url, with a specific User-Agent and returns the pagehtml
	public static String getPageHTMLSource(String url) {
		return getPageHTMLSource(url, defaultBrowserUserAgent);
	}

	public static String getPageHTMLSource(String url, String userAgent) {
		String pageSource = null;
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
			System.err.println("Exception (ignored): " + e.toString());
		} catch (IOException e) {
			System.err.println("Exception (ignored): " + e.toString());
		}
		if (debug) {
			System.err.println(
					String.format("url %s response: %d char", url, pageSource.length()));
			// System.err.println("Page source:\n%s" + pageSource);
		}
		return pageSource;
	}

}
