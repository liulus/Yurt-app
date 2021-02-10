package com.github.liulus.yurt.system.model.entity

import com.github.liulus.yurt.system.context.SystemConst
import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.Table

/**
 *
 * @author liulu
 * @version V1.0
 * @since 2021/2/9
 */
@Table(name = SystemConst.TABLE_PREFIX + "dictionary")
data class Dictionary(
        var id: Long? = null,
        var parentId: Long? = null,
        var dictKey: String? = null,
        var dictValue: String? = null,
        var orderNum: Int? = null,
        var remark: String? = null,

        @Column(name = "is_system")
        var system: Boolean? = null,
        @Column(name = "is_deleted")
        var deleted: Boolean? = null,
        var gmtCreated: LocalDateTime? = null,
        var gmtModified: LocalDateTime? = null,
        var gmtDeleted: LocalDateTime? = null,
)