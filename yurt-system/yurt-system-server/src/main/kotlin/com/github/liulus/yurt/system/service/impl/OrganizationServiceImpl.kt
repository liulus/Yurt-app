package com.github.liulus.yurt.system.service.impl

import com.github.liulus.yurt.convention.bean.BeanUtils
import com.github.liulus.yurt.convention.data.Page
import com.github.liulus.yurt.convention.exception.ServiceException
import com.github.liulus.yurt.system.model.dto.OrganizationQo
import com.github.liulus.yurt.system.model.dto.OrganizationVo
import com.github.liulus.yurt.system.model.entity.Organization
import com.github.liulus.yurt.system.repository.OrgRepository
import com.github.liulus.yurt.system.service.OrganizationService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.util.CollectionUtils
import org.springframework.util.StringUtils
import java.util.Optional
import javax.annotation.Resource

/**
 * User : liulu
 * Date : 17-10-3 下午4:29
 * version $Id: OrganizationServiceImpl.java, v 0.1 Exp $
 */
@Service
@Transactional
open class OrganizationServiceImpl : OrganizationService {
    @Resource
    private lateinit var orgRepository: OrgRepository
    
    override fun findPageList(qo: OrganizationQo?): Page<Organization?>? {
//        return orgRepository.selectPageList(qo);
        return null
    }

    override fun buildOrgTree(): OrganizationVo.Detail? {
        val organizations = orgRepository.selectAll()
        if (CollectionUtils.isEmpty(organizations)) {
            return null
        }
        val orgLevelMap = organizations!!.asSequence()
            .map { BeanUtils.convert(OrganizationVo.Detail::class.java, it) }
            .groupBy { it.parentId }
            .toMap()
        // 企业信息, 作为根节点只有一个
        val rootOrg = orgLevelMap[0L]!!
        if (CollectionUtils.isEmpty(rootOrg)) {
            return null
        }
        setChildren(rootOrg, orgLevelMap)
        return rootOrg.iterator().next()
    }

    private fun setChildren(parents: List<OrganizationVo.Detail?>, nodeMap: Map<Long?, List<OrganizationVo.Detail>>) {
        if (CollectionUtils.isEmpty(parents)) {
            return
        }
        for (detail in parents) {
            val children = nodeMap[detail!!.id]!!
            if (!CollectionUtils.isEmpty(children)) {
                detail.children = children
                setChildren(children, nodeMap)
            }
        }
    }

    override fun findById(id: Long): Organization? {
        return orgRepository.selectById(id)
    }

    override fun findByCode(orgCode: String?): Organization? {
        return orgRepository.selectByCode(orgCode)
    }

    private fun findRootOrg(): Organization {
        return orgRepository.selectRootOrg()!!
    }

    override fun insert(organization: Organization?): Long? {
        checkOrgCode(organization!!.code)
        val parentOrg = if (organization.parentId == null || organization.parentId == 0L) findRootOrg() else findById(
            organization.parentId!!
        )
        if (parentOrg == null) {
            throw ServiceException("不存在父节点" + organization.parentId)
        }

        // 处理 levelIndex
        val organizations = orgRepository.selectByParentId(parentOrg.id)
        val levelIndexes = organizations!!.asSequence().map { it?.levelIndex }.toList()

        organization.parentId = parentOrg.id
        //        organization.setLevelIndex(UserUtils.nextLevelIndex(parentOrg.getLevelIndex(), levelIndexes));
        orgRepository.insert(organization)
        return organization.id
    }

    override fun update(organization: Organization?) {
        val oldOrg = findById(organization!!.id!!)
        Optional.ofNullable(oldOrg)
            .map(Organization::code)
            .filter { code: String? -> code != organization.code }
            .ifPresent { orgCode: String? -> checkOrgCode(orgCode) }
        orgRepository.updateIgnoreNull(organization)
    }

    private fun checkOrgCode(orgCode: String?) {
        if (StringUtils.isEmpty(orgCode)) {
            return
        }
        val org = findByCode(orgCode)
        if (org != null) {
            throw ServiceException("机构号已经存在")
        }
    }

    override fun delete(ids: Array<Long?>?) {
        if (ids == null || ids.size == 0) {
            return
        }
//        val orgs = orgRepository.selectByIds(Arrays.asList(*ids))
//        val validIds: MutableList<Long?> = ArrayList(orgs.size)
//        for ((id, parentId, _, fullName) in orgs) {
//            val count = orgRepository.countByParentId(parentId)
//            if (count > 0) {
//                throw ServiceException(String.format("请先删除 %s 的子机构数据 ", fullName))
//            }
//            validIds.add(id)
//        }
//        orgRepository.deleteById(validIds[0])
    }
}