package com.github.liulus.yurt.system.repository;

import com.github.liulus.yurt.system.model.entity.UserRole;
import com.github.liulus.yurt.jdbc.JdbcRepository;

import java.util.List;

/**
 * @author liulu
 * @version 1.0
 * created_at 2020/5/26
 */
public interface UserRoleRepository extends JdbcRepository<UserRole> {
    List<UserRole> selectByUserId(Long userId);
}
