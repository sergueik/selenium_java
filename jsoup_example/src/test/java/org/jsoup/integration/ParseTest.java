package org.jsoup.integration;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import static org.junit.Assert.*;

import org.junit.Test;
import org.jsoup.HttpStatusException;
import org.jsoup.UnsupportedMimeTypeException;
import org.junit.Test;
import org.junit.Ignore;

import org.jsoup.nodes.Document;
import org.jsoup.Jsoup;
import org.jsoup.Connection;

import java.net.MalformedURLException;
import java.net.URL;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * Integration test: parses download links for from download page HTML  selecting by combination ofcriteria.
 *
 */
public class ParseTest {

	private static final String buildNumber = "15063"; // "17134"
	private static final String filename = "/edge_download_page.html";
	private static String edgedriverDownloadURL = "https://developer.microsoft.com/en-us/microsoft-edge/tools/webdriver/";

	@Test
	public void fetchURl() throws IOException {
		Document doc = Jsoup.parse(new URL(edgedriverDownloadURL), 10 * 1000);
		assertTrue(doc.title().contains("WebDriver - Microsoft Edge Development"));
	}

	@Test
	public void edgeDownloadPageTest() throws IOException {
		File in = getFile(filename);
		Document doc = Jsoup.parse(in, "UTF-8", edgedriverDownloadURL);
		assertEquals("WebDriver - Microsoft Edge Development", doc.title());
		assertEquals("en-US", doc.select("html").attr("lang"));

	}

	@Test
	public void edgeDownloadBasicLinksTest() throws IOException {
		File in = getFile(filename);
		Document doc = Jsoup.parse(in, "UTF-8", edgedriverDownloadURL);

		Elements downloadLinks = doc
				.select("ul.driver-downloads li.driver-download > a");
		assertEquals(6, downloadLinks.size());
		Elements versionParagraphs = doc.select(
				"ul.driver-downloads li.driver-download p.driver-download__meta");
		assertEquals(9, versionParagraphs.size());
	}

	// This test replicates what currently is done in
	// https://github.com/bonigarcia/webdrivermanager
	// src/main/java/io/github/bonigarcia/wdm/EdgeDriverManager.java
	@Test
	public void edgeDownloadExtendedLinksTest() throws IOException {
		File in = getFile(filename);
		Document doc = Jsoup.parse(in, "UTF-8", edgedriverDownloadURL);

		Elements downloadLinks = doc
				.select("ul.driver-downloads li.driver-download > a");
		assertEquals(6, downloadLinks.size());
		Elements versionParagraphs = doc.select(
				"ul.driver-downloads li.driver-download p.driver-download__meta");
		assertEquals(9, versionParagraphs.size());
		List<String> versions = new ArrayList<>();
		List<URL> urls = new ArrayList<>();
		for (int cnt = 0; cnt < downloadLinks.size(); cnt++) {
			String[] _versions = versionParagraphs.get(cnt).text().split(" ");
			// NOTE: this is really muddy code!
			String _version = _versions[1];
			if (!_version.equalsIgnoreCase("version")) {
				versions.add(_version);
				String _url = downloadLinks.get(cnt).attr("href");
				String _text = downloadLinks.get(cnt).text();
				if (_text.contains(String.format("Release %s", buildNumber))) { // whitespace-fragile
					System.err
							.println(String.format("Link %d|Version :%s|URL: %s|TEXT: %s",
									cnt, _version, _url, _text));
					urls.add(new URL(_url));

				}
				if (_text.contains(buildNumber)) {
					if (!urls.contains(new URL(_url))) {
						System.err
								.println(String.format("Link %d|Version :%s|URL: %s|TEXT: %s",
										cnt, _version, _url, _text));
						urls.add(new URL(_url));
					}

				}
			}
		}
		assertEquals(6, versions.size());
		assertEquals(1, urls.size());
	}

	// @Ignore
	@Test
	public void edgeDownloadLinkFilterTest() throws IOException {
		File in = getFile(filename);
		Document doc = Jsoup.parse(in, "UTF-8", edgedriverDownloadURL);

		Elements downloadLinks = doc
				.select("ul.driver-downloads li.driver-download > a");
		assertEquals(6, downloadLinks.size());
		for (int cnt = 0; cnt < downloadLinks.size(); cnt++) {
			System.err.println(String.format("# %d|TEXT: %s|URL: %s", cnt,
					downloadLinks.get(cnt).text(), downloadLinks.get(cnt).attr("href")));
		}
		Elements downloadFilteredLinks = doc.select(
				String.format("ul.driver-downloads li.driver-download > a:contains(%s)",
						buildNumber));
		assertEquals(1, downloadFilteredLinks.size());
		System.err.println(String.format("Filtered|TEXT: %s|URL: %s",
				downloadFilteredLinks.get(0).text(),
				downloadFilteredLinks.get(0).attr("href")));
		Elements versionParagraphs = doc.select(String.format(
				"ul.driver-downloads li.driver-download p:contains(%s)", buildNumber));
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
