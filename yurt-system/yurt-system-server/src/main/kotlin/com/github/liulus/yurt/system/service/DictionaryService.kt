package com.github.liulus.yurt.system.service

import com.github.liulus.yurt.convention.data.Page
import com.github.liulus.yurt.system.model.dto.DictDTO
import com.github.liulus.yurt.system.model.entity.Dictionary

/**
 * User : liulu
 * Date : 2017/4/8 20:47
 * version $Id: DictionaryService.java, v 0.1 Exp $
 */
interface DictionaryService {
    /**
     * 添加字典对象
     * @param add Dictionary
     */
    fun insert(add: DictDTO.Detail): Long

    /**
     * 更新字典对象
     * @param update Dictionary
     */
    fun update(update: DictDTO.Detail): Int

    /**
     * 根据 Id 删除字典对象
     * @param id Id
     * @return Boolean
     */
    fun deleteById(id: Long): Boolean

    /**
     * 根据 Id 查询字典对象
     * @param id Id
     * @return Dictionary
     */
    fun findById(id: Long): Dictionary?

    /**
     * 根据查询对象查询 字典列表
     * @param query 查询对象
     * @return Dictionary
     */
    fun findPageList(query: DictDTO.Query): Page<DictDTO.Detail>

}