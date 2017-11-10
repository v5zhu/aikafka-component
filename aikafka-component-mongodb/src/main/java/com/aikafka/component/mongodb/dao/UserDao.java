package com.aikafka.component.mongodb.dao;

import com.aikafka.component.mongodb.domain.User;

/**
 * All rights Reserved, Designed By MiGu Copyright(C) 2017 Company MiGu Co., Ltd.
 * <p>
 * 〈一句话类描述〉 项目名称:咪咕合管 包名称:   com.aikafka.component.mongodb.dao 类名称:   UserDao 类描述:   ${DESCRIPTION} 创建人:
 * zhuxiaolong@aspirecn.com 创建时间: 2017-11-10 9:43 版本：   V1.0.0
 *
 * @Author: zhuxiaolong@aspirecn.com
 */
public interface UserDao {
    void saveUser(User user);

    User findUserByUsername(String username);

    void updateUser(User user);

    void deleteUserById(Long id);
}
