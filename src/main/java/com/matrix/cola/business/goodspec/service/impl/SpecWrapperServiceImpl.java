package com.matrix.cola.business.goodspec.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.matrix.cola.business.goods.entity.GoodsEntity;
import com.matrix.cola.business.goods.service.GoodsService;
import com.matrix.cola.business.goodspec.entity.SpecEntity;
import com.matrix.cola.business.goodspec.entity.SpecEntityWrapper;
import com.matrix.cola.business.goodspec.service.SpecService;
import com.matrix.cola.business.goodspec.service.SpecWrapperService;
import com.matrix.cola.common.service.AbstractColaEntityWrapperService;
import com.matrix.cola.common.service.ColaCacheName;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 规格包装类接口实现类
 *
 * @author : cui_feng
 * @since : 2022-06-21 12:21
 */
@Service
@AllArgsConstructor
public class SpecWrapperServiceImpl extends AbstractColaEntityWrapperService<SpecEntity, SpecEntityWrapper, SpecService> implements SpecWrapperService {

    GoodsService goodsService;

    @Override
    public SpecEntityWrapper entityWrapper(SpecEntity entity) {
        SpecEntityWrapper specEntityWrapper = new SpecEntityWrapper();
        if (ObjectUtil.isNotNull(entity.getGoodsId())) {
            GoodsEntity goodsPO = cacheProxy.getObjectFromLoader(ColaCacheName.GOODS,entity.getGoodsId().toString(), () -> {
                return goodsService.getOne(entity.getGoodsId());
            });
            if (ObjectUtil.isNotNull(goodsPO)) {
                specEntityWrapper.setGoodsName(goodsPO.getName());
            }
        }
        return specEntityWrapper;
    }
}
