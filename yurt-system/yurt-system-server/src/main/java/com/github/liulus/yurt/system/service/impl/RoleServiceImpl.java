package com.github.liulus.yurt.system.service.impl;

import com.github.liulus.yurt.convention.bean.BeanUtils;
import com.github.liulus.yurt.convention.data.Page;
import com.github.liulus.yurt.convention.exception.ServiceException;
import com.github.liulus.yurt.system.model.dto.AuthorityVo;
import com.github.liulus.yurt.system.model.dto.RoleQo;
import com.github.liulus.yurt.system.model.entity.Authority;
import com.github.liulus.yurt.system.model.entity.Role;
import com.github.liulus.yurt.system.model.entity.RoleAuthority;
import com.github.liulus.yurt.system.model.entity.UserRole;
import com.github.liulus.yurt.system.repository.AuthorityRepository;
import com.github.liulus.yurt.system.repository.RoleAuthorityRepository;
import com.github.liulus.yurt.system.repository.RoleRepository;
import com.github.liulus.yurt.system.repository.UserRoleRepository;
import com.github.liulus.yurt.system.service.RoleService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * User : liulu
 * Date : 2017/11/19 16:42
 * version $Id: RoleServiceImpl.java, v 0.1 Exp $
 */
@Service
public class RoleServiceImpl implements RoleService {

    @Resource
    private RoleRepository roleRepository;
    @Resource
    private AuthorityRepository authorityRepository;
    @Resource
    private UserRoleRepository userRoleRepository;
    @Resource
    private RoleAuthorityRepository roleAuthorityRepository;

    @Override
    public Page<Role> findPageList(RoleQo roleQo) {
//        return roleRepository.selectPageList(roleQo);
        return null;
    }

    @Override
    public Role findById(Long roleId) {
        return roleRepository.selectById(roleId);
    }

    @Override
    public Role findByCode(String code) {
//        return roleRepository.selectByProperty(Role::getCode, code);
        return null;
    }

    @Override
    public Long insert(Role role) {
        checkCode(role.getCode());
        roleRepository.insert(role);
        return role.getId();
    }

    @Override
    public void update(Role role) {
        Role oldRole = findById(role.getId());

        Optional.ofNullable(oldRole)
                .map(Role::getCode)
                .filter(code -> !Objects.equals(code, role.getCode()))
                .ifPresent(this::checkCode);
        roleRepository.updateIgnoreNull(role);
    }

    private void checkCode(String code) {
        if (StringUtils.isEmpty(code)) {
            return;
        }
        Role role = findByCode(code);
        if (role != null) {
            throw new ServiceException("角色码已经存在");
        }
    }

    @Override
    public int delete(Long[] ids) {
        if (ids == null || ids.length == 0) {
            return 0;
        }
        List<Role> roles = roleRepository.selectByIds(Arrays.asList(ids));
        List<Long> validIds = new ArrayList<>(roles.size());

        for (Role role : roles) {
//            int count = roleRepository.countByProperty(Role::getId, role.getId());
            int count = 0;
            if (count > 0) {
                throw new ServiceException(String.format("请先取消角色 %s 绑定的 %d 个权限", role.getName(), count));
            }
            validIds.add(role.getId());
        }

//        return roleRepository.deleteByIds(validIds);
        return 0;
    }

    @Override
    public void bindAuthority(Long roleId, Long[] authorityIds) {
        Role role = findById(roleId);
        if (role == null) {
            return;
        }

        // 需要新增的有效 authId
        List<Authority> authorities = authorityRepository.selectByIds(Arrays.asList(authorityIds));
        List<Long> newAuthIds = authorities.stream().map(Authority::getId).collect(Collectors.toList());

        // 当前角色下的 旧的权限
        List<RoleAuthority> oldAuths = roleAuthorityRepository.selectByRoleId(roleId);

        // 对比找出需删除的 角色权限Id
        Long[] deleteRoleAuthIds = oldAuths.stream()
                .filter(oldAuth -> !newAuthIds.contains(oldAuth.getAuthorityId()))
                .map(RoleAuthority::getId)
                .toArray(Long[]::new);
        Optional.of(deleteRoleAuthIds)
                .filter(idArray -> idArray.length > 0)
                .ifPresent(idArray -> roleAuthorityRepository.deleteById(Arrays.asList(idArray).get(0)));

        // 对比找出需要新增的 authId
        List<Long> oldAuthIds = oldAuths.stream().map(RoleAuthority::getAuthorityId).collect(Collectors.toList());
        List<RoleAuthority> insertAuths = newAuthIds.stream()
                .filter(newAuthId -> !oldAuthIds.contains(newAuthId))
                .map(newAuthId -> {
                    RoleAuthority roleAuthority = new RoleAuthority();
                    roleAuthority.setRoleId(roleId);
                    roleAuthority.setAuthorityId(newAuthId);
                    return roleAuthority;
                }).collect(Collectors.toList());

//        roleAuthorityRepository.batchInsert(insertAuths);
    }

