package com.github.liulus.yurt.system.repository

import com.github.liulus.yurt.jdbc.JdbcRepository
import com.github.liulus.yurt.jdbc.annotation.Select
import com.github.liulus.yurt.system.model.dto.AuthorityQo
import com.github.liulus.yurt.system.model.dto.MenuDTO
import com.github.liulus.yurt.system.model.entity.Organization
import com.github.liulus.yurt.system.model.entity.Role
import com.github.liulus.yurt.system.model.entity.SysParam
import com.github.liulus.yurt.system.model.entity.RoleAuthority
import com.github.liulus.yurt.system.model.entity.UserRole

/**
 * @author liulu
 * @version 1.0
 * created_at 2020/5/26
 */
interface RoleRepository : JdbcRepository<Role?> {
    @Select(leftJoin = "user_role ur on role.id = ur.role_id", where = ["ur.user_id = :param"])
    fun selectByUserId(userId: Long?): List<Role?>?
}