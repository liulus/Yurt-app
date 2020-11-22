package com.github.liulus.yurt.system.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author liulu
 * @version V1.0
 * @since 2020/11/20
 */
@Controller
@RequestMapping("/user")
public class LoginController {

    @GetMapping("/login")
    public String userLogin() {

        return "login";
    }

}
