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
 * Date : 17-9-17 下午2:39
 * version $Id: Param.java, v 0.1 Exp $
 */
@Getter
@Setter
@ToString
@Table(name = SystemConst.TABLE_PREFIX + "param")
public class SysParam implements Serializable {

    private static final long serialVersionUID = -958178685990081472L;

    /**
     * 参数 Id
     */
    @Id
    private Long id;

    /**
     * 参数编码
     */
    private String code;

    /**
     * 参数值
     */
    private String value;

    /**
     * 备注
     */
    private String remark;

    /**
     * 是否系统级
     */
    @Column(name = "is_system")
    private Boolean system;

}
