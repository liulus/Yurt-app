package com.github.liulus.yurt.system.service;

import com.github.liulus.yurt.system.model.dto.CorporationVo;

/**
 * @author liulu
 * @version v1.0
 * date 2019-05-02
 */
public interface CorporationService {
    /**
     * 保存化企业信息
     *
     * @param corporationInfo corporationInfo
     * @return id
     */
    Long saveCorporation(CorporationVo.Info corporationInfo);

    /**
     * 获取企业信息
     *
     * @return CorporationVo.Info
     */
    CorporationVo.Info getCorporationInfo();

}
