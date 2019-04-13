package com.github.sergueik.example;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Parameter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.commons.configuration2.convert.DefaultListDelimiterHandler;
import org.apache.commons.configuration2.ex.ConfigurationException;

public class PageHTMLSourceGetter {

	private final static boolean debug = true;
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

	private static String whitelistedIpAdress = "";
	private static String externalIpAdress = "";
	final static String propertiesFilename = String.format(
			"%s/src/main/resources/%s", System.getProperty("user.dir"),
			"application.properties");

	// http://commons.apache.org/proper/commons-configuration/userguide/howto_properties.html#Lists_and_arrays
	// https://deviceatlas.com/blog/mobile-browser-user-agent-strings
	public static List<Object> getUserAgents() {
		List<Object> userAgentList = new ArrayList<>();
		Configurations configs = new Configurations();
		Configuration config;
		try {
			config = configs.properties(new File(propertiesFilename));
			userAgentList = config.getList("userAgent");
		} catch (ConfigurationException e) {
			// TODO Auto-generated catch block
			System.err.println(
					"Cannot load user agents from configuration properties file: "
							+ e.toString());
		}
		System.err.println("Loaded user agents: " + userAgentList.toString());
		return userAgentList;
	}

	public static String getPageHTMLSource(String url, String userAgent) {
		String pageSource = null;
		try {
			URLConnection urlConnection = (new URL(url)).openConnection();
			urlConnection.setRequestProperty("User-Agent", defaultBrowserUserAgent);
			// set X-FORWARDED-FOR HTTP header to prevent
			// "We have detected unusual traffic activity originating from your IP
			// address" redirect
			// https://johannburkard.de/blog/programming/java/x-forwarded-for-http-header.html
			if (!(externalIpAdress.isEmpty() || whitelistedIpAdress.isEmpty())) {
				urlConnection.setRequestProperty("X-FORWARDED-FOR",
						String.format("%s, %s", whitelistedIpAdress, externalIpAdress));
			}
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
