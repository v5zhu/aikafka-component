package com.aikafka.component.job.entity;

import com.aikafka.component.job.constant.DataDict;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description: 计划任务信息
 * @date 2017年7月22日
 */
@Data
public class ScheduleJob implements Serializable {

    private Long jobId;
    /**
     * 任务名称
     */
    private String jobName;
    /**
     * 任务分组
     */
    private String jobGroup;
    /**
     * 任务状态 是否启动任务
     */
    private String jobStatus = DataDict.JobStatus.INITIAL;
    /**
     * cron表达式
     */
    private String cronExpression;
    /**
     * 任务是否有状态
     */
    private boolean isConcurrent = false;
    /**
     * 任务描述
     */
    private String description;
    /**
     * spring bean id
     */
    private String beanId;
    /**
     * spring bean
     */
    private String beanClass;
    /**
     * 任务调用的方法名
     */
    private String methodName;

    private Date createTime = new Date();

    private Date updateTime = new Date();
}