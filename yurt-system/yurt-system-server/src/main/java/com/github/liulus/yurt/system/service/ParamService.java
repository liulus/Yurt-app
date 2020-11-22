package com.github.liulus.yurt.system.service;

import com.github.liulus.yurt.system.model.dto.SysParamQo;
import com.github.liulus.yurt.system.model.entity.SysParam;
import com.github.liulus.yurt.convention.data.Page;

/**
 * User : liulu
 * Date : 17-9-17 下午2:53
 * version $Id: ParamService.java, v 0.1 Exp $
 */
public interface ParamService {

    Page<SysParam> findPageList(SysParamQo qo);

    SysParam findById(Long id);

    SysParam findByCode(String code);

    Long insert(SysParam param);

    void update(SysParam param);

    void delete(Long... ids);
}
