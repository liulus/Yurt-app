package com.github.liulus.yurt.system.repository

import com.github.liulus.yurt.jdbc.JdbcRepository
import com.github.liulus.yurt.system.model.entity.UserRole

/**
 * @author liulu
 * @version 1.0
 * created_at 2020/5/26
 */
interface UserRoleRepository : JdbcRepository<UserRole?> {
    fun selectByUserId(userId: Long?): List<UserRole?>?
}