package com.github.liulus.yurt.system.security;

import com.github.liulus.yurt.convention.bean.BeanUtils;
import com.github.liulus.yurt.system.model.entity.Authority;
import com.github.liulus.yurt.system.model.entity.Role;
import com.github.liulus.yurt.system.model.entity.User;
import com.github.liulus.yurt.system.service.AuthorityService;
import com.github.liulus.yurt.system.service.OrganizationService;
import com.github.liulus.yurt.system.service.RoleService;
import com.github.liulus.yurt.system.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author liulu
 * @version V1.0
 * @since 2020/11/18
 */
public class LoginUserService implements UserDetailsService {


    @Resource
    private UserService userService;

    @Resource
    private OrganizationService organizationService;

    @Resource
    private RoleService roleService;

    @Resource
    private AuthorityService authorityService;

    private static final String[] ADMIN_USER = {"admin", "liulu"};

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userService.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("用户 " + username + "不存在!");
        }
        // 构建 loginUser
        LoginUser userDetail = BeanUtils.convert(LoginUser.class, user);
        Optional.ofNullable(userDetail.getOrgId())
                .filter(orgId -> orgId != 0L)
                .map(orgId -> organizationService.findById(orgId))
                .ifPresent(org -> {
                    userDetail.setOrgCode(org.getCode());
                    userDetail.setOrgName(org.getFullName());
                    userDetail.setLevelIndex(org.getLevelIndex());
                });

        // 超级管理员拥有所有权限
        if (Arrays.binarySearch(ADMIN_USER, user.getUsername()) >= 0) {
            List<Authority> authorities = authorityService.findAll();
            userDetail.setAuthorities(authorities.stream().map(Authority::getCode).collect(Collectors.toList()));
            return userDetail;
        }

        List<Role> roles = roleService.findByUserId(user.getId());
        if (CollectionUtils.isEmpty(roles)) {
            return userDetail;
        }
        // 查询用户权限
        List<Authority> roleAuthorities = authorityService.findByRoleIds(roles.stream().map(Role::getId).collect(Collectors.toList()));
//        userDetail.setRoles(roles);
        userDetail.setAuthorities(roleAuthorities.stream().map(Authority::getCode).collect(Collectors.toList()));
        return userDetail;
    }
}
