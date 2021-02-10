package com.github.liulus.yurt.system.service

import com.github.liulus.yurt.convention.data.Page
import com.github.liulus.yurt.system.model.dto.SysParamQo
import com.github.liulus.yurt.system.model.entity.SysParam

/**
 * User : liulu
 * Date : 17-9-17 下午2:53
 * version $Id: ParamService.java, v 0.1 Exp $
 */
interface ParamService {
    fun findPageList(qo: SysParamQo?): Page<SysParam?>?
    fun findById(id: Long?): SysParam?
    fun findByCode(code: String?): SysParam?
    fun insert(param: SysParam?): Long?
    fun update(param: SysParam?)
//    fun delete(vararg ids: Long?)
}