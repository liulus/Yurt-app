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
 * Date : 2017/11/19 16:28
 * version $Id: UserRole.java, v 0.1 Exp $
 */
@Getter
@Setter
@ToString
@Table(name = SystemConst.TABLE_PREFIX + "user_role")
public class UserRole implements Serializable {

    private static final long serialVersionUID = -8814219062642647147L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 用户Id
     */
    private Long userId;

    /**
     * 角色Id
     */
    private Long roleId;

}
