package com.matrix.cola.business.custcategory.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.matrix.cola.business.custcategory.entity.CustomerCategoryDetailEntity;
import com.matrix.cola.business.custcategory.entity.CustomerCategoryEntity;
import com.matrix.cola.business.custcategory.entity.CustomerCategoryTree;
import com.matrix.cola.business.custcategory.mapper.CustomerCategoryMapper;
import com.matrix.cola.business.custcategory.service.CustomerCategoryDetailService;
import com.matrix.cola.business.custcategory.service.CustomerCategoryService;
import com.matrix.cola.common.Result;
import com.matrix.cola.common.entity.Query;
import com.matrix.cola.common.service.AbstractColaEntityService;
import com.matrix.cola.common.service.ColaCacheName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * 客户分类服务实现类
 *
 * @author : cui_feng
 * @since : 2022-06-28 10:56
 */
@Service
public class CustomerCategoryServiceImpl extends AbstractColaEntityService<CustomerCategoryEntity, CustomerCategoryMapper> implements CustomerCategoryService {

    @Autowired
    @Lazy
    CustomerCategoryDetailService customerCategoryDetailService;

    @Override
    public Result getCategoryTree(Query<CustomerCategoryEntity> query) {
        return Result.list(CustomerCategoryTree.getCategoryTree(getList(query)));
    }

    @Override
    protected Result validate(CustomerCategoryEntity po) {

        if (StrUtil.isEmpty(po.getName())) {
            return Result.err("操作失败，客户分类名称不能为空");
        }

        if (StrUtil.isEmpty(po.getCode())) {
            return Result.err("操作失败，客户分类编码不能为空");
        }

        if (ObjectUtil.isNull(po.getParentId())) {
            return Result.err("操作失败，父节点不能为空");
        }

        LambdaQueryWrapper<CustomerCategoryEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(CustomerCategoryEntity::getName,po.getName());
        CustomerCategoryEntity customerCategoryPO = getOne(queryWrapper);
        if (ObjectUtil.isNotNull(customerCategoryPO) && !Objects.equals(po.getId(), customerCategoryPO.getId())) {
            return Result.err("操作失败，客户分类名称已存在");
        }

        queryWrapper.clear();
        queryWrapper.eq(CustomerCategoryEntity::getCode,po.getCode());
        customerCategoryPO = getOne(queryWrapper);
        if (ObjectUtil.isNotNull(customerCategoryPO) && !Objects.equals(po.getId(), customerCategoryPO.getId())) {
            return Result.err("操作失败，客户分类编码已存在");
        }

        if (!Objects.equals(0L,po.getParentId())) {
            queryWrapper.clear();
            if (ObjectUtil.isNull(getOne(po.getParentId()))) {
                return Result.err("操作失败，父节点不存在或已被删除");
            }
        }

        return super.validate(po);
    }

    @Override
    protected Result afterUpdate(CustomerCategoryEntity po) {
        cacheProxy.evict(ColaCacheName.CUSTOMER_CATEGORY,po.getId().toString());
        return super.afterUpdate(po);
    }

    @Override
    protected Result beforeDelete(CustomerCategoryEntity entity) {
        if (ObjectUtil.isNull(entity) || ObjectUtil.isNull(entity.getId())) {
            return Result.err("删除失败，id不能为空");
        }
        LambdaQueryWrapper<CustomerCategoryEntity> categoryWrapper = new LambdaQueryWrapper<>();
        categoryWrapper.eq(CustomerCategoryEntity::getParentId, entity.getId());
        if (ObjectUtil.isNotEmpty(getList(categoryWrapper))) {
            return Result.err("删除失败，该分类含有子分类，不能删除");
        }

        LambdaQueryWrapper<CustomerCategoryDetailEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(CustomerCategoryDetailEntity::getCategoryId,entity.getId());
        if (ObjectUtil.isNotEmpty(customerCategoryDetailService.getList(queryWrapper))) {
            return Result.err("删除失败，该分类下还有客户，不能删除");
        }
        cacheProxy.evict(ColaCacheName.CUSTOMER_CATEGORY,entity.getId().toString());
        return super.beforeDelete(entity);
    }
}
