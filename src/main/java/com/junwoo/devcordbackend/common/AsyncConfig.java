package com.junwoo.devcordbackend.common;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

/**
 *
 * @author junnukim1007gmail.com
 * @date 26. 1. 21.
 */
@Configuration
@EnableAsync
public class AsyncConfig {

    @Bean
    public Executor notificationExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(4);
        executor.setMaxPoolSize(10);
        executor.setQueueCapacity(1000);
        executor.setThreadNamePrefix("notification-");
        executor.initialize();
        return executor;
    }
}
