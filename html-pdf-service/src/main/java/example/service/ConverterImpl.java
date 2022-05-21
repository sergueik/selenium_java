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
package example.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.pdfbox.multipdf.PDFMergerUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.xhtmlrenderer.pdf.ITextRenderer;

import example.spi.Converter;

@Service
public class ConverterImpl extends BaseImpl implements Converter {
	private static final Logger logger = LoggerFactory.getLogger(ConverterImpl.class);

	@Override
	public void convertHtmlToPdf(String html, OutputStream out) {
		convertHtmlToPdf(html, null, out);
	}

	@Override
	public void convertHtmlToPdf(String html, String css, OutputStream out) {
		try {
			html = correctHtml(html);
			html = getFormedHTMLWithCSS(html, css);

			//This ITextRenderer is from the Flying Saucer library under LGPL license.
			//Should not be confused with the actual iText library.
			ITextRenderer r = new ITextRenderer();
			r.setDocumentFromString(html);
			r.layout();
			r.createPDF(out);
			r.finishPDF();

		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
		}
	}

	@Override
	public void convertHtmlToPdf(List<String> htmls, OutputStream out) {
		convertHtmlToPdf(htmls, null, out);
	}

	@Override
	public void convertHtmlToPdf(List<String> htmls, String css, OutputStream out) {
		try {
			PDFMergerUtility merge = new PDFMergerUtility();

			for (String html : htmls) {
				ByteArrayOutputStream bos = new ByteArrayOutputStream();

				// convertHtmlToPdf() performs null check on css by default, so
				// no need to do it here.
				convertHtmlToPdf(html, css, bos);

				ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
				merge.addSource(bis);
			}

			merge.setDestinationStream(out);
			merge.mergeDocuments(null);
		} catch (IOException e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
		}
	}


}
