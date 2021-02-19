package com.github.liulus.yurt.system.service

import com.github.liulus.yurt.convention.data.Page
import com.github.liulus.yurt.system.model.dto.RoleQo
import com.github.liulus.yurt.system.model.entity.Role

/**
 * User : liulu
 * Date : 2017/11/19 16:42
 * version $Id: RoleService.java, v 0.1 Exp $
 */
interface RoleService {
    /**
     * 分页查询角色列表
     *
     * @param roleQo 查询条件
     * @return 角色列表
     */
    fun findPageList(roleQo: RoleQo?): Page<Role?>?

    /**
     * 根据 roleId 查询角色
     *
     * @param roleId 角色Id
     * @return 角色
     */
    fun findById(roleId: Long?): Role?

    /**
     * 根据 roleCode 查询角色
     *
     * @param roleCode 角色code
     * @return 角色
     */
    fun findByCode(roleCode: String?): Role?

    /**
     * 插入一条角色记录
     *
     * @param role 角色
     * @return 角色Id
     */
    fun insert(role: Role?): Long?

    /**
     * 更新角色记录
     *
     * @param role 角色
     */
    fun update(role: Role?)

    /**
     * 根据id 删除权限记录
     *
     * @param ids 权限Id
     * @return 受影响记录数
     */
//    fun delete(ids: Array<Long?>?): Int

    /**
     * 绑定权限
     *
     * @param roleId       角色Id
     * @param authorityIds 权限Id
     */
//    fun bindAuthority(roleId: Long?, authorityIds: Array<Long?>?)

    /**
     * 绑定角色
     *
     * @param userId  userId
     * @param roleIds roleIds
     */
//    fun bindUser(userId: Long?, roleIds: Array<Long?>?)

    /**
     * 根据用户Id查询角色列表
     *
     * @param userId userId
     * @return List<Role>
    </Role> */
    fun findByUserId(userId: Long): List<Role>
//    fun findAuthorityTree(roleId: Long?): List<AuthorityVo.TreeNode?>?
}