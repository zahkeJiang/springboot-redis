package top.thone.function.test;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import top.thone.annotation.ExcludeInterceptor;
import top.thone.entity.Status;
import top.thone.factory.StatusFactory;

/**
 * @Author thone
 * @Description //TODO
 * @Date 5:08 PM-2019/8/31
 **/
@RestController
@RequestMapping("/test")
public class TestController {

    @ResponseBody
    @GetMapping("/hello")
    @ExcludeInterceptor
    public Status hello() {
        return StatusFactory.S_0_OK.report("hello spring boot");
    }
}
