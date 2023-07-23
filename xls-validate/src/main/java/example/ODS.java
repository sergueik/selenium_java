package example;

import static example.IO.readBytes;
import static example.IO.readFile;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.hamcrest.Matcher;
import org.jopendocument.dom.ODDocument;
import org.jopendocument.dom.ODPackage;
import org.jopendocument.dom.ODValueType;
import org.jopendocument.dom.spreadsheet.Cell;
import org.jopendocument.dom.spreadsheet.SpreadSheet;

import example.matchers.ODS.ContainsRow;
import example.matchers.ODS.ContainsText;
import example.matchers.ODS.DoesNotContainText;

public class ODS {
	public final String name;
	public final SpreadSheet ods;

	private ODS(String name, byte[] content) {
		this.name = name;
		try (InputStream inputStream = new ByteArrayInputStream(content)) {
			ods = SpreadSheet.get(new ODPackage(inputStream));
		} catch (Exception e) {
			throw new IllegalArgumentException("Invalid ODS " + name, e);
		}
	}

	public ODS(File file) {
		this(file.getAbsolutePath(), readFile(file));
	}

	public ODS(URL url) throws IOException {
		this(url.toString(), readBytes(url));
	}

	public ODS(URI uri) throws IOException {
		this(uri.toURL());
	}

	public ODS(byte[] content) {
		this("", content);
	}

	public ODS(InputStream inputStream) throws IOException {
		this(readBytes(inputStream));
	}

	public static Matcher<ODS> containsText(String text) {
		return new ContainsText(text);
	}

	public static Matcher<ODS> containsRow(String... cellTexts) {
		return new ContainsRow(cellTexts);
	}

	public static Matcher<ODS> doesNotContainText(String text) {
		return new DoesNotContainText(text);
	}

	// see also:
	// https://stackoverflow.com/questions/64423111/javajopendocument-nullpointerexception-when-using-getcellat0-0
	// https://www.jopendocument.org/docs/org/jopendocument/dom/ODValueType.html
	@SuppressWarnings("deprecation")
	public Object safeOOCellValue(Cell<ODDocument> cell) {
		if (cell == null) {
			return null;
		}
		Object result;
		String data = cell.getElement().getValue();
		ODValueType type = cell.getValueType();
		switch (type) {
		case FLOAT:
			result = Double.valueOf(data);
			break;
		case STRING:
			result = data;
			break;
		case DATE:
			Date date;
			try {
				date = new SimpleDateFormat("MM/dd/yy").parse(data);
			} catch (ParseException e) {
				date = new Date("01/01/1970");
			}
			result = date;
			break;
		case TIME:
			result = null; // TODO
			break;
		case BOOLEAN:
			result = Boolean.getBoolean(data);
			break;
		default:
			throw new IllegalStateException(
					"Can't evaluate cell value of " + type.getName());
		}
		return result;
	}

}
