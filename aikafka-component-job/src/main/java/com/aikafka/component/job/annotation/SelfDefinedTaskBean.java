package com.aikafka.component.job.annotation;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * TODO〈一句话类描述〉
 * 项目名称:咪咕合管
 * 包名称: com.aikafka.component.job.annotation
 * 类名称: SelfDefinedBean
 * 类描述:
 * 创建人: zhuxiaolong@aspirecn.com
 * 创建时间:2017/10/23
 * 版本： V1.0.0
 */
@Target({ElementType.TYPE,ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Component
public @interface SelfDefinedTaskBean {
}
