package com.matrix.cola.business.goods.service;

import com.matrix.cola.business.goods.entity.GoodsEntity;
import com.matrix.cola.common.Result;
import com.matrix.cola.common.entity.Query;
import com.matrix.cola.common.service.BaseColaEntityService;

/**
 * 物资实体服务接口类
 *
 * @author cui_feng
 * @since : 2022-04-20 14:18
 */
public interface GoodsService extends BaseColaEntityService<GoodsEntity> {

    /**
     * 通过物资分类查询物资分页数据
     * @param query 查询条件
     * @return 物资分页列表
     */
    Result getCategoryGoodsPage(Query<GoodsEntity> query);
}
