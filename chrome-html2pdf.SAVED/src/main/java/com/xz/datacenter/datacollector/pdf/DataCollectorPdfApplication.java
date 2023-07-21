package com.xz.datacenter.datacollector.pdf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication(scanBasePackages = "com.xz.datacenter.datacollector")
//@EnableEurekaClient
//@EnableFeignClients(basePackages = "com.xz.datacenter.datacollector")
@EnableAsync
//@EnableOAuth2Client
//@EnableResourceServer
@EnableScheduling
public class DataCollectorPdfApplication {

    public static void main(String[] args) {
        SpringApplication.run(DataCollectorPdfApplication.class, args);
    }

}
