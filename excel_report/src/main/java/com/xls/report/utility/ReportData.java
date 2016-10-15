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

import com.xls.report.config.Configuration;
import com.xls.report.config.ExcelConfiguration;

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
	
	public static Map<String, Map<String, ArrayList<String>>> getTestMethodDetail(String xmlFile) throws SAXException, IOException, ParserConfigurationException {
		testMethodDataMap = new HashMap<String, Map<String, ArrayList<String>>>();
		Map<String,ArrayList<String>> testDetailMap = null;
		ArrayList<String> testMethodDataList = null;
		Document doc = DocBuilder.getDocument(xmlFile);
		doc.getDocumentElement().normalize();
		list = NodeFactory.getNodeList(doc,Configuration.aTestNode); // for getting test nodes

		for (int i = 0; i < list.getLength(); i++) {
			testDetailMap = new HashMap<String,ArrayList<String>>();
			classNodeList = NodeFactory.getEleListByTagName(list.item(i), Configuration.aClassNode);
			sheetName = NodeFactory.getNameAttribute(list.item(i), Configuration.aNameAttribute);
			
			for (int j = 0; j < classNodeList.getLength(); j++) {
				classNodeName = NodeFactory.getNameAttribute(classNodeList.item(j), Configuration.aNameAttribute);
				methodNodeList = NodeFactory.getEleListByTagName(classNodeList.item(j), Configuration.aTestMethodNode); 

				for (int k = 0; k < methodNodeList.getLength(); k++) {
					testMethodDataList = new ArrayList<String>();
					testMethodName = NodeFactory.getNameAttribute(methodNodeList.item(k), Configuration.aNameAttribute);
					testMethodStatus = NodeFactory.getNameAttribute(methodNodeList.item(k),Configuration.aStatusAttribute);
					
					if(NodeFactory.isDataProviderPresent(methodNodeList.item(k), Configuration.aDataProviderAttribute)){
						String name = "";
						for(int m = 0; m < NodeFactory.getEleListByTagName(methodNodeList.item(k), Configuration.aValueAttribute).getLength(); m++){
							name = name + "," + NodeFactory.getEleListByTagName(methodNodeList.item(k), Configuration.aValueAttribute).item(m).getTextContent().trim();
						}
						testMethodName = testMethodName + "(" + name.substring(1, name.length()) + ")";
					}
					testMethodDataList.add(Configuration.aTestNameIndex, classNodeName + "." + testMethodName);
					testMethodDataList.add(Configuration.aTestStatusIndex, testMethodStatus);
					
					if ("fail".equalsIgnoreCase(testMethodStatus)) {
						exceptionNodeList = NodeFactory.getEleListByTagName(methodNodeList.item(k), Configuration.aExceptionNode);
						exceptionTraceNodeList = NodeFactory.getEleListByTagName(methodNodeList.item(k), Configuration.aMessageAttribute);
						exceptionTrace = exceptionTraceNodeList.item(Configuration.aFirstIndex).getTextContent();
						expMessage = NodeFactory.getNameAttribute(exceptionNodeList.item(Configuration.aFirstIndex), Configuration.aClassNode);
						testMethodDataList.add(Configuration.aExceptionMsgIndex, expMessage);
						testMethodDataList.add(Configuration.aExceptionStackTrace, ExcelConfiguration.transformExpMessage(exceptionTrace).trim());
					}
					else{
						testMethodDataList.add(Configuration.aExceptionMsgIndex, " ");
						testMethodDataList.add(Configuration.aExceptionStackTrace, " ");
					}
					testDetailMap.put(testMethodDataList.get(Configuration.aFirstIndex),testMethodDataList);
				}
			}
			testMethodDataMap.put(sheetName, testDetailMap);
		}
		return testMethodDataMap;
	}
	
	public static XSSFWorkbook createExcelFile(HashMap<String, Map<String, ArrayList<String>>> data) {
		XSSFWorkbook book = new XSSFWorkbook();
		XSSFCellStyle failCelStyle = book.createCellStyle();
		XSSFCellStyle passCelStyle = book.createCellStyle();
		
		for(String sheetNameKey : data.keySet()){
			XSSFSheet sheet = book.createSheet(sheetNameKey);
			XSSFRow row = ExcelConfiguration.CreateHeader(book,sheet,Configuration.aHeader);
			HashMap<String, ArrayList<String>> testMethods = (HashMap<String, ArrayList<String>>) data.get(sheetNameKey);
			int l = 1;
			
			for(String testMethod : testMethods.keySet()){
				passCelStyle.setFillForegroundColor(HSSFColor.BRIGHT_GREEN.index);
				failCelStyle.setFillForegroundColor(HSSFColor.RED.index);
				passCelStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
				failCelStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
				row = sheet.createRow(l++);
				XSSFCell cellName = row.createCell(Configuration.aTestNameIndex);
				cellName.setCellValue(testMethod);
				ArrayList<String> testData = testMethods.get(testMethod);
				XSSFCell cellStatus = row.createCell(Configuration.aTestStatusIndex);
				
				if ("fail".equalsIgnoreCase(testData.get(Configuration.aTestStatusIndex))) {
					cellStatus.setCellStyle(failCelStyle);
					cellStatus.setCellValue(testData.get(Configuration.aTestStatusIndex));
					XSSFCell expCell = row.createCell(Configuration.aExceptionMsgIndex);
					expCell.setCellValue(testData.get(Configuration.aExceptionMsgIndex));
					XSSFCell exceptionTraceCell = row.createCell(Configuration.aExceptionStackTrace);
					exceptionTraceCell.setCellValue(testData.get(Configuration.aExceptionStackTrace).trim());
					XSSFCell locatorCell = row.createCell(Configuration.aLocatorIndex);
					locatorCell.setCellValue(JsonUtility.deserializeJsonObject(testData.get(Configuration.aExceptionStackTrace).trim()));
				} else {
					cellStatus.setCellStyle(passCelStyle);
					cellStatus.setCellValue(testData.get(Configuration.aTestStatusIndex));
					XSSFCell expCell = row.createCell(Configuration.aExceptionMsgIndex);
					expCell.setCellValue(testData.get(Configuration.aExceptionMsgIndex));
					XSSFCell exceptionTraceCell = row.createCell(Configuration.aExceptionStackTrace);
					exceptionTraceCell.setCellValue(testData.get(Configuration.aExceptionStackTrace).trim());
				}
			}
		}
		return book;
	}

}
