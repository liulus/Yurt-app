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
 * Date : 17-10-3 下午4:22
 * version $Id: Organization.java, v 0.1 Exp $
 */
@Getter
@Setter
@ToString
@Table(name = SystemConst.TABLE_PREFIX + "organization")
public class Organization implements Serializable{

    private static final long serialVersionUID = -713093592928469785L;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** 父机构 Id */
    private Long parentId;

    /** 机构号 */
    private String code;

    /** 机构名 */
    private String fullName;

    /** 机构简称 */
    private String shortName;

    /** 机构类型 */
    private String type;

    /**
     * 特殊序号, 用于查询
     * 一个层级 3 位数字, 从 001 开始, 子机构: 父机构 serialNum + 3位数字
     * 例: 010 -> 层级为 1 的第 10 个机构, 没有父机构
     * 例: 007003 -> 层级为 2 且父机构 levelIndex 为 007 的第 3 个机构
     */
    private String levelIndex;

    /** 地址 */
    private String address;

    /** 备注 */
    private String remark;

}
