package example;

import java.io.FileInputStream;

import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.model.XWPFHeaderFooterPolicy;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFFooter;
import org.apache.poi.xwpf.usermodel.XWPFHeader;

public class HeaderFooterReader {

	public static void main(String[] args) {
		
		try {
			FileInputStream fis = new FileInputStream("test.docx");
			XWPFDocument xdoc = new XWPFDocument(OPCPackage.open(fis));
			XWPFHeaderFooterPolicy policy = new XWPFHeaderFooterPolicy(xdoc);

			XWPFHeader header = policy.getDefaultHeader();
			if (header != null) {
				System.out.println(header.getText());
			}

			XWPFFooter footer = policy.getDefaultFooter();
			if (footer != null) {
				System.out.println(footer.getText());
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

}
