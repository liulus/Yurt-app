package com.github.liulus.yurt.system.repository

import com.github.liulus.yurt.jdbc.JdbcRepository
import com.github.liulus.yurt.jdbc.annotation.If
import com.github.liulus.yurt.jdbc.annotation.Select
import com.github.liulus.yurt.system.model.dto.MenuDTO
import com.github.liulus.yurt.system.model.entity.Menu

/**
 * @author liulu
 * @version 1.0
 * created_at 2020/5/26
 */
interface MenuRepository : JdbcRepository<Menu> {

    @Select(
        where = ["is_deleted = 0"],
        testWheres = [
            If(test = "enabled != null", value = "is_enabled = :enabled"),
            If(test = "type != null && type != ''", value = "type = :type")
        ]
    )
    fun selectByQuery(query: MenuDTO.Query): List<Menu>

    @Select(columns = ["count(*)"], where = ["parent_id = :param"])
    fun countByParentId(parentId: Long): Int
}