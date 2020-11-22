package com.github.liulus.yurt.system.service.impl;

import com.github.liulus.yurt.convention.bean.BeanUtils;
import com.github.liulus.yurt.system.model.dto.CorporationVo;
import com.github.liulus.yurt.system.model.entity.Organization;
import com.github.liulus.yurt.system.repository.OrgRepository;
import com.github.liulus.yurt.system.service.CorporationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author liulu
 * @version v1.0
 * date 2019-05-02
 */
@Service
@Transactional
public class CorporationServiceImpl implements CorporationService {

    @Resource
    private OrgRepository orgRepository;

    @Override
    public Long saveCorporation(CorporationVo.Info corporationInfo) {
        Organization rootOrg = orgRepository.selectRootOrg();
        // 没有初始化过, 新增
        if (rootOrg == null) {
            rootOrg = BeanUtils.convert(corporationInfo, new Organization());
            rootOrg.setParentId(0L);
//            rootOrg.setLevelIndex(UserUtils.nextLevelIndex("", Collections.emptyList()));

            orgRepository.insert(rootOrg);
            return rootOrg.getId();
        }

        Organization upOrg = BeanUtils.convert(Organization.class, corporationInfo);
        upOrg.setId(rootOrg.getId());
        upOrg.setParentId(0L);
        upOrg.setLevelIndex(null);
        orgRepository.updateIgnoreNull(upOrg);

        return rootOrg.getId();
    }

    @Override
    public CorporationVo.Info getCorporationInfo() {
        Organization rootOrg = orgRepository.selectRootOrg();
        if (rootOrg == null) {
            return null;
        }
        return BeanUtils.convert(CorporationVo.Info.class, rootOrg);
    }
}
