package com.matrix.cola.business.goodscategory.service;

import com.matrix.cola.business.goodscategory.entity.GoodsCategoryEntity;
import com.matrix.cola.common.Result;
import com.matrix.cola.common.entity.Query;
import com.matrix.cola.common.service.BaseColaEntityService;

/**
 * 物资分类服务接口
 *
 * @author : cui_feng
 * @since : 2022-06-24 10:05
 */
public interface GoodsCategoryService extends BaseColaEntityService<GoodsCategoryEntity> {

    /**
     * 查询物资分类树
     * @param query 查询条件
     * @return 树
     */
    Result getCategoryTree(Query<GoodsCategoryEntity> query);
}
