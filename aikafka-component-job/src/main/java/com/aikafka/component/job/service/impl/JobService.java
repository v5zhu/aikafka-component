package com.aikafka.component.job.service.impl;

import com.aikafka.component.job.entity.ScheduleJob;

import java.util.List;

/**
 * TODO〈一句话类描述〉
 * 项目名称:咪咕合管
 * 包名称: com.aikafka.component.job.service.impl
 * 类名称: JobService
 * 类描述:
 * 创建人: zhuxiaolong@aspirecn.com
 * 创建时间:2017/10/22
 * 版本： V1.0.0
 */
public interface JobService {

    void addJob(ScheduleJob job);

    List<ScheduleJob> getAllJob();

    List<ScheduleJob> getRunningJob();

    void pauseJob(Long jobId) throws Exception;

    void resumeJob(Long jobId);

    void runAJobNow(Long jobId);

    void updateJobCron(ScheduleJob job);

    void verifyCronExpression(String cronExpression);

    void stopJob(Long jobId);
}
