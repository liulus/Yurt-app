package com.github.liulus.yurt.system.model.dto

import com.github.liulus.yurt.convention.data.PageQuery

/**
 * User : liulu
 * Date : 2017/7/13 19:49
 * version $Id: MenuDTO.java, v 0.1 Exp $
 */
interface MenuDTO {

    class Query : PageQuery() {
        var enabled: Boolean? = null
    }

    open class Base {
        var code: String? = null
        var name: String? = null
        var icon: String? = null
        var url: String? = null
        var orderNum: Int? = null
        var type: String? = null
        var remark: String? = null
    }

    class Detail : Base() {
        var id: Long? = null
        var parentId: Long? = null
        var enabled: Boolean? = null
        var isParent: Boolean? = null
        var children: List<Detail>? = null
    }


    class Add : Base() {
        var parentId: Long? = null
    }


    class Update : Base() {
        var id: Long? = null
    }


    class Tree {
        var id: Long? = null
        var parentId: Long? = null
        var name: String? = null
        var children: List<Tree?>? = null
        var isParent: Boolean? = null
    }
}