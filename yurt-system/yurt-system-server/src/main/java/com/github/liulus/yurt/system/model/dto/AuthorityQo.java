package com.github.liulus.yurt.system.model.dto;

import com.github.liulus.yurt.convention.data.PageQuery;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * User : liulu
 * Date : 2018/1/15 17:34
 * version $Id: AuthorityQo.java, v 0.1 Exp $
 */
@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class AuthorityQo extends PageQuery {

    private static final long serialVersionUID = 8174220538849445482L;

    private String authorityType;

    private List<Long> roleIds;
}
