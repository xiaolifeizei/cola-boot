package com.matrix.cola.business.customer.service.impl;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.pinyin.PinyinUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.matrix.cola.business.custcategory.entity.CustomerCategoryDetailEntity;
import com.matrix.cola.business.custcategory.entity.CustomerCategoryEntity;
import com.matrix.cola.business.custcategory.service.CustomerCategoryDetailService;
import com.matrix.cola.business.custcategory.service.CustomerCategoryService;
import com.matrix.cola.business.customer.entity.CustomerEntity;
import com.matrix.cola.business.customer.mapper.CustomerMapper;
import com.matrix.cola.business.customer.service.CustomerService;
import com.matrix.cola.common.Result;
import com.matrix.cola.common.entity.Query;
import com.matrix.cola.common.service.AbstractColaEntityService;
import com.matrix.cola.common.service.ColaCacheName;
import com.matrix.cola.common.utils.QueryUtil;
import com.matrix.cola.system.datalog.service.DataLogService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * 客户服务接口实现类
 *
 * @author : cui_feng
 * @since : 2022-04-07 11:48
 */
@Service
@AllArgsConstructor
public class CustomerServiceImpl extends AbstractColaEntityService<CustomerEntity, CustomerMapper> implements CustomerService {

    private DataLogService dataLogService;

    CustomerCategoryDetailService customerCategoryDetailService;

    CustomerCategoryService customerCategoryService;

    @Override
    public Result getCategoryCustomerPage(Query<CustomerEntity> query) {
        Page<CustomerEntity> page = new Page<>();
        if(ObjectUtil.isNotNull(query)) {
            page.setSize(query.getPageSize());
            page.setCurrent(query.getCurrentPage());
        }
        QueryUtil<CustomerEntity> queryUtil = new QueryUtil<>(query);
        IPage<CustomerEntity> queryPage =  getMapper().getCustomerList(page, queryUtil.getWrapper().eq("c.deleted",0).orderByAsc("id"));
        if (ObjectUtil.isNotNull(queryPage)) {
            this.afterQuery(queryPage.getRecords());
        }
        return Result.page(queryPage);
    }

    @Override
    public void afterQuery(List<CustomerEntity> poList) {
        if (ObjectUtil.isNotEmpty(poList)) {
            poList.forEach(customerPO -> {
                String categoryName = cacheProxy.getObjectFromLoader(ColaCacheName.CUSTOMER_CATEGORY_NAME, customerPO.getId().toString(), () -> {
                    Result result = customerCategoryDetailService.getCategoryByCustomerId(customerPO.getId());
                    if (ObjectUtil.isNotNull(result) && ObjectUtil.isNotEmpty(result.getList())) {
                        StringBuilder catName = new StringBuilder();
                        result.getList().forEach(category -> {
                            String categoryId = ((CustomerCategoryDetailEntity) category).getCategoryId().toString();
                            CustomerCategoryEntity customerCategoryPO = cacheProxy.getObjectFromLoader(ColaCacheName.CUSTOMER_CATEGORY,categoryId,()->{
                                return customerCategoryService.getOne(Long.valueOf(categoryId));
                            });
                            if (ObjectUtil.isNotNull(customerCategoryPO)) {
                                catName.append(customerCategoryPO.getName()).append(",");
                            }
                        });
                        if (StrUtil.isNotEmpty(catName)) {
                            return catName.substring(0,catName.length()-1);
                        }
                    }
                    return null;
                });
                if (StrUtil.isNotEmpty(categoryName)) {
                    customerPO.setCategoryName(categoryName);
                }
            });
        }
    }

    @Override
    protected Result validate(CustomerEntity po) {
        if (StrUtil.isEmpty(po.getName())) {
            return Result.err("操作失败，客户名称不能为空");
        }

        if (StrUtil.isNotEmpty(po.getName())) {
            LambdaQueryWrapper<CustomerEntity> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(CustomerEntity::getName, po.getName().trim());
            CustomerEntity customerPO = getOne(queryWrapper);
            if (ObjectUtil.isNotNull(customerPO) && !Objects.equals(po.getId(),customerPO.getId())) {
                return Result.err("操作失败，客户名称已经存在");
            }

            // 修改和添加统一设置拼音码
            po.setSpellCode(PinyinUtil.getFirstLetter(po.getName(),""));
        }

        return super.validate(po);
    }

    @Override
    protected Result afterAdd(CustomerEntity po) {

        if (ObjectUtil.isEmpty(po.getCategoryId())) {
            return Result.err("添加失败，客户分类不能为空");
        }

        String [] categoryIds = ArrayUtil.distinct(po.getCategoryId().split(","));
        Result result = addCategoryDetail(categoryIds, po.getId());
        if (!result.isSuccess()) {
            rollback();
            return result;
        }
        return Result.ok();
    }

    @Override
    protected Result beforeUpdate(CustomerEntity po) {
        CustomerEntity before = super.getOne(po.getId());

        if (StrUtil.isNotEmpty(po.getCategoryId())) {
            String [] categoryIds = ArrayUtil.distinct(po.getCategoryId().split(","));
            Result result = customerCategoryDetailService.deleteCategoryCustomerDetailByCustomerId(po.getId());
            if (!result.isSuccess()) {
                return result;
            }
            result = addCategoryDetail(categoryIds, po.getId());
            if (!result.isSuccess()) {
                rollback();
                return result;
            }
        }

        dataLogService.addUpdateLog("客户管理",before,po);
        cacheProxy.evict(ColaCacheName.CUSTOMER,po.getId().toString());
        cacheProxy.evict(ColaCacheName.CUSTOMER_CATEGORY_NAME, po.getId().toString());
        return Result.ok();
    }

    @Override
    protected Result beforeDelete(CustomerEntity entity) {
        if (ObjectUtil.isNull(entity) || ObjectUtil.isNull(entity.getId())) {
            return Result.err("删除失败，id不能为空");
        }
        CustomerEntity po = super.getOne(entity);
        dataLogService.addDeleteLog("客户管理",po);
        if (!customerCategoryDetailService.deleteCategoryCustomerDetailByCustomerId(entity.getId()).isSuccess()) {
            return Result.err("删除失败，分类与客户关联关系删除失败");
        }
        cacheProxy.evict(ColaCacheName.CUSTOMER,po.getId().toString());
        cacheProxy.evict(ColaCacheName.CUSTOMER_CATEGORY_NAME, po.getId().toString());
        return Result.ok();
    }

    private Result addCategoryDetail(String [] categoryIds, Long customerId) {
        for (String categoryId : categoryIds) {
            if (!NumberUtil.isLong(categoryId)) {
                continue;
            }
            CustomerCategoryDetailEntity customerCategoryDetailPO = new CustomerCategoryDetailEntity();
            customerCategoryDetailPO.setCustomerId(customerId);
            customerCategoryDetailPO.setCategoryId(Long.valueOf(categoryId));
            Result result = customerCategoryDetailService.insert(customerCategoryDetailPO);
            if (!result.isSuccess()) {
                return result;
            }
        }
        return Result.ok();
    }
}
