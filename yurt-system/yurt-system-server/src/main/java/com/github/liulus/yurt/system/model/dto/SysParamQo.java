package com.github.liulus.yurt.system.model.dto;

import com.github.liulus.yurt.convention.data.PageQuery;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * User : liulu
 * Date : 17-9-17 下午3:03
 * version $Id: ParamVo.java, v 0.1 Exp $
 */
@Getter
@Setter
@ToString
public class SysParamQo extends PageQuery {

    private static final long serialVersionUID = -117622963182937153L;

    private String keyword;

    private String code;



}
