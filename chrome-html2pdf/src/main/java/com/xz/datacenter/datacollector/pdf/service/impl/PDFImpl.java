package com.xz.datacenter.datacollector.pdf.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xz.datacenter.datacollector.pdf.service.PDFService;
import com.xz.datacenter.datacollector.pdf.util.HTML2PDF;
import com.xz.datacenter.datacollector.pdf.vo.PDFSessionDetail;
import com.xz.datacenter.datacollector.pdf.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description:
 * @Author: houyong
 * @Date: 2020/2/4
 */
@Slf4j
@Service
public class PDFImpl implements PDFService {


    @Value("${pdf.pdfWrongServer}")
    private String pdfWrongServer;

    @Value("${pdf.storagePath}")
    private String storagePath;

    @Value("${spring.redis.pdfCallbackKey}")
    private String pdfCallbackKey;

    @Value("${spring.redis.pdfRollbackKey}")
    private String pdfRollbackKey;

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    @Override
    @Async("executorPool")
    public void generatePDF(PDFSessionDetail pdfSessionDetail, String value) {

        JSONObject msgJson= JSON.parseObject(value);

        String projectId=msgJson.getString("projectId");
        String studentId=msgJson.getString("studentId");
        String subjectId=msgJson.getString("subjectId");
        String schoolId=msgJson.getString("schoolId");
        String classId=msgJson.getString("classId");
        String errorBookName=msgJson.getString("errorBookName");
        String schoolName=msgJson.getString("schoolId");
        String className=msgJson.getString("className");
        String studentName=msgJson.getString("studentName");
        String subjectName=msgJson.getString("subjectName");


        String url = String.format(pdfWrongServer, projectId, subjectId, studentId);


        String relationDir=projectId+"/"+schoolId+"/"+classId;
        String filePath=storagePath+"/"+relationDir;
        File pathFile=new File(filePath);
        if(!pathFile.exists())pathFile.mkdirs();
        String fileName=errorBookName+"_"+schoolName+"_"+className+"_"+studentName+"_"+subjectName+".pdf";

        String fullPath=filePath+"/"+fileName;
        String relationPath=relationDir+"/"+fileName;

        Map<String,Object> map=new HashMap<>();
        log.warn("访问pdf服务:"+url);
        Result result=HTML2PDF.html2Pdf(pdfSessionDetail.getSession(),url,fullPath);
        log.warn("完成pdf:"+fullPath);
        HTML2PDF.releaseSession(pdfSessionDetail);

        //完成后回调消息
        msgJson.put("success",result.isSuccess());
        msgJson.put("failure",!result.isSuccess());
        msgJson.put("filePath",result.isSuccess()?relationPath:"");
        msgJson.put("code",result.getCode());
        msgJson.put("description",result.getMessage());

        redisTemplate.opsForList().leftPush(pdfCallbackKey,JSON.toJSONString(msgJson));

        //生成完成删除回滚中的消息
        redisTemplate.opsForList().remove(pdfRollbackKey,0,value);
    }

}
