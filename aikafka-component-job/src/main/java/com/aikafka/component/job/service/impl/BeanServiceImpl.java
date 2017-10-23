package com.aikafka.component.job.service.impl;

import com.aikafka.component.job.annotation.SelfDefinedTaskBean;
import com.aikafka.component.job.spring.utils.SpringUtils;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * TODO〈一句话类描述〉
 * 项目名称:咪咕合管
 * 包名称: com.aikafka.component.job.service.impl
 * 类名称: BeanServiceImpl
 * 类描述:
 * 创建人: zhuxiaolong@aspirecn.com
 * 创建时间:2017/10/23
 * 版本： V1.0.0
 */
@Service
public class BeanServiceImpl implements BeanService {


    @Override
    public List<String> listBeanNames() {
        String[] names = SpringUtils.listBeanIds(SelfDefinedTaskBean.class);
        return Arrays.asList(names);
    }

    @Override
    public String getClassFullName(String beanId) {
        Object object = SpringUtils.getBean(beanId);
        if (object != null) {
            return object.getClass().getName();
        }
        return null;
    }
}
