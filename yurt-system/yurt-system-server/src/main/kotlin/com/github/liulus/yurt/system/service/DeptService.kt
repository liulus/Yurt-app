package com.github.liulus.yurt.system.service

import com.github.liulus.yurt.system.model.dto.DeptDTO

/**
 * User : liulu
 * Date : 17-10-3 下午4:29
 * version $Id: DeptService, v 0.1 Exp $
 */
interface DeptService {

    /**
     * 新增机构
     */
    fun insert(add: DeptDTO.Detail): Long

    /**
     * 修改机构
     */
    fun update(update: DeptDTO.Detail): Int

    /**
     * 查询部门树
     */
    fun buildDeptTree(): List<DeptDTO.Detail>

    /**
     * 根据id查询部门详情
     */
    fun findById(id: Long): DeptDTO.Detail?

}