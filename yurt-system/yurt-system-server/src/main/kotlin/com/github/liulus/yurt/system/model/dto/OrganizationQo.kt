package com.github.liulus.yurt.system.model.dto

import com.github.liulus.yurt.convention.data.PageQuery

/**
 * User : liulu
 * Date : 17-10-5 上午11:27
 * version $Id: OrganizationVo.java, v 0.1 Exp $
 */
class OrganizationQo : PageQuery() {
    var keyword: String? = null
    var parentId = 0L
    var orgCode: String? = null

}