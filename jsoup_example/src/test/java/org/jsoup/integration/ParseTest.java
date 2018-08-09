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

	private static final String filename = "/htmltests/edge_download_page.html";
	private static String edgedriverDownloadURL = "https://developer.microsoft.com/en-us/microsoft-edge/tools/webdriver/";

	@Test
	public void fetchURl() throws IOException {
		Document doc = Jsoup.parse(new URL(edgedriverDownloadURL), 10 * 1000);
		assertTrue(doc.title().contains("WebDriver - Microsoft Edge Development"));
	}

	@Test
	public void edgeDownloadPageTest() throws IOException {
		File in = getFile("/htmltests/edge_download_page.html");
		Document doc = Jsoup.parse(in, "UTF-8", edgedriverDownloadURL);
		assertEquals("WebDriver - Microsoft Edge Development", doc.title());
		assertEquals("en-US", doc.select("html").attr("lang"));

	}

	@Test
	public void edgeDownloadBasicLinksTest() throws IOException {
		File in = getFile("/htmltests/edge_download_page.html");
		Document doc = Jsoup.parse(in, "UTF-8", edgedriverDownloadURL);

		Elements downloadLinks = doc
				.select("ul.driver-downloads li.driver-download > a");
		assertEquals(6, downloadLinks.size());
		Elements versionParagraphs = doc.select(
				"ul.driver-downloads li.driver-download p.driver-download__meta");
		assertEquals(9, versionParagraphs.size());
	}

	@Test
	public void edgeDownloadExtendedLinksTest() throws IOException {
		File in = getFile("/htmltests/edge_download_page.html");
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
			String _version = _versions[1];
			if (!_version.equalsIgnoreCase("version")) {
				versions.add(_version);
				String _url = downloadLinks.get(cnt).attr("href");
				System.err.println(
						String.format("Link %d, Version :%s URL: %s", cnt, _version, _url));
				urls.add(new URL(_url));
			}
		}
		assertEquals(6, urls.size());
	}

	@Ignore
	@Test
	public void testNewsHomepage() throws IOException {
		File in = getFile("/htmltests/edge_download_page.html");
		Document doc = Jsoup.parse(in, "UTF-8", "http://www.news.com.au/");
		assertEquals(
				"News.com.au | News from Australia and around the world online | NewsComAu",
				doc.title());
		assertEquals("Brace yourself for Metro meltdown",
				doc.select(".id1225817868581 h4").text().trim());

		Element a = doc.select("a[href=/entertainment/horoscopes]").first();
		assertEquals("/entertainment/horoscopes", a.attr("href"));
		assertEquals("http://www.news.com.au/entertainment/horoscopes",
				a.attr("abs:href"));

		Element hs = doc.select("a[href*=naughty-corners-are-a-bad-idea]").first();
		assertEquals(
				"http://www.heraldsun.com.au/news/naughty-corners-are-a-bad-idea-for-kids/story-e6frf7jo-1225817899003",
				hs.attr("href"));
		assertEquals(hs.attr("href"), hs.attr("abs:href"));
		assertEquals("ipod - Google Search", doc.title());
		Elements results = doc.select("h3.r > a");
		assertEquals(12, results.size());
		assertEquals(
				"http://news.google.com/news?hl=en&q=ipod&um=1&ie=UTF-8&ei=uYlKS4SbBoGg6gPf-5XXCw&sa=X&oi=news_group&ct=title&resnum=1&ved=0CCIQsQQwAA",
				results.get(0).attr("href"));
		assertEquals("http://www.apple.com/itunes/", results.get(1).attr("href"));
		Element p = doc.select("p:contains(Volt will be sold in the United States")
				.first();
		assertEquals(
				"In July, GM said its electric Chevrolet Volt will be sold in the United States at $41,000 -- $8,000 more than its nearest competitor, the Nissan Leaf.",
				p.text());
	}

	File getFile(String resourceName) {
		try {
			File file = new File(ParseTest.class.getResource(resourceName).toURI());
			return file;
		} catch (URISyntaxException e) {
			throw new IllegalStateException(e);
		}
	}
}
