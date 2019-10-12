package top.thone.repository;

import top.thone.pojo.User;

import java.util.Optional;

public interface UserRepository extends BaseRepository<User, Long> {

    Optional<User> findByAccount(String account);
}
