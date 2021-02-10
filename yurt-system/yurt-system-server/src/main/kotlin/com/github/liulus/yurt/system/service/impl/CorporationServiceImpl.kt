package com.github.liulus.yurt.system.service.impl

import com.github.liulus.yurt.convention.bean.BeanUtils
import com.github.liulus.yurt.system.model.dto.CorporationVo
import com.github.liulus.yurt.system.model.entity.Organization
import com.github.liulus.yurt.system.repository.OrgRepository
import com.github.liulus.yurt.system.service.CorporationService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import javax.annotation.Resource

/**
 * @author liulu
 * @version v1.0
 * date 2019-05-02
 */
@Service
@Transactional
open class CorporationServiceImpl : CorporationService {
    @Resource
    private val orgRepository: OrgRepository? = null
    override fun saveCorporation(corporationInfo: CorporationVo.Info?): Long? {
        var rootOrg = orgRepository!!.selectRootOrg()
        // 没有初始化过, 新增
        if (rootOrg == null) {
            rootOrg = BeanUtils.convert(corporationInfo, Organization())
            rootOrg.parentId = 0L
            //            rootOrg.setLevelIndex(UserUtils.nextLevelIndex("", Collections.emptyList()));
            orgRepository.insert(rootOrg)
            return rootOrg.id
        }
        val upOrg = BeanUtils.convert(
            Organization::class.java, corporationInfo
        )
        upOrg.id = rootOrg.id
        upOrg.parentId = 0L
        upOrg.levelIndex = null
        orgRepository.updateIgnoreNull(upOrg)
        return rootOrg.id
    }

    override val corporationInfo: CorporationVo.Info?
        get() {
            val rootOrg = orgRepository!!.selectRootOrg() ?: return null
            return BeanUtils.convert(CorporationVo.Info::class.java, rootOrg)
        }
}