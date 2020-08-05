package example;

import java.io.FileInputStream;
import java.util.List;

import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFPictureData;

public class ImageReader {

	public static void main(String[] args) {

		try {
			FileInputStream fis = new FileInputStream("test.docx");
			XWPFDocument xdoc = new XWPFDocument(OPCPackage.open(fis));
			List<XWPFPictureData> pic = xdoc.getAllPictures();
			if (!pic.isEmpty()) {
				System.out.print(pic.get(0).getPictureType());
				System.out.print(pic.get(0).getData());
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
