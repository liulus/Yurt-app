package com.github.liulus.yurt.system.controller;

import com.github.liulus.yurt.system.model.dto.MenuDTO;
import com.github.liulus.yurt.system.service.MenuService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author liulu
 * @version v1.0
 * date 2019-04-24
 */
@RestController
@RequestMapping("/api")
public class MenuController {

    @Resource
    private MenuService menuService;

    @GetMapping("/menu/tree")
    public List<MenuDTO.Detail> menuTree() {
        return menuService.buildMenuTree(false, false);
    }

    @GetMapping("/my/menu")
    public List<MenuDTO.Detail> myMenu() {
        return menuService.buildMenuTree(true, true);
    }

    @PostMapping("/menu")
    public Long add(@RequestBody MenuDTO.Add add) {
        return menuService.insert(add);
    }

    @PutMapping("/menu")
    public int update(@RequestBody MenuDTO.Update update) {
        return menuService.update(update);
    }

    @PostMapping("/menu/change/status/{id}")
    public void changeStatus(@PathVariable Long id) {
        menuService.changeStatus(id);
    }

    @DeleteMapping("/menu/{id}")
    public void delete(@PathVariable Long id) {
        menuService.delete(id);
    }


}
