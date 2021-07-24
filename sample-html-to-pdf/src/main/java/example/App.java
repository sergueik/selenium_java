package example;

import com.lowagie.text.DocumentException;
import org.htmlcleaner.CleanerProperties;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.PrettyXmlSerializer;
import org.htmlcleaner.TagNode;
import org.xhtmlrenderer.pdf.ITextRenderer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class App {

	private static final String PAGE_TO_PARSE = "file:///home/sergueik/src/selenium_java/sample-html-to-pdf/example1.html";
	private static final String CHARSET = "UTF-8";
	private final static Options options = new Options();
	private static CommandLineParser commandLineparser = new DefaultParser();
	private static CommandLine commandLine = null;
	private static String inputUri;
	private static String outputFile;
	private static HtmlCleaner cleaner;
	private static CleanerProperties props;
	private static ITextRenderer renderer;
	private static TagNode node;
	private static ByteArrayOutputStream pdfOutputStream;
	private static ByteArrayOutputStream htmlOutputStream;
	private static FileOutputStream pdfFileOutputStream;

	private static boolean debug = false;

	public static void main(String[] args) throws ParseException {
		options.addOption("h", "help", false, "Help");
		options.addOption("d", "debug", false, "Help");
		options.addOption("i", "in", true, "page to print");
		options.addOption("o", "out", true, "file to save");
		commandLine = commandLineparser.parse(options, args);
		if (commandLine.hasOption("h")) {
			help();
		}
		if (commandLine.hasOption("d")) {
			debug = true;
		}
		outputFile = commandLine.getOptionValue("out");
		inputUri = Paths.get(commandLine.getOptionValue("in")).toUri().toString();
		if (debug) {
			System.err.println("Reading: " + inputUri);
		}
		try {
			byte[] pdfDoc = performPdfDocument(
					inputUri != null ? inputUri : PAGE_TO_PARSE);

			if (outputFile != null) {
				if (debug) {
					System.err.println("Writing: " + outputFile);
				}
				try {
					pdfFileOutputStream = new FileOutputStream(new File(outputFile));
					pdfFileOutputStream.write(pdfDoc);
					pdfFileOutputStream.close();
				} catch (IOException e) {
					System.err.println("Exception: ");
					e.printStackTrace();
				}
			} else {
				System.out.write(pdfDoc);
			}
		} catch (Exception e) {
			System.err.println("Exception: ");
			e.printStackTrace();
		}
	}

	private static byte[] performPdfDocument(String path)
			throws IOException, DocumentException {
		String html = getHtml(path);
		/*
		if (debug) {
			System.err.println("Read HTML: " + html.substring(0, 100));
		}
		*/
		htmlOutputStream = new ByteArrayOutputStream();

		cleaner = new HtmlCleaner();
		props = cleaner.getProperties();
		props.setCharset(CHARSET);
		node = cleaner.clean(html);
		new PrettyXmlSerializer(props).writeToStream(node, htmlOutputStream);

		renderer = new ITextRenderer();
		/*
		if (debug) {
			System.err
					.println("Cleaned HTML: " + out.toString(CHARSET).substring(0, 100));
		}
		*/
		renderer.setDocumentFromString(
				new String(htmlOutputStream.toByteArray(), CHARSET));
		renderer.layout();
		pdfOutputStream = new ByteArrayOutputStream();
		renderer.createPDF(pdfOutputStream);

		renderer.finishPDF();
		htmlOutputStream.flush();
		htmlOutputStream.close();

		byte[] result = pdfOutputStream.toByteArray();
		pdfOutputStream.close();

		return result;
	}

	private static String getHtml(String path) throws IOException {
		URLConnection urlConnection = new URL(path).openConnection();

		boolean redirect = false;

		try {
			((HttpURLConnection) urlConnection).setInstanceFollowRedirects(true);
			HttpURLConnection.setFollowRedirects(true);

			int status = ((HttpURLConnection) urlConnection).getResponseCode();
			if (HttpURLConnection.HTTP_OK != status
					&& (HttpURLConnection.HTTP_MOVED_TEMP == status
							|| HttpURLConnection.HTTP_MOVED_PERM == status
							|| HttpURLConnection.HTTP_SEE_OTHER == status)) {

				redirect = true;
			}
		} catch (ClassCastException e) {
			// java.lang.ClassCastException:
			// sun.net.www.protocol.file.FileURLConnection
			// cannot be cast to java.net.HttpURLConnection
			// ignore
		}
		if (redirect) {
			String newUrl = urlConnection.getHeaderField("Location");
			urlConnection = new URL(newUrl).openConnection();
		}

		urlConnection.setConnectTimeout(30000);
		urlConnection.setReadTimeout(30000);

		BufferedReader in = new BufferedReader(
				new InputStreamReader(urlConnection.getInputStream(), CHARSET));

		StringBuilder sb = new StringBuilder();
		String line;
		while (null != (line = in.readLine())) {
			sb.append(line).append("\n");
		}

		in.close();

		return sb.toString().trim();
	}

	public static void help() {
		System.exit(1);
	}

}
