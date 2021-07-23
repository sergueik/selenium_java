package org.farrukh.mirza.pdf.rest;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

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
@RequestMapping("service/template/html")
public class HtmlTemplateResource {
	private static final Logger logger = LoggerFactory.getLogger(HtmlTemplateResource.class);

	@Autowired
	protected Converter converter;

	@Autowired
	protected TemplateDataTransformer templateDataTransformer;

	private List<String> convertFromTemplateToHtml(String html, String css, String json) {
		try {

			html = StringEscapeUtils.unescapeHtml4(html);
			css = StringUtils.isBlank(css) ? "" : StringEscapeUtils.unescapeHtml4(css);
			json = StringUtils.isBlank(json) ? "" : StringEscapeUtils.unescapeHtml4(json);
			logger.debug("HTML: " + html);
			logger.debug("CSS: " + css);
			logger.debug("JSON: " + json);

			List<String> htmls = new ArrayList<>();
			List<String> formedHtmls = new ArrayList<>();

			if (StringUtils.isNotBlank(json)) {
				if (templateDataTransformer.isJsonArray(json)) {
					htmls = templateDataTransformer.transformHTMLTemplates(html, json);
				} else {
					htmls.add(templateDataTransformer.transformHTMLTemplate(html, json));
				}
			}

			// getFormedHTML() performs null check on css by default, so no
			// need to do it here.
			for(String h: htmls){
				formedHtmls.add(templateDataTransformer.getFormedHTML(h, css));
			}
			
			return formedHtmls;
		} catch (Throwable t) {
			logger.error(t.getMessage(), t);
		}

		return null;
	}

	private void convertFromTemplateToHtml(String html, String css) {
		convertFromTemplateToHtml(html, css, null);
	}

	private void convertFromTemplateToHtml(String html) {
		convertFromTemplateToHtml(html, null, null);
	}


	@RequestMapping(value = "params", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public List<String> convertHtmlToPdf(@RequestParam("html") String html, HttpServletRequest req) {
		logger.debug("This will convert html template and css request params to html and return as List of String.");
		try {
			return convertFromTemplateToHtml(html, req.getParameter("css"), req.getParameter("json"));
		} catch (Throwable t) {
			logger.error(t.getMessage(), t);
		}

		return null;
	}

	@RequestMapping(value = "body", method = RequestMethod.POST)
	@ResponseBody
	public List<String> convertHtmlToPdf(@RequestBody PdfRequest reqBody) {
		logger.debug("This will convert html template and css request params to html and return as List of String.");
		try {
			return convertFromTemplateToHtml(reqBody.getHtml(), reqBody.getCss(), reqBody.getJson());
		} catch (Throwable t) {
			logger.error(t.getMessage(), t);
		}

		return null;
	}

}
