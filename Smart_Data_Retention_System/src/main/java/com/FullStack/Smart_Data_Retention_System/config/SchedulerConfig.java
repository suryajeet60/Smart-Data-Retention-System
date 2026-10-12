package com.FullStack.Smart_Data_Retention_System.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

@Configuration
@EnableScheduling
@Slf4j
public class SchedulerConfig
{

    @Bean
    public ThreadPoolTaskScheduler taskScheduler()
    {

        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();

        scheduler.setPoolSize(5);

        scheduler.setThreadNamePrefix("Retention-Scheduler-");

        scheduler.setWaitForTasksToCompleteOnShutdown(true);

        scheduler.setAwaitTerminationSeconds(60);

        scheduler.setErrorHandler(ex -> log.error("Scheduler execution failed.", ex));

        scheduler.initialize();

        return scheduler;
    }
}
