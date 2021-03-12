package com.github.liulus.yurt.system.service.impl

import com.github.liulus.yurt.convention.bean.BeanUtils
import com.github.liulus.yurt.system.ext.nextLevelIndex
import com.github.liulus.yurt.system.model.dto.DeptDTO
import com.github.liulus.yurt.system.model.entity.Dept
import com.github.liulus.yurt.system.repository.DeptRepository
import com.github.liulus.yurt.system.service.DeptService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.util.StringUtils
import javax.annotation.Resource

/**
 * User : liulu
 * Date : 17-10-3 下午4:29
 * version $Id: OrganizationServiceImpl.java, v 0.1 Exp $
 */
@Service
@Transactional
open class DeptServiceImpl : DeptService {

    @Resource
    private lateinit var deptRepository: DeptRepository

    override fun insert(add: DeptDTO.Detail): Long {
        val parentId = requireNotNull(add.parentId) { "父部门id不能为空" }
        // 初始化创建
        val dept = BeanUtils.convert(add, Dept())
        if (parentId == 0L) {
            val exist = deptRepository.selectByQuery(DeptDTO.Query(parentId = parentId))
            check(exist.isNullOrEmpty()) { "已经创建根节点了, 无法创建" }
            dept.levelIndex = nextLevelIndex("", emptyList());
            return deptRepository.insert(dept)
        }
        checkName(add.name)
        val parentDept = deptRepository.selectById(parentId)
        checkNotNull(parentDept) { "数据错误, 父部门不存在" }

        // 构建levelIndex
        dept.levelIndex = generateNextLevelIndex(parentDept)
        return deptRepository.insert(dept)
    }

    override fun update(update: DeptDTO.Detail): Int {
        val id = requireNotNull(update.id) { "id不能为空" }
        val oldDept = deptRepository.selectById(id)
        checkNotNull(oldDept) { "部门 $id 不存在" }

        if (update.name != oldDept.name) {
            checkName(update.name)
        }
        val dept = BeanUtils.convert(update, Dept())
        // 父节点变化 更新层级索引
        if (update.parentId != oldDept.parentId) {
            val parentDept = deptRepository.selectById(requireNotNull(update.parentId))
            checkNotNull(parentDept) { "数据错误, 父部门不存在" }
            val start = StringUtils.startsWithIgnoreCase(parentDept.levelIndex, oldDept.levelIndex)
            check(!start) { "不能选择${parentDept.name}作为父部门" }
            dept.parentId = parentDept.id
            dept.levelIndex = generateNextLevelIndex(parentDept)
        }
        return deptRepository.updateIgnoreNull(dept)
    }

    private fun checkName(name: String?) {
        require(!name.isNullOrEmpty()) { "部门code不能为空" }
        val dept = deptRepository.selectByName(name)
        check(dept == null) { "部门名称 $name 已存在" }
    }

    private fun generateNextLevelIndex(parentDept: Dept): String {
        val existChildDept = deptRepository.selectByQuery(DeptDTO.Query(parentId = parentDept.id))
        val existIndexes = existChildDept.mapNotNull { it.levelIndex }
        val parentIndex = parentDept.levelIndex
        requireNotNull(parentIndex) { "父节点层级索引不能为空" }
        return nextLevelIndex(parentIndex, existIndexes)
    }

    override fun buildDeptTree(): List<DeptDTO.Detail> {
        val deptList = deptRepository.selectByQuery(DeptDTO.Query())
        if (deptList.isEmpty()) {
            return emptyList();
        }
        val deptMap = deptList.asSequence().sortedBy { it.orderNum }
            .mapNotNull { BeanUtils.convert(DeptDTO.Detail::class.java, it) }
            .groupBy { requireNotNull(it.parentId) }
        // 默认设置的跟机构
        val rootDeptList = deptMap[0L]
        setChildren(rootDeptList, deptMap)
        return rootDeptList ?: emptyList()
    }

    private fun setChildren(parents: List<DeptDTO.Detail>?, nodeMap: Map<Long, List<DeptDTO.Detail>>) {
        parents?.forEach {
            val children = nodeMap[it.id]
            if (!children.isNullOrEmpty()) {
                it.children = children
                setChildren(children, nodeMap)
            }
        }
    }

    override fun findById(id: Long): DeptDTO.Detail? {
        TODO("Not yet implemented")
    }
}