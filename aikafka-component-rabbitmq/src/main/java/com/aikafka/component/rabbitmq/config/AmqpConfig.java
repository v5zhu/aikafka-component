package com.aikafka.component.rabbitmq.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * All rights Reserved, Designed By MiGu Copyright(C) 2017 Company MiGu Co., Ltd.
 * <p>
 * 〈一句话类描述〉 项目名称:咪咕合管 包名称:   com.aikafka.component.rabbitmq.config 类名称:   AmqpConfig 类描述:   Amqp配置类 创建人:
 * zhuxiaolong@aspirecn.com 创建时间: 2017-11-08 12:02 版本： V1.0.0
 *
 * @Author: zhuxiaolong@aspirecn.com
 */
@ConfigurationProperties(prefix = "rabbitmq")
@Setter
@Getter
@Component(value = "amqpConfig")
public class AmqpConfig {

    private String address;

    private int port;

    private String username;

    private String password;

    private String virtualHost;

    private boolean publisherConfirms;

    private String queueName;

    private String exchangeType;

    private String exchange;

    private String routingKey;
}
