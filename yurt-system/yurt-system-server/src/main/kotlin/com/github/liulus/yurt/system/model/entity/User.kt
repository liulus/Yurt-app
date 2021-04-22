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
@Table(name = TABLE_PREFIX_SYS + "user")
data class User(
    var id: Long? = null,
    var deptId: Long? = null,
    var username: String? = null,
    var nickName: String? = null,
    var mobileNum: String? = null,
    var avatar: String? = null,
    var gender: Short? = null,
    var email: String? = null,
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
