package com.aikafka.component.job.service.impl;

import com.aikafka.component.job.entity.ScheduleJob;
import com.github.pagehelper.PageInfo;

/**
 * TODO〈一句话类描述〉
 * 项目名称:咪咕合管
 * 包名称: com.aikafka.component.job.service.impl
 * 类名称: TaskService
 * 类描述:
 * 创建人: zhuxiaolong@aspirecn.com
 * 创建时间:2017/10/23
 * 版本： V1.0.0
 */
public interface TaskService {
    void addTask(ScheduleJob jobDto);

    void editTask(ScheduleJob jobDto) throws Exception;

    void delTaskById(Long jobId) throws Exception;

    ScheduleJob getTaskById(Long jobId);

    PageInfo<ScheduleJob> getTasks(String jobName, int page, int pageSize) throws Exception;

    PageInfo<ScheduleJob> getAllTask(int page, int pageSize);

}
