package com.github.liulus.yurt.system.security

import com.github.liulus.yurt.system.util.JwtUtils.parseToken
import org.springframework.http.MediaType
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.authentication.AnonymousAuthenticationToken
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.util.StringUtils
import org.springframework.web.filter.OncePerRequestFilter
import java.io.IOException
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * @author liulu
 * @version v1.0
 * created_at 2020/6/25
 */
class JwtAuthenticationProcessingFilter : OncePerRequestFilter() {
    @Throws(ServletException::class, IOException::class)
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val tokenValue = extractToken(request)
        if (StringUtils.isEmpty(tokenValue)) {
            invoke(request, response, filterChain)
            return
        }
        try {
            val loginUser = parseToken(tokenValue)
            if (loginUser == null) {
                filterChain.doFilter(request, response)
                return
            }
            val authentication: Authentication =
                UsernamePasswordAuthenticationToken(loginUser, "", loginUser.authorities)
            SecurityContextHolder.getContext().authentication = authentication
        } catch (ex: Exception) {
            SecurityContextHolder.clearContext()
        }
        invoke(request, response, filterChain)
    }

    @Throws(ServletException::class, IOException::class)
    private operator fun invoke(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain) {
        try {
            filterChain.doFilter(request, response)
        } catch (deniedException: AccessDeniedException) {
            response.status = HttpServletResponse.SC_OK
            response.contentType = MediaType.APPLICATION_JSON_UTF8_VALUE
            val authentication = SecurityContextHolder.getContext().authentication
            if (authentication is AnonymousAuthenticationToken) {
                response.writer.write(String.format(DENIED_RES, "UN_LOGIN", "您当前未登录, 请登录"))
                return
            }
            response.writer.write(String.format(DENIED_RES, "ACCESS_DENIED", "您无权访问, 请重新登录"))
        } catch (ex: Exception) {
            throw ex
        }
    }

    protected fun extractToken(request: HttpServletRequest): String? {
        // first check the header...
        var token = extractHeaderToken(request)

        // bearer type allows a request parameter as well
        if (token == null) {
            logger.debug("Token not found in headers. Trying request parameters.")
            token = request.getParameter("access_token")
            if (token == null) {
                logger.debug("Token not found in request parameters.  Not an OAuth2 request.")
            }
        }
        return token
    }

    private fun extractHeaderToken(request: HttpServletRequest): String? {
        val headers = request.getHeaders("Authorization")
        while (headers.hasMoreElements()) { // typically there is only one (most servers enforce that)
            val value = headers.nextElement()
            if (value.toLowerCase().startsWith(BEARER_TYPE.toLowerCase())) {
                var authHeaderValue = value.substring(BEARER_TYPE.length).trim { it <= ' ' }
                val commaIndex = authHeaderValue.indexOf(',')
                if (commaIndex > 0) {
                    authHeaderValue = authHeaderValue.substring(0, commaIndex)
                }
                return authHeaderValue
            }
        }
        return null
    }

    companion object {
        private const val DENIED_RES = "{\"code\": \"%s\", \"success\": false, \"message\": \"%s\"}"
        private const val BEARER_TYPE = "Bearer"
    }
}