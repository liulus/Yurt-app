package com.github.liulus.yurt.system.controller

import com.github.liulus.yurt.convention.data.Page
import com.github.liulus.yurt.system.model.dto.UserDTO
import com.github.liulus.yurt.system.service.UserService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.annotation.Resource
import javax.validation.Valid


/**
 * @author liulu
 * @version V1.0
 * @since 2020/11/20
 */
@RestController
@RequestMapping("/api/user")
open class UserController {

    @Resource
    private lateinit var userService: UserService


    @GetMapping("/list")
    fun getUserList(@Valid query: UserDTO.Query): Page<UserDTO.View> {
        return userService.findPageList(query);
    }



}