package com.github.liulus.yurt.system.repository

import com.github.liulus.yurt.jdbc.JdbcRepository
import com.github.liulus.yurt.jdbc.annotation.If
import com.github.liulus.yurt.jdbc.annotation.Param
import com.github.liulus.yurt.jdbc.annotation.Select
import com.github.liulus.yurt.system.model.dto.DictDTO
import com.github.liulus.yurt.system.model.entity.Dictionary

/**
 * @author liulu
 * @version 1.0
 * created_at 2020/5/26
 */
interface DictionaryRepository : JdbcRepository<Dictionary> {

    @Select(where = ["parent_id = :parentId and dict_key = :dictKey"])
    fun selectByKeyAndParentId(
        @Param("dictKey") dictKey: String,
        @Param("parentId") parentId: Long
    ): Dictionary?

    @Select(
        testWheres = [
            If(test = "parentId != null", value = "parent_id = :parentId"),
            If(test = "keyword != null && type != ''", value = "dict_key like :keyword")
        ], where = ["is_deleted = 0"]
    )
    fun selectByQuery(query: DictDTO.Query): List<Dictionary>

    fun findByParentIds(ids: List<Long?>?): List<Dictionary?>?
    fun selectByParentId(parentId: Long?): List<Dictionary?>?
    fun countByParentId(parentId: Long?): Int

}