package top.thone.aspect;

import com.alibaba.fastjson.JSON;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import top.thone.util.Env;
import top.thone.entity.Log;
import top.thone.factory.StatusFactory;
import top.thone.util.CodeUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * @Author thone
 * @Description //TODO
 * @Date 12:06 PM-2019/9/3
 **/
@Aspect
@Component
public class ControllerAspect implements Ordered {
    private Logger logger = LoggerFactory.getLogger(ControllerAspect.class);

    @Pointcut("execution(@org.springframework.web.bind.annotation.ResponseBody * top.thone..*(..))")
    public void log() {
    }

    @Pointcut("execution( * top.thone.advice.GlobalExceptionAdvice.handle(..))")
    public void handle() {

    }

    @Around("log() && !handle()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        String uid = CodeUtil.getNumberRandom(6);
        Log log = new Log();
        log.setTimestamp(System.currentTimeMillis());

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpServletResponse response = attributes.getResponse();
        HttpServletRequest request = attributes.getRequest();
        log.setMethod(request.getMethod());
        if (request.getMethod() == "OPTIONS") {
            if (response != null) {
                response.setStatus(200);
            }
            return StatusFactory.S_0_OK.notice("pass");
        }
        log.setUrl(String.valueOf(request.getRequestURL()));
        log.setIp(String.valueOf(request.getSession().getAttribute(Env.IP)));
        String session;
        if ((session = request.getHeader(Env.SESSION_KEY)) != null) {
            HttpSession httpSession = request.getSession();
            if (httpSession != null) {
                session = session + "::" + httpSession.getAttribute(Env.USER_KEY);
            }
        }
        log.setSession(session);
        Map<String, String[]> parameterMap = request.getParameterMap();
        StringBuilder body = new StringBuilder();
        parameterMap.keySet().forEach(key ->{
            String[] strings = parameterMap.get(key);
            for (String string : strings) {
                body.append(":" + key + "=" + string);
            }
        });

        logger.info("[{}] [{}] (p*･ω･)p-~-~ [{}] resp: [{}]", session, uid, request.getMethod(), request.getRequestURL());
        logger.info("[{}] [{}] (p*･ω･)p-~-~ req: [{}]", session, uid, body.toString());

        Object[] args = joinPoint.getArgs();
        Object result = joinPoint.proceed(args);
        log.setResponse(JSON.toJSONString(result));
        logger.info("[{}] [{}] (p*･ω･)p-~-~ resp: [{}]", session, uid, log.getResponse());

        // TODO: 2019/9/3 log用于存储相关日志信息。
        return result;
    }

    @Override
    public int getOrder() {
        return 1;
    }
}
