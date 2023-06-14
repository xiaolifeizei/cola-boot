package com.matrix.cola.business.goodscategory.service;

import com.matrix.cola.business.goodscategory.entity.GoodsCategoryDetailEntity;
import com.matrix.cola.common.Result;
import com.matrix.cola.common.service.BaseEntityService;

/**
 * 物资分类与物资关联关系服务接口
 *
 * @author : cui_feng
 * @since : 2022-06-24 10:06
 */
public interface GoodsCategoryDetailService extends BaseEntityService<GoodsCategoryDetailEntity> {

    /**
     * 通过物资Id查询物资分类
     * @param goodsId 物资Id
     * @return 物资分类
     */
    Result getCategoryByGoodsId(Long goodsId);

    /**
     * 通过物资Id 物理删除物资分类关联关系
     * @param goodsId 物资Id
     * @return 结果
     */
    Result deleteCategoryGoodsDetailByGoodsId(Long goodsId);
}
