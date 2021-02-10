package com.github.liulus.yurt.system.context

import org.springframework.boot.context.properties.ConfigurationProperties

/**
 * @author liulu
 * @version V1.0
 * @since 2020/11/24
 */
@ConfigurationProperties(prefix = "yurt.view")
data class ViewProperties(
        var cdnHost: String? = null,
        var ui: String? = null,
)