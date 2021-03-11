package com.github.liulus.yurt.system.model.entity

import com.github.liulus.yurt.system.ext.TABLE_PREFIX_SYS
import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.Table

/**
 *
 * @author liulu
 * @version V1.0
 * @since 2021/2/9
 */
@Table(name = TABLE_PREFIX_SYS + "user_role")
data class UserRole(
        var id: Long? = null,
        var userId: Long? = null,
        var roleId: Long? = null,

        @Column(name = "is_deleted")
        var deleted: Boolean? = null,
        var gmtCreated: LocalDateTime? = null,
        var gmtModified: LocalDateTime? = null,
        var gmtDeleted: LocalDateTime? = null,
)