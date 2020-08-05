package example;

import java.io.FileInputStream;
import java.util.List;

import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

public class StyleReader {

	public static void main(String[] args) {
		try {
			FileInputStream fis = new FileInputStream("test.docx");
			XWPFDocument xdoc = new XWPFDocument(OPCPackage.open(fis));

			List<XWPFParagraph> paragraphList = xdoc.getParagraphs();

			for (XWPFParagraph paragraph : paragraphList) {

				for (XWPFRun rn : paragraph.getRuns()) {

					System.out.println(rn.isBold());
					System.out.println(rn.isHighlighted());
					System.out.println(rn.isCapitalized());
					System.out.println(rn.getFontSize());
				}

				System.out.println("********************************************************************");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

}
