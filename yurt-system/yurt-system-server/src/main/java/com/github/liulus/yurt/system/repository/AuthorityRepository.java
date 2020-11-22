package com.github.liulus.yurt.system.repository;


import com.github.liulus.yurt.convention.data.Page;
import com.github.liulus.yurt.jdbc.JdbcRepository;
import com.github.liulus.yurt.jdbc.annotation.Delete;
import com.github.liulus.yurt.jdbc.annotation.If;
import com.github.liulus.yurt.jdbc.annotation.Select;
import com.github.liulus.yurt.system.model.dto.AuthorityQo;
import com.github.liulus.yurt.system.model.entity.Authority;

import java.util.List;

/**
 * @author liulu
 * @version 1.0
 * created_at 2020/5/26
 */
public interface AuthorityRepository extends JdbcRepository<Authority> {

    @Select(testWheres = {
            @If(test = "roleIds != null and roleIds.size() > 0", value = "id in :roleIds")
    })
    Page<Authority> selectPageList(AuthorityQo qo);

    @Select
    List<Authority> selectAll();

    @Select(where = "code = :param")
    Authority selectByCode(String code);

    @Select(columns = "count(*)", where = "code = :param")
    int countByCode(String code);

    @Delete(where = "id in (:params)")
    int deleteByIds(List<Long> ids);
}
