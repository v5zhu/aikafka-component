package com.aikafka.component.mongodb.repository;

import com.aikafka.component.mongodb.MongodbApplication;
import com.aikafka.component.mongodb.dao.UserDao;
import com.aikafka.component.mongodb.domain.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * All rights Reserved, Designed By MiGu Copyright(C) 2017 Company MiGu Co., Ltd.
 * <p>
 * 〈一句话类描述〉 项目名称:咪咕合管 包名称:   com.aikafka.component.mongodb.repository 类名称:   TestUserRepository 类描述:   测试 创建人:
 * zhuxiaolong@aspirecn.com 创建时间: 2017-11-09 22:10 版本：   V1.0.0
 *
 * @Author: zhuxiaolong@aspirecn.com
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = MongodbApplication.class)
@WebAppConfiguration
public class TestUserRepository {



    @Autowired
    private UserDao userDao;


    @Test
    public void testSaveUser() throws Exception {
        User user=new User(21L,"小明","123456654321");
        userDao.saveUser(user);
    }

    @Test
    public void findUserByUserName(){
        User user= userDao.findUserByUsername("小明");
        System.out.println("user is "+user);
    }

    @Test
    public void updateUser(){
        User user=new User(21L,"天空","fffxxx");
        userDao.updateUser(user);
    }

    @Test
    public void deleteUserById(){
        userDao.deleteUserById(21L);
    }
}
