package top.thone.factory.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;
import top.thone.util.Env;
import top.thone.factory.WebSessionFactory;
import top.thone.util.CodeUtil;

import java.lang.reflect.Constructor;
import java.util.concurrent.TimeUnit;

/**
 * Create in 09:33 2018/8/7
 *
 * @author canfuu
 * @version v1.0
 * @descriotion 描述
 */
@Component("redisSessionFactory")
@EnableScheduling
@Scope(scopeName = "singleton")
public class WebSessionFactoryRedisTemplate implements WebSessionFactory {
    @Autowired
    private RedisTemplate<String, Object> template;
    @Autowired
    private Env env;
    @Override
    public String add(String client, Object id) {
        String session = client + ":" + CodeUtil.getUUID();
        return add(client, id, session);
    }

    @Override
    public String add(String client, Object id, String session) {
        template.opsForValue().set(client + ":" + id, session, env.getSessionExpire(), TimeUnit.MILLISECONDS);
        template.opsForValue().set(session, convertToStr(id), env.getSessionExpire(), TimeUnit.MILLISECONDS);
        return session;
    }

    @Override
    public String getSession(Object id, String client) {
        Object session;
        if(id==null){
            return null;
        }
        if ((session = template.opsForValue().get(client + ":" + id)) == null) {
            return null;
        } else {
            template.expire(client + ":" + id, env.getSessionExpire(), TimeUnit.MILLISECONDS);
            Boolean b = template.expire(session.toString(), env.getSessionExpire(), TimeUnit.MILLISECONDS);
            if (b == null || !b) {
                template.opsForValue().set(session.toString(), convertToStr(id), env.getSessionExpire(), TimeUnit.MILLISECONDS);
            }
            return session.toString();
        }
    }

    @Override
    public <T> T getId(String sessionId) {
        if (sessionId == null) {
            return null;
        }
        Object id = template.opsForValue().get(sessionId);

        if (id == null) {
            return null;
        } else {
            template.expire(sessionId, env.getSessionExpire(), TimeUnit.MILLISECONDS);

            return (T) convertToId(id.toString());
        }
    }

    @Override
    public <T> T getId(String sessionId, String client) {
        if (sessionId == null) {
            return null;
        }
        Object id = template.opsForValue().get(String.valueOf(sessionId));
        if (id == null) {
            return null;
        } else {
            template.expire(sessionId, env.getSessionExpire(), TimeUnit.MILLISECONDS);
            Boolean b = template.expire(client + ":" + id, env.getSessionExpire(), TimeUnit.MILLISECONDS);
            if (b == null || !b) {
                template.opsForValue().set(client + ":" + id, sessionId, env.getSessionExpire(), TimeUnit.MILLISECONDS);
            }
            return (T) convertToId(id.toString());
        }
    }

    @Override
    public boolean hasSession(Object key) {
        try {
            if (key == null) {
                return false;
            }
            Boolean b = template.expire(String.valueOf(key), env.getSessionExpire(), TimeUnit.MILLISECONDS);
            return b == null ? false : b;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean hasKey(Object key, String client) {
        try {

            if (key == null) {
                return false;
            }
            Boolean b;
            boolean hasSession = (b = template.expire(String.valueOf(key), env.getSessionExpire(), TimeUnit.MILLISECONDS)) == null ? false : b;
            if (!hasSession) {
                return (b = template.expire(String.valueOf(client + ":" + key), env.getSessionExpire(), TimeUnit.MILLISECONDS)) == null ? false : b;
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public void logout(String session, String client) {
        Object userIdStr = template.opsForValue().get(session);

        if (userIdStr != null) {
            Object userId = convertToId(userIdStr.toString());
            template.delete(client + ":" + userId);
            template.delete(session);
        }
    }
    private static <T> T convertToId(String classAndId){
        String className = classAndId.split("&&")[0];
        String idStr = classAndId.split("&&")[1];
        try {
            Class<T> id = (Class<T>) Class.forName(className);
            Constructor constructor = id.getDeclaredConstructor(String.class);
            Long l = CodeUtil.decode36C(idStr);
            if(l==null){
                return null;
            }
            return (T) constructor.newInstance(l.toString());
        } catch (Exception e) {
            return null;
        }
    }
    private static String convertToStr(Object id){
        return id.getClass().getName()+"&&"+CodeUtil.encode36C(Long.valueOf(String.valueOf(id)));
    }

}
