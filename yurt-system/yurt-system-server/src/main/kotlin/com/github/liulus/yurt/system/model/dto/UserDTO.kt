package com.github.liulus.yurt.system.model.dto

import com.github.liulus.yurt.convention.data.PageQuery

/**
 *
 * @author liulu
 * @version V1.0
 * @since 2021/3/17
 */
abstract class UserDTO {

    class Query : PageQuery() {
        var deptId: Long? = null
        var username: String? = null
        var mobileNum: String? = null
    }

    class View {
        var id: Long? = null
        var username: String? = null
        var nickName: String? = null
        var mobileNum: String? = null
        var status: String? = null
        var statusText: String? = null

        var deptId: Long? = null
        var deptName: String? = null

    }


}