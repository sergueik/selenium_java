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
 * @date 8 Jul 2016
 * Dublin, Ireland
 */
package org.farrukh.mirza.pdf.rest;

import org.apache.commons.lang3.StringEscapeUtils;
import org.farrukh.mirza.pdf.spi.TestDataProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("service/test/data")
public class TestDataResource {
	private static final Logger logger = LoggerFactory.getLogger(TestDataResource.class);

	@Autowired
	private TestDataProvider dataProvider;

	
	@RequestMapping({ "", "/" })
	@ResponseBody
	public String sayHello() {
		String testHtml =dataProvider.getHtmlDoc();
		String testCSS=dataProvider.getCssDoc();
		
		testHtml = StringEscapeUtils.escapeHtml4(testHtml);
		
		String testJson = "{\"html\": \""+StringEscapeUtils.escapeJson(testHtml)+"\", \"css\":\""+StringEscapeUtils.escapeJson(testCSS)+"\"}";
		
		logger.debug("This is the Test HTML to PDF wrapper service.");
		String resp = "This is the Test HTML to PDF wrapper service. " + "All methods are the same and GET. "
				+ "No need to provide HTML or CSS. <br/> <br/>" + "<h1>Test HTML: </h1>" + testHtml
				+ " <br/><br/>" + "<h1>Test CSS:</h1>" + testCSS 
				+ "<br/><br/>" + "<h1>Test JSON:</h1>" + testJson
				;

		logger.debug("HTML: " + testHtml);
		logger.debug("CSS: " + testCSS);
		logger.debug("JSON: " + testJson);
		
		return resp;
	}

	
	@RequestMapping("/html")
	@ResponseBody
	public String getHtml() {
//		return StringEscapeUtils.escapeHtml4(dataProvider.getHtmlDoc());
		return dataProvider.getHtmlDoc();
	}

	@RequestMapping("/html/template")
	@ResponseBody
	public String getHtmlTemplate() {
//		return StringEscapeUtils.escapeHtml4(dataProvider.getHtmlTemplateDoc());
		return dataProvider.getHtmlTemplateDoc();
	}

	
	@RequestMapping("/css")
	@ResponseBody
	public String getCss() {
//		return StringEscapeUtils.escapeHtml4(dataProvider.getCssDoc());
		return dataProvider.getCssDoc();
	}

	@RequestMapping("/json")
	@ResponseBody
	public String getJsonData() {
//		return StringEscapeUtils.escapeHtml4(dataProvider.getTestData());
		return dataProvider.getTestDataArray();
	}

}
