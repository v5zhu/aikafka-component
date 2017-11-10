package com.aikafka.component.rabbitmq;

import com.aikafka.component.rabbitmq.config.AmqpConfig;
import com.alibaba.fastjson.JSONObject;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * All rights Reserved, Designed By MiGu Copyright(C) 2017 Company MiGu Co., Ltd.
 * <p>
 * 〈一句话类描述〉 项目名称:咪咕合管 包名称:   com.aikafka.component.rabbitmq.config 类名称:   AmqpApplication 类描述:   Amqp启动类 创建人:
 * zhuxiaolong@aspirecn.com 创建时间: 2017-11-08 12:04 版本：   V1.0.0
 *
 * @Author: zhuxiaolong@aspirecn.com
 */
@EnableScheduling
@SpringBootApplication
public class RabbitmqApplication {
    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(RabbitmqApplication.class);
        application.setBannerMode(Banner.Mode.OFF);
        application.run(args);

    }

    @Bean
    public ConnectionFactory connectionFactory(@Qualifier("amqpConfig") AmqpConfig config) {
        System.out.println("创建RabbitMQ连接工厂参数:" + JSONObject.toJSONString(config));
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setAddresses(config.getAddress());
        connectionFactory.setPort(config.getPort());
        connectionFactory.setUsername(config.getUsername());
        connectionFactory.setPassword(config.getPassword());
        connectionFactory.setVirtualHost(config.getVirtualHost());
        //这里需要显示调用 才能进行消息的回调。
        connectionFactory.setPublisherConfirms(config.isPublisherConfirms()); //必须要设置
        return connectionFactory;
    }

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public RabbitTemplate rabbitTemplate(@Qualifier("amqpConfig") AmqpConfig config) {
        //通过使用RabbitTemplate来对开发者提供API操作
        RabbitTemplate template = new RabbitTemplate(connectionFactory(config));
        return template;
    }
}
