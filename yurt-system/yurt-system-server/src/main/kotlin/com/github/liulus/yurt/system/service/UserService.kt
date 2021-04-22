package com.github.liulus.yurt.system.service

import com.github.liulus.yurt.convention.data.Page
import com.github.liulus.yurt.system.model.dto.UserDTO
import com.github.liulus.yurt.system.model.dto.UserVo
import com.github.liulus.yurt.system.model.dto.UserVo.Register
import com.github.liulus.yurt.system.model.entity.User

/**
 * User : liulu
 * Date : 2017/8/12 11:20
 * version $Id: UserService.java, v 0.1 Exp $
 */
interface UserService {
    /**
     * 用户注册
     *
     * @param register register
     * @return Long
     */
    fun register(register: Register?): Long?

    /**
     * 根据用户 Id 查询用户
     *
     * @param id 用户Id
     * @return User
     */
    fun findById(id: Long?): User?

    /**
     * 根据用户名查询用户
     *
     * @param username 用户名
     * @return user
     */
    fun findByUsername(username: String): User?

    /**
     * 根据条件查询用户列表
     * @param query 查询条件
     * @return 用户列表
     */
    fun findPageList(query: UserDTO.Query): Page<UserDTO.View>

    /**
     * 新增用户
     *
     * @param add user
     */
    fun insert(user: UserDTO.View): Long

    /**
     * 修改用户
     *
     * @param user user
     */
    fun update(user: UserVo.Update?)

    /**
     * 删除用户
     *
     * @param id ids
     */
    fun delete(id: Long?)
}