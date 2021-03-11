package com.github.liulus.yurt.system.model.dto

import com.github.liulus.yurt.convention.data.PageQuery
import java.time.LocalDateTime
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

/**
 *
 * @author liulu
 * @version V1.0
 * @since 2021/3/2
 */
abstract class DictDTO {

    class Query : PageQuery() {
        var parentId: Long? = null

        @Size(max = 60, message = "关键字长度不能超过60")
        var keyword: String? = null
    }

    class Detail {
        var id: Long? = null

        @NotNull
        var parentId: Long? = null

        @NotBlank(message = "字典key不能为空")
        @Size(min = 1, max = 30, message = "字典key长度在1-30之间")
        var dictKey: String? = null

        @NotBlank(message = "字典值不能为空")
        @Size(min = 1, max = 30, message = "字典值长度在1-30之间")
        var dictValue: String? = null
        var orderNum: Int? = null

        @Size(max = 250, message = "备注最长不能超过250")
        var remark: String? = null
        var gmtCreated: LocalDateTime? = null
    }


}