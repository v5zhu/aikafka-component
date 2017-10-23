package com.aikafka.component.job.service.impl;

import com.aikafka.component.job.dao.ScheduleJobDao;
import com.aikafka.component.job.entity.ScheduleJob;
import com.aikafka.component.job.factory.QuartzJobFactoryAllowConcurrentExecution;
import com.aikafka.component.job.factory.QuartzJobFactoryDisallowConcurrentExecution;
import com.alibaba.fastjson.JSONObject;
import org.quartz.*;
import org.quartz.impl.matchers.GroupMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;
import org.springside.modules.mapper.BeanMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * 定时任务调度工厂，用于管理job
 * 包名称: com.migu.tsg.pms.iodd.job.factory
 * 类名称: ScheduleJobFactory
 * 类描述:
 * 创建人: zhuxiaolong@aspirecn.com
 * 创建时间:2017/9/28
 * 版本： V1.0.0
 */
@SuppressWarnings("ALL")
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
    public void addJob(ScheduleJob job) {
        if (job == null || !ScheduleJob.STATUS_RUNNING.equals(job.getJobStatus())) {
            return;
        }
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

    /**
     * 获取所有计划中的任务列表
     *
     * @return
     * @throws SchedulerException
     */
    @Override
    public List<ScheduleJob> getAllJob() {
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        GroupMatcher<JobKey> matcher = GroupMatcher.anyJobGroup();
        try {
            Set<JobKey> jobKeys = scheduler.getJobKeys(matcher);
            List<ScheduleJob> jobList = new ArrayList<ScheduleJob>();
            for (JobKey jobKey : jobKeys) {
                List<? extends Trigger> triggers = scheduler.getTriggersOfJob(jobKey);
                for (Trigger trigger : triggers) {
                    ScheduleJob job = new ScheduleJob();
                    job.setJobName(jobKey.getName());
                    job.setJobGroup(jobKey.getGroup());
                    job.setDescription("触发器:" + trigger.getKey());
                    Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
                    job.setJobStatus(triggerState.name());
                    if (trigger instanceof CronTrigger) {
                        CronTrigger cronTrigger = (CronTrigger) trigger;
                        String cronExpression = cronTrigger.getCronExpression();
                        job.setCronExpression(cronExpression);
                    }
                    jobList.add(job);
                }
            }
            return BeanMapper.mapList(jobList, ScheduleJob.class);
        } catch (SchedulerException e) {
            e.printStackTrace();

        }
        return null;
    }

    /**
     * 所有正在运行的job
     *
     * @return
     * @throws SchedulerException
     */
    @Override
    public List<ScheduleJob> getRunningJob() {
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        try {
            List<JobExecutionContext> executingJobs = scheduler.getCurrentlyExecutingJobs();
            List<ScheduleJob> jobList = new ArrayList<ScheduleJob>(executingJobs.size());
            for (JobExecutionContext executingJob : executingJobs) {
                ScheduleJob job = new ScheduleJob();
                JobDetail jobDetail = executingJob.getJobDetail();
                JobKey jobKey = jobDetail.getKey();
                Trigger trigger = executingJob.getTrigger();
                job.setJobName(jobKey.getName());
                job.setJobGroup(jobKey.getGroup());
                job.setDescription("触发器:" + trigger.getKey());
                Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
                job.setJobStatus(triggerState.name());
                if (trigger instanceof CronTrigger) {
                    CronTrigger cronTrigger = (CronTrigger) trigger;
                    String cronExpression = cronTrigger.getCronExpression();
                    job.setCronExpression(cronExpression);
                }
                jobList.add(job);
            }
            return BeanMapper.mapList(jobList, ScheduleJob.class);

        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 暂停一个job
     *
     * @param scheduleJob
     * @throws Exception
     */
    @Override
    public void pauseJob(Long jobId) throws Exception {
        ScheduleJob job = scheduleJobDao.selectByPrimaryKey(jobId);
        if (job == null) {
            throw new RuntimeException("参数错误");
        }
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        JobKey jobKey = JobKey.jobKey(job.getJobGroup(), job.getJobName());
        try {
            scheduler.pauseJob(jobKey);
        } catch (SchedulerException e) {
            e.printStackTrace();
            String[] infos = {job.getJobGroup(), job.getJobName(), e.getMessage()};
            logger.error("停止任务:group [{}],name [{}] 失败,异常信息[{}]", infos);
            throw new Exception("停止任务失败");
        }
    }

    ;

    /**
     * 恢复一个job
     *
     * @param jobId
     * @throws SchedulerException
     */
    @Override
    public void resumeJob(Long jobId) {
        ScheduleJob job = scheduleJobDao.selectByPrimaryKey(jobId);
        if (job == null) {
            throw new RuntimeException("参数错误");
        }
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        JobKey jobKey = JobKey.jobKey(job.getJobName(), job.getJobGroup());
        try {
            scheduler.resumeJob(jobKey);
        } catch (SchedulerException e) {
            //todo throw coreException
            e.printStackTrace();
        }
    }


    /**
     * 立即执行job
     *
     * @param jobGroup
     * @param jobName
     * @throws SchedulerException
     */
    @Override
    public void runAJobNow(Long jobId) {
        ScheduleJob job = scheduleJobDao.selectByPrimaryKey(jobId);
        if (job == null) {
            throw new RuntimeException("参数错误");
        }
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        JobKey jobKey = JobKey.jobKey(job.getJobName(), job.getJobGroup());
        try {
            scheduler.triggerJob(jobKey);
        } catch (SchedulerException e) {
            //todo throw coreException
            e.printStackTrace();
        }
    }

    /**
     * 更新job时间表达式
     *
     * @param scheduleJob
     * @throws SchedulerException
     */
    @Override
    public void updateJobCron(ScheduleJob scheduleJob) {
        Scheduler scheduler = schedulerFactoryBean.getScheduler();

        TriggerKey triggerKey = TriggerKey.triggerKey(scheduleJob.getJobName(), scheduleJob.getJobGroup());

        CronTrigger trigger = null;
        try {
            trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(scheduleJob.getCronExpression());
            trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();
            scheduler.rescheduleJob(triggerKey, trigger);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void verifyCronExpression(String cronExpression) {
        try {
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cronExpression);
        } catch (Exception e) {
            logger.error("cron表达式有误，不能被解析！");
            throw new RuntimeException("cron表达式错误");
        }
    }

    @Override
    public void stopJob(Long jobId) {
        ScheduleJob job = scheduleJobDao.selectByPrimaryKey(jobId);
        if (job == null) {
            throw new RuntimeException("参数错误");
        }
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        JobKey jobKey = JobKey.jobKey(job.getJobName(), job.getJobGroup());
        try {
            scheduler.deleteJob(jobKey);
            logger.info("任务分组[{}],任务名称 = [{}]------------------已停止", job.getJobGroup(), job.getJobName());
        } catch (SchedulerException e) {
            //todo throw coreException
            e.printStackTrace();
        }
    }
}

