package com.github.liulus.yurt.system.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author Liulu
 * @version v1.0
 * date 2019-05-15
 */
@Data
@NoArgsConstructor
public class Route implements Serializable {

    private static final long serialVersionUID = 3223158902606463776L;

    private String name;
    private String path;
    private String component;
    private Route redirect;
    private Map<String, Object> meta;
    private List<String> children;

    public Route(String path, String component) {
        this.path = path;
        this.component = component;
    }

    public Route(String name, String path, String component) {
        this.name = name;
        this.path = path;
        this.component = component;
    }


}
