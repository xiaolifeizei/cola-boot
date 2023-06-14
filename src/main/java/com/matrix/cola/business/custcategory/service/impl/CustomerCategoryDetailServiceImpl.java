package com.matrix.cola.business.custcategory.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.matrix.cola.business.custcategory.mapper.CustomerCategoryDetailMapper;
import com.matrix.cola.business.custcategory.service.CustomerCategoryDetailService;
import com.matrix.cola.business.custcategory.service.CustomerCategoryService;
import com.matrix.cola.business.custcategory.entity.CustomerCategoryDetailEntity;
import com.matrix.cola.common.Result;
import com.matrix.cola.common.service.AbstractEntityService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;

/**
 * 客户分类关联关系服务类
 *
 * @author : cui_feng
 * @since : 2022-06-28 11:00
 */
@Service
@AllArgsConstructor
public class CustomerCategoryDetailServiceImpl extends AbstractEntityService<CustomerCategoryDetailEntity, CustomerCategoryDetailMapper> implements CustomerCategoryDetailService {

    CustomerCategoryService customerCategoryService;

    @Override
    protected Result beforeAdd(CustomerCategoryDetailEntity po) {
        if (ObjectUtil.isNull(po.getCustomerId())) {
            return Result.err("添加失败，客户不能为空");
        }
        if (ObjectUtil.isNull(po.getCategoryId())) {
            return Result.err("添加失败，物资分类不能为空");
        }
        if (ObjectUtil.isNull(customerCategoryService.getOne(po.getCategoryId()))) {
            return Result.err("添加失败，物资分类不存在");
        }

        LambdaQueryWrapper<CustomerCategoryDetailEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(CustomerCategoryDetailEntity::getCategoryId,po.getCategoryId())
                .eq(CustomerCategoryDetailEntity::getCustomerId, po.getCustomerId());
        if (ObjectUtil.isNotNull(getOne(queryWrapper))) {
            return Result.err("添加失败，添加的数据已经存在");
        }

        return super.beforeAdd(po);
    }

    @Override
    public Result getCategoryByCustomerId(Long customerId) {
        if (ObjectUtil.isNull(customerId)) {
            return Result.list(Collections.emptyList());
        }
        LambdaQueryWrapper<CustomerCategoryDetailEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(CustomerCategoryDetailEntity::getCustomerId,customerId);
        return Result.list(getList(queryWrapper));
    }

    @Override
    public Result deleteCategoryCustomerDetailByCustomerId(Long goodsId) {
        if (getMapper().deleteCategoryDetailByCustomerId(goodsId) > 0) {
            return Result.ok();
        }
        return Result.err("删除客户分类关联关系失败");
    }
}
