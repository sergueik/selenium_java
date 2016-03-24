package com.mycompany.app;

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

import org.junit.Assert;
import static org.junit.Assert.*;
import org.jopendocument.dom.spreadsheet.MutableCell;
import org.jopendocument.dom.spreadsheet.Sheet;
import org.jopendocument.dom.spreadsheet.SpreadSheet;

public class App {
  public static void main(String[] args) {
    File file = new File("demo.ods");
    App reader = new App();
    reader.readODS(file);
  }
  public void readODS(File file)
  {
    Sheet sheet;
    try {
      sheet = SpreadSheet.createFromFile(file).getSheet(0); // can pass sheet name as string      
      int nColCount = sheet.getColumnCount();
      int nRowCount = sheet.getRowCount();
      // gives
      // Rows :1048576
      // Cols :1024
      MutableCell cell = null;
      for( int nColIndex = 0; nColIndex < nColCount; nColIndex++ ) {
        cell = sheet.getCellAt(nColIndex, 0);
        if (cell.getValue() == null || cell.getValue() == "") {
          break ;
        }
        System.out.print(cell.getValue() + "(" + nColIndex +")\t");
      }
      System.out.println();
      for (int nRowIndex = 1; nRowIndex < nRowCount; nRowIndex++ ) {
        if (sheet.getCellAt(0, nRowIndex).getValue() == null || sheet.getCellAt(0, nRowIndex).getValue() == "") {
          break ;
        }
        for( int nColIndex = 0; nColIndex < nColCount; nColIndex++ ) {
          cell = sheet.getCellAt(nColIndex, nRowIndex);
          if (cell.getValue() == null || cell.getValue() == "") {
            break ;
          }
          System.out.print(cell.getValue() + "\t");
        }
        System.out.println();
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
