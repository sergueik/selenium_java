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
package org.farrukh.mirza.pdf.rest;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.farrukh.mirza.pdf.rest.dto.PdfRequest;
import org.farrukh.mirza.pdf.spi.Converter;
import org.farrukh.mirza.pdf.spi.TemplateDataTransformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("service/convert/html")
public class HtmlPdfResource {

	private static final Logger logger = LoggerFactory.getLogger(HtmlPdfResource.class);

	@Autowired
	protected Converter converter;

	@Autowired
	protected TemplateDataTransformer templateDataTransformer;

	private void convertFromHtml(String html, String css, String json, OutputStream out) {
		try {

			html = StringEscapeUtils.unescapeHtml4(html);
			css = StringUtils.isBlank(css) ? "" : StringEscapeUtils.unescapeHtml4(css);
			json = StringUtils.isBlank(json) ? "" : StringEscapeUtils.unescapeHtml4(json);
			logger.debug("HTML: " + html);
			logger.debug("CSS: " + css);
			logger.debug("JSON: " + json);

			boolean isMultipleDocs = false;
			List<String> htmls = new ArrayList<>();

			if (StringUtils.isNotBlank(json)) {
				if (templateDataTransformer.isJsonArray(json)) {
					isMultipleDocs = true;
					htmls = templateDataTransformer.transformHTMLTemplates(html, json);
				} else {
					html = templateDataTransformer.transformHTMLTemplate(html, json);
				}
			}

			// convertHtmlToPdf() performs null check on css by default, so no
			// need to do it here.
			if (isMultipleDocs) {
				converter.convertHtmlToPdf(htmls, css, out);
			} else {
				converter.convertHtmlToPdf(html, css, out);
			}
		} catch (Throwable t) {
			logger.error(t.getMessage(), t);
		}

	}

	private void convertFromHtml(String html, String css, OutputStream out) {
		convertFromHtml(html, css, null, out);
	}

	private void convertFromHtml(String html, OutputStream out) {
		convertFromHtml(html, null, null, out);
	}

	@RequestMapping(value = "params", method = { RequestMethod.POST, RequestMethod.GET })
	public void convertHtmlToPdf(@RequestParam("html") String html, HttpServletRequest req, HttpServletResponse resp) {
		logger.debug("This will convert html and css request params to pdf in http servlet response.");
		try {

			resp.setContentType("application/pdf");
			resp.setHeader("Content-Disposition", "attachment;filename=document.pdf");
			convertFromHtml(html, req.getParameter("css"), req.getParameter("json"), resp.getOutputStream());

		} catch (Throwable t) {
			logger.error(t.getMessage(), t);
		}
	}

	@RequestMapping(value = "params/byte", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public byte[] convertHtmlToPdf(@RequestParam("html") String html, HttpServletRequest req) {
		logger.debug("This will convert html and css request params to pdf and return as byte array.");
		try {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			convertFromHtml(html, req.getParameter("css"), req.getParameter("json"), bos);

			return bos.toByteArray();
		} catch (Throwable t) {
			logger.error(t.getMessage(), t);
		}

		return null;
	}

	/* Sample Object
{
"html":"&lt;p&gt;&lt;img alt&#x3D;&quot;&quot; src&#x3D;&quot;https:&#x2F;&#x2F;www.google.com&#x2F;images&#x2F;branding&#x2F;googlelogo&#x2F;2x&#x2F;googlelogo_color_272x92dp.png&quot; style&#x3D;&quot;width:100%;&quot; &#x2F;&gt; &lt;h1&gt;January Announcement&lt;&#x2F;h1&gt;&lt;&#x2F;p&gt;&lt;p&gt;Good morning everyone, just a gentle reminder that we lost everything just like every January.&lt;br&#x2F;&gt; And now the CEO is going crazy. &lt;&#x2F;p&gt;&lt;p&gt;&lt;strong&gt;Quotes all around (Gibrish): &lt;&#x2F;strong&gt;&lt;&#x2F;p&gt;&lt;p&gt;&lt;strong&gt;Nina Myers:&amp;nbsp;&lt;&#x2F;strong&gt;He is gonna put a bullet in my head before I can say hello, and then he&#39;ll turn the gun on himself. Don&amp;#39;t change any audit numbers even in decimals. Every thing must be accounted for. Not a single dime should be out of place. All sale records &lt;h3&gt;must&lt;&#x2F;h3&gt; be closed by 26th Dec.&lt;&#x2F;p&gt;&lt;p&gt;Sarah Conor: Watching John with the machine, it was suddenly so clear. The terminator, would never stop. It would never leave him, and it would never hurt him, never shout at him, or get drunk and hit him, or say it was too busy to spend time with him. It would always be there. And it would die, to protect him. Of all the would-be fathers who came and went over the years, this thing, this machine, was the only one who measured up. In an insane world, it was the sanest choice. &lt;&#x2F;p&gt;&lt;p&gt;&lt;strong&gt;George Mason:&amp;nbsp;&lt;&#x2F;strong&gt;Believe it or not, I used to want to be a teacher. A long time ago. You know why I didn&#39;t? DOD offered me more money. That&#39;s how I made my decision. So I made myself miserable. And I made everybody else around me miserable. For an extra five thousand dollars a year. That was my price. &lt;&#x2F;p&gt;&lt;p&gt;Tom, don&#39;t let anybody kid you. It&#39;s all personal, every bit of business. Every piece of shit every man has to eat every day of his life is personal. They call it business. OK. But it&#39;s personal as hell. You know where I learned that from? The Don. My old man. The Godfather. If a bolt of lightning hit a friend of his the old man would take it personal. He took my going into the Marines personal. That&#39;s what makes him great. The Great Don. He takes everything personal Like God. He knows every feather that falls from the tail of a sparrow or however the hell it goes? Right? And you know something? Accidents don&#39;t happen to people who take accidents as a personal insult..&lt;&#x2F;p&gt;&lt;p&gt;Never let anyone know what you are thinking.&lt;&#x2F;p&gt;&lt;p&gt;Thank you,&lt;br&#x2F;&gt;&lt;&#x2F;p&gt;&lt;p&gt;AllCast&amp;nbsp;&lt;&#x2F;p&gt;"
}
	 */
	
	@RequestMapping(value = "body", method = RequestMethod.POST)
	public void convertHtmlToPdf(@RequestBody PdfRequest reqBody, HttpServletResponse resp) {
		logger.debug("This will convert html and css request body to pdf in http servlet response.");
		try {
			resp.setContentType("application/pdf");
			resp.setHeader("Content-Disposition", "attachment;filename=document.pdf");
			convertFromHtml(reqBody.getHtml(), reqBody.getCss(), reqBody.getJson(), resp.getOutputStream());

		} catch (Throwable t) {
			logger.error(t.getMessage(), t);
		}
	}

	
	@RequestMapping(value = "body/byte", method = RequestMethod.POST)
	@ResponseBody
	public byte[] convertHtmlToPdf(@RequestBody PdfRequest reqBody) {
		logger.debug("This will convert html and css request params to pdf and return as byte array.");
		try {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			convertFromHtml(reqBody.getHtml(), reqBody.getCss(), reqBody.getJson(), bos);

			return bos.toByteArray();
		} catch (Throwable t) {
			logger.error(t.getMessage(), t);
		}

		return null;
	}

}
