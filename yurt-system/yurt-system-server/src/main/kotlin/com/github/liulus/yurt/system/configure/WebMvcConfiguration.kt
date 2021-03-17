package com.github.liulus.yurt.system.configure

import com.github.liulus.yurt.convention.web.ApiResponseHandler
import com.github.liulus.yurt.system.model.Routes
import com.github.liulus.yurt.system.model.ViewProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import
import org.springframework.context.event.ContextRefreshedEvent
import org.springframework.context.event.EventListener
import org.springframework.web.context.WebApplicationContext
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import org.springframework.web.servlet.view.AbstractTemplateViewResolver
import java.util.Optional
import javax.annotation.Resource
import javax.servlet.ServletContext

/**
 * @author liulu
 * @version V1.0
 * @since 2020/11/21
 */
@Configuration
@EnableConfigurationProperties(ViewProperties::class)
@Import(ApiResponseHandler::class)
open class WebMvcConfiguration : WebMvcConfigurer {

    @Resource
    private lateinit var viewProperties: ViewProperties

    override fun addViewControllers(registry: ViewControllerRegistry) {
        registry.addViewController("/login").setViewName(viewProperties.ui + "/login")
        registry.addViewController("/console").setViewName(viewProperties.ui + "/console")
    }

    @Bean
    open fun sysRoutes(): Routes {
        val routes = Routes()
        val index = Routes.Route()
        index.path = "/"
        index.redirect = "/home"
        routes.addRoute(index)
        routes.addRoute("/home", "/view/${viewProperties.ui}/home.js")
        routes.addRoute("/sys/menu", "/view/${viewProperties.ui}/menu.js")
        routes.addRoute("/sys/dictionary", "/view/${viewProperties.ui}/dictionary.js")
        routes.addRoute("/sys/user", "/view/${viewProperties.ui}/user.js")
        routes.addRoute("/sys/user/edit", "/view/${viewProperties.ui}/user-edit.js")
        routes.addRoute("/sys/dept", "/view/${viewProperties.ui}/dept.js")
        return routes
    }

    @EventListener
    fun appStartListener(contextRefreshedEvent: ContextRefreshedEvent) {
        val globalData: MutableMap<String, Any?> = HashMap()
        globalData["cdnHost"] = viewProperties.cdnHost
        globalData["ui"] = viewProperties.ui
        val context = contextRefreshedEvent.applicationContext
        val routeBeans = context.getBeansOfType(Routes::class.java).values
        globalData["routes"] = routeBeans.asSequence()
            .flatMap { it.routes }.toList()
            .toTypedArray().contentToString()
        if (context is WebApplicationContext) {
            val contextPath = Optional.ofNullable(context.servletContext)
                .map { obj: ServletContext -> obj.contextPath }.orElse("")
            globalData["contextPath"] = contextPath
        }
        val viewResolver = context.getBean(AbstractTemplateViewResolver::class.java)
        viewResolver.attributesMap = globalData
    }
}
