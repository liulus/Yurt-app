package com.github.liulus.yurt.system.model.dto;

import com.github.liulus.yurt.convention.data.PageQuery;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * User : liulu
 * Date : 2017/7/13 19:49
 * version $Id: MenuDTO.java, v 0.1 Exp $
 */
public interface MenuDTO {

    @Getter
    @Setter
    @ToString
    class Query extends PageQuery {
        private Boolean enabled;
    }

    @Getter
    @Setter
    @ToString
    class Base {
        private String code;
        private String name;
        private String icon;
        private String url;
        private Integer orderNum;
        private String type;
        private String remark;
    }


    @Getter
    @Setter
    @ToString
    class Detail extends Base {

        private Long id;
        private Long parentId;
        private Boolean enabled;
        private Boolean isParent;
        private List<Detail> children;

    }

    @Getter
    @Setter
    @ToString
    class Add extends Base {
        private Long parentId;
    }

    @Getter
    @Setter
    @ToString
    class Update extends Base {
        private Long id;
    }

    @Getter
    @Setter
    @ToString
    class Tree {
        private Long id;
        private Long parentId;
        private String name;
        private List<Tree> children;

        private Boolean getIsParent() {
            return !CollectionUtils.isEmpty(children);
        }
    }


}