    @Override
    public void bindUser(Long userId, Long[] roleIds) {
        // 需要新增的有效 roleId
        List<Role> roles = roleRepository.selectByIds(Arrays.asList(roleIds));
        List<Long> newRoleIds = roles.stream().map(Role::getId).collect(Collectors.toList());

        // 当前用户下的 旧的角色
        List<UserRole> oldRoles = userRoleRepository.selectByUserId( userId);

        // 对比找出需删除的 用户角色Id
        Long[] deleteUserRoleIds = oldRoles.stream()
                .filter(oldRole -> !newRoleIds.contains(oldRole.getRoleId()))
                .map(UserRole::getId)
                .toArray(Long[]::new);
        Optional.of(deleteUserRoleIds)
                .filter(idArray -> idArray.length > 0)
                .ifPresent(idArray -> userRoleRepository.deleteById(Arrays.asList(idArray).get(0)));

        // 对比找出需要新增的 roleId
        List<Long> oldRoleIds = oldRoles.stream().map(UserRole::getRoleId).collect(Collectors.toList());
        List<UserRole> insertRoles = newRoleIds.stream()
                .filter(newRoleId -> !oldRoleIds.contains(newRoleId))
                .map(newRoleId -> {
                    UserRole userRole = new UserRole();
                    userRole.setUserId(userId);
                    userRole.setRoleId(newRoleId);
                    return userRole;
                }).collect(Collectors.toList());
//        userRoleRepository.batchInsert(insertRoles);
    }

    @Override
    public List<Role> findByUserId(Long userId) {
        return roleRepository.selectByUserId(userId);
    }

    @Override
    public List<AuthorityVo.TreeNode> findAuthorityTree(Long roleId) {

        List<Authority> allAuthorities = authorityRepository.selectAll();
        if (CollectionUtils.isEmpty(allAuthorities)) {
            return Collections.emptyList();
        }

        List<AuthorityVo.TreeNode> result = new ArrayList<>();


        List<Long> roleAuthIds = roleAuthorityRepository.selectByRoleId(roleId)
                .stream()
                .map(RoleAuthority::getAuthorityId)
                .collect(Collectors.toList());

        // 按 module 分组, 并设置节点是否选中
        Map<String, List<AuthorityVo.TreeNode>> moduleMap = allAuthorities.stream()
                .map(auth -> BeanUtils.convert(auth, new AuthorityVo.TreeNode()))
                .peek(auth -> auth.setChecked(roleAuthIds.contains(auth.getId())))
                .collect(Collectors.groupingBy(AuthorityVo.TreeNode::getModule));


        for (Map.Entry<String, List<AuthorityVo.TreeNode>> moduleEntry : moduleMap.entrySet()) {
            List<AuthorityVo.TreeNode> moduleEntryValue = moduleEntry.getValue();
            if (CollectionUtils.isEmpty(moduleEntryValue)) {
                continue;
            }
            AuthorityVo.TreeNode moduleNode = new AuthorityVo.TreeNode();
            moduleNode.setChecked(true);
            moduleNode.setCode(moduleEntry.getKey());
//            moduleNode.setName(AuthorityUtils.getModuleName(moduleEntry.getKey()));

            // 按 function 进行分组, 并设置moduleNode 是否选中
            Map<String, List<AuthorityVo.TreeNode>> funcMap = moduleEntryValue.stream()
                    .peek(funcAuth -> {
                        if (!funcAuth.getChecked()) {
                            moduleNode.setChecked(false);
                        }
                    })
                    .filter(funcAuth -> !StringUtils.isEmpty(funcAuth.getFunction()))
                    .collect(Collectors.groupingBy(AuthorityVo.TreeNode::getFunction));

            List<AuthorityVo.TreeNode> moduleChild = new ArrayList<>();
            for (Map.Entry<String, List<AuthorityVo.TreeNode>> funcEntry : funcMap.entrySet()) {
                List<AuthorityVo.TreeNode> funcChild = funcEntry.getValue();
                if (CollectionUtils.isEmpty(funcChild)) {
                    continue;
                }
                AuthorityVo.TreeNode funcNode = new AuthorityVo.TreeNode();
                funcNode.setChecked(true);
                funcNode.setCode(funcEntry.getKey());
//                funcNode.setName(AuthorityUtils.getFunctionName(funcEntry.getKey()));

                funcNode.setChildren(funcChild);
                // funcNode 子节点有一个未选中 就是未选中
                for (AuthorityVo.TreeNode treeNode : funcChild) {
                    if (!treeNode.getChecked()) {
                        funcNode.setChecked(false);
                        break;
                    }
                }
                moduleChild.add(funcNode);
            }
            // 添加所有 没有function的节点
            moduleChild.addAll(moduleEntryValue.stream()
                    .filter(funcAuth -> StringUtils.isEmpty(funcAuth.getFunction()))
                    .collect(Collectors.toList()));

            moduleNode.setChildren(moduleChild);
            result.add(moduleNode);
        }

        return result;
    }


}
