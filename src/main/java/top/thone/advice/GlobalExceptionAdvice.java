package top.thone.advice;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import top.thone.entity.Status;
import top.thone.factory.StatusFactory;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author thone
 * @Description 全局错误拦截处理
 * @Date 5:13 PM-2019/8/31
 **/
@RestControllerAdvice
public class GlobalExceptionAdvice {

    @ExceptionHandler(value = Exception.class)
    public Status defaultErrorHandler(HttpServletRequest req, Exception e) throws Exception {
        e.printStackTrace();
        return StatusFactory.S_2000_SERVER_DATA_ERROR.notice("服务异常，紧急修复中。");
    }

}
