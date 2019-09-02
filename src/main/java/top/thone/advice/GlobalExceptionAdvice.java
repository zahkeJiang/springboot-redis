package top.thone.advice;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import top.thone.entity.Status;
import top.thone.factory.StatusFactory;
import top.thone.function.test.api.Test;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author thone
 * @Description //TODO
 * @Date 5:13 PM-2019/8/31
 **/
@RestControllerAdvice
public class GlobalExceptionAdvice {

    @ExceptionHandler(value = Exception.class)
    public Status defaultErrorHandler(HttpServletRequest req, Exception e) throws Exception {
        e.printStackTrace();

        Test t = new Test();
        t.setId(1L);
        t.setName(null);
        return StatusFactory.S_0_OK.report(t).notice("服务异常，紧急修复中。");
    }

}
