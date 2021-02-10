package com.github.liulus.yurt.system.repository

import com.github.liulus.yurt.jdbc.JdbcRepository
import com.github.liulus.yurt.system.model.entity.SysParam

/**
 * @author liulu
 * @version 1.0
 * created_at 2020/5/26
 */
interface ParamRepository : JdbcRepository<SysParam?> {
    fun selectByCode(code: String?): SysParam?
}