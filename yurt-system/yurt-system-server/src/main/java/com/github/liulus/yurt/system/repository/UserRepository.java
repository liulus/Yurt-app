package com.github.liulus.yurt.system.repository;

import com.github.liulus.yurt.jdbc.annotation.Select;
import com.github.liulus.yurt.system.model.entity.User;
import com.github.liulus.yurt.jdbc.JdbcRepository;

/**
 * @author liulu
 * @version 1.0
 * created_at 2020/5/26
 */
public interface UserRepository extends JdbcRepository<User> {


    @Select(where = "username = :param")
    User selectByUsername(String username);
}
