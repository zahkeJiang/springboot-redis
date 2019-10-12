package top.thone.function.user.api;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * @Author thone
 * @Description //TODO
 * @Date 2:05 PM-2019/9/4
 **/
@Data
public class LoginRequest {
    @NotEmpty(message = "账户不能为空")
    private String account;

    @NotEmpty(message = "密码不能为空")
    private String password;
}
