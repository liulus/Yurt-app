package com.github.liulus.yurt.system.service.impl

import com.github.liulus.yurt.convention.bean.BeanUtils
import com.github.liulus.yurt.convention.data.Page
import com.github.liulus.yurt.convention.exception.ServiceException
import com.github.liulus.yurt.convention.util.Pages
import com.github.liulus.yurt.system.model.dto.UserDTO
import com.github.liulus.yurt.system.model.dto.UserVo
import com.github.liulus.yurt.system.model.dto.UserVo.Register
import com.github.liulus.yurt.system.model.entity.User
import com.github.liulus.yurt.system.repository.DeptRepository
import com.github.liulus.yurt.system.repository.UserRepository
import com.github.liulus.yurt.system.service.UserService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.util.StringUtils
import javax.annotation.Resource

/**
 * User : liulu
 * Date : 2017/8/12 11:20
 * version $Id: UserServiceImpl.java, v 0.1 Exp $
 */
@Service
@Transactional
open class UserServiceImpl : UserService {

    @Resource
    private lateinit var userRepository: UserRepository

    @Resource
    private lateinit var deptRepository: DeptRepository

    override fun register(register: Register?): Long? {
        if (register!!.password != register.confirmPassword) {
            throw ServiceException("密码和确认密码不一致")
        }
        if (register.smsCaptcha != "1234") {
            throw ServiceException("手机验证码不正确")
        }
        return insert(BeanUtils.convert(register, UserDTO.View()))
    }

    override fun findById(id: Long?): User? {
        return userRepository.selectById(id!!)
    }

    override fun findByUsername(username: String): User? {
        return userRepository.selectByUsername(username)
    }

    override fun findPageList(query: UserDTO.Query): Page<UserDTO.View> {
        if (!query.mobileNum.isNullOrEmpty()) {
            query.mobileNum = query.mobileNum + "%"
        }
        val dictList = userRepository.selectByQuery(query)
        val deptIds = dictList.mapNotNull { it.deptId }
        val deptMap = deptRepository.selectByIds(deptIds).associateBy({ it.id }, { it })

        return Pages.page(dictList).simpleMap {
            val view = BeanUtils.convert(it, UserDTO.View())
            view.deptName = deptMap[it.deptId]?.name ?: ""
            view
        }
    }

    override fun insert(user: UserDTO.View): Long {
        checkUsername(user!!.username!!)
        checkEmail(user.email!!)
        checkMobileNum(user.mobileNum!!)
        val addUser = BeanUtils.convert(user, User())

        // 设置默认值
        addUser.lock = false
        //        addUser.setCreator(UserUtils.getLoginUser().getUsername());
        val password = "123456"
        //        addUser.setPassword(UserUtils.encode(password));
        userRepository.insert(addUser)
        return addUser.id!!
    }

    override fun update(user: UserVo.Update?) {
        val oldUser = findById(user!!.id)
        if (oldUser!!.username != user.username) {
            checkUsername(user.username!!)
        }
        if (oldUser.email != user.email) {
            checkEmail(user.email!!)
        }
        if (oldUser.mobileNum != user.mobileNum) {
            checkMobileNum(user.mobileNum!!)
        }
        val upUser = BeanUtils.convert(user, User())
        userRepository.updateIgnoreNull(upUser)
    }

    private fun checkUsername(username: String) {
        if (StringUtils.isEmpty(username)) {
            return
        }
        //        int count = userRepository.countByProperty(User::getUsername, username);
//        if (count >= 1) {
//            throw new ServiceException("该用户名被使用");
//        }
    }

    private fun checkEmail(email: String) {
        if (StringUtils.isEmpty(email)) {
            return
        }
        //        int count = userRepository.countByProperty(User::getEmail, email);
//        if (count >= 1) {
//            throw new ServiceException("该邮箱已被使用");
//        }
    }

    private fun checkMobileNum(mobileNum: String) {
        if (StringUtils.isEmpty(mobileNum)) {
            return
        }
        //        int count = userRepository.countByProperty(User::getMobileNum, mobileNum);
//        if (count >= 1) {
//            throw new ServiceException("该手机号已被使用");
//        }
    }

    override fun delete(id: Long?) {
        userRepository.deleteById(id!!)
    }
}