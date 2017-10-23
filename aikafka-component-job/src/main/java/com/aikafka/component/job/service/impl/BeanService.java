package com.aikafka.component.job.service.impl;


import java.util.List;

/**
 * TODO〈一句话类描述〉
 * 项目名称:咪咕合管
 * 包名称: com.aikafka.component.job.service.impl
 * 类名称: BeanService
 * 类描述:
 * 创建人: zhuxiaolong@aspirecn.com
 * 创建时间:2017/10/23
 * 版本： V1.0.0
 */
public interface BeanService {

    List<String> listBeanNames();

    String getClassFullName(String beanId);

}
