package com.github.liulus.yurt.system.service.impl;

import com.github.liulus.yurt.convention.bean.BeanUtils;
import com.github.liulus.yurt.convention.exception.ServiceException;
import com.github.liulus.yurt.convention.util.Asserts;
import com.github.liulus.yurt.system.model.dto.MenuDTO;
import com.github.liulus.yurt.system.model.entity.Menu;
import com.github.liulus.yurt.system.repository.MenuRepository;
import com.github.liulus.yurt.system.service.MenuService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * User : liulu
 * Date : 2017/7/13 19:53
 * version $Id: MenuServiceImpl.java, v 0.1 Exp $
 */
@Service
@Transactional
public class MenuServiceImpl implements MenuService {

    @Resource
    private MenuRepository menuRepository;

    @Override
    public Menu findById(Long id) {
        return menuRepository.selectById(id);
    }

    @Override
    public List<MenuDTO.Detail> buildMenuTree(boolean filterDisabled, boolean filterEmpty) {
        MenuDTO.Query query = new MenuDTO.Query();
        query.setDisablePage(true);
        if (filterDisabled) {
            query.setEnabled(Boolean.TRUE);
        }
        List<Menu> menus = menuRepository.selectByQuery(query);
        Map<Long, List<MenuDTO.Detail>> menuMap = menus.stream()
                .sorted(Comparator.comparing(Menu::getOrderNum))
                .map(menu -> BeanUtils.convert(menu, new MenuDTO.Detail()))
                .collect(Collectors.groupingBy(MenuDTO.Detail::getParentId));
        List<MenuDTO.Detail> rootMenus = menuMap.get(0L);

        setChildren(rootMenus, menuMap);

        if (filterEmpty) {
            return rootMenus.stream()
                    .filter(detail -> StringUtils.hasText(detail.getUrl()) || detail.getIsParent())
                    .collect(Collectors.toList());
        }
        return rootMenus;
    }

    private void setChildren(List<MenuDTO.Detail> parents, Map<Long, List<MenuDTO.Detail>> nodeMap) {
        if (CollectionUtils.isEmpty(parents)) {
            return;
        }
        for (MenuDTO.Detail menu : parents) {
            List<MenuDTO.Detail> children = nodeMap.get(menu.getId());
            if (CollectionUtils.isEmpty(children)) {
                menu.setIsParent(false);
            } else {
                menu.setIsParent(true);
                menu.setChildren(children);
                setChildren(children, nodeMap);
            }
        }
    }


    @Override
    public Long insert(MenuDTO.Add add) {
        checkMenuCode(add.getCode());
        Menu menu = BeanUtils.convert(Menu.class, add);
        menu.setEnabled(true);
        return menuRepository.insert(menu);
    }

    @Override
    public int update(MenuDTO.Update update) {
        Asserts.notNull(update, "更新菜单不能为空");
        Menu old = menuRepository.selectById(update.getId());
        if (old == null) {
            return 0;
        }
        if (!Objects.equals(update.getCode(), old.getCode())) {
            checkMenuCode(update.getCode());
        }
        Menu menu = BeanUtils.convert(Menu.class, update);
        return menuRepository.updateIgnoreNull(menu);
    }

    private void checkMenuCode(String code) {
        Menu menu = menuRepository.findByCode(code);
        if (menu != null) {
            throw new ServiceException("菜单编码已经存在");
        }
    }

    @Override
    public int delete(Long id) {
        Menu menu = menuRepository.selectById(id);
        Asserts.notNull(menu, "要删除的菜单id {} 不存在", id);

        int count = menuRepository.countByParentId(menu.getId());
        if (count > 0) {
            throw new ServiceException(String.format("请先删除 %s 的子菜单数据 !", menu.getName()));
        }
        return menuRepository.deleteById(id);
    }

    @Override
    public void changeStatus(Long id) {
        Menu menu = findById(id);
        if (menu == null) {
            return;
        }
        Menu upMenu = new Menu();
        upMenu.setId(id);
        upMenu.setEnabled(!menu.getEnabled());
        menuRepository.updateIgnoreNull(upMenu);
    }

}
