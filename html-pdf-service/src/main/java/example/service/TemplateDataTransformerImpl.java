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
 * Repeat view implementation on 27/09/2018
 * Dublin, Ireland
 */
package example.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.jayway.jsonpath.JsonPath;

import example.spi.CustomHtmlTagsEnum;
import example.spi.TemplateDataTransformer;

@Service
public class TemplateDataTransformerImpl extends BaseImpl implements TemplateDataTransformer {
	/*
	 * https://github.com/json-path/JsonPath
	 */
	private final Logger logger = LoggerFactory.getLogger(TemplateDataTransformerImpl.class);

//	@Deprecated
//	private ObjectMapper mapper = new ObjectMapper();

	@Override
	public String getFormedHTML(String htmlBody, String css) {
		htmlBody = correctHtml(htmlBody);
		htmlBody = getFormedHTMLWithCSS(htmlBody, css);
		return htmlBody;
	}

	@Override
	public boolean isJsonArray(String json) {
		if (StringUtils.isNoneBlank(json) && StringUtils.isNoneBlank(json.trim()) && json.trim().startsWith("[")
				&& json.trim().endsWith("]")) {
			return true;
		}
		return false;
	}

	@Override
	public String transformHTMLTemplate(String htmlTemplate, String jsonObject) {
		try {
			// Replace &<space> with &amp;
			jsonObject = jsonObject.replaceAll("(&\\w*)(?!&.*;) ", "&amp; ");

			logger.info("Json Obj: " + jsonObject);

			////// REPEAT TAG CODE - START /////
			String htmlWithoutRepeatInput = new String(htmlTemplate);
			String htmlWithoutRepeatOutput = new String(htmlTemplate);
			// Replace all Repeat tags with repeated html
			// While loop will stop when all repeat tags are replaced and
			while (!(htmlWithoutRepeatOutput = transformRepeatTagInTemplate(new String(htmlWithoutRepeatInput), "$.",
					jsonObject)).equalsIgnoreCase(htmlWithoutRepeatInput)) {
				htmlWithoutRepeatInput = new String(htmlWithoutRepeatOutput);
			}

			htmlTemplate = htmlWithoutRepeatOutput;
			////// REPEAT TAG CODE - END /////

			List<String> keys = getUniqueKeysFromTemplate(htmlTemplate);
			Map<String, String> keyVals = getValuesFromJson(keys, jsonObject);

			keys.stream().forEach(k -> logger.trace("Key: " + k));
			keyVals.entrySet().stream()
					.forEach(entry -> logger.trace("Key: " + entry.getKey() + ", Val: " + entry.getValue()));

//			logger.debug(keyVals.get("signatures[0].name"));

			return transformTemplate(htmlTemplate, keyVals);

//			return transformTemplate(htmlTemplate, jsonObject);
		} catch (Throwable e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
		}
		return htmlTemplate;
	}

