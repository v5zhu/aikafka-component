package com.aikafka.component.mongodb.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * All rights Reserved, Designed By MiGu Copyright(C) 2017 Company MiGu Co., Ltd.
 * <p>
 * 〈一句话类描述〉 项目名称:咪咕合管 包名称:   com.aikafka.component.mongodb.docs 类名称:   User 类描述:   存储到mongodb的user信息 创建人:
 * zhuxiaolong@aspirecn.com 创建时间: 2017-11-09 22:00 版本：   V1.0.0
 *
 * @Author: zhuxiaolong@aspirecn.com
 */
@Data
@AllArgsConstructor
public class User implements Serializable{
    private Long id;

    private String username;

    private String password;
}
