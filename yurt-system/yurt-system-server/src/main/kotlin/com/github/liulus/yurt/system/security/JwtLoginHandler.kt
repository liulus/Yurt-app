package com.github.liulus.yurt.system.security

import com.github.liulus.yurt.system.util.JwtUtils.encode
import org.springframework.http.MediaType
import org.springframework.security.authentication.AccountExpiredException
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.CredentialsExpiredException
import org.springframework.security.authentication.DisabledException
import org.springframework.security.authentication.LockedException
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.web.authentication.AuthenticationFailureHandler
import org.springframework.security.web.authentication.AuthenticationSuccessHandler
import org.springframework.security.web.authentication.session.SessionAuthenticationException
import java.io.IOException
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * @author liulu
 * @version v1.0
 * created_at 2020/6/24
 */
class JwtLoginHandler : AuthenticationSuccessHandler, AuthenticationFailureHandler {
    @Throws(IOException::class, ServletException::class)
    override fun onAuthenticationSuccess(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authentication: Authentication
    ) {
        val loginUser = authentication.principal as LoginUser
        val jwt = encode(loginUser)
        response.contentType = MediaType.APPLICATION_JSON_UTF8_VALUE
        response.writer.write(String.format(SUCCESS_RES, jwt))
    }

    @Throws(IOException::class, ServletException::class)
    override fun onAuthenticationFailure(
        request: HttpServletRequest,
        response: HttpServletResponse,
        exception: AuthenticationException
    ) {
        response.status = HttpServletResponse.SC_OK
        response.contentType = MediaType.APPLICATION_JSON_UTF8_VALUE
        response.writer.write(String.format(FAILURE_RES, parseFailureMessage(exception)))
    }

    companion object {
        private const val SUCCESS_RES = "{\"code\": \"0\", \"success\": true, \"access_token\": \"%s\"}"
        private const val FAILURE_RES = "{\"code\": \"-1\", \"success\": false, \"message\": \"%s\"}"
        private fun parseFailureMessage(ex: Exception): String {
            if (ex is UsernameNotFoundException
                || ex is BadCredentialsException
            ) {
                return "用户名或密码错误!"
            }
            if (ex is LockedException) {
                return "该用户已被锁定!"
            }
            if (ex is DisabledException) {
                return "该用户已被禁用!"
            }
            if (ex is AccountExpiredException) {
                return "该用户已过期!"
            }
            if (ex is CredentialsExpiredException) {
                return "密码已过期!"
            }
            return if (ex is SessionAuthenticationException) {
                "该用户已登录!"
            } else "系统异常"
        }
    }
}