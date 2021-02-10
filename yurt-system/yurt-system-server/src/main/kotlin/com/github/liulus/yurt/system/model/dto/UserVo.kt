package com.github.liulus.yurt.system.model.dto

/**
 * User : liulu
 * Date : 17-10-3 下午3:51
 * version $Id: UserVo.java, v 0.1 Exp $
 */
abstract class UserVo {
    var code: String? = null
    var username: String? = null
    var jobNum: String? = null
    var nickName: String? = null
    var realName: String? = null
    var idCardNum: String? = null
    var avatar: String? = null
    var email: String? = null
    var mobileNum: String? = null
    var telephone: String? = null
    var password: String? = null
    var gender: Boolean? = null
    
    class List {
        var id: Long? = null
        var username: String? = null
        var mobileNum: String? = null
        var gender: Short? = null
        var lock: Boolean? = null
    }
    
    class Detail : UserVo() {
        var id: Long? = null
        var type: String? = null
        var lock: Boolean? = null
    }
    
    class Add : UserVo()
    
    class Update : UserVo() {
        var id: Long? = null
    }
    
    class Register {
        var mobileNum: String? = null
        var password: String? = null
        var confirmPassword: String? = null
        var smsCaptcha: String? = null
    }
}