package com.aikafka.component.job.factory;

import com.aikafka.component.job.entity.ScheduleJob;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 
 * 若一个方法一次执行不完下次轮转时则等待改方法执行完后才执行下一次操作
 * 项目名称:咪咕合管
 * 包名称: com.migu.tsg.pms.iodd.job
 * 类名称: Test
 * 类描述:
 * 创建人: zhuxiaolong@aspirecn.com
 * 创建时间:2017/9/28
 * 版本： V1.0.0
 */
@DisallowConcurrentExecution
public class QuartzJobFactoryDisallowConcurrentExecution implements Job {
	public final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		ScheduleJob scheduleJob = (ScheduleJob) context.getMergedJobDataMap().get("scheduleJob");
		TaskUtils.invokeMethod(scheduleJob);

	}
}