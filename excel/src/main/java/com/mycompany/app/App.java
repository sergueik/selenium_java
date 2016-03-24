package com.mycompany.app;
// https://gist.github.com/madan712/3912272
// https://poi.apache.org/apidocs/org/apache/poi/xssf/usermodel/XSSFWorkbook.html
import java.util.ArrayList;
import java.util.List;

import java.io.File;
import java.io.InputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

import java.io.StringWriter;
import java.lang.StringBuilder;
import java.lang.RuntimeException;


import java.util.concurrent.TimeUnit;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.Hashtable;


import org.apache.commons.io.FilenameUtils;

import org.apache.commons.io.FileUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.util.Iterator;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

import org.apache.poi.ss.usermodel.Cell;

import org.apache.poi.ss.usermodel.Row;

// OLE2 Office Documents needs HSSF
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

// Office 2007+ XML needs XSSF
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import org.junit.Assert;
import static org.junit.Assert.*;

public class App {
  public static void main(String[] args) {
    try	{
      String filename = "TestConfiguration.xls";
      InputStream ExcelFileToRead = new FileInputStream(filename );
      HSSFWorkbook wb = new HSSFWorkbook(ExcelFileToRead);

      HSSFSheet sheet = wb.getSheetAt(0);
      HSSFRow row;
      HSSFCell cell;

      Iterator rows = sheet.rowIterator();

      while (rows.hasNext()) {
        row = (HSSFRow) rows.next();
        Iterator cells = row.cellIterator();
        while (cells.hasNext()) {
          cell = (HSSFCell) cells.next();
          if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
            System.out.print(cell.getStringCellValue()+"\t");
          } else if(cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
            System.out.print(cell.getNumericCellValue()+"\t");
          }
          else
          {
            // TODO: Boolean, Formula, Errors
          }
        }
        System.out.println();
      }
    } catch (Exception e) {
      e.printStackTrace();
    }

	try {
    String filename = "demo.xlsx"; 
    XSSFWorkbook wb = new XSSFWorkbook(filename );
    XSSFSheet sheet = wb.getSheetAt(0);
    XSSFRow row;
    XSSFCell cell;
    Iterator rows = sheet.rowIterator();
    while (rows.hasNext()) {
      row = (XSSFRow) rows.next();
      Iterator cells = row.cellIterator();
      while (cells.hasNext()) {
        cell = (XSSFCell) cells.next();
        if (cell.getCellType() == XSSFCell.CELL_TYPE_STRING) {
          System.out.print(cell.getStringCellValue() + "(" + cell.getColumnIndex()  + ")\t" );
        } else if(cell.getCellType() == XSSFCell.CELL_TYPE_NUMERIC) {
          System.out.print(cell.getNumericCellValue()+ "(" + cell.getColumnIndex()  + ")\t" );
        } else {
          // TODO: Boolean, Formula, Errors
        }
      }
      System.out.println();
    }
  }  catch (Exception e)  {
    e.printStackTrace();
  }
  parseXSSFWorkbook();
}

  // @DataProvider(parallel = false)
  public static Object[][] parseXSSFWorkbook() {
    
    List<Object[]> data = new ArrayList<Object[]>();
    Object[] dataRow = { };
   
    String filename = "demo.xlsx"; 
    try{

      XSSFWorkbook wb = new XSSFWorkbook(filename );
      XSSFSheet sheet = wb.getSheetAt(0);
      XSSFRow row;
      XSSFCell cell;
      String name = "";
      double count = 0;

      Iterator rows = sheet.rowIterator();  
      // https://poi.apache.org/apidocs/org/apache/poi/xssf/usermodel/XSSFRow.html
      while (rows.hasNext()) {
        row = (XSSFRow) rows.next();
        Iterator cells = row.cellIterator();
        if (row.getRowNum() == 0){
          continue;
        }
        // https://poi.apache.org/apidocs/org/apache/poi/ss/usermodel/Cell.html
        while (cells.hasNext()) {
          cell = (XSSFCell) cells.next();
          if (cell.getColumnIndex() == 0) { 
            if (cell.getCellType() == XSSFCell.CELL_TYPE_NUMERIC) {
              count = cell.getNumericCellValue();
            } else {
              count = 0;
            }
          }
          if (cell.getColumnIndex() == 1) { 
            if (cell.getCellType() == XSSFCell.CELL_TYPE_STRING) {
              name = cell.getStringCellValue();
            } else if(cell.getCellType() == XSSFCell.CELL_TYPE_NUMERIC){
              name = Double.toString(cell.getNumericCellValue());
            } else {
              // TODO: Boolean, Formula, Errors
              name = "";
            }
          }
        }
        dataRow = new Object[]{name, count} ;
        data.add(dataRow);
        System.out.print(dataRow[1].toString() + "\t" +  dataRow[0].toString() + "\n");
      }
    }  catch (Exception e) {
      e.printStackTrace();
    }
    Object[][] dataArray = new Object[ data.size() ][];
    data.toArray( dataArray );
    return dataArray;
  }

}
