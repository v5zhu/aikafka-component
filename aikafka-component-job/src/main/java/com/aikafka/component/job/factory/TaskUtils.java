package com.aikafka.component.job.factory;

import com.aikafka.component.job.entity.ScheduleJob;
import com.aikafka.component.job.spring.utils.SpringUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


public class TaskUtils {
    public final static Logger LOG = LoggerFactory.getLogger(TaskUtils.class);

    /**
     * 通过反射调用scheduleJob中定义的方法
     *
     * @param scheduleJob
     */
    public static void invokeMethod(ScheduleJob scheduleJob) {
        Object object = null;
        Class clazz;
        if (StringUtils.isNotBlank(scheduleJob.getBeanClass())) {
            object = SpringUtils.getBean(scheduleJob.getBeanClass());
        } /*else if (StringUtils.isNotBlank(scheduleJob.getBeanClass())) {
            try {
				clazz = Class.forName(scheduleJob.getBeanClass());
				object = clazz.newInstance();
			} catch (Exception e) {
				e.printStackTrace();
			}

		}*/
        if (object == null) {
            LOG.error("任务名称 = [" + scheduleJob.getJobName() + "]---------------未启动成功，请检查是否配置正确！！！");
            return;
        }
        clazz = object.getClass();
        Method method = null;
        try {
            //无参调用
            method = clazz.getDeclaredMethod(scheduleJob.getMethodName());
        } catch (NoSuchMethodException e) {
            LOG.error("任务名称 = [" + scheduleJob.getJobName() + "]---------------未启动成功，方法名设置错误！！！");
        } catch (SecurityException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if (method != null) {
            try {
                //无参调用
                method.invoke(object);
            } catch (IllegalAccessException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
}
