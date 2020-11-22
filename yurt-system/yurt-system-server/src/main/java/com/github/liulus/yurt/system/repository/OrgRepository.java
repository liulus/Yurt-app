package com.github.liulus.yurt.system.repository;

import com.github.liulus.yurt.jdbc.annotation.Select;
import com.github.liulus.yurt.system.model.entity.Organization;
import com.github.liulus.yurt.jdbc.JdbcRepository;

import java.util.List;

/**
 * @author liulu
 * @version 1.0
 * created_at 2020/5/26
 */
public interface OrgRepository extends JdbcRepository<Organization> {


    @Select(where = "parent_id = 0")
    Organization selectRootOrg();

    List<Organization> selectAll();

    Organization selectByCode(String orgCode);

    List<Organization> selectByParentId(Long id);

    int countByParentId(Long parentId);
}
