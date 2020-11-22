package com.github.liulus.yurt.system.repository;


import com.github.liulus.yurt.jdbc.JdbcRepository;
import com.github.liulus.yurt.jdbc.annotation.If;
import com.github.liulus.yurt.jdbc.annotation.Param;
import com.github.liulus.yurt.jdbc.annotation.Select;
import com.github.liulus.yurt.system.model.dto.MenuDTO;
import com.github.liulus.yurt.system.model.entity.Menu;

import java.util.List;

/**
 * @author liulu
 * @version 1.0
 * created_at 2020/5/26
 */
public interface MenuRepository extends JdbcRepository<Menu> {

    @Select(where = "code = :code")
    Menu findByCode(@Param("code") String code);

    @Select(where = "is_deleted = 0", testWheres = {
            @If(test = "enabled != null", value = "is_enabled = :enabled")
    }, isPageQuery = true)
    List<Menu> selectByQuery(MenuDTO.Query query);

    @Select(columns = "count(*)", where = "parent_id = :param")
    int countByParentId(Long parentId);
}
