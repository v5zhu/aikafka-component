package com.aikafka.component.mongodb.dao;

import com.aikafka.component.mongodb.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

/**
 * All rights Reserved, Designed By MiGu Copyright(C) 2017 Company MiGu Co., Ltd.
 * <p>
 * 〈一句话类描述〉 项目名称:咪咕合管 包名称:   com.aikafka.component.mongodb.dao 类名称:   UserDaoImpl 类描述: 创建人:   zhuxiaolong@aspirecn.com
 * 创建时间: 2017-11-10 9:45 版本：   V1.0.0
 *
 * @Author: zhuxiaolong@aspirecn.com
 */
@Component
public class UserDaoImpl implements UserDao{

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public void saveUser(User user) {
        mongoTemplate.save(user);
    }

    @Override
    public User findUserByUsername(String username) {
        Query query=new Query(Criteria.where("username").is(username));
        User user =  mongoTemplate.findOne(query , User.class);
        return user;
    }

    @Override
    public void updateUser(User user) {
        Query query=new Query(Criteria.where("id").is(user.getId()));
        Update update= new Update().set("username", user.getUsername()).set("password", user.getPassword());
        //更新查询返回结果集的第一条
        mongoTemplate.updateFirst(query,update,User.class);
        //更新查询返回结果集的所有
        // mongoTemplate.updateMulti(query,update,UserEntity.class);
    }

    @Override
    public void deleteUserById(Long id) {
        Query query=new Query(Criteria.where("id").is(id));
        mongoTemplate.remove(query,User.class);
    }
}
