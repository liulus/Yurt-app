package com.github.liulus.yurt.system.service.impl

import com.github.liulus.yurt.convention.data.Page
import com.github.liulus.yurt.convention.exception.ServiceException
import com.github.liulus.yurt.system.model.dto.RoleQo
import com.github.liulus.yurt.system.model.entity.Role
import com.github.liulus.yurt.system.repository.AuthorityRepository
import com.github.liulus.yurt.system.repository.RoleAuthorityRepository
import com.github.liulus.yurt.system.repository.RoleRepository
import com.github.liulus.yurt.system.repository.UserRoleRepository
import com.github.liulus.yurt.system.service.RoleService
import org.springframework.stereotype.Service
import org.springframework.util.StringUtils
import java.util.Optional
import javax.annotation.Resource

/**
 * User : liulu
 * Date : 2017/11/19 16:42
 * version $Id: RoleServiceImpl.java, v 0.1 Exp $
 */
@Service
open class RoleServiceImpl : RoleService {
    @Resource
    private val roleRepository: RoleRepository? = null

    @Resource
    private val authorityRepository: AuthorityRepository? = null

    @Resource
    private val userRoleRepository: UserRoleRepository? = null

    @Resource
    private val roleAuthorityRepository: RoleAuthorityRepository? = null
    override fun findPageList(roleQo: RoleQo?): Page<Role?>? {
//        return roleRepository.selectPageList(roleQo);
        return null
    }

    override fun findById(roleId: Long?): Role? {
        return roleRepository!!.selectById(roleId)
    }

    override fun findByCode(code: String?): Role? {
//        return roleRepository.selectByProperty(Role::getCode, code);
        return null
    }

    override fun insert(role: Role?): Long? {
        checkCode(role!!.code)
        roleRepository!!.insert(role)
        return role.id
    }

    override fun update(role: Role?) {
        val oldRole = findById(role!!.id)
        Optional.ofNullable(oldRole)
            .map(Role::code)
            .filter { code: String? -> code != role.code }
            .ifPresent { code: String? -> checkCode(code) }
        roleRepository!!.updateIgnoreNull(role)
    }

    private fun checkCode(code: String?) {
        if (StringUtils.isEmpty(code)) {
            return
        }
        val role = findByCode(code)
        if (role != null) {
            throw ServiceException("角色码已经存在")
        }
    }

//    override fun delete(ids: Array<Long?>?): Int {
//        if (ids == null || ids.size == 0) {
//            return 0
//        }
//        val roles = roleRepository!!.selectByIds(Arrays.asList(*ids))
//        val validIds: MutableList<Long?> = ArrayList(roles.size)
//        for ((id, _, name) in roles) {
////            int count = roleRepository.countByProperty(Role::getId, role.getId());
//            val count = 0
//            if (count > 0) {
//                throw ServiceException(String.format("请先取消角色 %s 绑定的 %d 个权限", name, count))
//            }
//            validIds.add(id)
//        }

//        return roleRepository.deleteByIds(validIds);
//        return 0
//    }

//    override fun bindAuthority(roleId: Long?, authorityIds: Array<Long?>?) {
//        val (id, code, name, remark, deleted, gmtCreated, gmtModified, gmtDeleted) = findById(roleId) ?: return
//
//        // 需要新增的有效 authId
//        val authorities = authorityRepository!!.selectByIds(Arrays.asList(*authorityIds))
//        val newAuthIds = authorities.stream().map(Authority::id).collect(Collectors.toList())
//
//        // 当前角色下的 旧的权限
//        val oldAuths = roleAuthorityRepository!!.selectByRoleId(roleId)
//
//        // 对比找出需删除的 角色权限Id
//        val deleteRoleAuthIds = oldAuths.stream()
//            .filter { oldAuth: RoleAuthority -> !newAuthIds.contains(oldAuth.authorityId) }
//            .map(RoleAuthority::id)
//            .toArray<Long> { _Dummy_.__Array__() }
//        Optional.of(deleteRoleAuthIds)
//            .filter { idArray: Array<Long> -> idArray.size > 0 }
//            .ifPresent { idArray: Array<Long> -> roleAuthorityRepository.deleteById(Arrays.asList(*idArray)[0]) }
//
//        // 对比找出需要新增的 authId
//        val oldAuthIds = oldAuths.stream().map(RoleAuthority::authorityId).collect(Collectors.toList())
//        val insertAuths = newAuthIds.stream()
//            .filter { newAuthId: Long? -> !oldAuthIds.contains(newAuthId) }
//            .map { newAuthId: Long? ->
//                val roleAuthority = RoleAuthority()
//                roleAuthority.roleId = roleId
//                roleAuthority.authorityId = newAuthId
//                roleAuthority
//            }.collect(Collectors.toList())

//        roleAuthorityRepository.batchInsert(insertAuths);
//    }

//    override fun bindUser(userId: Long?, roleIds: Array<Long?>?) {
//        // 需要新增的有效 roleId
//        val roles = roleRepository!!.selectByIds(Arrays.asList(*roleIds))
//        val newRoleIds = roles.stream().map(Role::id).collect(Collectors.toList())
//
//        // 当前用户下的 旧的角色
//        val oldRoles = userRoleRepository!!.selectByUserId(userId)
//
//        // 对比找出需删除的 用户角色Id
//        oldRoles.asSequence().filter { newRoleIds.co }
//
//        val deleteUserRoleIds = oldRoles.stream()
//            .filter { (_, _, roleId) -> !newRoleIds.contains(roleId) }
//            .map(UserRole::id)
//            .toArray<Long> { _Dummy_.__Array__() }
//        Optional.of(deleteUserRoleIds)
//            .filter { idArray: Array<Long> -> idArray.size > 0 }
//            .ifPresent { idArray: Array<Long> -> userRoleRepository.deleteById(Arrays.asList(*idArray)[0]) }
//
//        // 对比找出需要新增的 roleId
//        val oldRoleIds = oldRoles.stream().map(UserRole::roleId).collect(Collectors.toList())
//        val insertRoles = newRoleIds.stream()
//            .filter { newRoleId: Long? -> !oldRoleIds.contains(newRoleId) }
//            .map { newRoleId: Long? ->
//                val userRole = UserRole()
//                userRole.userId = userId
//                userRole.roleId = newRoleId
//                userRole
//            }.collect(Collectors.toList())
//        //        userRoleRepository.batchInsert(insertRoles);
//    }

