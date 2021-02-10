package com.github.liulus.yurt.system.model.dto

import com.github.liulus.yurt.convention.data.PageQuery

/**
 * User : liulu
 * Date : 17-9-17 下午3:03
 * version $Id: ParamVo.java, v 0.1 Exp $
 */
class SysParamQo : PageQuery() {
    var keyword: String? = null
    var code: String? = null

}