package com.github.liulus.yurt.system.security

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

/**
 * User : liulu
 * Date : 2018/4/15 21:34
 * version $Id: LoginUser.java, v 0.1 Exp $
 */
class LoginUser : UserDetails {

    var id: Long? = null
    private var username: String? = null
    private var password: String? = null
    var nickName: String? = null
    var avatar: String? = null
    var email: String? = null
    var mobileNum: String? = null
    var status: String? = null
    var lock: Boolean? = null

    /**** Organization  */
    var orgId: Long? = null
    var orgCode: String? = null
    var orgName: String? = null
    var levelIndex: String? = null

    /*********** 权限信息  */
    var roles: Collection<String>? = null
    private var authorities: Collection<GrantedAuthority> = emptyList()

    override fun getAuthorities(): Collection<GrantedAuthority> {
        return authorities
    }

    fun setAuthorities(authorities: Collection<GrantedAuthority>?) {
        this.authorities = authorities ?: emptyList()
    }

    override fun getUsername(): String? {
        return username
    }

    fun setUsername(username: String?) {
        this.username = username
    }

    override fun getPassword(): String? {
        return password
    }

    fun setPassword(password: String?) {
        this.password = password
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return true
    }

}