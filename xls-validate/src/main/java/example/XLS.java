package example;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.hamcrest.Matcher;

import example.matchers.XLS.ContainsRow;
import example.matchers.XLS.ContainsText;
import example.matchers.XLS.DoesNotContainText;

import static example.IO.readBytes;
import static example.IO.readFile;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;

public class XLS {
	public final String name;
	public final Workbook excel;

	private XLS(String name, byte[] content) {
		this.name = name;
		try (InputStream inputStream = new ByteArrayInputStream(content)) {
			excel = WorkbookFactory.create(inputStream);
		} catch (Exception e) {
			throw new IllegalArgumentException("Invalid XLS " + name, e);
		}
	}

	public XLS(File xlsFile) {
		this(xlsFile.getAbsolutePath(), readFile(xlsFile));
	}

	public XLS(URL url) throws IOException {
		this(url.toString(), readBytes(url));
	}

	public XLS(URI uri) throws IOException {
		this(uri.toURL());
	}

	public XLS(byte[] content) {
		this("", content);
	}

	public XLS(InputStream inputStream) throws IOException {
		this(readBytes(inputStream));
	}

	public static Matcher<XLS> containsText(String text) {
		return new ContainsText(text);
	}

	public static Matcher<XLS> containsRow(String... cellTexts) {
		return new ContainsRow(cellTexts);
	}

	public static Matcher<XLS> doesNotContainText(String text) {
		return new DoesNotContainText(text);
	}
}
