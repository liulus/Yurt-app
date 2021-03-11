package com.github.liulus.yurt.system.service.impl

import com.github.liulus.yurt.convention.bean.BeanUtils
import com.github.liulus.yurt.convention.data.Page
import com.github.liulus.yurt.convention.util.Pages
import com.github.liulus.yurt.system.model.dto.DictDTO
import com.github.liulus.yurt.system.model.entity.Dictionary
import com.github.liulus.yurt.system.repository.DictionaryRepository
import com.github.liulus.yurt.system.service.DictionaryService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.util.StringUtils
import javax.annotation.Resource

/**
 * User : liulu
 * Date : 2017/4/8 20:49
 * version $Id: DictionaryServiceImpl.java, v 0.1 Exp $
 */
@Service
@Transactional
open class DictionaryServiceImpl : DictionaryService {

    @Resource
    private lateinit var dictionaryRepository: DictionaryRepository

    override fun insert(add: DictDTO.Detail): Long {
        checkDictKey(add.dictKey, add.parentId)
        val dict = BeanUtils.convert(Dictionary::class.java, add)
        dict.orderNum = dict.orderNum ?: 0
        return dictionaryRepository.insert(dict)
    }

    override fun update(update: DictDTO.Detail): Int {
        val id = requireNotNull(update.id) { "更新数据id必填" }
        val old = dictionaryRepository.selectById(id)
        checkNotNull(old) { "id $id 对应的记录不存在" }
        if (update.dictKey != old.dictKey) {
            checkDictKey(update.dictKey, old.parentId)
        }
        val dict = BeanUtils.convert(Dictionary::class.java, update)
        return dictionaryRepository.updateIgnoreNull(dict)
    }

    override fun deleteById(id: Long): Boolean {
        val count = dictionaryRepository.countByParentId(id)
        check(count == 0) { "此字典存在子字典项, 请先删除子字典" }
        val num = dictionaryRepository.deleteLogicalById(id)
        return num > 0
    }

    private fun checkDictKey(dictKey: String?, parentId: Long?) {
        require(!dictKey.isNullOrEmpty()) { "字典key不能为空" }
        requireNotNull(parentId) { "父节点id不能为空" }
        val dict = dictionaryRepository.selectByKeyAndParentId(dictKey, parentId)
        check(dict == null) { "字典Key已经存在" }
    }

    override fun findById(id: Long): Dictionary? {
        return dictionaryRepository.selectById(id)
    }

    override fun findPageList(query: DictDTO.Query): Page<DictDTO.Detail> {
        if (StringUtils.hasLength(query.keyword)) {
            query.keyword = query.keyword + '%'
        }
        val dictList = dictionaryRepository.selectByQuery(query)
        return Pages.page(dictList).simpleMap { BeanUtils.convert(DictDTO.Detail::class.java, it) }
    }
}