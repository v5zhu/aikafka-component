package com.aikafka.component.redis.client.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * redis集群配置
 * 项目名称:咪咕合管
 * 包名称: com.migu.tsg.pms.platform.component.portal.service.config
 * 类名称: RedisConfig
 * 类描述:
 * 创建人: zhuxiaolong@aspirecn.com
 * 创建时间:2017/10/11
 * 版本： V1.0.0
 */
@ConfigurationProperties(prefix = "redisCluster")
@Component
@Data
public class RedisClusterConfig {
    private List<String> nodes;

    private boolean withPassword;

    private String password;

    private int maxConnections;

    private long maxWaitMillis;

    private long connectionTimeout;

    private int maxAttempts;
}
