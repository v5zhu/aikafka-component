package com.aikafka.component.mongodb;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * All rights Reserved, Designed By MiGu Copyright(C) 2017 Company MiGu Co., Ltd.
 * <p>
 * 〈一句话类描述〉 项目名称:咪咕合管 包名称:   com.aikafka.component.mongodb 类名称:   MongodbApplication 类描述: 创建人: zhuxiaolong@aspirecn.com
 * 创建时间: 2017-11-09 22:11 版本：   V1.0.0
 *
 * @Author: zhuxiaolong@aspirecn.com
 */
@SpringBootApplication
public class MongodbApplication {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(MongodbApplication.class);
        application.setBannerMode(Banner.Mode.OFF);
        application.run(args);

    }
}
