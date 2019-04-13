package com.github.sergueik.example;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import org.junit.Test;
import com.github.sergueik.example.PageHTMLSourceGetter;

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

	@Test

	public void test7() throws Exception {
		String htmlSource = null;
		// PageHTMLSourceGetter.getUserAgents();
		for (int cnt = 0; cnt != maxcount; cnt++) {
			htmlSource = PageHTMLSourceGetter.getPageHTMLSource(url);
			assertTrue(htmlSource != null);
			assertTrue(htmlSource.length() > 10000);
			assertTrue(
					htmlSource.indexOf("we would like to ensure real humans") == -1);
			assertTrue(htmlSource.indexOf(
					"We have detected unusual traffic activity originating from your IP address") == -1);
			assertFalse(
					htmlSource.indexOf(String.format("Restaurant in %s", city)) == -1);
		}
	}

}
