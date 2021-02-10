package com.github.liulus.yurt.system.security


import com.github.liulus.yurt.convention.util.JsonUtils
import com.github.liulus.yurt.convention.util.Results
import org.springframework.http.MediaType
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.security.web.access.AccessDeniedHandler
import java.io.IOException
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * @author liulu
 * @version V1.0
 * @since 2021/2/7
 */
class JsonAccessDeniedHandler : AccessDeniedHandler, AuthenticationEntryPoint {
    @Throws(IOException::class, ServletException::class)
    override fun handle(
        request: HttpServletRequest,
        response: HttpServletResponse,
        accessDeniedException: AccessDeniedException
    ) {
        val noAuth = Results.failure("ACCESS_DENIED", "无权访问")
        response.contentType = MediaType.APPLICATION_JSON_UTF8_VALUE
        response.writer.write(JsonUtils.toJson(noAuth))
    }

    @Throws(IOException::class, ServletException::class)
    override fun commence(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authException: AuthenticationException
    ) {
        val unLogin = Results.failure("UN_LOGIN", "您当前未登录或登录超时, 请重新登录")
        response.contentType = MediaType.APPLICATION_JSON_UTF8_VALUE
        response.writer.write(JsonUtils.toJson(unLogin))
    }
}