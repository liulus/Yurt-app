package com.github.liulus.yurt.system.model

import java.util.ArrayList
import java.util.Optional

/**
 * @author Liulu
 * @version v1.0
 * date 2019-05-15
 */
class Routes {
    var routes: MutableList<Route> = ArrayList()

    class Route {
        var name: String? = null
        var path: String? = null
        var component: String? = null
        var redirect: String? = null
        var meta: Map<String, Any>? = null
        var children: List<String>? = null

        constructor()
        constructor(path: String?, component: String?) {
            this.path = path
            this.component = component
        }

        override fun toString(): String {
            val sb = StringBuilder("{")
            Optional.ofNullable(name).ifPresent { s -> sb.append(", name:'").append(s).append("'") }
            Optional.ofNullable(path).ifPresent { s -> sb.append(", path:'").append(s).append("'") }
            Optional.ofNullable(component).ifPresent { s -> sb.append(", component:_import('").append(s).append("')") }
            Optional.ofNullable(redirect).ifPresent { s -> sb.append(", redirect:'").append(s).append("'") }
            sb.append(" }")
            val index = sb.indexOf(",")
            if (index > 0) {
                sb.deleteCharAt(index)
            }
            return sb.toString()
        }
    }

    fun addRoute(route: Route?) {
        if (route == null) {
            return
        }
        routes.add(route)
    }

    fun addRoute(path: String?, component: String?) {
        routes.add(Route(path, component))
    }
}