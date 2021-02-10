package com.github.liulus.yurt.system.repository

import com.github.liulus.yurt.jdbc.JdbcRepository
import com.github.liulus.yurt.jdbc.annotation.Select
import com.github.liulus.yurt.system.model.entity.User

/**
 * @author liulu
 * @version 1.0
 * created_at 2020/5/26
 */
interface UserRepository : JdbcRepository<User> {

    @Select(where = ["username = :param"])
    fun selectByUsername(username: String): User?

}