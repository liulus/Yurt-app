package com.github.liulus.yurt.system.security;


import com.github.liulus.yurt.system.util.JwtUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;

/**
 * @author liulu
 * @version v1.0
 * created_at 2020/6/25
 */
public class JwtAuthenticationProcessingFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String tokenValue = extractToken(request);
        if (StringUtils.isEmpty(tokenValue)) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            LoginUser loginUser = JwtUtils.parseToken(tokenValue);
            if (loginUser == null) {
                filterChain.doFilter(request, response);
                return;
            }
            Authentication authentication = new UsernamePasswordAuthenticationToken(loginUser, "", loginUser.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (Exception ex) {
            SecurityContextHolder.clearContext();
        }
        filterChain.doFilter(request, response);
    }

    protected String extractToken(HttpServletRequest request) {
        // first check the header...
        String token = extractHeaderToken(request);

        // bearer type allows a request parameter as well
        if (token == null) {
            logger.debug("Token not found in headers. Trying request parameters.");
            token = request.getParameter("access_token");
            if (token == null) {
                logger.debug("Token not found in request parameters.  Not an OAuth2 request.");
            }
//            else {
//                request.setAttribute(OAuth2AuthenticationDetails.ACCESS_TOKEN_TYPE, OAuth2AccessToken.BEARER_TYPE);
//            }
        }

        return token;
    }

    private static final String BEARER_TYPE = "Bearer";

    private String extractHeaderToken(HttpServletRequest request) {
        Enumeration<String> headers = request.getHeaders("Authorization");
        while (headers.hasMoreElements()) { // typically there is only one (most servers enforce that)
            String value = headers.nextElement();
            if (value.toLowerCase().startsWith(BEARER_TYPE.toLowerCase())) {
                String authHeaderValue = value.substring(BEARER_TYPE.length()).trim();
                // Add this here for the auth details later. Would be better to change the signature of this method.
//                request.setAttribute(OAuth2AuthenticationDetails.ACCESS_TOKEN_TYPE,
//                        value.substring(0, OAuth2AccessToken.BEARER_TYPE.length()).trim());
                int commaIndex = authHeaderValue.indexOf(',');
                if (commaIndex > 0) {
                    authHeaderValue = authHeaderValue.substring(0, commaIndex);
                }
                return authHeaderValue;
            }
        }

        return null;
    }
}
