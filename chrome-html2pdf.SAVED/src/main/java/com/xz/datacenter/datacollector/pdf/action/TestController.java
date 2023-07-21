package com.xz.datacenter.datacollector.pdf.action;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Description:
 * @Author: houyong
 * @Date: 2020/2/6
 */
@RestController
public class TestController {

    @Value("${spring.redis.pdfKey}")
    private String pictureMsgKey;

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    @Autowired
    private MongoTemplate mongoTemplate;

    @RequestMapping("/testInsertMessage")
    public void testInsertMessage(){
        JSONObject msgObj=new JSONObject();
        JSONObject contentObj=new JSONObject();
        contentObj.put("createTime",new Date().getTime());

        List ossUrlList=new ArrayList();
        ossUrlList.add("http://znxunzhi-marking-pic.oss-cn-shenzhen.aliyuncs.com/430000-32ef33ec854742a3b986bcaada73091e/001/dcf2c7ee-9bb3-40ee-9f63-45fc03c45595/Card-A--25-10.jpg?x-oss-process=image/crop,x_813,y_733,w_854,h_418,g_nw");
        ossUrlList.add("http://znxunzhi-marking-pic.oss-cn-shenzhen.aliyuncs.com/430000-32ef33ec854742a3b986bcaada73091e/001/dcf2c7ee-9bb3-40ee-9f63-45fc03c45595/Card-A--25-10.jpg?x-oss-process=image/crop,x_817,y_88,w_812,h_664,g_nw");
        contentObj.put("oss_url",ossUrlList);

        contentObj.put("projectId","projectId");
        contentObj.put("studentId","studentId");
        contentObj.put("subjectCode","subjectCode");
        contentObj.put("questionNo","questionNo");

        msgObj.put("messageContent",contentObj);

        mongoTemplate.insert(contentObj,"project_quest");


        redisTemplate.opsForList().leftPush(pictureMsgKey, JSON.toJSONString(msgObj));



    }
}
