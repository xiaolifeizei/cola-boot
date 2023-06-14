package com.matrix.cola.business.goods.service.impl;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.pinyin.PinyinUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.matrix.cola.business.goods.mapper.GoodsMapper;
import com.matrix.cola.business.goods.service.GoodsService;
import com.matrix.cola.business.goodscategory.entity.GoodsCategoryDetailEntity;
import com.matrix.cola.business.goodscategory.entity.GoodsCategoryEntity;
import com.matrix.cola.business.goodscategory.service.GoodsCategoryDetailService;
import com.matrix.cola.business.goodscategory.service.GoodsCategoryService;
import com.matrix.cola.business.goods.entity.GoodsEntity;
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
 * 物资实体服务实现类
 *
 * @author : cui_feng
 * @since : 2022-04-13 13:14
 */
@Service
@AllArgsConstructor
public class GoodsServiceImpl extends AbstractColaEntityService<GoodsEntity, GoodsMapper> implements GoodsService {

    private DataLogService dataLogService;

    GoodsCategoryDetailService goodsCategoryDetailService;

    GoodsCategoryService goodsCategoryService;

    public Result getCategoryGoodsPage(Query<GoodsEntity> query) {
        Page<GoodsEntity> page = new Page<>();
        if(ObjectUtil.isNotNull(query)) {
            page.setSize(query.getPageSize());
            page.setCurrent(query.getCurrentPage());
        }
        QueryUtil<GoodsEntity> queryUtil = new QueryUtil<>(query);
        IPage<GoodsEntity> queryPage =  getMapper().getGoodsList(page, queryUtil.getWrapper().eq("g.deleted",0).orderByAsc("id"));
        if (ObjectUtil.isNotNull(queryPage)) {
            this.afterQuery(queryPage.getRecords());
        }
        return Result.page(queryPage);
    }

    @Override
    public void afterQuery(List<GoodsEntity> poList) {
        if (ObjectUtil.isNotEmpty(poList)) {
            poList.forEach(goodsPO -> {
                String categoryName = cacheProxy.getObjectFromLoader(ColaCacheName.GOODS_CATEGORY_NAME, goodsPO.getId().toString(), () -> {
                    Result result = goodsCategoryDetailService.getCategoryByGoodsId(goodsPO.getId());
                    if (ObjectUtil.isNotNull(result) && ObjectUtil.isNotEmpty(result.getList())) {
                        StringBuilder catName = new StringBuilder();
                         result.getList().forEach(category -> {
                             String categoryId = ((GoodsCategoryDetailEntity) category).getCategoryId().toString();
                             GoodsCategoryEntity goodsCategoryPO = cacheProxy.getObjectFromLoader(ColaCacheName.GOODS_CATEGORY,categoryId,()->{
                                 return goodsCategoryService.getOne(Long.valueOf(categoryId));
                             });
                             if (ObjectUtil.isNotNull(goodsCategoryPO)) {
                                 catName.append(goodsCategoryPO.getName()).append(",");
                             }
                         });
                         if (StrUtil.isNotEmpty(catName)) {
                             return catName.substring(0,catName.length()-1);
                         }
                    }
                    return null;
                });
                if (StrUtil.isNotEmpty(categoryName)) {
                    goodsPO.setCategoryName(categoryName);
                }
            });
        }
    }

    @Override
    protected Result validate(GoodsEntity po) {
        if (StrUtil.isEmpty(po.getName())) {
            return Result.err("操作失败，物资名称不能为空");
        }

        if (StrUtil.isNotEmpty(po.getName())) {
            LambdaQueryWrapper<GoodsEntity> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(GoodsEntity::getName, po.getName().trim());
            GoodsEntity goodsPO = getOne(queryWrapper);
            if (ObjectUtil.isNotNull(goodsPO) && !Objects.equals(po.getId(),goodsPO.getId())) {
                return Result.err("操作失败，物资名称已经存在");
            }

            // 修改和添加统一设置拼音码
            po.setSpellCode(PinyinUtil.getFirstLetter(po.getName(),""));
        }

        if (StrUtil.isNotEmpty(po.getCode())) {
            LambdaQueryWrapper<GoodsEntity> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(GoodsEntity::getCode, po.getCode().trim());
            GoodsEntity goodsPO = getOne(queryWrapper);
            if (ObjectUtil.isNotNull(goodsPO) && !Objects.equals(po.getId(),goodsPO.getId())) {
                return Result.err("操作失败，物资编码已经存在");
            }
        }

        return super.validate(po);
    }

    @Override
    protected Result afterAdd(GoodsEntity po) {

        if (ObjectUtil.isEmpty(po.getCategoryId())) {
            return Result.err("添加失败，物资分类不能为空");
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
    protected Result beforeUpdate(GoodsEntity po) {
        GoodsEntity before = super.getOne(po.getId());

        if (StrUtil.isNotEmpty(po.getCategoryId())) {
            String [] categoryIds = ArrayUtil.distinct(po.getCategoryId().split(","));
            Result result = goodsCategoryDetailService.deleteCategoryGoodsDetailByGoodsId(po.getId());
            if (!result.isSuccess()) {
                return result;
            }
            result = addCategoryDetail(categoryIds, po.getId());
            if (!result.isSuccess()) {
                rollback();
                return result;
            }
        }

        dataLogService.addUpdateLog("物资管理",before,po);
        cacheProxy.evict(ColaCacheName.GOODS,po.getId().toString());
        cacheProxy.evict(ColaCacheName.GOODS_CATEGORY_NAME, po.getId().toString());
        return Result.ok();
    }

    @Override
    protected Result beforeDelete(GoodsEntity entity) {
        if (ObjectUtil.isNull(entity) || ObjectUtil.isNull(entity.getId())) {
            return Result.err("删除失败，id 不能为空");
        }
        GoodsEntity goodsEntity = super.getOne(entity.getId());
        dataLogService.addDeleteLog("物资管理",goodsEntity);
        if (!goodsCategoryDetailService.deleteCategoryGoodsDetailByGoodsId(entity.getId()).isSuccess()) {
            return Result.err("删除失败，分类与物资关联关系删除失败");
        }
        cacheProxy.evict(ColaCacheName.GOODS,goodsEntity.getId().toString());
        cacheProxy.evict(ColaCacheName.GOODS_CATEGORY_NAME, goodsEntity.getId().toString());
        return Result.ok();
    }

    private Result addCategoryDetail(String [] categoryIds, Long goodsId) {
        for (String categoryId : categoryIds) {
            if (!NumberUtil.isLong(categoryId)) {
                continue;
            }
            GoodsCategoryDetailEntity goodsCategoryDetailPO = new GoodsCategoryDetailEntity();
            goodsCategoryDetailPO.setGoodsId(goodsId);
            goodsCategoryDetailPO.setCategoryId(Long.valueOf(categoryId));
            Result result = goodsCategoryDetailService.insert(goodsCategoryDetailPO);
            if (!result.isSuccess()) {
                return result;
            }
        }
        return Result.ok();
    }
}
