package com.github.liulus.yurt.system.model.entity;

import com.github.liulus.yurt.system.constant.SystemConst;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * User : liulu
 * Date : 2017/11/19 16:24
 * version $Id: Role.java, v 0.1 Exp $
 */
@Getter
@Setter
@ToString
@Table(name = SystemConst.TABLE_PREFIX + "role")
public class Role implements Serializable {

    private static final long serialVersionUID = 5145432426032902715L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 角色编号
     */
    private String code;

    /**
     * 角色名
     */
    private String name;

    /**
     * 角色备注
     */
    private String remark;


}
