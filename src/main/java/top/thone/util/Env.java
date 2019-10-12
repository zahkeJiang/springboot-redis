package top.thone.util;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

/**
 * @Author thone
 * @Description //TODO
 * @Date 4:39 PM-2019/9/2
 **/
@Component
@EnableScheduling
@Scope(scopeName = "singleton")
@ConfigurationProperties(prefix = "env-config")
@ConditionalOnMissingClass
public class Env {

    private Long sessionExpire;
    public static final String SESSION_KEY = "session";
    public static final String CLIENT_KEY = "client";
    public static final String USER_KEY = "user_key";
    public static final String X_CLIENT = "X-Client";
    public static final String IP = "IP";

    public Long getSessionExpire() {
        return sessionExpire;
    }

    public void setSessionExpire(Long sessionExpire) {
        this.sessionExpire = sessionExpire;
    }

}
