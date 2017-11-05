package com.aikafka.component.session.redis.init;

import com.aikafka.component.session.redis.config.SpringRedisClusterConfigFactory;
import org.springframework.session.web.context.AbstractHttpSessionApplicationInitializer;

/**
 * 加载RedisHttpSessionConfiguration配置文件 定义springSessionRepositoryFilter拦截所有的请求将session封装为spring session
 */

public class HttpSessionApplicationInitializer extends AbstractHttpSessionApplicationInitializer {

    public HttpSessionApplicationInitializer() {
        super(SpringRedisClusterConfigFactory.class);
    }

}
