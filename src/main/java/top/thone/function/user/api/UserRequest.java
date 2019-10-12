package top.thone.function.user.api;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * @Author thone
 * @Description //TODO
 * @Date 11:56 AM-2019/9/4
 **/
@Data
public class UserRequest {
    @NotEmpty(message = "账户不能为空")
    private String account;

    @NotEmpty(message = "密码不能为空")
    private String password;

    @NotEmpty(message = "昵称不能为空")
    private String nickname;
}
