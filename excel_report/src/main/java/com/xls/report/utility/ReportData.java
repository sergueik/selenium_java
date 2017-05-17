package com.xls.report.utility;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.xls.report.config.Configuration;
import com.xls.report.config.ExcelConfiguration;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.xls.report.config.ElementLocator;

/**
 * @author - rahul.rathore
 */
public class ReportData {

	private static NodeList list;
	private static NodeList classNodeList;
	private static NodeList methodNodeList;
	private static NodeList exceptionNodeList;
	private static NodeList exceptionTraceNodeList;
	private static String classNodeName;
	private static String testMethodName;
	private static String testMethodStatus;
	private static String expMessage = "";
	private static String exceptionTrace = "";
	private static String sheetName = "";
	private static Map<String, Map<String, ArrayList<String>>> testMethodDataMap = null;

	private static DocumentBuilderFactory fact;
	private static DocumentBuilder build;

	public static Document getDocument(String xmlFile)
			throws SAXException, IOException, ParserConfigurationException {
		fact = DocumentBuilderFactory.newInstance();
		build = fact.newDocumentBuilder();
		return build.parse(xmlFile);
	}

	public static Map<String, Map<String, ArrayList<String>>> getTestMethodDetail(
			String xmlFile)
			throws SAXException, IOException, ParserConfigurationException {
		testMethodDataMap = new HashMap<>();
		Map<String, ArrayList<String>> testDetailMap = null;
		ArrayList<String> testMethodDataList = null;
		Document doc = getDocument(xmlFile);
		doc.getDocumentElement().normalize();
		list = getNodeList(doc, Configuration.testNode); // for getting
																											// test nodes

		for (int i = 0; i < list.getLength(); i++) {
			testDetailMap = new HashMap<>();
			classNodeList = getEleListByTagName(list.item(i),
					Configuration.classNode);
			sheetName = getNameAttribute(list.item(i), Configuration.nameAttribute);

			for (int j = 0; j < classNodeList.getLength(); j++) {
				classNodeName = getNameAttribute(classNodeList.item(j),
						Configuration.nameAttribute);
				methodNodeList = getEleListByTagName(classNodeList.item(j),
						Configuration.testMethodNode);

				for (int k = 0; k < methodNodeList.getLength(); k++) {
					testMethodDataList = new ArrayList<>();
					testMethodName = getNameAttribute(methodNodeList.item(k),
							Configuration.nameAttribute);
					testMethodStatus = getNameAttribute(methodNodeList.item(k),
							Configuration.statusAttribute);

					if (isDataProviderPresent(methodNodeList.item(k),
							Configuration.dataProviderAttribute)) {
						String name = "";
						for (int m = 0; m < getEleListByTagName(methodNodeList.item(k),
								Configuration.valueAttribute).getLength(); m++) {
							name = name + ","
									+ getEleListByTagName(methodNodeList.item(k),
											Configuration.valueAttribute).item(m).getTextContent()
													.trim();
						}
						testMethodName = testMethodName + "("
								+ name.substring(1, name.length()) + ")";
					}
					testMethodDataList.add(Configuration.testNameIndex,
							classNodeName + "." + testMethodName);
					testMethodDataList.add(Configuration.testStatusIndex,
							testMethodStatus);

					if ("fail".equalsIgnoreCase(testMethodStatus)) {
						exceptionNodeList = getEleListByTagName(methodNodeList.item(k),
								Configuration.exceptionNode);
						exceptionTraceNodeList = getEleListByTagName(methodNodeList.item(k),
								Configuration.messageAttribute);
						exceptionTrace = exceptionTraceNodeList
								.item(Configuration.firstIndex).getTextContent();
						expMessage = getNameAttribute(
								exceptionNodeList.item(Configuration.firstIndex),
								Configuration.classNode);
						testMethodDataList.add(Configuration.exceptionMsgIndex, expMessage);
						testMethodDataList.add(Configuration.exceptionStackTrace,
								ExcelConfiguration.transformExpMessage(exceptionTrace).trim());
					} else {
						testMethodDataList.add(Configuration.exceptionMsgIndex, " ");
						testMethodDataList.add(Configuration.exceptionStackTrace, " ");
					}
					testDetailMap.put(testMethodDataList.get(Configuration.firstIndex),
							testMethodDataList);
				}
			}
			testMethodDataMap.put(sheetName, testDetailMap);
		}
		return testMethodDataMap;
	}

