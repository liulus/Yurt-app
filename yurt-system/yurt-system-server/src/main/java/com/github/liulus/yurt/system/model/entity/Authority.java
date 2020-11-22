package com.github.liulus.yurt.system.model.entity;

import com.github.liulus.yurt.system.constant.SystemConst;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * User : liulu
 * Date : 2017/11/19 16:20
 * version $Id: Authority.java, v 0.1 Exp $
 */
@Table(name = SystemConst.TABLE_PREFIX + "authority")
public class Authority implements Serializable {

    private static final long serialVersionUID = 1650537028788209650L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String code;
    private String name;
    private String module;
    private String function;
    private String remark;
    private Boolean isDeleted;
    private LocalDateTime gmtCreated;
    private LocalDateTime gmtModified;
    private LocalDateTime gmtDeleted;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getFunction() {
        return function;
    }

    public void setFunction(String function) {
        this.function = function;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }

    public LocalDateTime getGmtCreated() {
        return gmtCreated;
    }

    public void setGmtCreated(LocalDateTime gmtCreated) {
        this.gmtCreated = gmtCreated;
    }

    public LocalDateTime getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(LocalDateTime gmtModified) {
        this.gmtModified = gmtModified;
    }

    public LocalDateTime getGmtDeleted() {
        return gmtDeleted;
    }

    public void setGmtDeleted(LocalDateTime gmtDeleted) {
        this.gmtDeleted = gmtDeleted;
    }

    @Override
    public String toString() {
        return "Authority{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", module='" + module + '\'' +
                '}';
    }
}