	@Override
	public List<String> transformHTMLTemplates(String htmlTemplate, String jsonData) {
		try {
			// Replace &<space> with &amp;
			jsonData = jsonData.replaceAll("(&\\w*)(?!&.*;) ", "&amp; ");

			List<String> html = new ArrayList<>();

			int arrayLength = Integer.parseInt(JsonPath.read(jsonData, "$.length()").toString());
			for (int i = 0; i < arrayLength; i++) {
				String htmlTemplatePerObject = new String(htmlTemplate); 
				////// REPEAT TAG CODE - START /////
				String htmlWithoutRepeatInput = new String(htmlTemplatePerObject);
				String htmlWithoutRepeatOutput = new String(htmlTemplatePerObject);
				// Replace all Repeat tags with repeated html
				// While loop will stop when all repeat tags are replaced and
				while (!(htmlWithoutRepeatOutput = transformRepeatTagInTemplate(new String(htmlWithoutRepeatInput), "$.[" + i + "].",
						jsonData)).equalsIgnoreCase(htmlWithoutRepeatInput)) {
					htmlWithoutRepeatInput = new String(htmlWithoutRepeatOutput);
				}

				htmlTemplatePerObject = htmlWithoutRepeatOutput;
				////// REPEAT TAG CODE - END /////

				
				//This can't be outside loop because repeat depends on the Json Object
				//Thus keys can be separate for each json object in the main json array.
				List<String> keys = getUniqueKeysFromTemplate(htmlTemplatePerObject);
				Map<String, String> keyVals = getValuesFromJson(keys, "$.[" + i + "].", jsonData);
				String template = new String(htmlTemplatePerObject);
				template = transformTemplate(template, keyVals);

//				for (String k : keys) {
//					try {
//						String val = JsonPath.read(jsonData, "$.[" + i + "]." + k);
//						if (StringUtils.isBlank(val) || "null".equalsIgnoreCase(val)) {
//							val = "";
//						}
//
//						template = template.replaceAll("\\{" + k + "\\}", val);
//
//						// The following changes were done by James Cummins in
//						// the
//						// hope that spaces will be allowed in the keys.
//						// However, that did not work.
//						// In addition, because k was placed in brackets, so the
//						// service is unable to parse nested objects.
//						// Therefore, these changes are being reverted and some
//						// other solution will be looked into the future.
//
//						// #1 Value type prevents ClassCastException
//						// #2 k needs to be wrapped in ['k'] in case k has
//						// spaces
//						// Object val = JsonPath.read(jsonData,
//						// "$.["+i+"].['"+k+"']");
//
//						// Perform toString() on Object val.
//						// template = template.replaceAll("\\{" + k + "\\}",
//						// val!=null?val.toString():"");
//					} catch (Throwable t) {
//						logger.warn("Ignoring Exception while reading key value for " + k + ": " + t.getMessage());
//					}
//				}

				html.add(template);
			}

			return html;
		} catch (Throwable e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
		}
		return new ArrayList<>();
	}

	private String transformTemplate(String template, Map<String, String> keyVals) {
		logger.info("Template: " + template);
		for (Entry<String, String> e : keyVals.entrySet()) {
			if (StringUtils.isBlank(e.getValue()) || "null".equalsIgnoreCase(e.getValue())) {
				e.setValue("");
			}
			//Find [ and ] characters in the keys and replace them with \[ and \] in the keys
			String key = e.getKey();
			key = key.contains("[")? key.replaceAll("\\[", "\\\\["): key;
			key = key.contains("]")? key.replaceAll("\\]", "\\\\]"): key;
			logger.trace("New key: " + key);

			//Then use the formed key to replace values in the text
			template = template.replaceAll("\\{" + key + "\\}", e.getValue());
//			template = template.replaceAll("\\{" + e.getKey() + "\\}", e.getValue());
		}

		logger.info("Template Result: " + template);
		return template;
	}

