package com.cyyz.shirocas.shiro;

import com.cyyz.shirocas.utils.SerializeUtis;
import org.apache.commons.lang3.SerializationUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.SimpleSession;
import org.apache.shiro.session.mgt.ValidatingSession;
import org.apache.shiro.session.mgt.eis.CachingSessionDAO;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.apache.shiro.util.CollectionUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.jedis.JedisUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * Created by Administrator on 2016/1/28.
 */
public class ShiroSessionDAO extends CachingSessionDAO {

    private static final Logger logger = LoggerFactory.getLogger(ShiroSessionDAO.class);

    //保存到redis中的前綴 prefix + sessionId
    private String prefix = "";

    //設置會話過期時間
    private int seconds = 0;

    //沒有redis時，將session放到EhCache中
    private Boolean onlyEhCache;

    @Autowired
    private Jedis jedis;

    @Override
    public Session readSession(Serializable sessionId) {
        Session cached = null;
        cached = super.getCachedSession(sessionId);
        if (onlyEhCache) {
            return cached;
        }

        if (cached == null || cached.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY) == null) {
            try {
                cached = this.doReadSession(sessionId);
                if (cached == null) {
                    throw new UnknownSessionException();
                } else {
                    //重置Redis中緩存過期時間并緩存起來
                    ((ShiroSession) cached).setChanged(true);
                    super.update(cached);
                }
            } catch (UnknownSessionException e) {
                logger.warn("There is no session with id [" + sessionId + "]");
            }
        }
        return cached;
    }

    @Override
    protected void doUpdate(Session session) {

        try {
            if (session instanceof ValidatingSession && !((ValidatingSession) session).isValid()) {
                return;
            }
        } catch (Exception e) {
            logger.error("ValidatingSession error");
        }

        if (onlyEhCache) {
            return;
        }
        try {
            if (session instanceof ShiroSession) {
                //如果没有主要字段(除lastAccessTime以外的其他字段)发生改变
                ShiroSession ss = (ShiroSession) session;
                if (!ss.isChanged()) {
                    return;
                }
                Transaction tx = null;
                try {
                    tx = jedis.multi();
                    ss.setChanged(false);
                    ss.setLastAccessTime(DateTime.now().toDate());
                    tx.setex(prefix + session.getId(), seconds, SerializeUtis.serializeToString(ss));
                    logger.info("shiro session id {} 被更新," + session.getId() + session.getClass().getName());
                    tx.exec();
                } catch (Exception e) {
                    if (tx != null) {
                        tx.discard();
                    }

                }
            } else if (session instanceof Serializable) {
                jedis.setex(prefix + session.getId(), seconds, SerializeUtis.serializeToString((Serializable) session));
                logger.info("ID {} classname {} 作为非ShiroSession的对象被更新", session.getId(), session.getClass().getName());
            }
        } catch (Exception e) {
            logger.warn("更新Session失败", e);
        } finally {
            //FIXME
        }
    }

    @Override
    protected void doDelete(Session session) {

        try {
            jedis.del(prefix + session.getId());
            logger.info("shiro session id {}被删除", session.getId());
        } catch (Exception e) {
            logger.warn("删除Session失败", e);
        } finally {
            //FIXME
        }
    }

    @Override
    protected Serializable doCreate(Session session) {
        Serializable sessionId = this.generateSessionId(session);
        assignSessionId(session, sessionId);
        if (onlyEhCache) {
            return sessionId;
        }
        try {
            session.setTimeout(seconds);
            jedis.setex(prefix + sessionId, seconds, SerializeUtis.serializeToString((ShiroSession) session));
            logger.info("shiro session id {}被创建" + sessionId);
        } catch (Exception e) {
            logger.warn("创建Session失败", e);
        } finally {
            //FIXME
        }
        return sessionId;
    }

    /**
     * 从Rdis中读取Session，并重置过期时间
     *
     * @param sessionId
     * @return
     */
    @Override
    protected Session doReadSession(Serializable sessionId) {
        Session session = null;
        try {
            String key = prefix + sessionId;
            String value = jedis.get(key);
            if (StringUtils.isNoneBlank(value)) {
                session = SerializeUtis.deserializeFromString(value);
                logger.info("shiro session id {}被读取", sessionId);
            }

        } catch (Exception e) {
            logger.warn("读取Session失败", e);
        } finally {
            //FIXME  是否需要释放资源？

        }
        return session;
    }

    public Session doReadSessionWithOutExpire(Serializable sessionId) {
        if (onlyEhCache) {
            return readSession(sessionId);
        }

        Session session = null;
        try {
            String key = prefix + sessionId;
            String value = jedis.get(key);
            if (StringUtils.isNoneBlank(value)) {
                session = SerializeUtis.deserializeFromString(value);
            }
        } catch (Exception e) {
            logger.warn("读取Session失败");
        } finally {

            //FIXME
        }

        return session;
    }

    public void uncache(Serializable sessionId){
        try{
            Session session = super.getCachedSession(sessionId);
            super.uncache(session);
            logger.info("shiro session id {} 的缓存失效",sessionId);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public Collection<Session> getActiveSession(){
       try{
           Set<String> keys = jedis.keys(prefix+"*");
           if(CollectionUtils.isEmpty(keys)){
               return  null;
           }
           List<String> valueList = jedis.mget((keys.toArray(new String[0])));
           return SerializeUtis.deserializeFromStringController(valueList);
       }catch (Exception e){
           logger.warn("统计Session信息失败",e);
       }finally {
           //FIXME
       }
        return  null;
    }


    public Collection<Session> getEhCacheActiveSession(){
        return super.getActiveSessions();
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public void setOnlyEhCache(Boolean onlyEhCache) {
        this.onlyEhCache = onlyEhCache;
    }
}

