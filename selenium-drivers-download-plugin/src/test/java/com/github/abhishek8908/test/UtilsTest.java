package com.github.abhishek8908.test;

import com.github.abhishek8908.driver.ChromeDriver;
import com.github.abhishek8908.driver.DriverSettings;
import com.github.abhishek8908.driver.EdgeDriver;
import com.github.abhishek8908.driver.GeckoDriver;
import com.github.abhishek8908.plugin.Driver;
import com.github.abhishek8908.util.DriverUtil;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.io.FileUtils;
import org.jsoup.select.Elements;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import static com.github.abhishek8908.util.DriverUtil.*;
import static org.testng.Assert.assertEquals;
// greaterThan
import static org.testng.Assert.assertTrue;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class UtilsTest {

	@Ignore
	@Test
	public static void newDownload() throws IOException {

		String fromFile = "https://github.com/mozilla/geckodriver/releases/download/v0.20.1/geckodriver-v0.20.1-win32.zip";
		String toFile = "c:/temp/geckodriver-v0.20.1-win32.zip";
		// connectionTimeout, readTimeout = 10 seconds
		FileUtils.copyURLToFile(new URL(fromFile), new File(toFile), 10000, 10000);
	}

	@Ignore
	@Test
	public void testSystemProperty() throws ConfigurationException {
		System.setProperty("ver", "2.39");
		System.setProperty("os", "linux64");
		System.setProperty("ext", "zip");
		System.out.println(DriverUtil.readProperty("chromedriver.download.url"));
	}

	@Ignore
	@Test
	public void urlTest() {
		getFileNameFromUrl(
				"https://chromedriver.storage.googleapis.com/2.39/chromedriver_win32.zip");
		getFileNameFromUrl(
				"https://download.microsoft.com/download/C/0/7/C07EBF21-5305-4EC8-83B1-A6FCC8F93F45/MicrosoftWebDriver.exe");
	}

	@Ignore
	@Test
	public void fileRename() throws IOException {
		changeFileName("c:\\temp\\chromedriver.exe",
				"c:\\temp\\chromedriver-" + "2.38" + ".exe");
	}

	@Test
	public void fileLink() throws IOException {
		changeFileName("c:\\temp\\geckodriver",
				"c:\\temp\\geckodriver-" + "0.21.0" + "linux64.lnk", true);
	}

	@Ignore
	@Test
	public void testCleanDir() {
		cleanDir("c:\\temp");
	}

	@Ignore
	@Test
	public void testDriverExists() throws IOException {
		System.out
				.println(checkDriverVersionExists("chromedriver", "2.38", "c:/temp"));
	}

	@Ignore
	@Test
	public void testProperty() throws ConfigurationException {
		System.out.println(readProperty("chrome.download.url"));
		System.getProperty("os.name");
	}

	@Test(enabled = false)
	public void testChromeDriverConstructor() {
		DriverSettings settings = new DriverSettings();

		settings.setVer("2.39");
		settings.setOs("win32");
		settings.setDriverDir("c:/temp");
		try {
			new ChromeDriver(settings).getDriver().setDriverInSystemProperty();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ConfigurationException e) {
			e.printStackTrace();
		}

	}

	@Test(enabled = false)
	public void testChromeDriverConstructorAction() {
		DriverSettings settings = new DriverSettings();

		settings.setVer(null);
		settings.setOs("win32");
		settings.setDriverDir("c:/temp");
		try {
			new ChromeDriver(settings).getDriver().setDriverInSystemProperty();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ConfigurationException e) {
			e.printStackTrace();
		}

	}

	@Test(enabled = true)
	public void testGeckoDriverConstructorAction() {
		DriverSettings settings = new DriverSettings();
		System.clearProperty("ver");
		settings.setVer(null);
		settings.setOs("win32");
		settings.setDriverDir("c:/temp");
		try {
			new GeckoDriver(settings).getDriver().setDriverInSystemProperty();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ConfigurationException e) {
			e.printStackTrace();
		}

	}

	// https://stackoverflow.com/questions/19393402/jsoup-parsing-get-next-element
	//
	private static final String data = ""
			+ "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\"\"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">"
			+ "<html xmlns=\"http://www.w3.org/1999/xhtml\">" + "<head>"
			+ "<title>PSD2HTML</title>"
			+ "<meta http-equiv=\"Content-Type\"content=\"text/html; charset=utf-8\"/>"
			+ "<meta name=\"viewport\"content=\"width=device-width, initial-scale=1.0\"/>"
			+ "<style type=\"text/css\">"
			+ ".ExternalClass{display:block !important;}" + "* {"
			+ "-webkit-text-size-adjust:none;" + "-webkit-text-resize:100%;"
			+ "text-resize:100%;" + "}" + "</style>" + "</head>"
			+ "<body style=\"margin:0; padding:0;\"bgcolor=\"#ededeb\"link=\"#0086da\">"
			+ "<style type=\"text/css\">" + "* {" + "-webkit-text-size-adjust:none;"
			+ "-webkit-text-resize:100%;" + "text-resize:100%;" + "}" + "</style>"
			+ "<table width=\"100%\"cellspacing=\"0\"cellpadding=\"0\"bgcolor=\"#ededeb\">"
			+ "<!--[if IE]>" + "<tr>" + "<td align=\"center\">"
			+ "<table width=\"609\"cellpadding=\"0\"cellspacing=\"0\"border=\"0\"align=\"center\">"
			+ "<![endif]-->" + "<!--[if mso]>" + "<tr>" + "<td align=\"center\">"
			+ "<table width=\"609\"cellpadding=\"0\"cellspacing=\"0\"border=\"0\"align=\"center\">"
			+ "<![endif]-->" + "<tr>"
			+ "<td align=\"center\"style=\"padding-top:47px; padding-bottom:46px; padding-left:5px; padding-right:5px;\">"
			+ "<div style=\"max-width:599px; margin:0 auto;\">"
			+ "<table width=\"100%\"cellpadding=\"0\"cellspacing=\"0\"align=\"center\"bgcolor=\"#ffffff\"style=\"border-bottom:1px solid #cdd1d2; margin:0 auto;\">"
			+ "<tr><td colspan=\"3\"height=\"27\"style=\"line-height:0; font-size:0;\"></td></tr>"
			+ "<tr>" + "<td width=\"8%\"></td>" + "<td width=\"84%\">"
			+ "<table width=\"100%\"cellpadding=\"0\"cellspacing=\"0\">" + "<tr>"
			+ "<td style=\"font:14px Arial, Helvetica, sans-serif; line-height:17px; color:#4c515a;\">Hello, </td>"
			+ "</tr>"
			+ "<tr><td height=\"10\"style=\"line-height:0; font-size:0;\"></td></tr>"
			+ "<tr>"
			+ "<td style=\"font:14px Arial, Helvetica, sans-serif; line-height:17px; color:#4c515a;\"><div style=\"line-height:10px; width:100%;\"><span style=\"line-height:17px;\">We have reset your password. Your login information with the new password is below.</span></div></td>"
			+ "</tr>"
			+ "<tr><td height=\"14\"style=\"line-height:0; font-size:0;\"></td></tr>"
			+ "</table>" + "</td>" + "<td width=\"8%\"></td>" + "</tr>" + "<tr>"
			+ "<td colspan=\"3\"bgcolor=\"#f9f9f9\">"
			+ "<table width=\"100%\"cellpadding=\"0\"cellspacing=\"0\"bgcolor=\"#f9f9f9\">"
			+ "<tr><td colspan=\"3\"height=\"16\"style=\"line-height:0; font-size:0;\"></td></tr>"
			+ "<tr>" + "<td width=\"8%\"></td>" + "<td width=\"84%\">"
			+ "<table width=\"100%\"cellpadding=\"0\"cellspacing=\"0\">" + "<tr>"
			+ "<td width=\"78\"style=\"font:14px Arial, Helvetica, sans-serif; line-height:10px; color:#4c515a; text-align:right;\"><span style=\"line-height:17px;\"><strong style=\"color:#000;\">Login:&nbsp;</strong></span></td>"
			+ "<td style=\"font:14px Arial, Helvetica, sans-serif; line-height:10px; color:#4c515a;\"><span style=\"line-height:17px;\">vlada2.aleksandrova@p2h.com</span></td>"
			+ "</tr>" + "<tr>"
			+ "<td width=\"78\"style=\"font:14px Arial, Helvetica, sans-serif; line-height:10px; color:#4c515a; text-align:right;\"><span style=\"line-height:17px;\"><strong style=\"color:#000;\">Password:&nbsp;FOO BAR</strong> </span></td>"
			+ "<td style=\"font:14px Arial, Helvetica, sans-serif; line-height:10px; color:#4c515a;\"><span style=\"line-height:17px;\">vt22Lzwb</span></td>"
			+ "</tr>" + "</table>" + "</td>" + "<td width=\"8%\"></td>" + "</tr>"
			+ "<tr><td colspan=\"3\"height=\"14\"style=\"line-height:0; font-size:0;\"></td></tr>"
			+ "</table>" + "</td>" + "</tr>" + "<tr>" + "<td width=\"8%\"></td>"
			+ "<td width=\"84%\">"
			+ "<table width=\"100%\"cellpadding=\"0\"cellspacing=\"0\"style=\"border-collapse:collapse;\">"
			+ "<tr><td height=\"15\"style=\"line-height:0; font-size:0;\"></td></tr>"
			+ "<tr>"
			+ "<td style=\"font:14px Arial, Helvetica, sans-serif; line-height:17px; color:#4c515a; word-spacing:-1px;\"><div style=\"line-height:10px; width:100%;\"><span style=\"line-height:17px;\">You can change your password any time by logging in to <a style=\"text-decoration:underline; color:#0086da;\"href=\"http://p2h.com/login.html\">http://p2h.com/login.html</a>."
			+ "</span></div></td>" + "</tr>"
			+ "<tr><td height=\"10\"style=\"line-height:0; font-size:0;\"></td></tr>"
			+ "<tr>"
			+ "<td style=\"font:14px Arial, Helvetica, sans-serif; line-height:18px; color:#4c515a;\">Yours, <br />The team of PSD2HTML&reg; pros</td>"
			+ "</tr>"
			+ "<tr><td height=\"14\"style=\"line-height:0; font-size:0; border-bottom:2px solid #d6dadc;\"></td></tr>"
			+ "<tr><td height=\"14\"style=\"line-height:0; font-size:0;\"></td></tr>"
			+ "<tr>"
			+ "<td style=\"font:12px Arial, Helvetica, sans-serif; line-height:18px; color:#aeb6ba; padding-bottom:24px;\"><div style=\"line-height:10px; width:100%;\"><span style=\"line-height:18px;\">Please do not reply to this email. This mailbox is not monitored and you will not receive a response. For assistance, log in to your account and post your message there.</span></div></td>"
			+ "</tr>" + "</table>" + "</td>" + "<td width=\"8%\"></td>" + "</tr>"
			+ "</table>" + "</div>" + "</td>" + "</tr>" + "<!--[if mso]></table>"
			+ "</td>" + "</tr><![endif]-->" + "<!--[if IE]></table>" + "</td>"
			+ "</tr>" + "</table>" + "</td>" + "</tr><![endif]-->" + "</table>"
			+ "</body>" + "</html>";

	@Test(enabled = true)
	public void testJsoupNavigation1() {

		Document doc = Jsoup.parse(data);
		Elements passwordNodeSiblings = doc.select(
				String.format("body table span > strong:contains(%s)", "Password:"));
		assertEquals(1, passwordNodeSiblings.size());
		Element passwordNodeSibling = passwordNodeSiblings.get(0);
		passwordNodeSiblings = doc.select(String
				.format("body table span > strong:contains(%s):eq(0)", "Password:"));
		// The eq(n) selector in jsoup checks the element's sibling index,
		// that is the count from the element's parent.
		assertEquals(1, passwordNodeSiblings.size());
		System.err.println(
				String.format("Reference node: %s", passwordNodeSibling.text()));
		System.err.println(String.format("Data: %s",
				passwordNodeSibling.parent().parent().nextElementSibling().text()));

	}

	@Test
	public void testJsoupNavigation2() {
		// var input = File("C:\\template.html")
		// val doc = Jsoup.parse(input, "UTF-8", "http://example.com/")

		Document doc = Jsoup.parse(data);
		Elements passwordNodes = doc.select("tr:contains(Password:)>td>span");
		// assertThat(passwordNodes.size(), greaterThan(1)));
		assertTrue(passwordNodes.size() > 1);
		Element passwordNode = passwordNodes.get(1);
		assertEquals(passwordNode.text(), "vt22Lzwb");

	}

	@Test(enabled = false)
	public void testEdgeDriverConstructorAction() {
		DriverSettings settings = new DriverSettings();

		settings.setVer(null);
		settings.setOs("win32");
		settings.setDriverDir("c:/temp");
		try {
			new EdgeDriver(settings).getDriver().setDriverInSystemProperty();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ConfigurationException e) {
			e.printStackTrace();
		}

	}

}
