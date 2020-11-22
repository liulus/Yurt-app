package com.github.liulus.yurt.system.security;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

/**
 * User : liulu
 * Date : 2018/4/15 21:34
 * version $Id: LoginUser.java, v 0.1 Exp $
 */
@Data
public class LoginUser implements UserDetails, Serializable {

    private static final long serialVersionUID = 6675934947596333693L;

    private Long id;
    private String code;
    private String username;
    private String password;
    private String nickName;
    private String avatar;
    private Boolean sex;
    private String email;
    private String mobileNum;
    private String telephone;
    private String userType;
    private String userStatus;
    private Boolean lock;

    /**** Organization ****/
    private Long orgId;
    private String orgCode;
    private String orgName;
    private String levelIndex;


    /*********** 权限信息 ************/

    Collection<String> roles;
    Collection<String> authorities;

    Collection<? extends GrantedAuthority> grantedAuthorities;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (CollectionUtils.isEmpty(authorities)) {
            return Collections.emptyList();
        }
        if (CollectionUtils.isEmpty(grantedAuthorities)) {
            grantedAuthorities = authorities.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
        }
        return this.grantedAuthorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }


}
