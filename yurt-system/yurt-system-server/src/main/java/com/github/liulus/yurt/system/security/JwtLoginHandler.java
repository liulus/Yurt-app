package com.github.liulus.yurt.system.security;


import com.github.liulus.yurt.system.util.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author liulu
 * @version v1.0
 * created_at 2020/6/24
 */
@Slf4j
public class JwtLoginHandler implements AuthenticationSuccessHandler, AuthenticationFailureHandler {

    private static final String SUCCESS_RES = "{\"code\": \"0\", \"success\": true, \"access_token\": \"%s\"}";
    private static final String FAILURE_RES = "{\"code\": \"-1\", \"success\": false, \"message\": \"%s\"}";

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        String jwt = JwtUtils.encode(loginUser);
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        response.getWriter().write(String.format(SUCCESS_RES, jwt));
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        response.getWriter().write(String.format(FAILURE_RES, parseFailureMessage(exception)));
    }

    private static String parseFailureMessage(Exception ex) {
        if (ex instanceof UsernameNotFoundException
                || ex instanceof BadCredentialsException) {
            return "用户名或密码错误!";
        }
        if (ex instanceof LockedException) {
            return "该用户已被锁定!";
        }
        if (ex instanceof DisabledException) {
            return "该用户已被禁用!";
        }
        if (ex instanceof AccountExpiredException) {
            return "该用户已过期!";
        }
        if (ex instanceof CredentialsExpiredException) {
            return "密码已过期!";
        }
        if (ex instanceof SessionAuthenticationException) {
            return "该用户已登录!";
        }
        return "系统异常";
    }

}
