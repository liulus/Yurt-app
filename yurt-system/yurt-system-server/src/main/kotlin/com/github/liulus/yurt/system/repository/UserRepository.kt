package com.github.liulus.yurt.system.repository

import com.github.liulus.yurt.jdbc.JdbcRepository
import com.github.liulus.yurt.jdbc.annotation.If
import com.github.liulus.yurt.jdbc.annotation.Select
import com.github.liulus.yurt.system.model.dto.UserDTO
import com.github.liulus.yurt.system.model.entity.User

/**
 * @author liulu
 * @version 1.0
 * created_at 2020/5/26
 */
interface UserRepository : JdbcRepository<User> {

    @Select(where = ["username = :param"])
    fun selectByUsername(username: String): User?

    @Select(
        testWheres = [
            If(test = "username !=null && username != ''", value = "username like :username"),
            If(test = "mobileNum !=null && mobileNum != ''", value = "mobile_num like :mobileNum"),
        ], where = [Select.NOT_DELETED]
    )
    fun selectByQuery(query: UserDTO.Query): List<User>

}