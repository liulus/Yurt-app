package com.github.liulus.yurt.system.service.impl;

import com.github.liulus.yurt.convention.data.Page;
import com.github.liulus.yurt.convention.exception.ServiceException;
import com.github.liulus.yurt.system.model.dto.SysParamQo;
import com.github.liulus.yurt.system.model.entity.SysParam;
import com.github.liulus.yurt.system.repository.ParamRepository;
import com.github.liulus.yurt.system.service.ParamService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * User : liulu
 * Date : 17-9-17 下午2:53
 * version $Id: ParamServiceImpl.java, v 0.1 Exp $
 */
@Service
@Transactional
public class ParamServiceImpl implements ParamService {

    @Resource
    private ParamRepository paramRepository;

    @Override
    public Page<SysParam> findPageList(SysParamQo qo) {
//        return paramRepository.selectPageList(qo);
        return null;
    }

    @Override
    public SysParam findById(Long id) {
        return paramRepository.selectById(id);
    }

    @Override
    public SysParam findByCode(String code) {
        return paramRepository.selectByCode(code);
    }

    @Override
    public Long insert(SysParam param) {
        checkCode(param.getCode());
        param.setSystem(false);

        paramRepository.insert(param);
        return param.getId();
    }

    @Override
    public void update(SysParam param) {
        SysParam oldParam = findById(param.getId());
        if (oldParam == null) {
            return;
        }
        if (!Objects.equals(param.getCode(), oldParam.getCode())) {
            if (oldParam.getSystem()) {
                throw new ServiceException(String.format("%s 是系统级参数, 不允许修改", oldParam.getCode()));
            }
            checkCode(param.getCode());
        }
        param.setSystem(null);
        paramRepository.updateIgnoreNull(param);
    }

    private void checkCode(String code) {
        SysParam param = findByCode(code);
        if (param != null) {
            throw new ServiceException("参数 code 已经存在");
        }
    }

    @Override
    public void delete(Long... ids) {
        if (ids == null || ids.length == 0) {
            return;
        }

        List<SysParam> params = paramRepository.selectByIds(Arrays.asList(ids));

        List<Long> validIds = new ArrayList<>(params.size());

        for (SysParam param : params) {
            if (param.getSystem()) {
                throw new ServiceException(String.format("%s 是系统级参数, 不允许删除", param.getCode()));
            }
            validIds.add(param.getId());
        }
//        paramRepository.deleteByIds(validIds);
    }
}
