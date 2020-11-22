package com.github.liulus.yurt.system.configure;

import com.github.liulus.yurt.system.security.JwtAuthenticationProcessingFilter;
import com.github.liulus.yurt.system.security.JwtLoginHandler;
import com.github.liulus.yurt.system.security.LoginUserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

/**
 * @author liulu
 * @version v1.0
 * date 2019-05-10
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, proxyTargetClass = true)
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Bean
    public UserDetailsService loginUserService() {
        return new LoginUserService();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(loginUserService()).passwordEncoder(passwordEncoder());
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.addFilterBefore(new JwtAuthenticationProcessingFilter(), UsernamePasswordAuthenticationFilter.class);
        JwtLoginHandler handler = new JwtLoginHandler();
        // 登录配置
        http.formLogin()
                .successHandler(handler)
                .failureHandler(handler);

        // 允许匿名访问
        http.anonymous().principal("anonymous");
        http.authorizeRequests()
                .antMatchers("/api/**").authenticated()
                .anyRequest().permitAll();

        // 跨域问题
        http.cors().configurationSource(source());

        // 对于前台页面请求, 禁用一些不必要的拦截器
        http.logout().disable()
                .requestCache().disable()
                .csrf().disable()
                .securityContext().disable()
                .headers().disable()
                .sessionManagement().disable();
    }


    public CorsConfigurationSource source() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config);
        return source;
    }


}
