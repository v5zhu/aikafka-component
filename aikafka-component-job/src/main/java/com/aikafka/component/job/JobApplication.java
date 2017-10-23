package com.aikafka.component.job;

import com.aikafka.component.job.config.QuartzConfig;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import java.util.Properties;

/**
 * TODO〈一句话类描述〉
 * 项目名称:咪咕合管
 * 包名称: com.aikafka.component.job
 * 类名称: JobApplication
 * 类描述:
 * 创建人: zhuxiaolong@aspirecn.com
 * 创建时间:2017/10/22
 * 版本： V1.0.0
 */
//@EnableTransactionManagement
@SpringBootApplication
public class JobApplication {
    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(JobApplication.class);
        application.setBannerMode(Banner.Mode.OFF);
        application.run(args);

    }

    @Bean
    public SchedulerFactoryBean schedulerFactoryBean(@Qualifier("quartzConfig") QuartzConfig config) {
        SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
        // 用于quartz集群,加载quartz数据源
        if (config.isCluster()) {
            Properties prop = new Properties();
            prop.put("quartz.scheduler.instanceName", "Aikafka");
            prop.put("org.quartz.scheduler.instanceId", "AUTO");
            prop.put("org.quartz.scheduler.skipUpdateCheck",
                    "true");
            prop.put("org.quartz.scheduler.jmx.export", "true");

            prop.put("org.quartz.jobStore.class", "org.quartz.impl.jdbcjobstore.JobStoreTX");
            prop.put("org.quartz.jobStore.driverDelegateClass", "org.quartz.impl.jdbcjobstore.StdJDBCDelegate");
            prop.put("org.quartz.jobStore.dataSource", "quartzDataSource");
            prop.put("org.quartz.jobStore.tablePrefix", config.getTablePrefix());
            prop.put("org.quartz.jobStore.isClustered", config.isCluster());

            prop.put("org.quartz.jobStore.clusterCheckinInterval", "20000");
            prop.put("org.quartz.jobStore.dataSource",
                    "myDS");
            prop.put("org.quartz.jobStore.maxMisfiresToHandleAtATime", "1");
            prop.put("org.quartz.jobStore.misfireThreshold", "120000");
            prop.put("org.quartz.jobStore.txIsolationLevelSerializable", "true");
            prop.put("org.quartz.jobStore.selectWithLockSQL", "SELECT * FROM {0}LOCKS WHERE LOCK_NAME = ? FOR UPDATE");

            prop.put("org.quartz.threadPool.class", "org.quartz.simpl.SimpleThreadPool");
            prop.put("org.quartz.threadPool.threadCount", "10");
            prop.put("org.quartz.threadPool.threadPriority", "5");
            prop.put("org.quartz.threadPool.threadsInheritContextClassLoaderOfInitializingThread", "true");

            prop.put("org.quartz.dataSource.myDS.driver", config.getDriver());
            prop.put("org.quartz.dataSource.myDS.URL", config.getUrl());
            prop.put("org.quartz.dataSource.myDS.user", config.getUsername());
            prop.put("org.quartz.dataSource.myDS.password", config.getPassword());
            prop.put("org.quartz.dataSource.myDS.maxConnections", 20);

            prop.put("org.quartz.plugin.triggHistory.class", "org.quartz.plugins.history.LoggingJobHistoryPlugin");
            prop.put("org.quartz.plugin.shutdownhook.class", "org.quartz.plugins.management.ShutdownHookPlugin");
            prop.put("org.quartz.plugin.shutdownhook.cleanShutdown", "true");
            schedulerFactoryBean.setQuartzProperties(prop);
        }

        return schedulerFactoryBean;
    }
}
