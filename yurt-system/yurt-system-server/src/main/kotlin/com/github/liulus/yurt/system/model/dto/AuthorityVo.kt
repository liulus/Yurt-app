package com.github.liulus.yurt.system.model.dto

/**
 * User : liulu
 * Date : 2017/11/19 16:40
 * version $Id: AuthorityVo.java, v 0.1 Exp $
 */
class AuthorityVo {
    var code: String? = null
    var name: String? = null
    var module: String? = null

    // zTree 属性 隐藏选择框
    var nocheck: Boolean? = null
    var isParent: Boolean? = null
    var children: List<AuthorityVo>? = null
    
    class TreeNode {
        var id: Long? = null
        var code: String? = null
        var name: String? = null
        var module: String? = null
        var function: String? = null
        var checked: Boolean? = null
        var children: List<TreeNode>? = null
    }
    
}