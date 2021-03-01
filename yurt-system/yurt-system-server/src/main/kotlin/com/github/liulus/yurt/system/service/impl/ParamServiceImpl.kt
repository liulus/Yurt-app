package com.github.liulus.yurt.system.service.impl

import com.github.liulus.yurt.convention.data.Page
import com.github.liulus.yurt.convention.exception.ServiceException
import com.github.liulus.yurt.system.model.dto.SysParamQo
import com.github.liulus.yurt.system.model.entity.SysParam
import com.github.liulus.yurt.system.repository.ParamRepository
import com.github.liulus.yurt.system.service.ParamService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import javax.annotation.Resource

/**
 * User : liulu
 * Date : 17-9-17 下午2:53
 * version $Id: ParamServiceImpl.java, v 0.1 Exp $
 */
@Service
@Transactional
open class ParamServiceImpl : ParamService {
    @Resource
    private val paramRepository: ParamRepository? = null
    override fun findPageList(qo: SysParamQo?): Page<SysParam?>? {
//        return paramRepository.selectPageList(qo);
        return null
    }

    override fun findById(id: Long?): SysParam? {
        return paramRepository!!.selectById(id!!)
    }

    override fun findByCode(code: String?): SysParam? {
        return paramRepository!!.selectByCode(code)
    }

    override fun insert(param: SysParam?): Long? {
        checkCode(param!!.code)
        param.system = false
        paramRepository!!.insert(param)
        return param.id
    }

    override fun update(param: SysParam?) {
        val (_, code, _, _, system) = findById(param!!.id) ?: return
        if (param.code != code) {
            if (system!!) {
                throw ServiceException(String.format("%s 是系统级参数, 不允许修改", code))
            }
            checkCode(param.code)
        }
        param.system = null
        paramRepository!!.updateIgnoreNull(param)
    }

    private fun checkCode(code: String?) {
        val param = findByCode(code)
        if (param != null) {
            throw ServiceException("参数 code 已经存在")
        }
    }

//    override fun delete(vararg ids: Long) {
//        if (ids == null || ids.size == 0) {
//            return
//        }
//        val params = paramRepository!!.selectByIds(Arrays.asList(*ids))
//        val validIds: MutableList<Long?> = ArrayList(params.size)
//        for ((id, code, _, _, system) in params) {
//            if (system!!) {
//                throw ServiceException(String.format("%s 是系统级参数, 不允许删除", code))
//            }
//            validIds.add(id)
//        }
//        //        paramRepository.deleteByIds(validIds);
//    }
}