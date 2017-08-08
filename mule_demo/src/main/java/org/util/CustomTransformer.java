package org.util;

import java.util.Iterator;

import org.json.*;

import org.mule.api.transformer.TransformerException;
import org.mule.transformer.AbstractTransformer;

public class CustomTransformer extends AbstractTransformer {

	@Override
	protected Object doTransform(Object src, String enc) throws TransformerException {

		System.err.println(String.format("{%s}", src.toString()));

		/*
		 * JSONObject resultObj = new JSONObject(String.format("{%s}",
		 * src.toString())); Iterator<String> masterServerIterator =
		 * resultObj.keys(); while (masterServerIterator.hasNext()) { String
		 * masterServer = masterServerIterator.next(); JSONArray resultDataArray
		 * = resultObj.getJSONArray(masterServer); for (int cnt = 0; cnt <
		 * resultDataArray.length(); cnt++) { System.err.println(masterServer +
		 * " " + resultDataArray.get(cnt)); } }
		 */
		return src.toString().replace("World", "");
	}

}
