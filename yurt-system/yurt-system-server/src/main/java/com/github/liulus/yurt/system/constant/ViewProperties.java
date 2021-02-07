package com.github.liulus.yurt.system.constant;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author liulu
 * @version V1.0
 * @since 2020/11/24
 */
@Data
@ConfigurationProperties(prefix = "yurt.view")
public class ViewProperties {

    private String cdnHost;

    private String ui;

}
