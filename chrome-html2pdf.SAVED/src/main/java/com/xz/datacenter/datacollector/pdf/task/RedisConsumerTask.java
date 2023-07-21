package com.xz.datacenter.datacollector.pdf.task;

// import com.xz.ajiaedu.common.lang.StringUtil;
import com.xz.datacenter.datacollector.pdf.service.PDFService;
import com.xz.datacenter.datacollector.pdf.util.HTML2PDF;
import com.xz.datacenter.datacollector.pdf.vo.PDFSessionDetail;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @Description:下载任务消费定时任务
 * @Author: houyong
 * @Date: 2019/12/26
 */

@Slf4j
@Component
public class RedisConsumerTask extends Thread {

    @Value("${spring.redis.pdfKey}")
    private String pdfKey;

    @Value("${spring.redis.pdfRollbackKey}")
    private String pdfRollbackKey;

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    @Autowired
    private PDFService pdfService;

    @Scheduled(fixedDelayString = "${pdf.redis.consumer.interval}")
    public void consumerTask(){
        try {
            long msgSize=redisTemplate.opsForList().size(pdfKey);
            if(msgSize>0){
                for(int i=0;i<msgSize;i++){
                    PDFSessionDetail pdfSessionDetail=null;
                    pdfSessionDetail= HTML2PDF.useSession();
                    if(pdfSessionDetail!=null){
                        Object value=redisTemplate.opsForList().rightPopAndLeftPush(pdfKey,pdfRollbackKey);
                        if(value!=null&& String.isNotEmpty(value.toString())){
                            log.warn("监听消息准备生成文件："+value);
                            pdfService.generatePDF(pdfSessionDetail,value.toString());
                        }else{
                            HTML2PDF.releaseSession(pdfSessionDetail);
                            break;
                        }

                    }else{
                        break;
                    }
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage(),e);
        }


    }

}



