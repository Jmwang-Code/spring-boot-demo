package com.cn.jmw;

import com.cn.jmw.utils.SchemaSyncJob;
import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * @author jmw
 * @Description TODO
 * @date 2023年06月02日 16:51
 * @Version 1.0
 */
@Component
public class Trigger {

    @Bean
    public Scheduler getScheduler() throws SchedulerException {
        SchedulerFactory schedulerFactory = new org.quartz.impl.StdSchedulerFactory();
        Scheduler scheduler = schedulerFactory.getScheduler();
        scheduler.start();
        return scheduler;
    }

    private final Scheduler scheduler;

    public Trigger(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    public void main() {
        org.quartz.Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("")
                .withSchedule(SimpleScheduleBuilder.repeatMinutelyForever(1))
                .startNow()
                .build();
        JobDetail jobDetail = JobBuilder.newJob()
                .withIdentity("")
                .ofType(SchemaSyncJob.class)
                .build();
        try {
            scheduler.scheduleJob(jobDetail, trigger);
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }
    }
}
