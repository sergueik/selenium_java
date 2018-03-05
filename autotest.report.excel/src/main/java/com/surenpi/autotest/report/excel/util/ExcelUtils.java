/*
 *
 *  * Copyright 2002-2007 the original author or authors.
 *  *
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  *      http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *
 */

package com.surenpi.autotest.report.excel.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.surenpi.autotest.report.ReportStatus;

/**
 * @author suren
 */
public class ExcelUtils
{
    private static final String HEAD_MODULE_NAME = "模块名称";
    private static final String HEAD_CLAZZ = "类名";
    private static final String HEAD_METHOD = "方法名";
    private static final String HEAD_STATUS = "状态";
    private static final String HEAD_TOTAL_TIME = "耗时";
    private static final String HEAD_BEGINE_TIME = "开始时间";
    private static final String HEAD_END_TIME = "结束时间";
    private static final String HEAD_DETAIL = "详细信息";
    private static final String HEAD_MODULE_DESC = "模块描述";

    private static final Map<String, String> headerMap = new LinkedHashMap<String, String>();

    private File targetFile;

    private FileOutputStream fos = null;
    private HSSFWorkbook workbook;
    private HSSFSheet sheet;

    private int currentRow;

    public ExcelUtils(File targetFile)
    {
        this.targetFile = targetFile;
    }

    /**
     * 初始化
     */
    public void init()
    {
        headerMap.put(HEAD_MODULE_NAME, "moduleName");
        headerMap.put(HEAD_MODULE_DESC, "moduleDescription");
        headerMap.put(HEAD_CLAZZ, "clazzName");
        headerMap.put(HEAD_METHOD, "methodName");
        headerMap.put(HEAD_TOTAL_TIME, "totalTime");
        headerMap.put(HEAD_BEGINE_TIME, "beginTime");
        headerMap.put(HEAD_END_TIME, "endTime");
        headerMap.put(HEAD_DETAIL, "detail");
        headerMap.put(HEAD_STATUS, "status");

        workbook = new HSSFWorkbook();
        sheet = workbook.createSheet("测试报告明细");
    }

    /**
     * 将objList中的对象列表到导出到文件中
     *
     * @param objList 对象列表
     * @return 导出成功返回true
     */
    public boolean export(List<Object> objList)
    {
        try
        {
            if(fos == null)
            {
                fos = new FileOutputStream(targetFile);
            }

            return exportStream(objList);
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * 导出一条记录
     * @param targetObj 目标对象
     * @return 是否成功
     */
    public boolean export(Object targetObj)
    {
        List<Object> list = new ArrayList<Object>(1);
        list.add(targetObj);

        return export(list);
    }

    /**
     * 将objList中的对象列表到导出到输出流中
     *
     * @param objList 对象列表
     * @return 导出成功返回true
     */
    public boolean exportStream(List<Object> objList)
    {
        createTitle(sheet);

        if(objList != null)
        {
            int size = objList.size();
            int incr = size + currentRow;
            for(int i = currentRow; i < incr; i++, currentRow++)
            {
                fillContent(sheet, i, objList.get(i - currentRow));
            }
        }

        return false;
    }

    /**
     * 设置表头
     *
     * @param sheet
     */
    private void createTitle(HSSFSheet sheet)
    {
        if(currentRow > 0)
        {
            return;
        }

        HSSFRow row = sheet.createRow(currentRow++);
        int column = 0;

        for(String key : headerMap.keySet())
        {
            HSSFCell cell = row.createCell(column++);
            cell.setCellValue(key);
        }
    }

    /**
     * 根据一个对象填充一行数据
     *
     * @param sheet
     * @param rowNum 行号
     * @param data 数据对象
     */
    private void fillContent(HSSFSheet sheet, int rowNum, Object data)
    {
        if(sheet == null || data == null)
        {
            return;
        }

        HSSFRow row = sheet.createRow(rowNum);
        int column = 0;

        Class<? extends Object> dataCls = data.getClass();
        for(String key : headerMap.keySet())
        {
            String name = headerMap.get(key);

            try
            {
                Method method = dataCls.getMethod(String.format("get%s%s",
                        name.substring(0, 1).toUpperCase(), name.substring(1)));
                Object value = method.invoke(data);
                if(value != null)
                {
                    HSSFCell cell = row.createCell(column);
                    cell.setCellValue(value.toString());

                    if(value.toString().equals(ReportStatus.EXCEPTION.name()))
                    {
                        HSSFFont font = workbook.createFont();
                        font.setColor(HSSFFont.COLOR_RED);

                        HSSFCellStyle style = workbook.createCellStyle();
                        style.setFont(font);
                        cell.setCellStyle(style);
                    }
                }
            }
            catch (SecurityException e)
            {
                e.printStackTrace();
            }
            catch (IllegalArgumentException e)
            {
                e.printStackTrace();
            }
            catch (IllegalAccessException e)
            {
                e.printStackTrace();
            }
            catch (NoSuchMethodException e)
            {
                e.printStackTrace();
            }
            catch (InvocationTargetException e)
            {
                e.printStackTrace();
            }
            finally
            {
                column++;
            }
        }
    }

    /**
     * 填写静态信息
     * @param info 静态信息
     * @param sheetName 名称
     */
    public void fillStaticInfo(Map<String, String> info, String sheetName)
    {
        HSSFSheet staticInfoSheet = workbook.createSheet(sheetName);
        info.forEach((name, value) -> {
            int lastRowNum = staticInfoSheet.getLastRowNum();

            HSSFRow row = staticInfoSheet.createRow(++lastRowNum);

            HSSFCell nameCell = row.createCell(0);
            HSSFCell valueCell = row.createCell(1);

            nameCell.setCellValue(name);
            valueCell.setCellValue(value);
        });
    }

    /**
     * 保存结果
     */
    public void save()
    {
        if(fos != null)
        {
            sheet.autoSizeColumn(0);
            sheet.autoSizeColumn(1);
            sheet.autoSizeColumn(2);
            sheet.autoSizeColumn(3);
            sheet.autoSizeColumn(4);
            sheet.autoSizeColumn(5);
            sheet.autoSizeColumn(6);
            sheet.autoSizeColumn(8);

            try
            {
                workbook.write(fos);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }

            try
            {
                fos.close();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }
}
