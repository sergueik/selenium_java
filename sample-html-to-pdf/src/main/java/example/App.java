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

public class App {

	private static final String PAGE_TO_PARSE = "file:///home/sergueik/src/selenium_java/sample-html-to-pdf/example1.html";
	private static final String CHARSET = "UTF-8";

	public static void main(String[] args) {

		try {
			byte[] pdfDoc = performPdfDocument(args.length > 0 ? args[0] : PAGE_TO_PARSE);
			System.out.write(pdfDoc);
		} catch (Exception ex) {

			System.out.println("<strong>Something wrong</strong><br /><br />");
			ex.printStackTrace();
		}
	}

	private static byte[] performPdfDocument(String path) throws IOException, DocumentException {
		String html = getHtml(path);

		ByteArrayOutputStream out = new ByteArrayOutputStream();

		HtmlCleaner cleaner = new HtmlCleaner();
		CleanerProperties props = cleaner.getProperties();
		props.setCharset(CHARSET);
		TagNode node = cleaner.clean(html);
		new PrettyXmlSerializer(props).writeToStream(node, out);

		ITextRenderer renderer = new ITextRenderer();
		renderer.setDocumentFromString(new String(out.toByteArray(), CHARSET));
		renderer.layout();
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		renderer.createPDF(outputStream);

		renderer.finishPDF();
		out.flush();
		out.close();

		byte[] result = outputStream.toByteArray();
		outputStream.close();

		return result;
	}

	private static String getHtml(String path) throws IOException {
		URLConnection urlConnection = new URL(path).openConnection();

		boolean redirect = false;

		try {
			((HttpURLConnection) urlConnection).setInstanceFollowRedirects(true);
			HttpURLConnection.setFollowRedirects(true);

			int status = ((HttpURLConnection) urlConnection).getResponseCode();
			if (HttpURLConnection.HTTP_OK != status && (HttpURLConnection.HTTP_MOVED_TEMP == status
					|| HttpURLConnection.HTTP_MOVED_PERM == status || HttpURLConnection.HTTP_SEE_OTHER == status)) {

				redirect = true;
			}
		} catch (ClassCastException e) {
			// java.lang.ClassCastException: sun.net.www.protocol.file.FileURLConnection
			// cannot be cast to java.net.HttpURLConnection
			// ignore
		}
		if (redirect) {
			String newUrl = urlConnection.getHeaderField("Location");
			urlConnection = new URL(newUrl).openConnection();
		}

		urlConnection.setConnectTimeout(30000);
		urlConnection.setReadTimeout(30000);

		BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), CHARSET));

		StringBuilder sb = new StringBuilder();
		String line;
		while (null != (line = in.readLine())) {
			sb.append(line).append("\n");
		}

		in.close();

		return sb.toString().trim();
	}

}
