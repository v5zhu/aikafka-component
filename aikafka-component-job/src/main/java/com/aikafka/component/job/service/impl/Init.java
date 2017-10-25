package com.aikafka.component.job.service.impl;

import com.aikafka.component.job.dao.ScheduleJobDao;
import com.aikafka.component.job.entity.ScheduleJob;
import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * TODO〈一句话类描述〉
 * 项目名称:咪咕合管
 * 包名称: com.aikafka.component.job.service.impl
 * 类名称: Init
 * 类描述:
 * 创建人: zhuxiaolong@aspirecn.com
 * 创建时间:2017/10/23
 * 版本： V1.0.0
 */
@SuppressWarnings("ALL")
@Component
public class Init {

    @Autowired
    private SchedulerFactoryBean schedulerFactoryBean;

    @Autowired
    private ScheduleJobDao scheduleJobDao;

    @Autowired
    private JobService jobService;

    @PostConstruct
    public void init() throws Exception {

        Scheduler scheduler = schedulerFactoryBean.getScheduler();

        // 这里获取任务信息数据
        List<ScheduleJob> jobList = scheduleJobDao.getAll();

        for (ScheduleJob job : jobList) {
            jobService.join(job);
        }
    }
}
