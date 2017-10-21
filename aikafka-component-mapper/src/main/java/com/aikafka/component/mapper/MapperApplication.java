package com.aikafka.component.mapper;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * TODO〈一句话类描述〉
 * 项目名称:咪咕合管
 * 包名称: com.aikafka.component.mapper
 * 类名称: MapperApplication
 * 类描述:
 * 创建人: zhuxiaolong@aspirecn.com
 * 创建时间:2017/10/21
 * 版本： V1.0.0
 */
@SpringBootApplication
public class MapperApplication {
    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(MapperApplication.class);
        application.setBannerMode(Banner.Mode.OFF);
        application.run(args);

    }
}
