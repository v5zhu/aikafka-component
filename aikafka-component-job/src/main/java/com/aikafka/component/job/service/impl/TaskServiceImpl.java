package com.aikafka.component.job.service.impl;

import com.aikafka.component.job.dao.ScheduleJobDao;
import com.aikafka.component.job.entity.ScheduleJob;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * TODO〈一句话类描述〉
 * 项目名称:咪咕合管
 * 包名称: com.aikafka.component.job.service.impl
 * 类名称: TaskServiceImpl
 * 类描述:
 * 创建人: zhuxiaolong@aspirecn.com
 * 创建时间:2017/10/23
 * 版本： V1.0.0
 */
@SuppressWarnings("ALL")
@Service
public class TaskServiceImpl implements TaskService {
    public final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private SchedulerFactoryBean schedulerFactoryBean;

    @Autowired
    private ScheduleJobDao scheduleJobDao;

    @Autowired
    private JobService jobService;

    /**
     * 从数据库中取 区别于getAllJob
     *
     * @return
     */
    @Override
    public PageInfo<ScheduleJob> getAllTask(int page, int pageSize) {
        //获取第1页，10条内容，默认查询总数count
        PageHelper.startPage(page, pageSize, true);
        List<ScheduleJob> jobs = scheduleJobDao.getAll();
        //分页实现
        //或者使用PageInfo类（下面的例子有介绍）
        PageInfo<ScheduleJob> pageInfo = new PageInfo<ScheduleJob>(jobs);

        return pageInfo;
    }

    @Override
    public ScheduleJob getTaskByGroupName(String jobGroup, String jobName) {
        ScheduleJob job = scheduleJobDao.findByGroupName(jobGroup, jobName);
        return job;
    }

    @Override
    public PageInfo<ScheduleJob> getTasks(String jobName, int page, int pageSize) throws Exception {
        //获取第1页，10条内容，默认查询总数count
        PageHelper.startPage(page, pageSize, true);
        List<ScheduleJob> jobs = scheduleJobDao.findTasksByJobName(jobName);
        //分页实现
        //或者使用PageInfo类（下面的例子有介绍）
        PageInfo<ScheduleJob> pageInfo = new PageInfo<ScheduleJob>(jobs);

        return pageInfo;

    }

    /**
     * 添加到数据库中 区别于addJob
     */
    @Override
    @Transactional(rollbackFor = {Exception.class,RuntimeException.class})
    public void addTask(ScheduleJob job) {
        job.setCreateTime(new Date());
        scheduleJobDao.insert(job);
    }

    @Override
    @Transactional(rollbackFor = {Exception.class,RuntimeException.class})
    public void editTask(ScheduleJob job) throws Exception {

        job.setUpdateTime(new Date());
        scheduleJobDao.updateByPrimaryKey(job);
    }

    /**
     * 从数据库中查询job
     */
    @Override
    public ScheduleJob getTaskById(Long jobId) {
        return scheduleJobDao.selectByPrimaryKey(jobId);
    }

    @Override
    @Transactional(rollbackFor = {Exception.class,RuntimeException.class})
    public void delTaskById(Long jobId) throws Exception {
        scheduleJobDao.deleteByPrimaryKey(jobId);
    }
}
