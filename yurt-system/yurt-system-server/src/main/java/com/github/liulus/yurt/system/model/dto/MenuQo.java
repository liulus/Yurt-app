package com.github.liulus.yurt.system.model.dto;

import com.github.liulus.yurt.convention.data.PageQuery;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * User : liulu
 * Date : 2018/1/15 10:41
 * version $Id: MenuQo.java, v 0.1 Exp $
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MenuQo extends PageQuery {

    private static final long serialVersionUID = 1204535134806916841L;

    private Long id;

    private Long parentId = 0L;

    private String menuCode;

    private String keyword;


}
