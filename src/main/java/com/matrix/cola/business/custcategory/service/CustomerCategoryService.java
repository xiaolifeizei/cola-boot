package com.matrix.cola.business.custcategory.service;

import com.matrix.cola.business.custcategory.entity.CustomerCategoryEntity;
import com.matrix.cola.common.Result;
import com.matrix.cola.common.entity.Query;
import com.matrix.cola.common.service.BaseColaEntityService;

/**
 * 客户分类实体类服务接口
 *
 * @author : cui_feng
 * @since : 2022-06-28 10:49
 */
public interface CustomerCategoryService extends BaseColaEntityService<CustomerCategoryEntity> {

    /**
     * 获取客户分类树
     * @param query 查询条件
     * @return 结果
     */
    Result getCategoryTree(Query<CustomerCategoryEntity> query);
}
