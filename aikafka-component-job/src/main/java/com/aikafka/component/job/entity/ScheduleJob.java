package com.aikafka.component.job.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description: 计划任务信息
 * @date 2017年7月22日
 */
@Data
public class ScheduleJob implements Serializable {

    public static final String STATUS_RUNNING = "1";
    public static final String STATUS_NOT_RUNNING = "0";
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
    private String jobStatus;
    /**
     * cron表达式
     */
    private String cronExpression;
    /**
     * 任务是否有状态
     */
    private boolean isConcurrent;
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

    private Date createTime;

    private Date updateTime;
}