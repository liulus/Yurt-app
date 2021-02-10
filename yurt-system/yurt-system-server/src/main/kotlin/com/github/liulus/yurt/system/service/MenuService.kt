package com.github.liulus.yurt.system.service

import com.github.liulus.yurt.system.model.dto.MenuDTO
import com.github.liulus.yurt.system.model.entity.Menu

/**
 * User : liulu
 * Date : 2017/7/10 10:50
 * version $Id: MenuService.java, v 0.1 Exp $
 */
interface MenuService {
    /**
     * 根据Id查询菜单
     *
     * @param id 主键
     * @return 菜单
     */
    fun findById(id: Long?): Menu?

    /**
     * 构建菜单树
     *
     * @param filterDisabled 是否过滤禁用菜单
     * @param filterEmpty    是否过滤子菜单为空并且没有url
     * @return Detail
     */
    fun buildMenuTree(filterDisabled: Boolean, filterEmpty: Boolean): List<MenuDTO.Detail?>?

    /**
     * 增加菜单
     *
     * @param add Menu
     */
    fun insert(add: MenuDTO.Add?): Long?

    /**
     * 更新菜单
     *
     * @param update Menu
     */
    fun update(update: MenuDTO.Update?): Int

    /**
     * 删除菜单
     *
     * @param id id
     */
    fun delete(id: Long?): Int

    /**
     * 改变菜单状态, 启用->禁用, 禁用->启用
     *
     * @param id id
     */
    fun changeStatus(id: Long?)
}