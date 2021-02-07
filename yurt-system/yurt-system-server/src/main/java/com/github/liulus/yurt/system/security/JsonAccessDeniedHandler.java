package com.github.liulus.yurt.system.security;

import com.github.liulus.yurt.convention.data.Result;
import com.github.liulus.yurt.convention.util.JsonUtils;
import com.github.liulus.yurt.convention.util.Results;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author liulu
 * @version V1.0
 * @since 2021/2/7
 */
public class JsonAccessDeniedHandler implements AccessDeniedHandler, AuthenticationEntryPoint {


    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        Result<?> noAuth = Results.failure("ACCESS_DENIED", "无权访问");
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        response.getWriter().write(JsonUtils.toJson(noAuth));
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        Result<?> unLogin = Results.failure("UN_LOGIN", "您当前未登录或登录超时, 请重新登录");
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        response.getWriter().write(JsonUtils.toJson(unLogin));
    }
}
