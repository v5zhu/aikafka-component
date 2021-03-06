package com.aikafka.component.rabbitmq.object;

import com.aikafka.component.rabbitmq.domain.User;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * All rights Reserved, Designed By MiGu Copyright(C) 2017 Company MiGu Co., Ltd.
 * <p>
 * 〈一句话类描述〉 项目名称:咪咕合管 包名称:   com.aikafka.component.rabbitmq.fanout 类名称:   FanoutSender 类描述: 创建人:
 * zhuxiaolong@aspirecn.com 创建时间: 2017-11-10 12:03 版本：   V1.0.0
 *
 * @Author: zhuxiaolong@aspirecn.com
 */
@Component
public class ObjectSender {

    @Autowired
    private AmqpTemplate rabbitTemplate;

    public void send(User user) {
        System.out.println("Sender object: " + user.toString());
        this.rabbitTemplate.convertAndSend("object", user);
    }
}
