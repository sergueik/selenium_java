package com.xz.datacenter.datacollector.downloader;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.UUID;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@Slf4j
class EcgManagerApplicationTests {

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;


    @Test
    void redisTest(){

        for(int i=0;i<100000;i++){
            String value= UUID.randomUUID()+"|"+UUID.randomUUID()+"|"+UUID.randomUUID();
            redisTemplate.opsForList().leftPush("pdfTask",value);

        }




//        try {
//            while (true){
//                Thread.sleep(100);
//
//                for(int i=0;i<100;i++){
//                    new Thread(new Runnable() {
//                        @Override
//                        public void run() {
//                            long a=new Date().getTime();
//                            Object o=redisTemplate.opsForValue().get("a");
//                            redisTemplate.opsForValue().set("a",a+"");
//                            System.out.println(o);
//                            long b=new Date().getTime();
//                            log.warn("完成："+(b-a));
//                        }
//                    }).start();
//                }
//
//
//            }
//
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

    }


}
