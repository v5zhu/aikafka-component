package com.aikafka.component.job.spring.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@Lazy(false)
public class SpringUtils implements ApplicationContextAware {

    private static ApplicationContext applicationContext = null;

    private static Logger logger = LoggerFactory.getLogger(SpringUtils.class);

    /**
     * 取得存储在静态变量中的ApplicationContext.
     */
    public static ApplicationContext getApplicationContext() {
        assertContextInjected();
        return applicationContext;
    }

    public static String getRootRealPath() {
        String rootRealPath = "";
        try {
            rootRealPath = getApplicationContext().getResource("").getFile().getAbsolutePath();
        } catch (IOException e) {
            logger.warn("获取系统根目录失败");
        }
        return rootRealPath;
    }

    public static String getResourceRootRealPath() {
        String rootRealPath = "";
        try {
            rootRealPath = new DefaultResourceLoader().getResource("").getFile().getAbsolutePath();
        } catch (IOException e) {
            logger.warn("获取资源根目录失败");
        }
        return rootRealPath;
    }

    /**
     * 从静态变量applicationContext中取得Bean, 自动转型为所赋值对象的类型.
     */
    @SuppressWarnings("unchecked")
    public static <T> T getBean(String name) {
        assertContextInjected();
        return (T) applicationContext.getBean(name);
    }

    /**
     * 从静态变量applicationContext中取得Bean, 自动转型为所赋值对象的类型.
     */
    public static <T> T getBean(Class<T> requiredType) {
        assertContextInjected();
        return applicationContext.getBean(requiredType);
    }

    /**
     * 清除SpringContextHolder中的ApplicationContext为Null.
     */
    public static void clearHolder() {
        if (logger.isDebugEnabled()) {
            logger.debug("清除SpringContextHolder中的ApplicationContext:" + applicationContext);
        }
        applicationContext = null;
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

    /**
     * 检查ApplicationContext不为空.
     */
    private static void assertContextInjected() {
        if (null == applicationContext) {
            logger.error("applicaitonContext属性未注入, 请在applicationContext.xml中定义SpringContextHolder.");
        }
    }
}