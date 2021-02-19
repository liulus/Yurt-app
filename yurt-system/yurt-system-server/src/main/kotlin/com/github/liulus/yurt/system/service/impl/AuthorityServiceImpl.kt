package com.github.liulus.yurt.system.service.impl

import com.github.liulus.yurt.convention.data.Page
import com.github.liulus.yurt.convention.exception.ServiceException
import com.github.liulus.yurt.system.model.dto.AuthorityQo
import com.github.liulus.yurt.system.model.entity.Authority
import com.github.liulus.yurt.system.repository.AuthorityRepository
import com.github.liulus.yurt.system.repository.RoleAuthorityRepository
import com.github.liulus.yurt.system.service.AuthorityService
import com.github.liulus.yurt.system.service.DictionaryService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.util.CollectionUtils
import org.springframework.util.StringUtils
import javax.annotation.Resource

/**
 * User : liulu
 * Date : 2017/11/19 16:41
 * version $Id: AuthorityServiceImpl.java, v 0.1 Exp $
 */
@Service
@Transactional
open class AuthorityServiceImpl : AuthorityService {
    @Resource
    private lateinit var authorityRepository: AuthorityRepository

    @Resource
    private lateinit var roleAuthorityRepository: RoleAuthorityRepository

    @Resource
    private val dictionaryService: DictionaryService? = null
    override fun findPageList(qo: AuthorityQo): Page<Authority> {
        return authorityRepository.selectPageList(qo)
    }

//    override fun findAuthorityTree(): List<AuthorityVo> {
//        val authorities = authorityRepository.selectAll()
//        val authorityTypes = dictionaryService!!.findChildByRootKey("")
//        if (CollectionUtils.isEmpty(authorityTypes)) {
//            return BeanUtils.convertList(
//                AuthorityVo::class.java, authorities
//            )
//        }
//        val result: MutableList<AuthorityVo> = ArrayList()
//        val typeMap = authorities.stream()
//            .filter { (_, _, _, module) -> StringUtils.hasText(module) }
//            .map { authority: Authority -> BeanUtils.convert(authority, AuthorityVo()) }
//            .collect(Collectors.groupingBy { obj: AuthorityVo -> obj.module })
//        authorityTypes.forEach(Consumer { dictionary: Dictionary? ->
//            val childVos = typeMap[dictionary!!.dictKey]!!
//            if (!CollectionUtils.isEmpty(childVos)) {
//                val authorityVo = AuthorityVo()
//                authorityVo.isParent = true
//                authorityVo.nocheck = true
//                authorityVo.code = dictionary.dictKey
//                authorityVo.name = dictionary.dictValue
//                authorityVo.children = childVos
//                result.add(authorityVo)
//            }
//        })
//
//        // 过滤没有权限类型的
//        val other = authorities.stream()
//            .filter { (_, _, _, module) -> StringUtils.isEmpty(module) }
//            .map { authority: Authority -> BeanUtils.convert(authority, AuthorityVo()) }
//            .collect(Collectors.toList())
//        result.addAll(other)
//        return result
//    }

    override fun findAll(): List<Authority> {
        return authorityRepository.selectAll()
    }

//    override fun findByRoleId(roleId: Long): List<Authority> {
//        val roleAuthorities = roleauthorityRepository.selectByRoleId(roleId)
//        if (CollectionUtils.isEmpty(roleAuthorities)) {
//            return emptyList()
//        }
//        val authIds = roleAuthorities.stream().map(RoleAuthority::authorityId).collect(Collectors.toList())
//        return authorityRepository.selectByIds(authIds)
//    }

    override fun findByRoleIds(roleIds: List<Long>): List<Authority> {
        val roleAuthorities = roleAuthorityRepository.selectByRoleIds(roleIds)
        if (CollectionUtils.isEmpty(roleAuthorities)) {
            return emptyList()
        }
        val authIds = roleAuthorities.asSequence().map { it.authorityId }.toList()
        return authorityRepository.selectByIds(authIds)
    }

//    override fun findById(id: Long): Authority {
//        return authorityRepository.selectById(id)
//    }

//    override fun findByCode(code: String): Authority {
//        return authorityRepository.selectByCode(code)
//    }

//    override fun insert(authority: Authority): Long {
//        checkAuthorityCode(authority.code)
//        authorityRepository.insert(authority)
//        return authority.id!!
//    }

//    override fun update(authority: Authority) {
//        val oldAuthority = findById(authority.id!!)
//        Optional.ofNullable(oldAuthority)
//            .map(Authority::code)
//            .filter { code: String? -> code != authority.code }
//            .ifPresent { code: String? -> checkAuthorityCode(code) }
//        authorityRepository.updateIgnoreNull(authority)
//    }

    private fun checkAuthorityCode(code: String?) {
        if (StringUtils.isEmpty(code)) {
            throw ServiceException("权限码不能为空!")
        }
        val count = authorityRepository.countByCode(code!!)
        if (count > 0) {
            throw ServiceException("权限码已经存在!")
        }
    }

//    override fun delete(ids: Array<Long>): Int {
//        return if (ids == null || ids.size == 0) {
//            0
//        } else authorityRepository.deleteByIds(Arrays.asList(*ids))
//    }
}