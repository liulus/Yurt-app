package com.github.liulus.yurt.system.model.entity;

import com.github.liulus.yurt.system.constant.SystemConst;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * User : liulu
 * Date : 2017/7/10 10:06
 * version $Id: Menu.java, v 0.1 Exp $
 */
@Getter
@Setter
@ToString
@Table(name = SystemConst.TABLE_PREFIX + "menu")
public class Menu implements Serializable {

    private static final long serialVersionUID = -6077674673399919945L;

    /**
     * 菜单Id
     */
    @Id
    private Long id;

    /**
     * 父菜单Id
     */
    private Long parentId;

    /**
     * 菜单编码
     */
    private String code;

    /**
     * 菜单名称
     */
    private String name;

    /**
     * 菜单图标
     */
    private String icon;

    /**
     * 菜单url
     */
    private String url;

    /**
     * 顺序号
     */
    private Integer orderNum;

    /**
     * 备注
     */
    private String remark;

    /**
     * 菜单类型
     */
    private String type;

    /**
     * 是否启用
     */
    @Column(name = "is_enabled")
    private Boolean enabled;

    /**
     * 权限码
     */
    private String authCode;

}
