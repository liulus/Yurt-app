package com.github.liulus.yurt.system.service;

import com.github.liulus.yurt.system.model.dto.OrganizationQo;
import com.github.liulus.yurt.system.model.dto.OrganizationVo;
import com.github.liulus.yurt.system.model.entity.Organization;
import com.github.liulus.yurt.convention.data.Page;

/**
 * User : liulu
 * Date : 17-10-3 下午4:29
 * version $Id: OrganizationService.java, v 0.1 Exp $
 */
public interface OrganizationService {
    /**
     * 分页查询机构数据
     *
     * @param vo 查询条件
     * @return
     */
    Page<Organization> findPageList(OrganizationQo vo);

    /**
     * 以企业为根, 构建组织树
     *
     * @return 整个企业的组织树
     */
    OrganizationVo.Detail buildOrgTree();

    /**
     * 查询单个机构
     *
     * @param id
     * @return
     */
    Organization findById(Long id);

    Organization findByCode(String orgCode);

    Long insert(Organization organization);

    void update(Organization organization);

    void delete(Long[] ids);

}
