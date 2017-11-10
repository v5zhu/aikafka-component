package com.aikafka.component.rabbitmq.fanout;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * All rights Reserved, Designed By MiGu Copyright(C) 2017 Company MiGu Co., Ltd.
 * <p>
 * 〈一句话类描述〉 项目名称:咪咕合管 包名称:   com.aikafka.component.rabbitmq.fanout 类名称:   FanoutReceiveA 类描述: 创建人:
 * zhuxiaolong@aspirecn.com 创建时间: 2017-11-10 12:04 版本：   V1.0.0
 *
 * @Author: zhuxiaolong@aspirecn.com
 */
@Component
@RabbitListener(queues = "fanout.B")
public class FanoutReceiverB {
    @RabbitHandler
    public void process(String message) {
        System.out.println("fanout Receiver B  : " + message);
    }
}
