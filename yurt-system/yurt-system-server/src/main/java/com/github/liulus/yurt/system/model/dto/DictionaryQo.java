package com.github.liulus.yurt.system.model.dto;

import com.github.liulus.yurt.convention.data.PageQuery;
import lombok.Data;

/**
 * User : liulu
 * Date : 2017/4/8 20:52
 * version $Id: DictionaryVo.java, v 0.1 Exp $
 */
@Data
public class DictionaryQo extends PageQuery {

    private static final long serialVersionUID = -2144418218430870185L;

    private String keyword;

    private Long parentId;

}
