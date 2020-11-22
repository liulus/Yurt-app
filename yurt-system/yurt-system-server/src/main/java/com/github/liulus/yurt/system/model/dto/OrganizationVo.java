package com.github.liulus.yurt.system.model.dto;

import lombok.Data;

import java.util.List;

/**
 * User : liulu
 * Date : 2018/4/11 20:34
 * version $Id: OrganizationVo.java, v 0.1 Exp $
 */
@Data
public abstract class OrganizationVo {

    private String code;

    private String fullName;

    private String shortName;

    private String type;

    private String address;

    private String remark;

    @Data
    public static class ListRes extends OrganizationVo {
        private Long id;
    }

    @Data
    public static class Add extends OrganizationVo {
        private Long parentId;
    }

    @Data
    public static class Update extends OrganizationVo {
        private Long id;
    }

    @Data
    public static class Detail extends OrganizationVo {
        private Long id;
        private Long parentId;
        private List<Detail> children;
    }


}
