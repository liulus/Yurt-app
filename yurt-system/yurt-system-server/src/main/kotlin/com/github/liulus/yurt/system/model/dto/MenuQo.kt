package com.github.liulus.yurt.system.model.dto

import com.github.liulus.yurt.convention.data.PageQuery

/**
 * User : liulu
 * Date : 2018/1/15 10:41
 * version $Id: MenuQo.java, v 0.1 Exp $
 */
class MenuQo : PageQuery() {
    var id: Long? = null
    var parentId = 0L
    var menuCode: String? = null
    var keyword: String? = null
    
}