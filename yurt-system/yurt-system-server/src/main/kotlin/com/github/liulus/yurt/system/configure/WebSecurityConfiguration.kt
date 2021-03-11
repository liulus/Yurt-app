package com.github.liulus.yurt.system.configure

import com.github.liulus.yurt.system.security.JsonAccessDeniedHandler
import com.github.liulus.yurt.system.security.JwtAuthenticationProcessingFilter
import com.github.liulus.yurt.system.security.JwtLoginHandler
import com.github.liulus.yurt.system.security.LoginUserService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource

/**
 *
 * @author liulu
 * @version V1.0
 * @since 2021/2/9
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, proxyTargetClass = true)
open class WebSecurityConfiguration : WebSecurityConfigurerAdapter() {

    @Bean
    open fun loginUserService(): UserDetailsService {
        return LoginUserService()
    }

    @Bean
    open fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.userDetailsService(loginUserService()).passwordEncoder(passwordEncoder())
    }

    override fun configure(http: HttpSecurity) {
        http.addFilterBefore(JwtAuthenticationProcessingFilter(), UsernamePasswordAuthenticationFilter::class.java)
        val handler = JwtLoginHandler()
        // 登录配置
        http.formLogin()
                .loginPage("/login")
                .successHandler(handler)
                .failureHandler(handler)

        // 允许匿名访问
        http.anonymous().principal("anonymous")
        http.authorizeRequests()
                .antMatchers("/api/**").authenticated()
                .anyRequest().permitAll()
        // 权限异常控制
        val deniedHandler = JsonAccessDeniedHandler()
        http.exceptionHandling().accessDeniedHandler(deniedHandler).authenticationEntryPoint(deniedHandler)
        // 跨域问题
//        http.cors().configurationSource(source())

        // 对于前台页面请求, 禁用一些不必要的拦截器
        http.logout().disable()
                .requestCache().disable()
                .csrf().disable()
                .securityContext().disable()
                .headers().disable()
                .sessionManagement().disable()
    }

    private fun source(): CorsConfigurationSource {
        val source = UrlBasedCorsConfigurationSource()
        val config = CorsConfiguration()
        config.allowCredentials = true
        config.addAllowedOrigin("*")
        config.addAllowedHeader("*")
        config.addAllowedMethod("*")
        source.registerCorsConfiguration("/**", config)
        return source
    }
}