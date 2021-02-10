package com.github.liulus.yurt.system.controller

import com.github.liulus.yurt.system.model.dto.MenuDTO
import com.github.liulus.yurt.system.service.MenuService
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.annotation.Resource

/**
 * @author liulu
 * @version v1.0
 * date 2019-04-24
 */
@RestController
@RequestMapping("/api")
open class MenuController {
    @Resource
    private val menuService: MenuService? = null

    @GetMapping("/menu/tree")
    fun menuTree(): List<MenuDTO.Detail?>? {
        return menuService!!.buildMenuTree(false, false)
    }

    @GetMapping("/my/menu")
    fun myMenu(): List<MenuDTO.Detail?>? {
        return menuService!!.buildMenuTree(true, true)
    }

    @PostMapping("/menu")
    fun add(@RequestBody add: MenuDTO.Add?): Long? {
        return menuService!!.insert(add)
    }

    @PutMapping("/menu")
    fun update(@RequestBody update: MenuDTO.Update?): Int {
        return menuService!!.update(update)
    }

    @PostMapping("/menu/change/status/{id}")
    fun changeStatus(@PathVariable id: Long?) {
        menuService!!.changeStatus(id)
    }

    @DeleteMapping("/menu/{id}")
    fun delete(@PathVariable id: Long?) {
        menuService!!.delete(id)
    }
}