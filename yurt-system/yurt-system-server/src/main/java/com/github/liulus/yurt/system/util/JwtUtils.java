package com.github.liulus.yurt.system.util;

import com.github.liulus.yurt.convention.bean.BeanUtils;
import com.github.liulus.yurt.system.security.LoginUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.JwsHeader;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.util.Assert;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

/**
 * @author liulu
 * @version v1.0
 * created_at 2020/6/24
 */
public abstract class JwtUtils {

    private static final String SIGN_KEY = "lit-admin";

    private static final String[] IGNORE = {"password", "accountNonExpired", "accountNonLocked", "authorities", "enabled", "credentialsNonExpired", "grantedAuthorities"};

    private static final long EXPIRE = 1000 * 60 * 60 * 12;

    public static String encode(LoginUser user) {
        Assert.notNull(user, "login user must not bu null");
        Map<String, Object> claimMap = BeanUtils.beanToMap(user, IGNORE);
        Set<String> authorities = AuthorityUtils.authorityListToSet(user.getAuthorities());
        claimMap.put("authorities", authorities);

        return Jwts.builder()
                .setHeaderParam(JwsHeader.ALGORITHM, SignatureAlgorithm.HS256)
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setClaims(claimMap)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE))
                .setId(UUID.randomUUID().toString())
                .signWith(SignatureAlgorithm.HS256, SIGN_KEY)
                .compact();
    }


    public static LoginUser parseToken(String token) {
        if (!isValidToken(token)) {
            return null;
        }

        Claims claims = Jwts.parser().setSigningKey(SIGN_KEY).parseClaimsJws(token).getBody();
        LoginUser loginUser = BeanUtils.mapToBean(LoginUser.class, claims, IGNORE);
        // 手动设置值
        loginUser.setId(claims.get("id", Long.class));
        loginUser.setOrgId(claims.get("orgId", Long.class));
        //noinspection unchecked
        loginUser.setAuthorities(claims.get("authorities", List.class));
        return loginUser;
    }


    public static boolean isValidToken(String tokenValue) {
        if (tokenValue == null || tokenValue.isEmpty()) {
            return false;
        }
        try {
            Claims claims = Jwts.parser().setSigningKey(SIGN_KEY).parseClaimsJws(tokenValue).getBody();
            if (System.currentTimeMillis() > claims.getExpiration().getTime()) {
                // Expired token
                return false;
            }
        } catch (Exception ex) {
            // invalid token
            return false;
        }
        return true;
    }


    public static void main(String[] args) {
        LoginUser loginUser = new LoginUser();
        loginUser.setId(3L);
        loginUser.setCode("990999");
        loginUser.setUsername("liulu");
        loginUser.setEmail("skary0812@yeah.net");
        loginUser.setOrgId(9L);
        loginUser.setAuthorities(Arrays.asList("123213", "1ewqeqwe"));

        String token = encode(loginUser);
        System.out.println(token);

        boolean validToken = isValidToken(token);


        LoginUser user = parseToken(token);

        System.out.println(user);

    }


}
