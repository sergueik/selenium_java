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
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.collections4.CollectionUtils;

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
	private static List<String> selectors = Arrays.asList(new String[] {
			"#leftColumn", "#rightColumn", "#left-column", "#right-column" });
	private static Elements jsoupElements;
	private static Elements jsoupElements2;
	private static Element jsoupElement;
	private static Element jsoupElement2;

	private static boolean debug = false;
	private final static Options options = new Options();
	private static CommandLineParser commandLineparser = new DefaultParser();
	private static CommandLine commandLine = null;
	private static List<String> expectedNodes = new ArrayList<>();

	public static void setExpectedNodes(List<String> value) {
		App.expectedNodes = value;
	}

	public static void main(String[] args) {
		String url = "http://localhost:4444/grid/console";
		String host = "localhost";
		int port = 4444;
		String list = null;
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
			List<String> nodes = new ArrayList<>();
			if (commandLine.hasOption("list")) {
				list = getValue(commandLine.getOptionValue("list"));
				nodes = Arrays.asList(list.split(","));
			}

			App app = new App();
			App.setExpectedNodes(nodes);
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
			if (!status) {
				System.err.println("Hub is down: " + host);
				return;
			}
			int statusCode = app.getResponseCodeForURLUsingHead(url);
			if (debug) {
				System.err.println(url + " HTTP status code: " + statusCode);
			}
			if (statusCode == 404) {
				System.err.println("Hub page is not found: " + url);
				return;
			}
			String html = app.getPage(url);
			if (debug) {
				System.err.println("Page HTML: " + html.substring(0, 20) + "...");
			}
			jsoupDocument = Jsoup.parse(html);
			List<String> foundNodes = new ArrayList<>();

			if (debug) {
				System.err.println(
						"Page as Jsoup: " + jsoupDocument.html().substring(0, 20) + "...");
			}
			for (String selector : selectors) {
				jsoupElements = jsoupDocument.select(selector);

				if (debug) {
					System.err.println(
							String.format("Searching with selector \"%s\"", selector));

				}
				if (jsoupElements != null && jsoupElements.iterator().hasNext()) {
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
						System.err.println("Identified node: " + node);
						foundNodes.add(node);
					}
				}
			}
			// see also:
			// https://stackoverflow.com/questions/9933403/subtracting-one-arraylist-from-another-arraylist
			// expectedNodes.removeAll(foundNodes);
			// java.lang.UnsupportedOperationException
			System.err.println("Missing nodes:"
					+ CollectionUtils.subtract(expectedNodes, foundNodes));
		} catch (ParseException e) {

		} catch (IOException e) {
		}
	}

	// accepts a host address
	public boolean connectionCheck(String host, int port) {
		boolean status = false;
		try {
			Socket clientSocket = new Socket(host, port);
			// successfully opened socket to host, port
			clientSocket.close();
			status = true;
		} catch (ConnectException e) {
			// host and port combination not valid / host is down
			if (debug) {
				System.err.println("Host is down: " + e.getMessage());
			}
		} catch (IOException e) {
			// ignore
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

	public static String readFile(String path, Charset encoding)
			throws IOException {
		byte[] encoded = Files.readAllBytes(Paths.get(path));
		return new String(encoded, encoding);
	}

	public static String getValue(String data) {
		String value = null;
		if (data.matches("(?i)^@[a-z_0-9./:]+")) {
			String datafilePath = Paths.get(System.getProperty("user.dir"))
					.resolve(data.replaceFirst("^@", "")).toAbsolutePath().toString();

			try {
				value = readFile(datafilePath, Charset.forName("UTF-8"))
						.replaceAll(" *\\r?\\n *", ",");
			} catch (IOException e) {

			}
		} else {
			value = data;
		}
		if (debug)
			System.err.println("Got value: " + value);
		return value;
	}
}
