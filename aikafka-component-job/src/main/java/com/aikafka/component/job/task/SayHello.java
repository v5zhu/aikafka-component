package com.aikafka.component.job.task;

import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * TODO〈一句话类描述〉
 * 项目名称:咪咕合管
 * 包名称: com.aikafka.component.job.task
 * 类名称: SayHello
 * 类描述:
 * 创建人: zhuxiaolong@aspirecn.com
 * 创建时间:2017/10/23
 * 版本： V1.0.0
 */
@Component
public class SayHello {

    public void say() {
        System.out.println("say hello at " + new Date());
    }
}
