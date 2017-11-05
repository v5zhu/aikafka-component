package com.aikafka.component.session.redis.config;

import com.aikafka.component.redis.client.config.RedisClusterConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisNode;
import org.springframework.data.redis.connection.jedis.JedisClusterConnection;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.session.data.redis.RedisFlushMode;
import org.springframework.session.data.redis.config.ConfigureRedisAction;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import redis.clients.jedis.JedisPoolConfig;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * RedisHttpSessionConfiguration的配置文件 引入RedisHttpSessionConfiguration.class
 * maxInactiveIntervalInSeconds设置session在redis里的超时
 */
@Configuration
@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 3600*8, redisFlushMode = RedisFlushMode.IMMEDIATE)
public class SpringRedisClusterConfigFactory {
    @Autowired
    private RedisClusterConfig redisClusterConfig;


    @Bean
    public static ConfigureRedisAction configureRedisAction() {
        return ConfigureRedisAction.NO_OP;
    }

    @Bean
    public JedisConnectionFactory jedisConnectionFactory() {
        // return connectionFactory();
        JedisConnectionFactory jedisConnectionFactory =
                new JedisConnectionFactory(redisClusterConfiguration(), jedisPoolConfig());

        jedisConnectionFactory.setUsePool(true);
        jedisConnectionFactory.setTimeout(100000);

        // jedisConnectionFactory.setHostName("localhost");
        if (redisClusterConfig.isWithPassword()) {
            jedisConnectionFactory.setPassword(redisClusterConfig.getPassword());
        }

        return jedisConnectionFactory;
    }


    @Bean
    public JedisClusterConnection jedisClusterConnection() {
        return (JedisClusterConnection) jedisConnectionFactory().getConnection();
    }


    /**
     * redis集群配置 配置redis集群的结点及其它一些属性
     *
     * @return
     */
    private RedisClusterConfiguration redisClusterConfiguration() {
        RedisClusterConfiguration redisClusterConfig = new RedisClusterConfiguration();

        redisClusterConfig.setClusterNodes(getClusterNodes());

        redisClusterConfig.setMaxRedirects(3);
        return redisClusterConfig;

    }

    /**
     * JedisPoolConfig 配置
     * <p>
     * 配置JedisPoolConfig的各项属性
     *
     * @return
     */
    private JedisPoolConfig jedisPoolConfig() {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        // 连接耗尽时是否阻塞, false报异常,ture阻塞直到超时, 默认true
        jedisPoolConfig.setBlockWhenExhausted(true);

        // 是否启用pool的jmx管理功能, 默认true
        jedisPoolConfig.setJmxEnabled(true);

        // 默认就好
        // jedisPoolConfig.setJmxNamePrefix("pool");

        // jedis调用returnObject方法时，是否进行有效检查
        jedisPoolConfig.setTestOnReturn(true);

        // 是否启用后进先出, 默认true
        jedisPoolConfig.setLifo(true);

        // 最大空闲连接数, 默认8个
        jedisPoolConfig.setMaxIdle(8);

        // 最大连接数, 默认8个
        jedisPoolConfig.setMaxTotal(redisClusterConfig.getMaxConnections());

        // 获取连接时的最大等待毫秒数(如果设置为阻塞时BlockWhenExhausted),如果超时就抛异常, 小于零:阻塞不确定的时间,
        // 默认-1
        jedisPoolConfig.setMaxWaitMillis(redisClusterConfig.getMaxWaitMillis());

        // 逐出连接的最小空闲时间 默认1800000毫秒(30分钟)
        jedisPoolConfig.setMinEvictableIdleTimeMillis(1800000);


        // 最小空闲连接数, 默认0
        jedisPoolConfig.setMinIdle(3);

        // 每次逐出检查时 逐出的最大数目 如果为负数就是 : 1/abs(n), 默认3
        jedisPoolConfig.setNumTestsPerEvictionRun(3);

        // 对象空闲多久后逐出, 当空闲时间>该值 且 空闲连接>最大空闲数
        // 时直接逐出,不再根据MinEvictableIdleTimeMillis判断 (默认逐出策略)
        jedisPoolConfig.setSoftMinEvictableIdleTimeMillis(1800000);

        // 在获取连接的时候检查有效性, 默认false
        jedisPoolConfig.setTestOnBorrow(false);

        // 在空闲时检查有效性, 默认false
        jedisPoolConfig.setTestWhileIdle(false);

        // 逐出扫描的时间间隔(毫秒) 如果为负数,则不运行逐出线程, 默认-1
        jedisPoolConfig.setTimeBetweenEvictionRunsMillis(-1);

        return jedisPoolConfig;
    }

    /**
     * redis集群节点IP和端口的添加 节点：RedisNode redisNode = new RedisNode("127.0.0.1",6379);
     *
     * @return
     */
    private Set<RedisNode> getClusterNodes() {
        // 添加redis集群的节点
        Set<RedisNode> clusterNodes = new HashSet<RedisNode>();
        List<String> nodes = redisClusterConfig.getNodes();
        for (String node : nodes) {
            String[] info = node.split(":");
            clusterNodes.add(new RedisNode(info[0], Integer.parseInt(info[1])));
        }

        // 这三个主节点是我本机的IP和端口,从节点没有加入 ，这里不是我真实的IP，虽然是内网，还是不要太直接了
        // clusterNodes.add(new RedisNode("211.139.191.145", 6380));
        // clusterNodes.add(new RedisNode("211.139.191.145", 6381));
        // clusterNodes.add(new RedisNode("211.139.191.145", 6382));

        return clusterNodes;
    }


    @Bean(name = "redisSessionTemplate")
    public RedisTemplate<String, Object> redisTemplate(JedisConnectionFactory factory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<String, Object>();
        redisTemplate.setConnectionFactory(factory);
        RedisSerializer<String> serializer1 = new StringRedisSerializer();
        RedisSerializer<Object> serializer2 = new JdkSerializationRedisSerializer();
        redisTemplate.setKeySerializer(serializer1);
        redisTemplate.setValueSerializer(serializer2);
        redisTemplate.setHashKeySerializer(serializer1);
        redisTemplate.setHashValueSerializer(serializer2);

        // System.out.println("config=" + redisTemplate);
        return redisTemplate;
    }
}