    override fun findByUserId(userId: Long?): List<Role?>? {
        return roleRepository!!.selectByUserId(userId)
    }

//    override fun findAuthorityTree(roleId: Long?): List<AuthorityVo.TreeNode?>? {
//        val allAuthorities = authorityRepository!!.selectAll()
//        if (CollectionUtils.isEmpty(allAuthorities)) {
//            return emptyList()
//        }
//        val result: MutableList<AuthorityVo.TreeNode?> = ArrayList()
//        val roleAuthIds = roleAuthorityRepository!!.selectByRoleId(roleId)
//            .stream()
//            .map(RoleAuthority::authorityId)
//            .collect(Collectors.toList())
//
//        // 按 module 分组, 并设置节点是否选中
//        val moduleMap = allAuthorities.stream()
//            .map { auth: Authority? -> BeanUtils.convert(auth, AuthorityVo.TreeNode()) }
//            .peek { auth: AuthorityVo.TreeNode -> auth.checked = roleAuthIds.contains(auth.id) }
//            .collect(Collectors.groupingBy(Function<AuthorityVo.TreeNode, String> { AuthorityVo.TreeNode.getModule() }))
//        for ((key, moduleEntryValue) in moduleMap) {
//            if (CollectionUtils.isEmpty(moduleEntryValue)) {
//                continue
//            }
//            val moduleNode = AuthorityVo.TreeNode()
//            moduleNode.checked = true
//            moduleNode.code = key
//            //            moduleNode.setName(AuthorityUtils.getModuleName(moduleEntry.getKey()));
//
//            // 按 function 进行分组, 并设置moduleNode 是否选中
//            val funcMap = moduleEntryValue.stream()
//                .peek { funcAuth: AuthorityVo.TreeNode? ->
//                    if (!funcAuth!!.checked) {
//                        moduleNode.checked = false
//                    }
//                }
//                .filter { funcAuth: AuthorityVo.TreeNode? ->
//                    !StringUtils.isEmpty(
//                        funcAuth!!.function
//                    )
//                }
//                .collect(Collectors.groupingBy(Function<AuthorityVo.TreeNode, String> { AuthorityVo.TreeNode.getFunction() }))
//            val moduleChild: MutableList<AuthorityVo.TreeNode> = ArrayList()
//            for ((key1, funcChild) in funcMap) {
//                if (CollectionUtils.isEmpty(funcChild)) {
//                    continue
//                }
//                val funcNode = AuthorityVo.TreeNode()
//                funcNode.checked = true
//                funcNode.code = key1
//                //                funcNode.setName(AuthorityUtils.getFunctionName(funcEntry.getKey()));
//                funcNode.children = funcChild
//                // funcNode 子节点有一个未选中 就是未选中
//                for (treeNode in funcChild) {
//                    if (!treeNode!!.checked) {
//                        funcNode.checked = false
//                        break
//                    }
//                }
//                moduleChild.add(funcNode)
//            }
//            // 添加所有 没有function的节点
//            moduleChild.addAll(moduleEntryValue.stream()
//                .filter { funcAuth: AuthorityVo.TreeNode? ->
//                    StringUtils.isEmpty(
//                        funcAuth!!.function
//                    )
//                }
//                .collect(Collectors.toList()))
//            moduleNode.children = moduleChild
//            result.add(moduleNode)
//        }
//        return result
//    }
}