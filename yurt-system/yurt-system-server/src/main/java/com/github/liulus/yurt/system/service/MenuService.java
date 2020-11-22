package com.github.liulus.yurt.system.service;

import com.github.liulus.yurt.system.model.dto.MenuDTO;
import com.github.liulus.yurt.system.model.entity.Menu;

import java.util.List;

/**
 * User : liulu
 * Date : 2017/7/10 10:50
 * version $Id: MenuService.java, v 0.1 Exp $
 */
public interface MenuService {

    /**
     * 根据Id查询菜单
     *
     * @param id 主键
     * @return 菜单
     */
    Menu findById(Long id);

    /**
     * 构建菜单树
     *
     * @param filterDisabled 是否过滤禁用菜单
     * @param filterEmpty    是否过滤子菜单为空并且没有url
     * @return Detail
     */
    List<MenuDTO.Detail> buildMenuTree(boolean filterDisabled, boolean filterEmpty);

    /**
     * 增加菜单
     *
     * @param add Menu
     */
    Long insert(MenuDTO.Add add);

    /**
     * 更新菜单
     *
     * @param update Menu
     */
    int update(MenuDTO.Update update);

    /**
     * 删除菜单
     *
     * @param id id
     */
    int delete(Long id);


    /**
     * 改变菜单状态, 启用->禁用, 禁用->启用
     *
     * @param id id
     */
    void changeStatus(Long id);

}
