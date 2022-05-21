/*
 * {{{ header & license
 * Copyright (c) 2016 Farrukh Mirza
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public License
 * as published by the Free Software Foundation; either version 3
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA.
 * }}}
 */

/**
 * @author Farrukh Mirza
 * 24/06/2016 
 * Dublin, Ireland
 */
package org.farrukh.mirza.pdf.test.converter;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import org.farrukh.mirza.pdf.test.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import example.spi.Converter;
import example.spi.TemplateDataTransformer;
import example.spi.TestDataProvider;

@RunWith(SpringJUnit4ClassRunner.class)
// @SpringApplicationConfiguration(classes=Application.class)
@SpringApplicationConfiguration(Application.class)
public class TemplateTransformationTest {

	@Autowired
	public Converter converter;

	@Autowired
	public TemplateDataTransformer transformer;

	@Autowired
	public TestDataProvider dataProvider;

	private String getSimpleHtml() {
		return dataProvider.getHtmlDoc();
	}

	private String getHtmlUsingTemplate() {
		return transformer.transformHTMLTemplate(dataProvider.getHtmlTemplateDoc(), dataProvider.getTestDataObject());
	}

	private List<String> getHtmlsUsingTemplate() {
		return transformer.transformHTMLTemplates(dataProvider.getHtmlTemplateDoc(), dataProvider.getTestDataArray());
	}


	@Test
	public void testHtmlUsingTemplate(){
		System.out.println(getHtmlUsingTemplate());
	}

	@Test
	public void testHtmlUsingTemplates(){
		System.out.println(transformer.transformHTMLTemplates(dataProvider.getHtmlTemplateDoc(), dataProvider.getTestDataArray()));
	}

//	@Test
//	public void testHtmlToPdfFile() {
//		try {
//			OutputStream file = new FileOutputStream(new File("HTMLtoPDFTest.pdf"));
//			converter.convertHtmlToPdf(getSimpleHtml(), file);
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		}
//	}

//	@Test
//	public void testHtmlToPdfByteFile() {
//		try {
//			ByteArrayOutputStream bos = new ByteArrayOutputStream();
//			OutputStream file = new FileOutputStream(new File("HTMLtoPDFTestByte.pdf"));
//			converter.convertHtmlToPdf(getSimpleHtml(), bos);
//			file.write(bos.toByteArray());
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}

//	@Test
//	public void testHtmlToPdfFileWithCss() {
//		try {
//			OutputStream file = new FileOutputStream(new File("HTMLtoPDFTestCss.pdf"));
//			converter.convertHtmlToPdf(getSimpleHtml(), dataProvider.getCssDoc(), file);
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		}
//	}

//	@Test
//	public void testHtmlToPdfByteFileWithCss() {
//		try {
//			ByteArrayOutputStream bos = new ByteArrayOutputStream();
//			OutputStream file = new FileOutputStream(new File("HTMLtoPDFTestByteCss.pdf"));
//			converter.convertHtmlToPdf(getSimpleHtml(), dataProvider.getCssDoc(), bos);
//			file.write(bos.toByteArray());
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}

//	@Test
//	public void testHtmlTemplateToPdfFile() {
//		try {
//			OutputStream file = new FileOutputStream(new File("HTMLTemplatetoPDFTest.pdf"));
//			converter.convertHtmlToPdf(getHtmlUsingTemplate(), file);
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		}
//
//	}

//	@Test
//	public void testHtmlTemplateToPdfByteFile() {
//		try {
//			ByteArrayOutputStream bos = new ByteArrayOutputStream();
//			OutputStream file = new FileOutputStream(new File("HTMLTemplatetoPDFTestByte.pdf"));
//			converter.convertHtmlToPdf(getHtmlUsingTemplate(), bos);
//			file.write(bos.toByteArray());
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}

	@Test
	public void testHtmlTemplateToPdfFileWithCss() {
		try {
			OutputStream file = new FileOutputStream(new File("HTMLTemplatetoPDFTestCss.pdf"));
			converter.convertHtmlToPdf(getHtmlUsingTemplate(), dataProvider.getCssDoc(), file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testHtmlTemplatesToPdfFileWithCss() {
		try {
			OutputStream file = new FileOutputStream(new File("HTMLTemplatestoPDFTestCss.pdf"));
			converter.convertHtmlToPdf(getHtmlsUsingTemplate(), dataProvider.getCssDoc(), file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	//	@Test
//	public void testHtmlTemplateToPdfByteFileWithCss() {
//		try {
//			ByteArrayOutputStream bos = new ByteArrayOutputStream();
//			OutputStream file = new FileOutputStream(new File("HTMLTemplatetoPDFTestByteCss.pdf"));
//			converter.convertHtmlToPdf(getHtmlUsingTemplate(), dataProvider.getCssDoc(), bos);
//			file.write(bos.toByteArray());
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}

}
