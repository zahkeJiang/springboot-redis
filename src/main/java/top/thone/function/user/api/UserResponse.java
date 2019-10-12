package top.thone.function.user.api;

import lombok.Data;
import top.thone.pojo.enums.Gender;

/**
 * @Author thone
 * @Description //TODO
 * @Date 12:06 PM-2019/9/4
 **/
@Data
public class UserResponse {
    private Long id;

    private String nickname;

    private String account;

    private Gender gender;

    private String introduce;
}
