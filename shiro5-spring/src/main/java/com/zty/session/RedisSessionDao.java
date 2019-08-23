package com.zty.session;

import com.zty.util.JedisUtil;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.springframework.util.CollectionUtils;
import org.springframework.util.SerializationUtils;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * 自定义RedisSessionDao
 */
public class RedisSessionDao extends AbstractSessionDAO {
    @Resource
    JedisUtil jedisUtil;
    //设置key：前缀+sessionId组成
    private final String SHIRO_SESSION_PREFIX = "redis-session:";
    //获取key
    private byte[] getKey(String sessionId){
        return (SHIRO_SESSION_PREFIX+sessionId).getBytes();
    }
    //保存session对象
    private void saveSession(Session session){
        if(session != null && session.getId() != null){
            byte[] key = getKey(session.getId().toString());//键
            byte[] value = SerializationUtils.serialize(session);//值，序列化session对象
            jedisUtil.set(key,value);//设置到缓存中
            jedisUtil.expire(key,600);//失效时间
        }
    }
    //创建session
    @Override
    protected Serializable doCreate(Session session) {
        Serializable sessionId = generateSessionId(session);
        assignSessionId(session,sessionId);//将sessionId和session绑定
        saveSession(session);
        System.out.println("create session");
        return sessionId;
    }
    //获取session对象
    @Override
    protected Session doReadSession(Serializable sessionId) {
        System.out.println("read session");
        if(sessionId == null){
            return null;
        }
        byte[] key = getKey(sessionId.toString());
        byte[] value = jedisUtil.get(key);
        //反序列化为session对象
        return (Session) SerializationUtils.deserialize(value);
    }
    //更新session对象
    @Override
    public void update(Session session) throws UnknownSessionException {
        saveSession(session);
    }
    //删除session对象
    @Override
    public void delete(Session session) {
        if(session == null && session.getId() != null){
            return;
        }
        byte[] key = getKey(session.getId().toString());
        jedisUtil.del(key);
    }
    //获取活跃的（存活的session）session
    @Override
    public Collection<Session> getActiveSessions() {
        //获取redis中所有session的key
        Set<byte[]>keys = jedisUtil.keys(SHIRO_SESSION_PREFIX);
        Set<Session>sessions = new HashSet<>();
        if(CollectionUtils.isEmpty(keys)){
            return sessions;
        }
        for (byte[] key : keys) {
            Session session = (Session) SerializationUtils.deserialize(jedisUtil.get(key));
            sessions.add(session);
        }
        return sessions;
    }
}
