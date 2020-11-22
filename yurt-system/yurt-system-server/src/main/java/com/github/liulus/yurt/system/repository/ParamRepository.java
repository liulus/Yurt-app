package com.github.liulus.yurt.system.repository;

import com.github.liulus.yurt.system.model.entity.SysParam;
import com.github.liulus.yurt.jdbc.JdbcRepository;

/**
 * @author liulu
 * @version 1.0
 * created_at 2020/5/26
 */
public interface ParamRepository extends JdbcRepository<SysParam> {
    SysParam selectByCode(String code);
}
