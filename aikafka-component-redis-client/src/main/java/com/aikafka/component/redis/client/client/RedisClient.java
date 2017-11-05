/**
 * All rights Reserved, Designed By MiGu Copyright(C) 2017 Company MiGu Co., Ltd.
 */
package com.aikafka.component.redis.client.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * TODO〈一句话类描述〉 项目名称:咪咕合管 包名称: com.abc.redis 类名称: SessionClient 类描述: TODO〈类详细描述〉 创建人: pengjun_a 创建时间:2017年9月28日
 * 下午5:48:34 版本： V1.0.0
 */
@Component
public class RedisClient {

    private static final String SESSION_PREFIX = "spring:session:sessions:";
    private static final String ATTR_PREFIX = "sessionAttr:";
    public static final String ATTR_TASK_TYPE_LIST = ATTR_PREFIX + "taskTypeList";
    public static final String ATTR_LOGIN_NAME = ATTR_PREFIX + "LOGIN_NAME";
    public static final String ATTR_SESSION_TICKET = ATTR_PREFIX + "SESSION_TICKET";
    public static final String ATTR_DOMAIN_FLAG = ATTR_PREFIX + "domain";
    private static final String ATTR_LOGIN_STAFF = ATTR_PREFIX + "LOGIN_STAFF";
    @Qualifier("redisSessionTemplate")
    @Autowired
    RedisTemplate<String, Object> redisTemplate;



    public boolean isSessionExist(String ticket) {
        String sessionId = SESSION_PREFIX + ticket;
        System.out.println(redisTemplate.hasKey(sessionId));
        return redisTemplate.hasKey(sessionId);

    }

    public boolean isSessionExist(HttpSession httpSession) {
        return isSessionExist(httpSession.getId());
    }


    public HttpSession getSession(String id) {
        throw new RuntimeException("method not implement.");
    }

    public List<String> getTaskTypeList(String id) {
        String sessionId = SESSION_PREFIX + id;

        return (List<String>) redisTemplate.opsForHash().get(sessionId, ATTR_TASK_TYPE_LIST);
    }

    public String getDomain(String id) {
        String sessionId = SESSION_PREFIX + id;
        return (String) redisTemplate.opsForHash().get(sessionId, ATTR_DOMAIN_FLAG);
    }

    public String getLoginName(String id) {
        String sessionId = SESSION_PREFIX + id;
        return (String) redisTemplate.opsForHash().get(sessionId, ATTR_LOGIN_NAME);

    }

    public String getSessionTicket(String id) {
        String sessionId = SESSION_PREFIX + id;
        return (String) redisTemplate.opsForHash().get(sessionId, ATTR_SESSION_TICKET);

    }

    public <T> T getAttribute(String id, String attr, Class<T> clazz) {
        String sessionId = SESSION_PREFIX + id;
        String actAttr = ATTR_PREFIX + attr;
        return (T) redisTemplate.opsForHash().get(sessionId, actAttr);
    }

    public void setAttribute(String id, String attr, Object value) {
        String sessionId = SESSION_PREFIX + id;
        String actAttr = ATTR_PREFIX + attr;
        redisTemplate.opsForHash().put(sessionId, actAttr, value);
    }


    @PostConstruct
    public void postConstruct() {
        /*
         * StringRedisSerializer serializer = new StringRedisSerializer();
         * redisTemplate.setKeySerializer(serializer); redisTemplate.setValueSerializer(serializer);
         * redisTemplate.setHashKeySerializer(serializer);
         * redisTemplate.setHashValueSerializer(serializer);
         * redisTemplate.setDefaultSerializer(serializer);
         */
    }


    public void deleteSession(String sessionId) {
        redisTemplate.opsForHash().delete(sessionId);
    }
}

