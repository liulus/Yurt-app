package com.github.liulus.yurt.system.configure;

import com.github.liulus.yurt.system.constant.ViewProperties;
import com.github.liulus.yurt.system.model.dto.Routes;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.view.MustacheViewResolver;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author liulu
 * @version V1.0
 * @since 2020/11/21
 */
@Configuration
@EnableConfigurationProperties(ViewProperties.class)
public class WebMvcConfiguration implements WebMvcConfigurer {

    @Resource
    private ViewProperties viewProperties;

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/login").setViewName(viewProperties.getUi() + "/login");
        registry.addViewController("/console").setViewName(viewProperties.getUi() + "/console");
    }


    @Bean
    public Routes sysRoutes() {
        Routes routes = new Routes();
        Routes.Route index = new Routes.Route();
        index.setPath("/");
        index.setRedirect("/home");
        routes.addRoute(index);
        routes.addRoute("/home", "/view/" + viewProperties.getUi() + "/home.js");
        routes.addRoute("/menu/index", "/view/" + viewProperties.getUi() + "/menu.js");
        return routes;
    }


    @EventListener
    public void appStartListener(ContextRefreshedEvent contextRefreshedEvent) {
        Map<String, Object> globalData = new HashMap<>();
        globalData.put("cdnHost", viewProperties.getCdnHost());
        globalData.put("ui", viewProperties.getUi());
        ApplicationContext context = contextRefreshedEvent.getApplicationContext();
        Collection<Routes> routeBeans = context.getBeansOfType(Routes.class).values();

        List<Routes.Route> routes = new ArrayList<>();
        routeBeans.forEach(r -> routes.addAll(r.getRoutes()));
        globalData.put("routes", Arrays.toString(routes.toArray()));
        if (context instanceof WebApplicationContext) {
            ServletContext servletContext = ((WebApplicationContext) context).getServletContext();
            String contextPath = Optional.ofNullable(servletContext)
                    .map(ServletContext::getContextPath).orElse("");
            globalData.put("contextPath", contextPath);
        }

        MustacheViewResolver viewResolver = context.getBean(MustacheViewResolver.class);
        viewResolver.setAttributesMap(globalData);
    }

}
