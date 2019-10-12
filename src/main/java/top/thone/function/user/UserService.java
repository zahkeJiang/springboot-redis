package top.thone.function.user;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import top.thone.entity.Status;
import top.thone.factory.StatusFactory;
import top.thone.factory.WebSessionFactory;
import top.thone.function.user.api.UserRequest;
import top.thone.function.user.api.UserResponse;
import top.thone.pojo.User;
import top.thone.repository.UserRepository;

/**
 * @Author thone
 * @Description //TODO
 * @Date 11:51 AM-2019/9/4
 **/
@Service
public class UserService {
    @Autowired
    @Qualifier("redisSessionFactory")
    private WebSessionFactory sessionFactory;
    @Autowired
    private UserRepository userRepository;
    /**
     * 用户注册处理逻辑
     * @param request
     * @return
     */
    public Status register(UserRequest request) {
        User user = userRepository.findByAccount(request.getAccount()).orElse(null);
        if (user != null){
            return StatusFactory.S_2000_SERVER_DATA_ERROR.notice("此账户已存在");
        }
        user = new User();
        user.setAccount(request.getAccount());
        user.setNickname(request.getNickname());
        user.setPassword(request.getPassword());
        User save = userRepository.save(user);
        UserResponse response = loadUserResponse(save);
        return StatusFactory.S_0_OK.report(response);
    }

    /**
     * 装配返回数据
     * @param save
     * @return
     */
    private UserResponse loadUserResponse(User save) {
        UserResponse response = new UserResponse();
        BeanUtils.copyProperties(save, response, UserResponse.class);
        return response;
    }

    /**
     * 登录
     * @param request
     * @param client
     * @return
     */
    public Status login(UserRequest request, String client) {
        User user = userRepository.findByAccount(request.getAccount()).orElse(null);
        if (user == null){
            return StatusFactory.S_2000_SERVER_DATA_ERROR.notice("账户不存在");
        }

        if (!user.getPassword().equals(request.getPassword())){
            return StatusFactory.S_2000_SERVER_DATA_ERROR.notice("密码错误");
        }
        String session;
        if (sessionFactory.hasKey(user.getId(), client)){
            session = sessionFactory.getSession(user.getId(), client);
        }else {
            session = sessionFactory.add(client, user.getId());
        }

        return StatusFactory.S_0_OK.report(session);
    }
}
