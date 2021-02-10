package com.github.liulus.yurt.system.model.dto

import com.github.liulus.yurt.convention.data.PageQuery

/**
 * User : liulu
 * Date : 2018/1/16 15:17
 * version $Id: UserQo.java, v 0.1 Exp $
 */
class UserQo : PageQuery() {
    var id: Long? = null
    var keyword: String? = null
    var username: String? = null
    var orgId: String? = null
    var orgCode: String? = null
    var orgName: String? = null
    var serialNum: String? = null

}