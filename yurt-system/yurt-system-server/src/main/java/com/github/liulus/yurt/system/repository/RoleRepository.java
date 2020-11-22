package com.github.liulus.yurt.system.repository;

import com.github.liulus.yurt.jdbc.JdbcRepository;
import com.github.liulus.yurt.jdbc.annotation.Select;
import com.github.liulus.yurt.system.model.entity.Role;

import java.util.List;

/**
 * @author liulu
 * @version 1.0
 * created_at 2020/5/26
 */
public interface RoleRepository extends JdbcRepository<Role> {

    @Select(leftJoin = "user_role ur on role.id = ur.role_id", where = "ur.user_id = :param")
    List<Role> selectByUserId(Long userId);
}
