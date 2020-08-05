package example;

import java.io.FileInputStream;
import java.util.List;

import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;

public class ParagraphReader {

	public static void main(String[] args) {
		try {
			FileInputStream fis = new FileInputStream("test.docx");
			XWPFDocument xdoc = new XWPFDocument(OPCPackage.open(fis));

			List<XWPFParagraph> paragraphList = xdoc.getParagraphs();

			for (XWPFParagraph paragraph : paragraphList) {

				System.out.println(paragraph.getText());
				System.out.println(paragraph.getAlignment());// LEFT
				System.out.print(paragraph.getRuns().size());
				System.out.println(paragraph.getStyle());

				// Returns numbering format for this paragraph, eg bullet or
				// lowerLetter.
				System.out.println(paragraph.getNumFmt());
				System.out.println(paragraph.getAlignment());// LEFT

				System.out.println(paragraph.isWordWrapped());

				System.out.println("********************************************************************");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

}
