package com.aikafka.component.rabbitmq.domain;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * All rights Reserved, Designed By MiGu Copyright(C) 2017 Company MiGu Co., Ltd.
 * <p>
 * 〈一句话类描述〉 项目名称:咪咕合管 包名称:   com.aikafka.component.rabbitmq.domain 类名称:   User 类描述: 创建人:   zhuxiaolong@aspirecn.com
 * 创建时间: 2017-11-10 12:06 版本：   V1.0.0
 *
 * @Author: zhuxiaolong@aspirecn.com
 */
@Data
@ToString
public class User implements Serializable{

    private Long id;

    private String username;

    private String password;
}
