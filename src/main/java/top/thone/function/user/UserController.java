package top.thone.function.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.thone.util.Env;
import top.thone.annotation.ExcludeInterceptor;
import top.thone.entity.Status;
import top.thone.function.user.api.UserRequest;

/**
 * @Author thone
 * @Description //TODO
 * @Date 11:51 AM-2019/9/4
 **/
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;


    /**
     * 注册接口
     * @param request
     * @return
     */
    @ResponseBody
    @PostMapping
    @ExcludeInterceptor
    public Status register(@RequestBody UserRequest request){
        return userService.register(request);
    }

    @ResponseBody
    @PostMapping("/auth")
    @ExcludeInterceptor
    public Status login(@RequestBody UserRequest request,
                        @RequestHeader(Env.X_CLIENT) String client){
        return userService.login(request, client);
    }

}
