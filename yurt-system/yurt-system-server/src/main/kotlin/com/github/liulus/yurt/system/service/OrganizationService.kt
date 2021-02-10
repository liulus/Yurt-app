package com.github.liulus.yurt.system.service

import com.github.liulus.yurt.convention.data.Page
import com.github.liulus.yurt.system.model.dto.OrganizationQo
import com.github.liulus.yurt.system.model.dto.OrganizationVo
import com.github.liulus.yurt.system.model.entity.Organization

/**
 * User : liulu
 * Date : 17-10-3 下午4:29
 * version $Id: OrganizationService.java, v 0.1 Exp $
 */
interface OrganizationService {
    /**
     * 分页查询机构数据
     *
     * @param vo 查询条件
     * @return
     */
    fun findPageList(vo: OrganizationQo?): Page<Organization?>?

    /**
     * 以企业为根, 构建组织树
     *
     * @return 整个企业的组织树
     */
    fun buildOrgTree(): OrganizationVo.Detail?

    /**
     * 查询单个机构
     *
     * @param id
     * @return
     */
    fun findById(id: Long?): Organization?
    fun findByCode(orgCode: String?): Organization?
    fun insert(organization: Organization?): Long?
    fun update(organization: Organization?)
    fun delete(ids: Array<Long?>?)
}