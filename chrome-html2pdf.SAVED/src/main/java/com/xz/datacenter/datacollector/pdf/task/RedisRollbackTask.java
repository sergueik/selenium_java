package com.xz.datacenter.datacollector.pdf.task;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xz.ajiaedu.common.lang.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;

/**
 * @Description:消息息回滚定时任务
 * @Author: houyong
 * @Date: 2019/12/26
 */

@Slf4j
@Component
public class RedisRollbackTask {

    @Value("${pdf.redis.consumer.rollback.timeout}")
    private long timeoutMillisecond;

    @Value("${spring.redis.pdfKey}")
    private String pdfKey;

    @Value("${spring.redis.pdfRollbackKey}")
    private String pdfRollbackKey;

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    @Scheduled(fixedDelayString = "${pdf.redis.consumer.rollback.interval}")
    public void callbackTask(){
        try {
            List<Object> list=redisTemplate.opsForList().range(pdfRollbackKey,0,-1);
            long currentTime=new Date().getTime();
            log.debug("检查超时队列总数："+(list!=null?list.size():0)+"，时间"+currentTime);
            if(!CollectionUtils.isEmpty(list)){
                for(Object obj:list){
                    if(obj!=null&& StringUtil.isNotEmpty(obj.toString())){
                        JSONObject msgObj= JSON.parseObject(obj.toString());
                        long createTime=msgObj.getLong("createTime");
                        if(currentTime-createTime>timeoutMillisecond){
                            log.debug("检查到超时的消息："+obj.toString());
                            //检查重试次数 超过3次直接丢弃
                            int retryTimes=msgObj.getIntValue("retryTimes");
                            retryTimes=retryTimes<0?0:retryTimes;
                            retryTimes++;
                            if(retryTimes<=3){
                                msgObj.put("retryTimes",retryTimes);
                                redisTemplate.opsForList().leftPush(pdfKey,JSON.toJSONString(msgObj));
                            }
                            redisTemplate.opsForList().remove(pdfRollbackKey,0,obj);

                        }
                    }
                }

            }


        } catch (Exception e) {
            log.error(e.getMessage(),e);
        }


    }


}
