package com.github.liulus.yurt.system.model.dto

import javax.validation.constraints.Min
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

/**
 * User : liulu
 * Date : 2017/7/13 19:49
 * version $Id: MenuDTO.java, v 0.1 Exp $
 */
object MenuDTO {

    data class Query(var enabled: Boolean? = null, var type: String? = null)

    open class View {
        var parentId: Long? = null
        var code: String? = null

        @Size(min = 1, max = 30, message = "菜单名称长度 1-30 之间")
        var name: String? = null
        var icon: String? = null
        var url: String? = null
        var orderNum: Int? = null
        var authCode: String? = null
        var type: String? = null
        var remark: String? = null
    }

    data class Detail(
        var id: Long? = null,
        var enabled: Boolean? = null,
        var children: List<Detail>? = null
    ) : View()

    class Add : View()
    class Update : View() {
        @NotNull(message = "id不能为空")
        @Min(value = 1L, message = "id不正确")
        var id: Long? = null
    }
}