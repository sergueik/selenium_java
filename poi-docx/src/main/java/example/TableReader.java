package example;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.wp.usermodel.CharacterRun;
import org.apache.poi.xwpf.usermodel.IBodyElement;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;

public class TableReader {

	// cannot be made static
	private static XWPFDocument openDocument(String file) throws Exception {
		FileInputStream fis = new FileInputStream(file);
		XWPFDocument document = new XWPFDocument(OPCPackage.open(fis));
		return document;
	}

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
						for (int i = 0; i < table.getRows().size(); i++) {

							for (int j = 0; j < table.getRow(i).getTableCells().size(); j++) {
								String findText = "Year";
								String replaceText = "YEAR";
								// CharacterRun run =
								XWPFTableCell cell = table.getRow(i).getCell(j);

								if (cell.getText().contains(findText)) {
									
									cell.setText(replaceText);
									// .replaceText(findText, replaceText);
								}
								System.out.println("-->" + table.getRow(i).getCell(j).getText());
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
