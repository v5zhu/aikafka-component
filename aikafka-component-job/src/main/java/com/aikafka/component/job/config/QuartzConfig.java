package com.aikafka.component.job.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * TODO〈一句话类描述〉
 * 项目名称:咪咕合管
 * 包名称: com.aikafka.component.job.config
 * 类名称: QuartzConfig
 * 类描述:
 * 创建人: zhuxiaolong@aspirecn.com
 * 创建时间:2017/10/23
 * 版本： V1.0.0
 */
@ConfigurationProperties(prefix = "quartz")
@Configuration
@Data
public class QuartzConfig {
    private boolean cluster=false;

    private String driver;

    private String url;

    private String username;

    private String password;

    private String tablePrefix;
}
