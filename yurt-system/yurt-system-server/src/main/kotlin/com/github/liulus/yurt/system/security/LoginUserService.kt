package com.github.liulus.yurt.system.security

import com.github.liulus.yurt.convention.bean.BeanUtils
import com.github.liulus.yurt.system.ext.ADMIN_USER
import com.github.liulus.yurt.system.service.AuthorityService
import com.github.liulus.yurt.system.service.DeptService
import com.github.liulus.yurt.system.service.RoleService
import com.github.liulus.yurt.system.service.UserService
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.util.CollectionUtils
import java.util.Arrays
import java.util.Optional
import javax.annotation.Resource

/**
 * @author liulu
 * @version V1.0
 * @since 2020/11/18
 */
class LoginUserService : UserDetailsService {

    @Resource
    private lateinit var userService: UserService

    @Resource
    private lateinit var deptService: DeptService

    @Resource
    private lateinit var roleService: RoleService

    @Resource
    private lateinit var authorityService: AuthorityService

    override fun loadUserByUsername(username: String): UserDetails {
        val user = userService.findByUsername(username)
            ?: throw UsernameNotFoundException("用户 " + username + "不存在!")

        // 构建 loginUser
        val loginUser = BeanUtils.convert(LoginUser::class.java, user)
        Optional.ofNullable(loginUser.orgId)
            .filter { it > 0L }
            .map { deptService.findById(it) }
            .ifPresent {
                loginUser.orgCode = it.code
                loginUser.orgName = it.name
            }

        // 超级管理员拥有所有权限
        if (Arrays.binarySearch(ADMIN_USER, user.username) >= 0) {
            val authorities = authorityService.findAll()
            val auths = authorities.asSequence().map { it.code }
                .map { SimpleGrantedAuthority(it) }.toList()
            loginUser.setAuthorities(auths)
            return loginUser
        }
        val roles = roleService.findByUserId(user.id!!)
        if (CollectionUtils.isEmpty(roles)) {
            return loginUser
        }

        // 查询用户权限
        val roleAuthorities = authorityService.findByRoleIds(roles.map { it.id!! })
        loginUser.roles = roles.map { it.code.toString() }
        val auths = roleAuthorities.asSequence().map { it?.code }
            .map { SimpleGrantedAuthority(it) }.toList()
        loginUser.setAuthorities(auths)
        return loginUser
    }

}