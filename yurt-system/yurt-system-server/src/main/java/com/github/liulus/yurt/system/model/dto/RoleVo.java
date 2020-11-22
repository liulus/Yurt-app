package com.github.liulus.yurt.system.model.dto;

import lombok.Data;

/**
 * User : liulu
 * Date : 2018/4/16 16:35
 * version $Id: RoleVo.java, v 0.1 Exp $
 */
public abstract class RoleVo {

    @Data
    public static class BindAuthority {

        private Long roleId;

        private Long[] authorityIds;

    }

    @Data
    public static class BindUser {
        private Long userId;

        private Long[] roleIds;
    }

}
