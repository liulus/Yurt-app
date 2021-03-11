package com.github.liulus.yurt.system.ext

import com.github.liulus.yurt.convention.bean.BeanUtils
import com.github.liulus.yurt.convention.exception.ServiceException
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
 *
 * @author liulu
 * @version V1.0
 * @since 2021/3/11
 */

private const val SIGN_KEY = "lit-admin"
private val IGNORE = arrayOf(
    "password", "authorities", "accountNonExpired",
    "accountNonLocked", "enabled", "credentialsNonExpired"
)

private const val EXPIRE = 1000 * 60 * 60 * 12

fun encodeToToken(user: LoginUser): String {
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

fun parseToken(token: String?): LoginUser? {
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

fun nextLevelIndex(parentIndex: String, existIndexes: List<String>?): String {
    if (existIndexes.isNullOrEmpty()) {
        return parentIndex + "1|"
    }
    val idxes = existIndexes.filter { it.isNotEmpty() }
        .map { it.substring(parentIndex.length).replace("|", "") }
        .map { it.toInt() }
        .sorted()
    val count = idxes.size
    val maxSerialNum = idxes[count - 1]
    if (maxSerialNum <= count) {
        val next = maxSerialNum + 1
        return "$parentIndex$next|"
    }
    var i = 1
    for (serialNum in idxes) {
        if (i != serialNum) {
            return "$parentIndex$i|"
        }
        i++
    }
    throw ServiceException("已达到机构最大数量")
}