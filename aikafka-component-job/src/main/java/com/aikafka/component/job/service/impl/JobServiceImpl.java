package com.aikafka.component.job.service.impl;

import com.aikafka.component.job.dao.ScheduleJobDao;
import com.aikafka.component.job.entity.ScheduleJob;
import com.aikafka.component.job.factory.QuartzJobFactoryAllowConcurrentExecution;
import com.aikafka.component.job.factory.QuartzJobFactoryDisallowConcurrentExecution;
import com.alibaba.fastjson.JSONObject;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;

/**
 * 定时任务调度工厂，用于管理job
 * 包名称: com.migu.tsg.pms.iodd.job.factory
 * 类名称: ScheduleJobFactory
 * 类描述:
 * 创建人: zhuxiaolong@aspirecn.com
 * 创建时间:2017/9/28
 * 版本： V1.0.0
 */
@SuppressWarnings("SpringJavaAutowiringInspection")
@Service
public class JobServiceImpl implements JobService {

    public final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SchedulerFactoryBean schedulerFactoryBean;
    @Autowired
    private ScheduleJobDao scheduleJobDao;


    /**
     * 添加任务
     *
     * @param job
     */
    @Override
//    @Trans
    public void addJob(ScheduleJob job) {
        if (job == null || !ScheduleJob.STATUS_RUNNING.equals(job.getJobStatus())) {
            return;
        }
        scheduleJobDao.insert(job);
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        logger.info(scheduler + "添加任务:{}", JSONObject.toJSONString(job));
        TriggerKey triggerKey = TriggerKey.triggerKey(job.getJobName(), job.getJobGroup());
        try {
            CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);

            // 不存在，创建一个
            if (null == trigger) {
                Class clazz = job.isConcurrent()
                        ? QuartzJobFactoryAllowConcurrentExecution.class
                        : QuartzJobFactoryDisallowConcurrentExecution.class;

                JobDetail jobDetail = JobBuilder.newJob(clazz).withIdentity(job.getJobName(), job.getJobGroup())
                        .build();

                jobDetail.getJobDataMap().put("scheduleJob", job);

                CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(job.getCronExpression());

                trigger = TriggerBuilder.newTrigger().withIdentity(job.getJobName(), job.getJobGroup()).withSchedule
                        (scheduleBuilder).build();

                scheduler.scheduleJob(jobDetail, trigger);
            } else {
                // Trigger已存在，那么更新相应的定时设置
                CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(job.getCronExpression());

                // 按新的cronExpression表达式重新构建trigger
                trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();

                // 按新的trigger重新设置job执行
                scheduler.rescheduleJob(triggerKey, trigger);
            }
        } catch (SchedulerException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }
    }
}

