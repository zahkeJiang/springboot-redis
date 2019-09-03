package top.thone.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author thone
 * @Description 用于添加接口白名单的注解。
 *              默认拦截所有接口（header中提供session才可访问），通过此注解加入白名单
 * @Date 8:24 PM-2019/9/2
 **/
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ExcludeInterceptor {
    boolean value() default true;
}
