package com.github.liulus.yurt.system.util

import com.github.liulus.yurt.convention.bean.BeanUtils
import com.github.liulus.yurt.system.security.LoginUser
import io.jsonwebtoken.Header
import io.jsonwebtoken.JwsHeader
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.security.core.authority.AuthorityUtils
import org.springframework.security.core.authority.SimpleGrantedAuthority
import java.util.Date
import java.util.UUID

/**
 * @author liulu
 * @version v1.0
 * created_at 2020/6/24
 */
object JwtUtils {
    private const val SIGN_KEY = "lit-admin"
    private val IGNORE = arrayOf(
        "password", "authorities", "accountNonExpired",
        "accountNonLocked", "enabled", "credentialsNonExpired"
    )

    private const val EXPIRE = 1000 * 60 * 60 * 12

    @JvmStatic
    fun encode(user: LoginUser): String {
        val claimMap = BeanUtils.beanToMap(user, *IGNORE)
        val authorities = AuthorityUtils.authorityListToSet(user.authorities)
        claimMap["authorities"] = authorities
        return Jwts.builder()
            .setHeaderParam(JwsHeader.ALGORITHM, SignatureAlgorithm.HS256)
            .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
            .setClaims(claimMap)
            .setIssuedAt(Date())
            .setExpiration(Date(System.currentTimeMillis() + EXPIRE))
            .setId(UUID.randomUUID().toString())
            .signWith(SignatureAlgorithm.HS256, SIGN_KEY)
            .compact()
    }

    @JvmStatic
    fun parseToken(token: String?): LoginUser? {
        if (!isValidToken(token)) {
            return null
        }
        val claims = Jwts.parser().setSigningKey(SIGN_KEY).parseClaimsJws(token).body
        val loginUser = BeanUtils.mapToBean(LoginUser::class.java, claims, *IGNORE)
        // 手动设置值
        loginUser.id = claims.get("id", java.lang.Long::class.java).toLong()
        loginUser.orgId = claims.get("orgId", java.lang.Long::class.java)?.toLong()

        val authorities = claims.get("authorities", List::class.java).asSequence()
            .map { SimpleGrantedAuthority(it.toString()) }
            .toList()
        loginUser.setAuthorities(authorities)
        return loginUser
    }

    private fun isValidToken(tokenValue: String?): Boolean {
        if (tokenValue.isNullOrEmpty()) {
            return false
        }
        try {
            val claims = Jwts.parser().setSigningKey(SIGN_KEY).parseClaimsJws(tokenValue).body
            if (System.currentTimeMillis() > claims.expiration.time) {
                // Expired token
                return false
            }
        } catch (ex: Exception) {
            // invalid token
            return false
        }
        return true
    }
}