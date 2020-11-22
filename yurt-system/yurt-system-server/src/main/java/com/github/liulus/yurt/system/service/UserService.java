package com.github.liulus.yurt.system.service;

import com.github.liulus.yurt.system.model.dto.UserQo;
import com.github.liulus.yurt.system.model.dto.UserVo;
import com.github.liulus.yurt.system.model.entity.User;
import com.github.liulus.yurt.convention.data.Page;

/**
 * User : liulu
 * Date : 2017/8/12 11:20
 * version $Id: UserService.java, v 0.1 Exp $
 */
public interface UserService {

    /**
     * 用户注册
     *
     * @param register register
     * @return Long
     */
    Long register(UserVo.Register register);

    /**
     * 根据用户 Id 查询用户
     *
     * @param id 用户Id
     * @return User
     */
    User findById(Long id);

    /**
     * 根据用户名查询用户
     *
     * @param username 用户名
     * @return user
     */
    User findByUsername(String username);


    /**
     * 根据条件查询用户列表
     *
     * @param qo 查询条件
     * @return 用户列表
     */
    Page<UserVo.List> findPageList(UserQo qo);

    /**
     * 新增用户
     *
     * @param user user
     */
    Long insert(UserVo.Add user);

    /**
     * 修改用户
     *
     * @param user user
     */
    void update(UserVo.Update user);

    /**
     * 删除用户
     *
     * @param id ids
     */
    void delete(Long id);


}
