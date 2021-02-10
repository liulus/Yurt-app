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
@Table(name = SystemConst.TABLE_PREFIX + "user")
data class User(
        var id: Long? = null,
        var orgId: Long? = null,
        var code: String? = null,
        var username: String? = null,
        var nickName: String? = null,
        var mobileNum: String? = null,
        var realName: String? = null,
        var idCardNum: String? = null,
        var avatar: String? = null,
        var gender: Short? = null,
        var email: String? = null,
        var type: String? = null,
        var status: String? = null,
        var creator: String? = null,

        @Column(name = "is_lock")
        var lock: Boolean? = null,
        @Column(name = "is_deleted")
        var deleted: Boolean? = null,
        var gmtCreated: LocalDateTime? = null,
        var gmtModified: LocalDateTime? = null,
        var gmtDeleted: LocalDateTime? = null,
) {
    var password: String? = null
}