	private String transformRepeatTagInTemplate(String template, String jsonSelector, String json) {
		if (CustomHtmlTagsEnum.REPEAT.isPresentAndValid(template)) {
			logger.debug("JSON: " + json);
			final String innerHtml = CustomHtmlTagsEnum.REPEAT.getInnerHtml(template);
			logger.debug("Inner HTML: " + innerHtml);
			// Get all unique keys from the innerHTML of the <repeat>...</repeat> tag
			// The resulting keys in the list would be like below as an example:
			// 1. parent.child[*].name.first
			// 2. parent.child[*].name.last
			// 3. parent.child[*].dob
			// 4. parent.child[*].address
			List<String> keys = getUniqueKeysFromTemplate(innerHtml);
			// Now get the array part(s) separated and unique, e.g., the list from above
			// example will result in a hash set of a single element, i.e.,
			// --> parent.child
			HashSet<String> keyArrayElement = new HashSet<>();
			for (String k : keys) {
				keyArrayElement.add(k.substring(0, k.indexOf("[")));
			}
			// Next find among all arrays referenced under the repeat tag, which one has the
			// most elements.
			// Assumption: If multiple arrays are referenced, then all are assumed to be of
			// same size within the <repeat> tag
			// Assumption: Only a single level of <repeat> is considered. Nested <repeat>
			// tags are not allowed
			int maxArraySize = 0;
			for (String k : keyArrayElement) {
				logger.debug("Fetching size of array " + jsonSelector + k + ".length()");
				int arraySize = Integer.parseInt(JsonPath.read(json, jsonSelector + k + ".length()").toString());
				maxArraySize = arraySize > maxArraySize ? arraySize : maxArraySize;
			}
			logger.debug("Maximum number of array elements " + maxArraySize);

			// Now repeat the inner HTML with indexed keys instead of the template key
			// parent.child[*].name.first will be replaced by parent.child[0].name.first,
			// then parent.child[1].name.first, ...
			String indexedInnerHTML = "";
			for (int i = 0; i < maxArraySize; i++) {
				String indexedInnerHTMLRow = new String(innerHtml);
				for (String k : keyArrayElement) {
					logger.debug("Original Search: " + k + "[" + JSON_OBJECT_ARRAY_REPEAT_TAG_WILDCARD + "]");
					logger.debug("Expected to be replaced with: " + k + "[" + i + "]");
					indexedInnerHTMLRow = indexedInnerHTMLRow.replaceAll(
							k + "\\[" + JSON_OBJECT_ARRAY_REPEAT_TAG_WILDCARD + "\\]", k + "\\[" + i + "\\]");

					logger.debug("Wildcard converted row " + i + ": " + indexedInnerHTMLRow);
				}
				indexedInnerHTML += indexedInnerHTMLRow;
			}

			logger.debug(indexedInnerHTML);
			// Now replace the original template text containing REPEAT tag with the actual
			// repetition of indexed HTML
			// Original: <repeat>{parent.child[*].name.first}
			// {parent.child[*].name.last}</repeat>
			// Result: {parent.child[0].name.first}
			// {parent.child[0].name.last}{parent.child[1].name.first}
			// {parent.child[1].name.last}
//			template = template.replaceFirst(CustomHtmlTagsEnum.REPEAT.getTagWithInnerHtmlSubstring(template),
//					indexedInnerHTML);
			template = CustomHtmlTagsEnum.REPEAT.replaceTagWithInnerHtmlByReplacement(template, indexedInnerHTML);
		}
		return template;
	}

	private Map<String, String> getValuesFromJson(List<String> keys, String json) {
		return getValuesFromJson(keys, "$.", json);
	}

	private Map<String, String> getValuesFromJson(List<String> keys, String jsonSelector, String json) {
		Map<String, String> map = new HashMap<>();

		for (String k : keys) {
			String val = "";
			try {
				val = val + JsonPath.read(new String(json), jsonSelector + k);
			} catch (Throwable t) {
				// val = "N/A";
				logger.error("Exception while reading key value for " + k + ": " + t.getMessage());
			}
			// if(!StringUtils.isBlank(val)){
			map.put(k, val);
			// }
		}

		return map;
	}

	private List<String> getUniqueKeysFromTemplate(String template) {
		List<String> keys = new ArrayList<>();
		Pattern p = Pattern.compile("\\{.*?\\}");
		Matcher m = p.matcher(template);
		while (m.find()) {
			String k = m.group().subSequence(1, m.group().length() - 1).toString();
			if (!keys.contains(k))
				keys.add(k);
		}

		return keys;
	}

	////// OLD METHODS - START /////////
	@Deprecated
	private String getHtmlFromTemplateAndData(String htmlTemplate, Map<String, Object> data) {
		logger.debug("Json Object contains " + data.entrySet().size() + " properties.");
		for (Entry<String, Object> e : data.entrySet()) {
			// logger.debug(e.getKey() + ":" + e.getValue());
			htmlTemplate = htmlTemplate.replaceAll("\\{" + e.getKey() + "\\}", (String) e.getValue());
			// logger.debug("HTML Template: " + htmlTemplate);
		}

		logger.debug("Final HTML Template: " + htmlTemplate);

		return htmlTemplate;
	}

	@Deprecated
	private String transformTemplate(String template, String json) {
		logger.info("Json Obj: " + json);

		List<String> keys = getUniqueKeysFromTemplate(template);
		Map<String, String> keyVals = getValuesFromJson(keys, json);

		return transformTemplate(template, keyVals);
	}
	////// OLD METHODS - END /////////

}
