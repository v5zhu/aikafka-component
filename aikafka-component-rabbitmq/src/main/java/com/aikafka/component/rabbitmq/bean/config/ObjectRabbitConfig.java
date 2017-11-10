package com.aikafka.component.rabbitmq.bean.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * All rights Reserved, Designed By MiGu Copyright(C) 2017 Company MiGu Co., Ltd.
 * <p>
 * 〈一句话类描述〉 项目名称:咪咕合管 包名称:   com.aikafka.component.rabbitmq.bean.config 类名称:   ObjectRabbitConfig 类描述: 创建人:
 * zhuxiaolong@aspirecn.com 创建时间: 2017-11-10 12:15 版本：   V1.0.0
 *
 * @Author: zhuxiaolong@aspirecn.com
 */
@Configuration
public class ObjectRabbitConfig {


    @Bean
    public Queue objectQueue() {
        return new Queue("object");
    }

}
