package com.github.liulus.yurt.system.model.dto;

import com.github.liulus.yurt.convention.data.PageQuery;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * User : liulu
 * Date : 2018/1/16 15:17
 * version $Id: UserQo.java, v 0.1 Exp $
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserQo extends PageQuery {

    private static final long serialVersionUID = 3920536469407790559L;

    private Long id;
    
    private String keyword;

    private String username;

    private String orgId;

    private String orgCode;

    private String orgName;

    private String serialNum;

}
