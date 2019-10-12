package top.thone.config;

import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import top.thone.util.Env;
import top.thone.annotation.ExcludeInterceptor;
import top.thone.factory.WebSessionFactory;
import top.thone.util.CodeUtil;
import top.thone.util.HttpUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author thone
 * @Description //TODO
 * @Date 12:28 PM-2019/9/2
 **/
@Configuration
public class WebMvcConfiguration extends WebMvcConfigurationSupport {
    @Autowired
    @Qualifier("redisSessionFactory")
    private WebSessionFactory sessionFactory;
    /**
     * FastJson配置
     */
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(fastJsonHttpMessageConverters());
    }

    @Bean
    public HttpMessageConverter fastJsonHttpMessageConverters() {
        //1. 需要定义一个converter转换消息的对象
        FastJsonHttpMessageConverter fasHttpMessageConverter = new FastJsonHttpMessageConverter();
        //2. 添加fastjson的配置信息，比如:是否需要格式化返回的json的数据
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        fastJsonConfig.setSerializeFilters(new JsonColumnFilter());
        //3. 处理中文乱码问题
        List<MediaType> fastMediaTypes = new ArrayList<>();
        fastMediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
        fasHttpMessageConverter.setSupportedMediaTypes(fastMediaTypes);
        //4. 在converter中添加配置信息
        fasHttpMessageConverter.setFastJsonConfig(fastJsonConfig);

        return fasHttpMessageConverter;
    }

    @Bean
    public SecurityInterceptor getSecurityInterceptor() {
        return new SecurityInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(getSecurityInterceptor());
        super.addInterceptors(registry);
    }

    protected class SecurityInterceptor extends HandlerInterceptorAdapter {
        @Override
        public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            ExcludeInterceptor excludeInterceptor = handlerMethod.getMethodAnnotation(ExcludeInterceptor.class);
            boolean pass = true;
            if(excludeInterceptor==null || !excludeInterceptor.value()) {//没有加入白名单
                if(request.getHeader(Env.SESSION_KEY)!=null && !request.getHeader(Env.SESSION_KEY).contains(String.valueOf(request.getHeader(Env.X_CLIENT)))){
                    response.setStatus(401);
                    response.sendError(401, "跨端无权访问");
                    return false;
                }
                pass = request.getHeader(Env.SESSION_KEY) != null && sessionFactory.hasSession(request.getHeader(Env.SESSION_KEY));
                if ("OPTIONS".equals(request.getMethod())) {
                    if (response != null) {
                        response.setStatus(200);
                        response.sendError(200, "通过");
                    }
                    return false;
                }
                if (!pass) {
                    response.setStatus(401);
                    response.sendError(401, "登录状态失效");
                    return false;
                }
            }
            Object userId = sessionFactory.getId(request.getHeader(Env.SESSION_KEY));
            if (userId!=null && !CodeUtil.isInteger(String.valueOf(userId))) {
                response.setStatus(401);
                response.sendError(401, "登录状态失效");
                return false;
            }
            String client = "dayu:other";
            if (request.getHeader(Env.X_CLIENT) != null) {
                client = request.getHeader(Env.X_CLIENT);
            }
            request.getSession().setAttribute(Env.USER_KEY, userId);
            request.getSession().setAttribute(Env.CLIENT_KEY, client);
            request.getSession().setAttribute(Env.IP, HttpUtil.getIpAddress(request));
            return pass;
        }
    }
}
