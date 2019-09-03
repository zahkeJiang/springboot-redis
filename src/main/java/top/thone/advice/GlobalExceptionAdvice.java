package top.thone.advice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import top.thone.entity.Status;
import top.thone.factory.StatusFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * @Author thone
 * @Description 全局错误拦截处理
 * @Date 5:13 PM-2019/8/31
 **/
@RestControllerAdvice
public class GlobalExceptionAdvice {
    private Logger logger = LoggerFactory.getLogger(GlobalExceptionAdvice.class);

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Status handle(Exception e) throws Exception {
        logger.error("错误的请求 " + e.getMessage(), e);
        return StatusFactory.S_2000_SERVER_DATA_ERROR.error(e.getMessage()).notice("服务异常，紧急修复中。");
    }

    @ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
    @ResponseBody
    public Status handle(HttpRequestMethodNotSupportedException e) {
        logger.error("错误的请求 " + e.getMessage(), e);
        String supportMethod = (e.getSupportedMethods() == null || e.getSupportedMethods().length == 0) ? null : Arrays.toString(e.getSupportedMethods());
        return StatusFactory.S_2000_SERVER_DATA_ERROR.error("本次请求，服务端不支持" + e.getMethod() + "方法，仅支持" + supportMethod + "中的方法");
    }
}
