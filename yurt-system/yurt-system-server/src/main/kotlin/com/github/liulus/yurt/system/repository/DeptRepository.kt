package com.github.liulus.yurt.system.repository

import com.github.liulus.yurt.jdbc.JdbcRepository
import com.github.liulus.yurt.jdbc.annotation.If
import com.github.liulus.yurt.jdbc.annotation.Select
import com.github.liulus.yurt.system.model.dto.DeptDTO
import com.github.liulus.yurt.system.model.entity.Dept

/**
 * @author liulu
 * @version 1.0
 * created_at 2020/5/26
 */
interface DeptRepository : JdbcRepository<Dept> {

    @Select(where = ["code = :param", Select.NOT_DELETED])
    fun selectByCode(code: String): Dept?

    @Select(
        testWheres = [
            If(test = "parentId != null", value = "parent_id = :parentId")
        ], where = [Select.NOT_DELETED]
    )
    fun selectByQuery(query: DeptDTO.Query): List<Dept>

}