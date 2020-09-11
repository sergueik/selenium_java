package example;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.usermodel.IBodyElement;
// import org.apache.poi.xwpf.usermodel.IRunElement;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;

public class TableReader {

	// cannot be made static
	private static XWPFDocument openDocument(String file) throws Exception {
		FileInputStream fis = new FileInputStream(file);
		XWPFDocument document = new XWPFDocument(OPCPackage.open(fis));
		return document;
	}

	private static final String findText = "Degree";
	private static final String replaceText = "Acknowledgement";

	public static void main(String[] args) {
		try {
			XWPFDocument xdoc = openDocument("test.docx");
			Iterator<IBodyElement> bodyElementIterator = xdoc.getBodyElementsIterator();
			while (bodyElementIterator.hasNext()) {
				IBodyElement element = bodyElementIterator.next();

				if ("TABLE".equalsIgnoreCase(element.getElementType().name())) {
					List<XWPFTable> tableList = element.getBody().getTables();
					for (XWPFTable table : tableList) {
						System.out.println("Total Number of Rows of Table:" + table.getNumberOfRows());
						for (int rowNum = 0; rowNum < table.getRows().size(); rowNum++) {
							XWPFTableRow row = table.getRow(rowNum);
							for (int colNum = 0; colNum < row.getTableCells().size(); colNum++) {
								XWPFTableCell cell = row.getCell(colNum);
								if (cell.getText().contains(findText)) {

									List<XWPFParagraph> paragraphs = cell.getParagraphs();
									for (XWPFParagraph paragraph : paragraphs) {
										List<XWPFRun> runElemens = paragraph.getRuns();

										for (XWPFRun runElement : runElemens) {
											String text = runElement.getText(0);
											if (text.contains(findText)) {
												System.out.println(findText + " ~> " + replaceText);
												runElement.setText(String.format("%-30s",
														text.replaceFirst(findText, replaceText)), 0);
											}
										}
									}
								}
								System.out.println("-->" + cell.getText());
							}
						}
					}
				}
			}
			saveDocument(xdoc, "new.docx");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private static void saveDocument(XWPFDocument doc, String file) {
		try (FileOutputStream out = new FileOutputStream(file)) {
			doc.write(out);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
