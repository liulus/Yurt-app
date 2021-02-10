package com.github.liulus.yurt.system.service

import com.github.liulus.yurt.system.model.dto.CorporationVo

/**
 * @author liulu
 * @version v1.0
 * date 2019-05-02
 */
interface CorporationService {
    /**
     * 保存化企业信息
     *
     * @param corporationInfo corporationInfo
     * @return id
     */
    fun saveCorporation(corporationInfo: CorporationVo.Info?): Long?

    /**
     * 获取企业信息
     *
     * @return CorporationVo.Info
     */
    val corporationInfo: CorporationVo.Info?
}