package com.cyyz.shirocas.shiro;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.CachingSessionDAO;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import redis.clients.jedis.Jedis;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/1/27.
 */
public class MySessionDAO extends EnterpriseCacheSessionDAO {


    @Autowired
    private Jedis jedis ;

    @Autowired
    private RedisTemplate<Serializable,Serializable> redisTemplate ;

    @Override
    protected Serializable doCreate(Session session) {
        Serializable sessionId = generateSessionId(session);
        assignSessionId(session,sessionId);
        System.out.println("Create session"+session.getId());

        return sessionId;
    }

}
