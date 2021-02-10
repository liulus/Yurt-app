package com.github.liulus.yurt.system.repository

import com.github.liulus.yurt.jdbc.JdbcRepository
import com.github.liulus.yurt.system.model.entity.Dictionary

/**
 * @author liulu
 * @version 1.0
 * created_at 2020/5/26
 */
interface DictionaryRepository : JdbcRepository<Dictionary?> {
    fun findByKeyAndParentId(dictKey: String?, parentId: Long?): Dictionary?
    fun findByParentIds(ids: List<Long?>?): List<Dictionary?>?
    fun selectByParentId(parentId: Long?): List<Dictionary?>?
    fun countByParentId(parentId: Long?): Int
}