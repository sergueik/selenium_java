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

package com.surenpi.autotest.report.excel.writer;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.annotation.PreDestroy;

import org.modelmapper.ModelMapper;

import com.surenpi.autotest.report.RecordReportWriter;
import com.surenpi.autotest.report.ReportStatus;
import com.surenpi.autotest.report.excel.model.ExcelReport;
import com.surenpi.autotest.report.excel.util.ExcelUtils;
import com.surenpi.autotest.report.record.ExceptionRecord;
import com.surenpi.autotest.report.record.NormalRecord;
import com.surenpi.autotest.report.record.ProjectRecord;
import com.surenpi.autotest.report.util.DateUtils;

/**
 * Excel格式报告导出
 * @author suren
 */
public class ExcelReportWriter implements RecordReportWriter
{
    private ExcelUtils utils;
    private ProjectRecord projectRecord;

    @Override
    public void write(ExceptionRecord record)
    {
        NormalRecord normalRecord = record.getNormalRecord();

        ModelMapper mapper = new ModelMapper();
        ExcelReport excelReport = mapper.map(normalRecord, ExcelReport.class);
        excelReport.setDetail(record.getStackTraceText());
        fillReport(normalRecord, ReportStatus.EXCEPTION, excelReport);

        utils.export(excelReport);
    }

    @Override
    public void write(NormalRecord normalRecord)
    {
        ExcelReport excelReport = new ModelMapper().map(normalRecord, ExcelReport.class);
        fillReport(normalRecord, ReportStatus.NORMAL, excelReport);

        utils.export(excelReport);
    }

    /**
     * 填充公共数据
     * @param normalRecord
     * @param reportStatus
     * @param excelReport
     */
    private void fillReport(NormalRecord normalRecord, ReportStatus reportStatus, ExcelReport excelReport)
    {
        excelReport.setStatus(reportStatus.name());
        excelReport.setBeginTime(DateUtils.getDateText(normalRecord.getBeginTime()));
        excelReport.setEndTime(DateUtils.getDateText(normalRecord.getEndTime()));
        excelReport.setTotalTime(((normalRecord.getEndTime() - normalRecord.getBeginTime()) / 1000) + "秒");
    }

    @Override
    public void write(ProjectRecord projectRecord)
    {
        utils = new ExcelUtils(
                new File(projectRecord.getName() + "-" + System.currentTimeMillis() + ".xls"));
        utils.init();
        this.projectRecord = projectRecord;
    }

    /**
     * 保存文件
     */
    @PreDestroy
    public void saveFile()
    {
        Map<String,String> info = new LinkedHashMap<String, String>();
        info.put("测试流程", projectRecord.getName());
        info.put("IP", projectRecord.getAddressInfo());
        info.put("操作系统", projectRecord.getOsName() + "-"
                + projectRecord.getOsArch() + "-"
                + projectRecord.getOsVersion());
        info.put("项目地址", "https://github.com/LinuxSuRen/phoenix.webui.framework");
        if(projectRecord.getUserInfo() != null)
        {
            info.putAll(projectRecord.getUserInfo());
        }

        utils.fillStaticInfo(info, "环境信息");
        utils.save();
    }
}
