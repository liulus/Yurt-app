package com.github.liulus.yurt.system.model.dto;

import lombok.Data;

/**
 * @author liulu
 * @version v1.0
 * date 2019-05-02
 */
public abstract class CorporationVo {


    @Data
    public static class Info {
        private String code;
        private String fullName;
        private String shortName;
        private String address;
    }




}
