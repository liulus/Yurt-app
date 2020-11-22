package com.github.liulus.yurt.system.repository;


import com.github.liulus.yurt.jdbc.JdbcRepository;
import com.github.liulus.yurt.jdbc.annotation.Select;
import com.github.liulus.yurt.system.model.entity.RoleAuthority;

import java.util.Collection;
import java.util.List;

/**
 * @author liulu
 * @version 1.0
 * created_at 2020/5/26
 */
public interface RoleAuthorityRepository extends JdbcRepository<RoleAuthority> {

    @Select(where = "role_id = :param")
    List<RoleAuthority> selectByRoleId(Long roleId);

    @Select(where = "role_id in (:param)")
    List<RoleAuthority> selectByRoleIds(Collection<Long> roleIds);

}
