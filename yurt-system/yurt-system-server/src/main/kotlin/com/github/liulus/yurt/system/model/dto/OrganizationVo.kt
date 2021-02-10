package com.github.liulus.yurt.system.model.dto

/**
 * User : liulu
 * Date : 2018/4/11 20:34
 * version $Id: OrganizationVo.java, v 0.1 Exp $
 */
abstract class OrganizationVo {
    var code: String? = null
    var fullName: String? = null
    var shortName: String? = null
    var type: String? = null
    var address: String? = null
    var remark: String? = null

    class ListRes : OrganizationVo() {
        var id: Long? = null
    }

    class Add : OrganizationVo() {
        var parentId: Long? = null
    }

    class Update : OrganizationVo() {
        var id: Long? = null
    }

    class Detail : OrganizationVo() {
        var id: Long? = null
        var parentId: Long? = null
        var children: List<Detail>? = null
    }
}