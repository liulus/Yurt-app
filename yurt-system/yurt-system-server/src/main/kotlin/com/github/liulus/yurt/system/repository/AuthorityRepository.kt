package com.github.liulus.yurt.system.repository

import com.github.liulus.yurt.convention.data.Page
import com.github.liulus.yurt.jdbc.JdbcRepository
import com.github.liulus.yurt.jdbc.annotation.Delete
import com.github.liulus.yurt.jdbc.annotation.If
import com.github.liulus.yurt.jdbc.annotation.Select
import com.github.liulus.yurt.system.model.dto.AuthorityQo
import com.github.liulus.yurt.system.model.entity.Authority

/**
 * @author liulu
 * @version 1.0
 * created_at 2020/5/26
 */
interface AuthorityRepository : JdbcRepository<Authority?> {
    @Select(testWheres = [If(test = "roleIds != null and roleIds.size() > 0", value = "id in :roleIds")])
    fun selectPageList(qo: AuthorityQo): Page<Authority>

    @Select
    fun selectAll(): List<Authority>

    @Select(where = ["code = :param"])
    fun selectByCode(code: String?): Authority?

    @Select(columns = ["count(*)"], where = ["code = :param"])
    fun countByCode(code: String?): Int

    @Delete(where = ["id in (:params)"])
    fun deleteByIds(ids: List<Long?>?): Int
}