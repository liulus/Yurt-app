package com.github.liulus.yurt.system.repository

import com.github.liulus.yurt.jdbc.JdbcRepository
import com.github.liulus.yurt.jdbc.annotation.Select
import com.github.liulus.yurt.system.model.entity.RoleAuthority

/**
 * @author liulu
 * @version 1.0
 * created_at 2020/5/26
 */
interface RoleAuthorityRepository : JdbcRepository<RoleAuthority> {

    @Select(where = ["role_id = :param"])
    fun selectByRoleId(roleId: Long): List<RoleAuthority>

    @Select(where = ["role_id in (:param)"])
    fun selectByRoleIds(roleIds: Collection<Long>): List<RoleAuthority>
}