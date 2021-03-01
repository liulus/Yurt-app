package com.github.liulus.yurt.system.service.impl

import com.github.liulus.yurt.convention.bean.BeanUtils
import com.github.liulus.yurt.convention.exception.ServiceException
import com.github.liulus.yurt.system.context.SystemConst
import com.github.liulus.yurt.system.model.dto.MenuDTO
import com.github.liulus.yurt.system.model.entity.Menu
import com.github.liulus.yurt.system.repository.MenuRepository
import com.github.liulus.yurt.system.service.MenuService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.util.StringUtils
import javax.annotation.Resource

/**
 * User : liulu
 * Date : 2017/7/13 19:53
 * version $Id: MenuServiceImpl.java, v 0.1 Exp $
 */
@Service
@Transactional
open class MenuServiceImpl : MenuService {

    @Resource
    private lateinit var menuRepository: MenuRepository


    override fun findById(id: Long?): Menu? {
        return menuRepository.selectById(id!!)
    }

    override fun buildAllMenuTree(): List<MenuDTO.Detail> {
        val query = MenuDTO.Query()
        val menus = menuRepository.selectByQuery(query)
        return buildMenuTree(menus)
    }

    override fun findUserMenus(userId: Long): List<MenuDTO.Detail> {
        val query = MenuDTO.Query()
        query.enabled = true
        query.type = SystemConst.MENU_TYPE_MENU
        val menus = menuRepository.selectByQuery(query)
        val menuTree = buildMenuTree(menus)
        return menuTree.filter { StringUtils.hasText(it.url) || !it.children.isNullOrEmpty() }.toList()
    }

    private fun buildMenuTree(originList: List<Menu>): List<MenuDTO.Detail> {
        val menuMap = originList.asSequence().sortedBy { it.orderNum }
            .map { BeanUtils.convert(MenuDTO.Detail::class.java, it) }
            .groupBy { it.parentId!! }
            .toMap()
        val rootMenus = menuMap[0L]
        setChildren(rootMenus, menuMap)
        return rootMenus ?: emptyList()
    }

    private fun setChildren(parents: List<MenuDTO.Detail>?, nodeMap: Map<Long, List<MenuDTO.Detail>>) {
        if (parents.isNullOrEmpty()) {
            return
        }
        for (menu in parents) {
            val children = nodeMap[menu.id]
            if (!children.isNullOrEmpty()) {
                menu.children = children
                setChildren(children, nodeMap)
            }
        }
    }

    override fun insert(add: MenuDTO.Add): Long {
        val menu = BeanUtils.convert(Menu::class.java, add)
        menu.enabled = true
        return menuRepository.insert(menu)
    }

    override fun update(update: MenuDTO.Update): Int {
        menuRepository.selectById(update.id!!)
            ?: throw ServiceException("菜单id ${update.id} 不存在")
        val upMenu = BeanUtils.convert(Menu::class.java, update)
        return menuRepository.updateIgnoreNull(upMenu)
    }

    override fun delete(id: Long): Int {
        val oldMenu = menuRepository.selectById(id)
            ?: throw ServiceException("菜单id $id 不存在")
        val count = menuRepository.countByParentId(id)
        if (count > 0) {
            throw ServiceException("请先删除 ${oldMenu.name} 的子菜单数据 !")
        }
        return menuRepository.deleteLogicalById(id)
    }

    override fun changeStatus(id: Long) {
        val oldMenu = menuRepository.selectById(id) ?: return
        val upMenu = Menu()
        upMenu.id = id
        upMenu.enabled = !oldMenu.enabled!!
        menuRepository.updateIgnoreNull(upMenu)
    }
}