	// @SuppressWarnings("deprecation")
	public static XSSFWorkbook createExcelFile(
			HashMap<String, Map<String, ArrayList<String>>> data) {
		XSSFWorkbook book = new XSSFWorkbook();
		XSSFCellStyle failCelStyle = book.createCellStyle();
		XSSFCellStyle passCelStyle = book.createCellStyle();

		// http://stackoverflow.com/questions/19145628/auto-size-height-for-rows-in-apache-poi
		for (String sheetNameKey : data.keySet()) {
			XSSFSheet sheet = book.createSheet(sheetNameKey);
			XSSFRow row = ExcelConfiguration.CreateHeader(book, sheet,
					Configuration.header);
			HashMap<String, ArrayList<String>> testMethods = (HashMap<String, ArrayList<String>>) data
					.get(sheetNameKey);
			int l = 1;

			for (String testMethod : testMethods.keySet()) {
				passCelStyle.setFillForegroundColor(HSSFColor.BRIGHT_GREEN.index);
				failCelStyle.setFillForegroundColor(HSSFColor.RED.index);
				passCelStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
				failCelStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
				row = sheet.createRow(l++);
				XSSFCell cellName = row.createCell(Configuration.testNameIndex);
				cellName.setCellValue(testMethod);
				sheet.autoSizeColumn(Configuration.testNameIndex);
				ArrayList<String> testData = testMethods.get(testMethod);
				XSSFCell cellStatus = row.createCell(Configuration.testStatusIndex);

				if ("fail"
						.equalsIgnoreCase(testData.get(Configuration.testStatusIndex))) {
					cellStatus.setCellStyle(failCelStyle);
					cellStatus.setCellValue(testData.get(Configuration.testStatusIndex));
					XSSFCell expCell = row.createCell(Configuration.exceptionMsgIndex);
					expCell.setCellValue(testData.get(Configuration.exceptionMsgIndex));
					sheet.autoSizeColumn(Configuration.exceptionMsgIndex);
					XSSFCell exceptionTraceCell = row
							.createCell(Configuration.exceptionStackTrace);
					exceptionTraceCell.setCellValue(
							testData.get(Configuration.exceptionStackTrace).trim());
					sheet.autoSizeColumn(Configuration.exceptionStackTrace);
					XSSFCell locatorCell = row.createCell(Configuration.locatorIndex);
					String text = testData.get(Configuration.exceptionStackTrace).trim();
					String jsonString = null;
					Gson gson = new GsonBuilder().create();
					int sIndex = text.indexOf('{');
					int eIndex = text.indexOf('}');
					jsonString = (sIndex == -1 || eIndex == -1) ? ""
							: text.substring(sIndex, (eIndex + 1));
					ElementLocator locator = gson.fromJson(jsonString,
							ElementLocator.class);
					locatorCell.setCellValue((locator == null) ? "" : locator.toString());
				} else {
					cellStatus.setCellStyle(passCelStyle);
					cellStatus.setCellValue(testData.get(Configuration.testStatusIndex));
					sheet.autoSizeColumn(Configuration.testStatusIndex);
					XSSFCell expCell = row.createCell(Configuration.exceptionMsgIndex);
					expCell.setCellValue(testData.get(Configuration.exceptionMsgIndex));
					sheet.autoSizeColumn(Configuration.exceptionMsgIndex);
					XSSFCell exceptionTraceCell = row
							.createCell(Configuration.exceptionStackTrace);
					exceptionTraceCell.setCellValue(
							testData.get(Configuration.exceptionStackTrace).trim());
					sheet.autoSizeColumn(Configuration.exceptionStackTrace);
				}
			}
		}
		return book;
	}

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
