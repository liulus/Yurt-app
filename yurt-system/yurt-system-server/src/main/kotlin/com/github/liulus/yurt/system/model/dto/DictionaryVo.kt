package com.github.liulus.yurt.system.model.dto

/**
 * User : liulu
 * Date : 2018/3/26 16:30
 * version $Id: DictionaryVo.java, v 0.1 Exp $
 */
abstract class DictionaryVo {
    var dictKey: String? = null
    var dictValue: String? = null
    var orderNum: Int? = null
    var remark: String? = null
    
    class Add : DictionaryVo() {
        var parentId: Long? = null
    }

    class Update : DictionaryVo() {
        var id: Long? = null
    }

    class Detail : DictionaryVo() {
        var id: Long? = null
        var parentId: Long? = null
        var children: List<Detail>? = null
    }
}