package com.github.liulus.yurt.system.repository;


import com.github.liulus.yurt.jdbc.JdbcRepository;
import com.github.liulus.yurt.system.model.entity.Dictionary;

import java.util.List;

/**
 * @author liulu
 * @version 1.0
 * created_at 2020/5/26
 */
public interface DictionaryRepository extends JdbcRepository<Dictionary> {

    Dictionary findByKeyAndParentId(String dictKey, Long parentId);

    List<Dictionary> findByParentIds(List<Long> ids);

    List<Dictionary> selectByParentId(Long parentId);

    int countByParentId(Long parentId);

}
