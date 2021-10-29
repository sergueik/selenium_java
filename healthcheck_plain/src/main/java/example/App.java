package example;

/**
 * Copyright 2021 Serguei Kouzmine
 */

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class App {

	private static Document jsoupDocument;
	private static List<String> jsoupSelectors = Arrays
			.asList(new String[] { "#leftColumn", "#rightColumn" });
	private static Elements jsoupElements;
	private static Elements jsoupElements2;
	private static Element jsoupElement;
	private static Element jsoupElement2;

	private static boolean debug = false;
	private final static Options options = new Options();
	private static CommandLineParser commandLineparser = new DefaultParser();
	private static CommandLine commandLine = null;

	public static void main(String[] args) {
		String url = "http://localhost:4444/grid/console";
		String host = "localhost";
		int port = 4444;
		options.addOption("h", "help", false, "help");
		options.addOption("d", "debug", false, "debug");
		options.addOption("u", "url", true, "url");
		options.addOption("l", "list", true, "expected list");
		try {

			commandLine = commandLineparser.parse(options, args);
			if (commandLine.hasOption("h")) {
				help();
			}
			if (commandLine.hasOption("d")) {
				debug = true;
			}
			if (commandLine.hasOption("url")) {
				url = commandLine.getOptionValue("url");
			}

			App app = new App();

			Pattern pattern = Pattern
					.compile("http://([^:/]+):([0-9]+)/grid/console");
			if (debug) {
				System.err.println("Pattern:\n" + pattern.toString());
			}
			Matcher matcher = pattern.matcher(url);
			assertThat(matcher.find(), is(true));
			host = matcher.group(1);
			port = Integer.parseInt(matcher.group(2));
			if (debug) {
				System.err.println("open Socket : " + host + " " + port);
			}
			boolean status = app.connectionCheck(host, port);
			if (debug) {
				System.err.println("Socket status: " + status);
			}
			int statusCode = app.getResponseCodeForURLUsingHead(url);
			if (debug) {
				System.err.println(url + " HTTP status code: " + statusCode);
			}
			String html = app.getPage(url);
			if (debug) {
				System.err.println("Page HTML: " + html.substring(0, 20) + "...");
			}
			jsoupDocument = Jsoup.parse(html);
			if (debug) {
				System.err.println(
						"Page as Jsoup: " + jsoupDocument.html().substring(0, 20) + "...");
			}
			for (String jsoupSelector : jsoupSelectors) {
				jsoupElements = jsoupDocument.select(jsoupSelector);

				if (debug) {
					System.err.println(String
							.format("Searching  via jsoup selector \"%s\"", jsoupSelector));

				}
				assertThat(jsoupElements, notNullValue());
				assertThat(jsoupElements.iterator().hasNext(), is(true));
				jsoupElement = jsoupElements.first();
				jsoupElements2 = jsoupElement.getElementsByAttributeValue("class",
						"proxyid");
				Iterator<Element> iterator = jsoupElements2.iterator();
				while (iterator.hasNext()) {
					jsoupElement2 = iterator.next();
					// contains few "p" inside
					String text = jsoupElement2.text();
					if (debug) {
						System.err
								.println(String.format("Processing element:\"%s\"", text));
					}
					pattern = Pattern.compile("^.*\\s+http://([^:/]+):([0-9]+).*");
					if (debug) {
						System.err.println("Pattern:\n" + pattern.toString());
					}
					matcher = pattern.matcher(text);
					assertThat(matcher.find(), is(true));
					String node = matcher.group(1);
					System.err.println("Identified node:" + node);
				}
			}
		} catch (ParseException e) {

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

	public static void help() {
		System.exit(1);
	}
}
