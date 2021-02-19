package com.github.liulus.yurt.system.service

import com.github.liulus.yurt.convention.data.Page
import com.github.liulus.yurt.system.model.dto.AuthorityQo
import com.github.liulus.yurt.system.model.entity.Authority

/**
 * User : liulu
 * Date : 2017/11/19 16:41
 * version $Id: AuthorityService.java, v 0.1 Exp $
 */
interface AuthorityService {
    /**
     * 分页查询权限列表
     *
     * @param qo 查询条件
     * @return 权限列表
     */
    fun findPageList(qo: AuthorityQo): Page<Authority>
//    fun findAuthorityTree(): List<AuthorityVo?>?
    fun findAll(): List<Authority>

    /**
     * 根据角色Id查询权限列表
     *
     * @param roleId 角色Id
     * @return 权限列表
     */
//    fun findByRoleId(roleId: Long?): List<Authority?>?
    fun findByRoleIds(roleIds: List<Long>): List<Authority>

    /**
     * 根据 authorityId 查询权限
     *
     * @param authorityId 权限Id
     * @return 权限
     */
//    fun findById(authorityId: Long?): Authority?

    /**
     * 根据权限码查询权限
     *
     * @param code 权限码
     * @return 权限
     */
//    fun findByCode(code: String?): Authority?

    /**
     * 插入一条权限记录
     *
     * @param authority 权限
     * @return 权限Id
     */
//    fun insert(authority: Authority?): Long?

    /**
     * 更新权限记录
     *
     * @param authority 权限
     */
//    fun update(authority: Authority?)

    /**
     * 根据id 删除权限记录
     *
     * @param ids 权限Id
     * @return 受影响记录数
     */
//    fun delete(ids: Array<Long?>?): Int
}