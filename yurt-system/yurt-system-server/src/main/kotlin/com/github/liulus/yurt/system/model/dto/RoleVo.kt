package com.github.liulus.yurt.system.model.dto

/**
 * User : liulu
 * Date : 2018/4/16 16:35
 * version $Id: RoleVo.java, v 0.1 Exp $
 */
abstract class RoleVo {
    
    class BindAuthority {
        var roleId: Long? = null
        var authorityIds: Array<Long>? = null
    }

    class BindUser {
        var userId: Long? = null
        var roleIds: Array<Long>? = null
    }
}