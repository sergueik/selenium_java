package example;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFTable;

import org.junit.Assert;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class ReadWordTableTest {
	private static String expected = null;
	private static String tableHtml = null;

	@Test
	public void testWord1() {
		ReadWordTable readWordTable = new ReadWordTable();
		try (InputStream inputStream = ReadWordTableTest.class.getClassLoader().getResourceAsStream("table1.docx");
				XWPFDocument document = new XWPFDocument(inputStream);) {
			List<XWPFTable> tables = document.getTables();
			tableHtml = readWordTable.readTable(tables.get(0));
			expected = "<table><tr><td></td><td colspan='2'></td><td></td><td></td><td></td><td></td><td></td></tr><tr><td rowspan='25'></td><td rowspan='15'></td><td rowspan='5'></td><td></td><td rowspan='25'></td><td rowspan='25'></td><td rowspan='25'></td><td rowspan='3'></td></tr><tr><td></td></tr><tr><td></td></tr><tr><td></td><td rowspan='2'></td></tr><tr><td></td></tr><tr><td rowspan='10'></td><td></td><td></td></tr><tr><td></td><td rowspan='4'></td></tr><tr><td></td></tr><tr><td></td></tr><tr><td></td></tr><tr><td></td><td rowspan='2'></td></tr><tr><td></td></tr><tr><td></td><td rowspan='3'></td></tr><tr><td></td></tr><tr><td></td></tr><tr><td rowspan='9'></td><td rowspan='9'></td><td></td><td></td></tr><tr><td></td><td rowspan='9'></td></tr><tr><td></td></tr><tr><td></td></tr><tr><td></td></tr><tr><td></td></tr><tr><td></td></tr><tr><td></td></tr><tr><td></td></tr><tr><td colspan='2'></td><td></td></tr></table>";
			assertThat(tableHtml, is(expected));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testWord2() {
		ReadWordTable readWordTable = new ReadWordTable();
		try (InputStream inputStream = ReadWordTableTest.class.getClassLoader().getResourceAsStream("table2.docx");
				XWPFDocument document = new XWPFDocument(inputStream);) {
			List<XWPFTable> tables = document.getTables();
			tableHtml = readWordTable.readTable(tables.get(0));
			expected = "<table><tr><td></td><td colspan='2'></td><td></td><td></td><td></td><td></td><td></td></tr><tr><td rowspan='25'></td><td rowspan='15'></td><td rowspan='5'></td><td></td><td rowspan='26'></td><td rowspan='26'></td><td rowspan='26'></td><td rowspan='3'></td></tr><tr><td></td></tr><tr><td></td></tr><tr><td></td><td rowspan='2'></td></tr><tr><td></td></tr><tr><td rowspan='10'></td><td></td><td></td></tr><tr><td></td><td rowspan='4'></td></tr><tr><td></td></tr><tr><td></td></tr><tr><td></td></tr><tr><td></td><td rowspan='2'></td></tr><tr><td></td></tr><tr><td></td><td rowspan='3'></td></tr><tr><td></td></tr><tr><td></td></tr><tr><td rowspan='9'></td><td rowspan='9'></td><td></td><td></td></tr><tr><td></td><td rowspan='10'></td></tr><tr><td></td></tr><tr><td></td></tr><tr><td></td></tr><tr><td></td></tr><tr><td></td></tr><tr><td></td></tr><tr><td></td></tr><tr><td colspan='2'></td><td></td></tr><tr><td></td><td></td><td></td><td></td></tr></table>";
			assertThat(tableHtml, is(expected));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
