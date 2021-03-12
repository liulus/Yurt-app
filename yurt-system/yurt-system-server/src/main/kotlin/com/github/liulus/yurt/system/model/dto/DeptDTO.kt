package com.github.liulus.yurt.system.model.dto

import java.time.LocalDateTime
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

/**
 *
 * @author liulu
 * @version V1.0
 * @since 2021/3/10
 */
abstract class DeptDTO {

    class Query(var parentId: Long? = null, var orgCode: String? = null)


    data class Detail(
        var id: Long? = null,
        @NotNull
        var parentId: Long? = null,
        @NotEmpty
        @Size(min = 3, max = 250)
        var name: String? = null,
        var orderNum: Int? = null,
        var enabled: Boolean? = null,
        @Size(min = 2, max = 500)
        var remark: String? = null,
        var gmtCreated: LocalDateTime? = null,
        var children: List<Detail>? = null
    )

}