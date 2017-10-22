package com.aikafka.component.job.factory;


import com.aikafka.component.job.entity.ScheduleJob;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * 允许任务并发
 * 项目名称:咪咕合管
 * 包名称: com.migu.tsg.pms.iodd.job
 * 类名称: Test
 * 类描述:
 * 创建人: zhuxiaolong@aspirecn.com
 * 创建时间:2017/9/28
 * 版本： V1.0.0
 */
public class QuartzJobFactoryAllowConcurrentExecution implements Job {
	public final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		ScheduleJob scheduleJob = (ScheduleJob) context.getMergedJobDataMap().get("scheduleJob");
		TaskUtils.invokeMethod(scheduleJob);
	}
}