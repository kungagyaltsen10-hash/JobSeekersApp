package com.jobseekers.config;

import com.jobseekers.domain.scheduler.AutoApplyQuartzJob;
import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QuartzConfig {
    @Bean
    public JobDetail autoApplyJobDetail() {
        return JobBuilder.newJob(AutoApplyQuartzJob.class)
                .withIdentity("autoApplyJob")
                .storeDurably()
                .build();
    }

    @Bean
    public Trigger autoApplyTrigger(JobDetail autoApplyJobDetail) {
        return TriggerBuilder.newTrigger()
                .forJob(autoApplyJobDetail)
                .withIdentity("autoApplyTrigger")
                .withSchedule(CronScheduleBuilder.cronSchedule("0 0 2 * * ?"))
                .build();
    }
}
