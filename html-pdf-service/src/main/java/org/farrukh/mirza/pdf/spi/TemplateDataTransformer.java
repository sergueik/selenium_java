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
package org.farrukh.mirza.pdf.spi;

import java.util.List;

public interface TemplateDataTransformer {

	/**
	 * Checks whether JSON passed in contains a single object or an Array of objects, which will return in multiple PDFs stitched together.
	 * @param json
	 * @return boolean
	 */
	public boolean isJsonArray(String json);
	
	/**
	 * Transforms HTML Template containing variables with jsonObject
	 * HTML Template can contain variables in the format <code>{variable.name}</code>
	 * jsonObject should contain the key as variable.name with data value, e.g., 
	 * 		HTML Template: <h1>Hello {name}</h1>
	 * 		Json Object: {"name":"Farrukh Mirza", "signature":"Jack Bauer"} 
	 * @param htmlTemplate
	 * @param jsonObject - contains a single object
	 * @return htmlTemplate with variables replaced by jsonData
	 */
	public String transformHTMLTemplate(String htmlTemplate, String jsonObject);
	
	
	/**
	 * Transforms HTML Template containing variables with jsonData
	 * HTML Template can contain variables in the format <code>{variable.name}</code>
	 * Each element of jsonData should contain the key as variable.name with data value, e.g., 
	 * 		HTML Template: <h1>Hello {name}</h1>
	 * 		Json Data: [{"name":"Farrukh Mirza", "signature":"Jack Bauer"}, {"name":"John Doe", "signature":"Jack Bauer"}, {"name":"Richard Roe", "signature":"Jack Bauer"}] 
	 * @param htmlTemplate
	 * @param jsonData - contains a list of json objects
	 * @return List of htmlTemplates with variables replaced by jsonData
	 */
	public List<String> transformHTMLTemplates(String htmlTemplate, String jsonData);
	
	
	public String getFormedHTML(String htmlBody, String css);
}
