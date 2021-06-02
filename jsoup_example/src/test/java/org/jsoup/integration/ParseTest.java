package org.jsoup.integration;

import org.jsoup.Jsoup;
import org.jsoup.Connection;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import static org.junit.Assert.*;

import org.junit.Test;
import org.jsoup.HttpStatusException;
import org.jsoup.UnsupportedMimeTypeException;
import org.junit.Test;
import org.junit.Ignore;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * Integration test: parses download links for from download page HTML selecting
 * by combination ofcriteria.
 *
 */
public class ParseTest {

	private static final String buildNumber = "15063"; // "17134"
	private static final String filename = "/edge_download_page.html";
	private static String edgedriverDownloadURL = "https://developer.microsoft.com/en-us/microsoft-edge/tools/webdriver/";

	@Test
	public void fetchURL() throws IOException {
		Document doc = Jsoup.parse(new URL(edgedriverDownloadURL), 10 * 1000);
		assertTrue(doc.title().contains("Microsoft Edge Driver"));
		assertTrue(doc.title().contains("Microsoft Edge Developer"));
	}

	@Test
	public void edgeDownloadCachedPageTest() throws IOException {
		File file = getFile(filename);
		Document doc = Jsoup.parse(file, "UTF-8", edgedriverDownloadURL);
		assertEquals("WebDriver - Microsoft Edge Development", doc.title());
		assertEquals("en-US", doc.select("html").attr("lang"));

	}

	@Test
	public void edgeDownloadBasicLinksTest() throws IOException {
		File file = getFile(filename);
		Document doc = Jsoup.parse(file, "UTF-8", edgedriverDownloadURL);

		Elements downloadLinks = doc.select("ul.driver-downloads li.driver-download > a");
		assertEquals(6, downloadLinks.size());
		Elements versionParagraphs = doc.select("ul.driver-downloads li.driver-download p.driver-download__meta");
		assertEquals(9, versionParagraphs.size());
	}

	@Test
	public void edgeDownloadLinkFilterTest() throws IOException {
		File file = getFile(filename);
		Document doc = Jsoup.parse(file, "UTF-8", edgedriverDownloadURL);

		// cssSelector
		// section#downloads ul[class *= 'driver-downloads']

		org.jsoup.select.Elements downloadLinks = doc.select("ul.driver-downloads li.driver-download > a");
		assertEquals(6, downloadLinks.size());
		for (int cnt = 0; cnt < downloadLinks.size(); cnt++) {
			org.jsoup.nodes.Element downloadLink = downloadLinks.get(cnt);
			System.err.println(
					String.format("# %d|TEXT: %s|URL: %s", cnt, downloadLink.text(), downloadLink.attr("href")));
		}
		org.jsoup.select.Elements downloadFilteredLinks = doc
				.select(String.format("ul.driver-downloads li.driver-download > a:contains(%s)", buildNumber));
		assertEquals(1, downloadFilteredLinks.size());
		System.err.println(String.format("Filtered|TEXT: %s|URL: %s", downloadFilteredLinks.get(0).text(),
				downloadFilteredLinks.get(0).attr("href")));

		// The following code replicates what currently is done in
		// https://github.com/bonigarcia/webdrivermanager
		// src/main/java/io/github/bonigarcia/wdm/EdgeDriverManager.java
		Elements versionParagraphs = doc
				.select(String.format("ul.driver-downloads li.driver-download p:contains(%s)", buildNumber));
		assertEquals(1, versionParagraphs.size());
		System.err.println("Element HTML:" + versionParagraphs.get(0).hasText());
	}

	public File getFile(String resourceName) {
		try {
			File file = new File(ParseTest.class.getResource(resourceName).toURI());
			return file;
		} catch (URISyntaxException e) {
			throw new IllegalStateException(e);
		}
	}
}
