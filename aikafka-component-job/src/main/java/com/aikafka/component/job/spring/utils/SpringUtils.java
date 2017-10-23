package com.aikafka.component.job.spring.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.lang.annotation.Annotation;

@Service
@Lazy(false)
public class SpringUtils implements ApplicationContextAware {

    private static ApplicationContext applicationContext = null;

    private static Logger logger = LoggerFactory.getLogger(SpringUtils.class);

    /**
     * 取得存储在静态变量中的ApplicationContext.
     */
    private static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    /**
     * 从静态变量applicationContext中取得Bean, 自动转型为所赋值对象的类型.
     */
    @SuppressWarnings("unchecked")
    public static <T> T getBean(String name) {
        return (T) applicationContext.getBean(name);
    }

    /**
     * 从静态变量applicationContext中取得Bean, 自动转型为所赋值对象的类型.
     */
    public static <T> T getBean(Class<T> requiredType) {
        return applicationContext.getBean(requiredType);
    }

    public static <T> String[] listBeanIds(Class<?> annotationType) {
        return applicationContext.getBeanNamesForAnnotation((Class<? extends Annotation>) annotationType);
    }

    /**
     * 实现ApplicationContextAware接口, 注入Context到静态变量中.
     */
    @Override
    public synchronized void setApplicationContext(ApplicationContext applicationContext) {
        // logger.debug("注入ApplicationContext到SpringContextHolder:{}", applicationContext);

        if (SpringUtils.applicationContext != null) {
            logger.info("SpringContextHolder中的ApplicationContext被覆盖, 原有ApplicationContext为:"
                    + SpringUtils.applicationContext);
        }

        SpringUtils.applicationContext = applicationContext; // NOSONAR
    }
}