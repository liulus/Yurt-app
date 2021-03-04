package com.github.liulus.yurt.system.service

import com.github.liulus.yurt.convention.data.Page
import com.github.liulus.yurt.system.model.dto.DictDTO
import com.github.liulus.yurt.system.model.dto.DictionaryVo
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
     *
     * @param dictionary Dictionary
     */
    fun update(dictionary: DictionaryVo.Update?): Int

    /**
     * 根据 Id 查询字典对象
     *
     * @param id Id
     * @return Dictionary
     */
    fun findById(id: Long?): Dictionary?

    /**
     * 根据查询对象查询 字典列表
     * @param query 查询对象
     * @return Dictionary
     */
    fun findPageList(query: DictDTO.Query): Page<DictDTO.Detail>

    /**
     * 构建字典的层级
     *
     * @param id id
     * @return DictionaryVo.Detail list
     */
    fun buildDictLevelById(id: Long?): DictionaryVo.Detail?

    /**
     * 根据 Id 删除字典对象
     *
     * @param ids Id
     * @return Dictionary
     */
//    fun deleteByIds(vararg ids: Long?): Int

    /**
     * 根据 根字典key 查询字典
     *
     * @param key key
     * @return Dictionary
     */
    fun findByRootKey(key: String?): Dictionary?

    /**
     * 根据字典key 查询字典
     *
     * @param keys keys
     * @return Dictionary
     */
    fun findByKeys(vararg keys: String?): Dictionary?

    /**
     * 查找根级字典的所有子字典
     *
     * @param rootKey rootKey
     * @return List<Dictionary>
    </Dictionary> */
    fun findChildByRootKey(rootKey: String?): List<Dictionary?>?

    /**
     * 查找指定字典下的子字典
     *
     * @param keys keys
     * @return List<Dictionary>
    </Dictionary> */
//    fun findChildByKeys(vararg keys: String?): List<Dictionary?>?

    /**
     * 根据父字典Id查询所有子字典
     *
     * @param parentId parentId
     * @return
     */
    fun findChildByParentId(parentId: Long?): List<Dictionary?>?
}