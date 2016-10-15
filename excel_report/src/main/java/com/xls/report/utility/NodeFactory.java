package com.xls.report.utility;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * @author - rahul.rathore
 */
public class NodeFactory {

	public static NodeList getNodeList(Document document, String nodeName) {
		return document.getElementsByTagName(nodeName);
	}

	public static String getNameAttribute(Node node, String attribute) {
		return ((Element) node).getAttribute(attribute);
	}

	public static NodeList getEleListByTagName(Node node, String tagName) {
		return ((Element) node).getElementsByTagName(tagName);
	}

	public static boolean isDataProviderPresent(Node node,
			final String dataProvider) {
		return (getNameAttribute(node, "data-provider").length() >= 1);
	}
}
