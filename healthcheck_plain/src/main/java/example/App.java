package example;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.Socket;
import java.net.URL;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomNode;
import com.gargoylesoftware.htmlunit.html.DomNodeList;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;

public class App {
	private static WebClient client = new WebClient();
	private static HtmlPage page;
	private static HtmlElement element;
	private static boolean useHtmlUnit = false;
	private static Document jsoupDocument;
	private static List<String> jsoupSelectors = Arrays
			.asList(new String[] { "#leftColumn", "#rightColumn" });
	private static Document parentDocument;
	private static Elements jsoupElements;
	private static Elements jsoupElements2;
	private static Element jsoupElement;
	private static Element jsoupElement2;
	private static Document childDocument;

	private static String attributeName;
	private static String attributeValue;

	public static void main(String[] args) {
		String host = "localhost";
		int port = 4444;
		String url = "http://localhost:4444/grid/console";
		App app = new App();
		boolean status = app.connectionCheck("localhost", 4444);
		System.err.println("Status: " + status);
		try {
			int statusCode = app.getResponseCodeForURLUsingHead(url);
			System.err.println("Status Code: " + statusCode);
			String html = app.getPage(url);
			System.err.println("Page HTML: " + html.substring(0, 20) + "...");
			if (useHtmlUnit) {
				page = client.getPage(url);
				// WARNING: Expected content type of 'application/javascript' or
				// 'application/ecmascript' for remotely loaded JavaScript element at
				// 'http://localhost:4444/grid/resources/org/openqa/grid/images/console-beta.js',
				// but got ''.
				assertThat(page, notNullValue());
				System.err
						.println("Page as XML: " + page.asXml().substring(0, 20) + "...");
				System.err.println("Looking individual elements");

				element = (HtmlElement) (page.getElementsById("rightColumn").get(0));
				assertThat(element, notNullValue());
				System.err.println("Element: " + element.asXml());
			}

			jsoupDocument = Jsoup.parse(html);
			System.err.println(
					"Page as Jsoup: " + jsoupDocument.html().substring(0, 20) + "...");
			for (String jsoupSelector : jsoupSelectors) {
				jsoupElements = jsoupDocument.select(jsoupSelector);

				System.err.println(String.format("Searching  via jsoup selector \"%s\"",
						jsoupSelector));
				assertThat(jsoupElements, notNullValue());
				assertThat(jsoupElements.iterator().hasNext(), is(true));
				// assertThat(jsoupElements.eachText().size(), greaterThan(1));
				// System.err.println(String.format("Processing jsoup selector \"%s\"
				// %s",
				// jsoupSelector, jsoupElements.first().html()));
				jsoupElement = jsoupElements.first();
				// String tagName = "p";
				jsoupElements2 = jsoupElement.getElementsByAttributeValue("class",
						"proxyid");
				Iterator<Element> iterator = jsoupElements2.iterator();
				while (iterator.hasNext()) {
					jsoupElement2 = iterator.next();
					System.err.println("Processing element:" + jsoupElement2.text());

				}
			}

		} catch (IOException e) {
		}
	}

	public boolean connectionCheck(String host /* can be a host address */,
			int port) {
		boolean status = false;
		try {
			Socket clientSocket = new Socket(host, port);
			// successfully opened socket to host, port
			clientSocket.close();
			status = true;
		} catch (ConnectException e) {
			// host and port combination not valid / host is down
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return status;
	}

	public int getResponseCodeForURL(String address) throws IOException {
		return getResponseCodeForURLUsing(address, "GET");
	}

	public int getResponseCodeForURLUsingHead(String address) throws IOException {
		return getResponseCodeForURLUsing(address, "HEAD");
	}

	private int getResponseCodeForURLUsing(String address, String method)
			throws IOException {
		HttpURLConnection.setFollowRedirects(false);
		HttpURLConnection connection = (HttpURLConnection) new URL(address)
				.openConnection();
		connection.setRequestMethod(method);

		return connection.getResponseCode();
	}

	private String getPage(String address) throws IOException {
		HttpURLConnection.setFollowRedirects(true);
		HttpURLConnection connection = (HttpURLConnection) new URL(address)
				.openConnection();
		connection.setRequestMethod("GET");
		BufferedReader bufferedReader = new BufferedReader(
				new InputStreamReader(connection.getInputStream()));
		String inputLine;
		StringBuffer content = new StringBuffer();
		while ((inputLine = bufferedReader.readLine()) != null) {
			content.append(inputLine);
		}
		bufferedReader.close();
		connection.disconnect();
		return content.toString();
	}

}
