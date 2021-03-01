package com.github.liulus.yurt.system.service.impl

import com.github.liulus.yurt.convention.bean.BeanUtils
import com.github.liulus.yurt.convention.data.Page
import com.github.liulus.yurt.convention.exception.ServiceException
import com.github.liulus.yurt.system.model.dto.UserQo
import com.github.liulus.yurt.system.model.dto.UserVo
import com.github.liulus.yurt.system.model.dto.UserVo.Register
import com.github.liulus.yurt.system.model.entity.User
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
    private val userRepository: UserRepository? = null

    override fun register(register: Register?): Long? {
        if (register!!.password != register.confirmPassword) {
            throw ServiceException("密码和确认密码不一致")
        }
        if (register.smsCaptcha != "1234") {
            throw ServiceException("手机验证码不正确")
        }
        return insert(BeanUtils.convert(register, UserVo.Add()))
    }

    override fun findById(id: Long?): User? {
        return userRepository!!.selectById(id!!)
    }

    override fun findByUsername(username: String): User? {
        return  userRepository!!.selectByUsername(username)
    }

    override fun findPageList(qo: UserQo?): Page<UserVo.List?>? {
//        LoginUser loginUser = UserUtils.getLoginUser();

//        if (loginUser != null && loginUser.hasOrg()) {
//            qo.setSerialNum(loginUser.getLevelIndex());
//            if (StringUtils.isEmpty(qo.getOrgCode())) {
//                qo.setOrgCode(loginUser.getOrgCode());
//            }
//        }
//        Page<User> pageList = userRepository.selectPageList(qo);
        return null
        //        return PageUtils.convert(pageList, UserVo.List.class);
    }

    override fun insert(user: UserVo.Add?): Long? {
        checkUsername(user!!.username!!)
        checkEmail(user.email!!)
        checkMobileNum(user.mobileNum!!)
        val addUser = BeanUtils.convert(user, User())

        // 设置默认值
        addUser.lock = false
        //        addUser.setCreator(UserUtils.getLoginUser().getUsername());
        val password = if (StringUtils.hasText(user.password)) user.password else "123456"
        //        addUser.setPassword(UserUtils.encode(password));
        userRepository!!.insert(addUser)
        return addUser.id
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
        userRepository!!.updateIgnoreNull(upUser)
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
        userRepository!!.deleteById(id!!)
    }
}