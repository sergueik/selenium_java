package com.xz.datacenter.datacollector.pdf.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

/**
 * @Description:
 * @Author: houyong
 * @Date: 2019/12/27
 */
@Configuration
public class ThreadPoolConfig {

    @Value("${spring.task.execution.pool.core-size}")
    private Integer corePoolSize;

    @Value("${spring.task.execution.pool.max-size}")
    private Integer maxPoolSize;

    @Bean("executorPool")
    public Executor executorPool() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(corePoolSize);
        //taskExecutor.setMaxPoolSize(maxPoolSize);
        //taskExecutor.setQueueCapacity(20000);
        taskExecutor.setThreadNamePrefix("donwnTask");
        taskExecutor.initialize();

        return taskExecutor;
    }
}
