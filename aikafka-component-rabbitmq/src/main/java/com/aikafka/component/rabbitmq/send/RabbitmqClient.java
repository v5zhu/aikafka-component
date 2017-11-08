package com.aikafka.component.rabbitmq.send;

import com.aikafka.component.rabbitmq.config.AmqpConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * All rights Reserved, Designed By MiGu Copyright(C) 2017 Company MiGu Co., Ltd.
 * <p>
 * 〈一句话类描述〉 项目名称:咪咕合管 包名称:   com.aikafka.component.rabbitmq.send 类名称:   Send 类描述:   发送类 创建人:   zhuxiaolong@aspirecn.com
 * 创建时间: 2017-11-08 12:14 版本：   V1.0.0
 *
 * @author zhuxiaolong@aspirecn.com
 * @Author: zhuxiaolong@aspirecn.com
 */
@Component
public class RabbitmqClient implements RabbitTemplate.ConfirmCallback {
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    @Qualifier("amqpConfig")
    private AmqpConfig config;

    public void send(Object message) {
        CorrelationData correlationId = new CorrelationData(UUID.randomUUID().toString());
        rabbitTemplate.convertAndSend(config.getExchange(), config.getRoutingKey(), message, correlationId);
    }

    @Scheduled(cron = "0/2 * * * * ?")
    public void test() {
        this.send("测试消息队列");
    }

    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        System.out.println(" 回调id:" + correlationData);
        if (ack) {
            System.out.println("消息成功消费");
        } else {
            System.out.println("消息消费失败:" + cause);
        }
    }
}
