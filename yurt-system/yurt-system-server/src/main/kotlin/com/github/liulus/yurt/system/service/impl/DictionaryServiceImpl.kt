package com.github.liulus.yurt.system.service.impl

import com.github.liulus.yurt.convention.bean.BeanUtils
import com.github.liulus.yurt.convention.data.Page
import com.github.liulus.yurt.convention.exception.ServiceException
import com.github.liulus.yurt.convention.util.Pages
import com.github.liulus.yurt.system.model.dto.DictDTO
import com.github.liulus.yurt.system.model.dto.DictionaryVo
import com.github.liulus.yurt.system.model.entity.Dictionary
import com.github.liulus.yurt.system.repository.DictionaryRepository
import com.github.liulus.yurt.system.service.DictionaryService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.util.Assert
import org.springframework.util.CollectionUtils
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
        val dict = BeanUtils.convert(Dictionary::class.java, add)
        checkDictKey(dict.dictKey, dict.parentId)
        dict.system = false
        if (dict.orderNum == null) {
            dict.orderNum = 0
        }
        return dictionaryRepository.insert(dict)
    }

    override fun update(update: DictionaryVo.Update?): Int {
        val dictionary = BeanUtils.convert(update, Dictionary())
        val (_, _, dictKey, _, _, _, system) = findById(dictionary.id) ?: return 0
        if (dictionary.dictKey != dictKey) {
            if (system!!) {
                throw ServiceException(String.format("%s 是系统级字典, 不允许修改", dictKey))
            }
            checkDictKey(dictionary.dictKey, dictionary.parentId)
        }
        return dictionaryRepository.updateIgnoreNull(dictionary)
    }

    private fun checkDictKey(dictKey: String?, parentId: Long?) {
        require(!dictKey.isNullOrEmpty()) { "字典key不能为空" }
        requireNotNull(parentId) { "父节点id不能为空" }
        val dict = dictionaryRepository.selectByKeyAndParentId(dictKey, parentId)
        Assert.isNull(dict, "字典Key已经存在")
    }

    override fun findById(id: Long?): Dictionary? {
        return if (id == null || id == 0L) {
            null
        } else dictionaryRepository.selectById(id)
    }

    override fun findPageList(query: DictDTO.Query): Page<DictDTO.Detail> {
        val dictList = dictionaryRepository.selectByQuery(query)
        val page = Pages.page(dictList)
        val targetList = page.results.sortedBy { it.orderNum }.map { BeanUtils.convert(DictDTO.Detail::class.java, it) }
        return page.transform(targetList)
    }

    override fun buildDictLevelById(id: Long?): DictionaryVo.Detail? {
        val dictionary = findById(id) ?: return null
        val result = BeanUtils.convert(dictionary, DictionaryVo.Detail())
        buildLevel(listOf(result))
        return result
    }

    private fun buildLevel(result: List<DictionaryVo.Detail?>) {
        if (CollectionUtils.isEmpty(result)) {
            return
        }
        val ids = result.map { it?.id }
        val dictionaries = dictionaryRepository.findByParentIds(ids)
        if (CollectionUtils.isEmpty(dictionaries)) {
            return
        }
        val details = BeanUtils.convertList(
            DictionaryVo.Detail::class.java, dictionaries
        )

        val childDicts = details.asSequence().sortedBy { it.orderNum }
            .groupBy { it.parentId }.toMap()

//        val childDicts = details.stream()
//            .sorted(Comparator.comparing { obj: DictionaryVo.Detail -> obj.orderNum })
//            .collect(Collectors.groupingBy(Function<DictionaryVo.Detail, Long> { DictionaryVo.Detail.getParentId() }))
        for (detail in result) {
            detail!!.children = childDicts[detail.id]
        }
        buildLevel(details)
    }

//    override fun deleteByIds(vararg ids: Long): Int {
//        if (ids == null || ids.size == 0) {
//            return 0
//        }
//        val dictionaries = dictionaryRepository!!.selectByIds(Arrays.asList(*ids))
//        if (CollectionUtils.isEmpty(dictionaries)) {
//            return 0
//        }
//        val validIds: MutableList<Long?> = ArrayList(dictionaries.size)
//        for (dictionary in dictionaries) {
//            if (dictionary!!.system!!) {
//                throw ServiceException(String.format("%s 是系统级字典, 不允许删除 !", dictionary.dictKey))
//            }
//            val childDict = countByParentId(dictionary.id)
//            if (childDict > 0) {
//                throw ServiceException(String.format("请先删除 %s 的子字典数据 !", dictionary.dictKey))
//            }
//            validIds.add(dictionary.id)
//        }
//        return dictionaryRepository.deleteById(validIds[0])
//    }

    /**
     * *********************************************** *
     * ****************  以下为接口用  **************** *
     * *********************************************** *
     */
    override fun findByRootKey(key: String?): Dictionary? {
        return dictionaryRepository.selectByKeyAndParentId(key!!, 0L)
    }

    override fun findByKeys(vararg keys: String?): Dictionary? {
        if (keys == null || keys.size == 0) {
            return null
        }
        var result = Dictionary()
        result.id = 0L
        for (key in keys) {
            result = dictionaryRepository!!.selectByKeyAndParentId(key!!, result.id!!)!!
        }
        return result
    }

    override fun findChildByRootKey(rootKey: String?): List<Dictionary?>? {
        val (id) = findByRootKey(rootKey) ?: return emptyList()
        return findChildByParentId(id)
    }

//    override fun findChildByKeys(vararg keys: String): List<Dictionary?>? {
//        val (id) = findByKeys(*keys) ?: return emptyList()
//        return findChildByParentId(id)
//    }

    override fun findChildByParentId(parentId: Long?): List<Dictionary?>? {
        return dictionaryRepository!!.selectByParentId(parentId)
    }

    private fun countByParentId(parentId: Long?): Int {
        return dictionaryRepository!!.countByParentId(parentId)
    }
}