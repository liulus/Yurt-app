package com.github.liulus.yurt.system.service.impl

import com.github.liulus.yurt.convention.bean.BeanUtils
import com.github.liulus.yurt.convention.exception.ServiceException
import com.github.liulus.yurt.system.model.dto.MenuDTO
import com.github.liulus.yurt.system.model.entity.Menu
import com.github.liulus.yurt.system.repository.MenuRepository
import com.github.liulus.yurt.system.service.MenuService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.util.Assert
import org.springframework.util.CollectionUtils
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
        return menuRepository.selectById(id)
    }

    override fun buildMenuTree(filterDisabled: Boolean, filterEmpty: Boolean): List<MenuDTO.Detail> {
        val query = MenuDTO.Query()
        query.isDisablePage = true
        if (filterDisabled) {
            query.enabled = true
        }
        val menus = menuRepository.selectByQuery(query)

        val menuMap = menus.asSequence().sortedBy { it.orderNum }
            .map { BeanUtils.convert(MenuDTO.Detail::class.java, it) }
            .groupBy { it.parentId }
            .toMap()
        val rootMenus = menuMap[0L]!!
        setChildren(rootMenus, menuMap)

        if (filterEmpty) {
            return rootMenus.filter { StringUtils.hasText(it.url) || it.isParent!! }.toList()
        }
        return rootMenus
    }

    private fun setChildren(parents: List<MenuDTO.Detail>, nodeMap: Map<Long?, List<MenuDTO.Detail>>) {
        if (CollectionUtils.isEmpty(parents)) {
            return
        }
        for (menu in parents) {
            val children = nodeMap[menu.id]
            if (CollectionUtils.isEmpty(children)) {
                menu.isParent = false
            } else {
                menu.isParent = true
                menu.children = children
                setChildren(children!!, nodeMap)
            }
        }
    }

    override fun insert(add: MenuDTO.Add?): Long? {
        checkMenuCode(add!!.code)
        val menu = BeanUtils.convert(
            Menu::class.java, add
        )
        menu.enabled = true
        return menuRepository.insert(menu)
    }

    override fun update(update: MenuDTO.Update?): Int {
        Assert.notNull(update, "更新菜单不能为空")
        val (_, _, code) = menuRepository.selectById(update!!.id) ?: return 0
        if (update.code != code) {
            checkMenuCode(update.code)
        }
        val menu = BeanUtils.convert(
            Menu::class.java, update
        )
        return menuRepository.updateIgnoreNull(menu)
    }

    private fun checkMenuCode(code: String?) {
        val menu = menuRepository.findByCode(code)
        if (menu != null) {
            throw ServiceException("菜单编码已经存在")
        }
    }

    override fun delete(id: Long?): Int {
        val menu = menuRepository.selectById(id)
        Assert.notNull(menu, String.format("要删除的菜单id %d 不存在", id))
        val count = menuRepository.countByParentId(menu?.id)
        if (count > 0) {
            throw ServiceException(String.format("请先删除 %s 的子菜单数据 !", menu?.name))
        }
        return menuRepository.deleteById(id)
    }

    override fun changeStatus(id: Long?) {
        val (_, _, _, _, _, _, _, _, _, _, enabled) = findById(id) ?: return
        val upMenu = Menu()
        upMenu.id = id
        upMenu.enabled = !enabled!!
        menuRepository.updateIgnoreNull(upMenu)
    }
}