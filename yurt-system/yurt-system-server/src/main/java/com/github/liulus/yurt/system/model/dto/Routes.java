package com.github.liulus.yurt.system.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author Liulu
 * @version v1.0
 * date 2019-05-15
 */
public class Routes implements Serializable {

    private List<Route> routes;

    @Getter
    @Setter
    public static class Route {
        private String name;
        private String path;
        private String component;
        private String redirect;
        private Map<String, Object> meta;
        private List<String> children;

        public Route() {
        }

        public Route(String path, String component) {
            this.path = path;
            this.component = component;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder("{");
            Optional.ofNullable(name).ifPresent(s -> sb.append(", name:'").append(s).append('\''));
            Optional.ofNullable(path).ifPresent(s -> sb.append(", path:'").append(s).append('\''));
            Optional.ofNullable(component).ifPresent(s -> sb.append(", component:_import('").append(s).append("')"));
            Optional.ofNullable(redirect).ifPresent(s -> sb.append(", redirect:'").append(s).append('\''));
            sb.append(" }");
            int index = sb.indexOf(",");
            if (index > 0) {
                sb.deleteCharAt(index);
            }
            return sb.toString();
        }
    }

    public void addRoute(Route route) {
        if (routes == null) {
            routes = new ArrayList<>();
        }
        if (route == null) {
            return;
        }
        routes.add(route);
    }

    public void addRoute(String path, String component) {
        if (routes == null) {
            routes = new ArrayList<>();
        }
        routes.add(new Route(path, component));
    }

    public List<Route> getRoutes() {
        return Optional.ofNullable(routes).orElse(Collections.emptyList());
    }

    public void setRoutes(List<Route> routes) {
        this.routes = routes;
    }
}
