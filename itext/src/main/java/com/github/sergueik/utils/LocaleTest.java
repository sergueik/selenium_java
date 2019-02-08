package com.github.sergueik.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;

public class LocaleTest {

	public static void main(String args[]) throws IOException, DocumentException {

		try {
			// Create Document instance.
			Document document = new Document();

			// Create OutputStream instance.
			OutputStream outputStream = new FileOutputStream(
					new File("C:/Users/Serguei/Desktop/TestFont2.pdf"));

			// Create PDFWriter instance.
			PdfWriter.getInstance(document, outputStream);

			// Open the document.
			document.open();

			BaseFont times = BaseFont.createFont("c:/windows/fonts/times.ttf",
					"cp1251", BaseFont.EMBEDDED);

			Paragraph p = new Paragraph("Номер заказа:", new Font(times, 14));
			Paragraph p1 = new Paragraph("Статус заказа:", new Font(times, 14));
			Paragraph p2 = new Paragraph("Дата и время заказа:", new Font(times, 14));

			// Add content to the document using Paragraph, Chunk etc
			// objects with font object.
			document.add(p);
			document.add(p1);
			document.add(p2);

			// Close document and outputStream.
			document.close();
			outputStream.close();

			System.out.println("Pdf created successfully.");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
