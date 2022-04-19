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
package example.service;

import org.apache.commons.lang3.StringUtils;

public abstract class BaseImpl {

	protected final String JSON_OBJECT_ARRAY_REPEAT_TAG_WILDCARD = "\\*";

	protected String getFormedHTMLWithCSS(String htmlBody, String css) {
		StringBuffer sb = new StringBuffer();
		sb.append("<html>");
		sb.append("<head>");
		if (StringUtils.isNotBlank(css)) {
			sb.append("<style type='text/css'>");
			sb.append(css);
			sb.append("</style>");
		}
		sb.append("</head>");
		sb.append("<body>");
		sb.append(htmlBody);
		sb.append("</body>");
		sb.append("</html>");
		return sb.toString();
	}

	protected String correctHtml(String html) {
		html = html.replaceAll("&nbsp;", "&#160;");
//		html = html.replaceAll(" & ", " &amp; ");
		html = html.replaceAll("(&\\w*)(?!&.*;) ", "&amp; ");// Replace &<space> with &amp;

		return html;
	}

}
