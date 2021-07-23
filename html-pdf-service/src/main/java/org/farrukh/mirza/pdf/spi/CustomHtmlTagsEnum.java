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

package org.farrukh.mirza.pdf.spi;

/**
 * @author Farrukh Mirza
 * @date 27/09/2018 Dublin, Ireland
 */
public enum CustomHtmlTagsEnum {
	REPEAT("<repeat>", "</repeat>"), LOOP("<loop>", "</loop>");

	private String start;
	private String end;

	private CustomHtmlTagsEnum(String start, String end) {
		this.start = start;
		this.end = end;
	}

	public String start() {
		return start;
	}

	public String end() {
		return end;
	}

	public boolean isStartPresent(String html) {
		return html.toLowerCase().indexOf(start) >= 0;
	}

	public boolean isEndPresent(String html) {
		return html.toLowerCase().indexOf(end) >= 0;
	}

	public boolean isPresent(String html) {
		return isStartPresent(html) || isEndPresent(html);
	}

	/*
	 * Both start and end tags are present end tag appears after the start tag
	 * finishes tag may not have any child tags
	 */
	public boolean isPresentAndValid(String html) {
		return isStartPresent(html) && isEndPresent(html)
				&& (html.toLowerCase().indexOf(end) >= (html.toLowerCase().indexOf(start) + start.length()));
	}

	/*
	 * Return everything between start and end
	 */
	public String getInnerHtml(String html) {
		return html.substring((html.toLowerCase().indexOf(start) + start.length()), html.toLowerCase().indexOf(end));
	}

	/*
	 * Return everything between start and end, including the tags
	 */
	public String getTagWithInnerHtmlSubstring(String html) {
		return html.substring(html.toLowerCase().indexOf(start), (html.toLowerCase().indexOf(end) + end.length()));
	}
	
	/*
	 * Replace everything in <param>html</param> between start and end, including the tags, with the replacement String provided
	 */
	public String replaceTagWithInnerHtmlByReplacement(String html, String replacement) {
		return html.substring(0, html.toLowerCase().indexOf(start)) + replacement + html.substring((html.toLowerCase().indexOf(end) + end.length()));
	}
}
